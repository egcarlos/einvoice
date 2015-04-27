/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.summary;

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
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.commons.recurrent.RecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.SummaryDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushSummaryRecurrent extends AbstractRecurrentTask<Document> {

    private static final String DOCUMENT_QUERY = "SELECT DISTINCT o.document FROM DocumentResponse o WHERE o.document.documentNumber LIKE 'RC%' AND o.replicate = TRUE";

    private static final String DOCUMENT_RESPONSE_QEURY = "SELECT o FROM DocumentResponse o WHERE o.replicate = TRUE AND o.document = :document";

    @EJB
    private SummaryDatabaseManagerLocal sum;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    /**
     * Bloquear una respuesta de documento para ser replicada
     */
    private Function<DocumentResponse, Boolean> tryLockSingle;

    /**
     * Retorna los document response pendientes de repplicar en un documento.
     */
    private Function<Document, List<DocumentResponse>> findTasksSingle;

    @Override
    @Schedule(hour = "*/1", minute = "0", second = "0", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();

        this.findTasks = () -> prv.seek(e -> e.createQuery(DOCUMENT_QUERY, Document.class).getResultList());

        this.tryLock = t -> true;

        this.getId = t -> RecurrentTask.buildTaskId(t.getClientId(), t.getDocumentType(), t.getDocumentNumber(), "replicate response");

        this.consumer = t -> sum.handle(e -> {
            Map<String, String> responses = this.findTasksSingle.apply(t).stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> mapName(r) != null)
                    .collect(Collectors.toMap(r -> mapName(r), r -> r.getValue()));

            if (responses.isEmpty()) {
                return;
            }

            SummaryHeaderPK id = createId(t);
            String setPart = responses.entrySet().stream()
                    .map(d -> "d." + d.getKey() + " = :" + d.getKey())
                    .reduce(null, (a, b) -> a == null ? b : a + ", " + b);

            logger.info(() -> this.tm("sending " + responses));

            Query query = e.createQuery(
                    "UPDATE SummaryHeader d SET " + setPart + " WHERE d.id = :id"
            );
            responses
                    .forEach((k, v) -> query.setParameter(k, v));
            query
                    .setParameter("id", id)
                    .executeUpdate();
        });

        this.findTasksSingle = t -> prv.seek(e -> e.createQuery(DOCUMENT_RESPONSE_QEURY, DocumentResponse.class).setParameter("document", t).getResultList());

        this.tryLockSingle = t -> prv.seek(e -> e
                .createNamedQuery("DocumentResponse.tryLock")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );
    }

    private SummaryHeaderPK createId(Document t) {
        SummaryHeaderPK id = new SummaryHeaderPK(
                t.getClientId().split("-")[0],
                t.getClientId().split("-")[1],
                t.getDocumentNumber()
        );
        return id;
    }

    private String mapName(DocumentResponse t) {
        //TODO cargar desde un archivo de texto...
        switch (t.getName()) {
            case "hashCode":
                return "bl_hashFirma";
            case "integratedStatus":
                return "bl_estadoProceso";
            case "recordStatus":
                return "bl_estadoRegistro";
            case "pdfFileUrl":
                return "bl_urlpdf";
            case "signatureValue":
                return "bl_firma";
            case "xmlFileSignUrl":
                return "bl_urlxmlubl";
            case "xmlFileSunatUrl":
                return "bl_urlcdr";
            case "sunatMessage":
                return "bl_mensajeSunat";
            case "messages":
                return "bl_mensaje";
        }
        return null;
    }

}
