/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.commons.model.ModelTools;
import pe.labtech.einvoice.commons.model.RecurrentHelper;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushInvoiceRecurrent extends AbstractRecurrentTask<Long> {

    @EJB
    private PublicDatabaseManagerLocal pub;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    /**
     * Bloquear una respuesta de documento para ser replicada
     */
    private Function<DocumentResponse, Boolean> tryLockSingle;

    /**
     * Retorna los document response pendientes de repplicar en un documento.
     */
    private Function<Long, List<DocumentResponse>> findTasksSingle;

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/1", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();
        this.findTasks = () -> RecurrentHelper.lookupAllResponses(prv, "F", "B");
        this.findTasksSingle = t -> RecurrentHelper.lookupResponse(prv, DocumentResponse.class, t);
        this.tryLock = t -> true;
        this.tryLockSingle = t -> RecurrentHelper.lockResponse(prv, t.getDocument().getId(), t.getName());
        this.getId = t -> RecurrentHelper.buildId(t, "replicate");
        this.consumer = t -> {
            DocumentHeaderPK id = createId(prv.seek(e -> e.find(Document.class, t)));
            Map<String, Object> responses = this.findTasksSingle.apply(t).stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> isValid(ModelTools.mapResponseName(r.getName())))
                    .collect(Collectors.toMap(r -> ModelTools.mapResponseName(r.getName()), r -> r.getValue()));
            if (responses.isEmpty()) {
                return;
            }
            if (responses.containsKey("bl_estadoRegistro")) {
                String estado = (String) responses.get("bl_estadoRegistro");//siempre es un caracter y nada más
                responses.put("bl_estadoRegistro", estado.charAt(0));
            }
            RecurrentHelper.sendResponses(pub, id, responses);
        };
    }

    private static final List<String> validNames = Arrays.asList(
            "bl_urlpdf",
            "bl_urlxmlubl",
            "bl_firma",
            "bl_hashFirma",
            "bl_mensaje",
            "bl_estadoProceso",
            "bl_mensajeSunat",
            "bl_estadoRegistro"
    );

    private boolean isValid(String name) {
        if (name == null) {
            return false;
        }
        return validNames.contains(name);
    }

    private DocumentHeaderPK createId(Document t) {
        return pub.seek(e -> e
                .createQuery(
                        "SELECT O.id FROM DocumentHeader O WHERE O.tipoDocumentoEmisor = :tde AND O.numeroDocumentoEmisor = :nde AND O.tipoDocumento = :td AND O.serieNumero = :sn",
                        DocumentHeaderPK.class
                )
                .setParameter("tde", t.getClientId().split("-")[0].charAt(0))
                .setParameter("nde", t.getClientId().split("-")[1])
                .setParameter("td", t.getDocumentType())
                .setParameter("sn", t.getDocumentNumber())
                .getSingleResult());
    }

}
