/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

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
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.model.RecurrentHelper;
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

    private static final String DOCUMENT_QUERY = "SELECT DISTINCT o.document.id FROM DocumentResponse o WHERE (o.document.documentNumber LIKE 'F%' OR o.document.documentNumber LIKE 'B%') AND o.replicate = TRUE";

    private static final String DOCUMENT_RESPONSE_QEURY = "SELECT o FROM DocumentResponse o WHERE o.replicate = TRUE AND o.document.id = :id";

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
        this.findTasks = () -> prv.seek(e -> e.createQuery(DOCUMENT_QUERY, Long.class).setMaxResults(10000).getResultList());
        this.tryLock = t -> true;
        this.getId = t -> RecurrentHelper.buildId(t, "replicate");
        this.consumer = t -> {
            Document document = prv.seek(e -> e.find(Document.class, t));
            DocumentHeaderPK id = createId(document);

            Map<String, String> responses = this.findTasksSingle.apply(t).stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> mapName(r) != null)
                    .collect(Collectors.toMap(r -> mapName(r), r -> r.getValue()));

            if (responses.isEmpty()) {
                return;
            }

            String setPart = responses.entrySet().stream()
                    .map(d -> "d." + d.getKey() + " = :" + d.getKey())
                    .reduce(null, (a, b) -> a == null ? b : a + ", " + b);

            logger.info(() -> this.tm("sending " + responses));
            
            pub.handle(e -> {
                Query q = e.createQuery(
                        "UPDATE DocumentHeader d SET " + setPart + " WHERE d.id = :id"
                );
                responses.forEach((k, v) -> q.setParameter(k, v));
                q.setParameter("id", id).executeUpdate();
            });
        };

        this.findTasksSingle = t -> prv.seek(e -> e.createQuery(DOCUMENT_RESPONSE_QEURY, DocumentResponse.class).setParameter("id", t).getResultList());

        this.tryLockSingle = t -> prv.seek(e -> e
                .createNamedQuery("DocumentResponse.tryLock")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );
    }

    private DocumentHeaderPK createId(Document t) {
        DocumentHeaderPK id = new DocumentHeaderPK(
                t.getClientId().split("-")[0],
                t.getClientId().split("-")[1],
                t.getDocumentType(),
                t.getDocumentNumber()
        );
        return id;
    }

    private String mapName(DocumentResponse t) {

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
