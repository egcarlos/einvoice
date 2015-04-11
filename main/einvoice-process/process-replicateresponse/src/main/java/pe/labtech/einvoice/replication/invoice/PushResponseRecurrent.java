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
import pe.labtech.einvoice.commons.recurrent.RecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushResponseRecurrent extends AbstractRecurrentTask<Document> {

    @EJB
    PublicDatabaseManagerLocal manager;

    @EJB
    PrivateDatabaseManagerLocal privateManager;

    /**
     * Bloquear una respuesta de documento para ser replicada
     */
    private Function<DocumentResponse, Boolean> tryLockSingle;

    /**
     * Retorna los document response pendientes de repplicar en un documento.
     */
    private Function<Document, List<DocumentResponse>> findTasksSingle;

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();

        this.findTasks = () -> privateManager.seek(e -> e
                .createQuery(
                        "SELECT DISTINCT o.document FROM DocumentResponse o WHERE o.replicate = TRUE",
                        Document.class
                )
                .getResultList()
        );

        this.tryLock = t -> true;
        this.getId = t -> RecurrentTask.buildTaskId(t.getClientId(), t.getDocumentType(), t.getDocumentNumber(), "replicate response");
        this.consumer = t -> manager.handle(e -> {
            //obtenemos los pares de campo y valor a replicar en el caso.
            Map<String, String> responses = this.findTasksSingle
                    .apply(t)
                    .stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> mapName(r) != null)
                    .collect(
                            Collectors.toMap(
                                    r -> mapName(r),
                                    r -> r.getValue()
                            )
                    );

            //cortocircuito si no hay
            if (responses.isEmpty()) {
                return;
            }

            //obtener nombre de entidad e id de registro
            String targetEntity;
            Object id;
            if (isInvoice(t)) {
                id = new DocumentHeaderPK(
                        t.getClientId().split("-")[0],
                        t.getClientId().split("-")[1],
                        t.getDocumentType(),
                        t.getDocumentNumber()
                );
                targetEntity = "DocumentHeader";
            } else if (isInvoiceSummary(t)) {
                id = new SummaryHeaderPK(
                        t.getClientId().split("-")[0],
                        t.getClientId().split("-")[1],
                        t.getDocumentNumber()
                );
                targetEntity = "SummaryHeader";
            } else if (isCancelSummary(t)) {
                id = new CancelHeaderPK(
                        t.getClientId().split("-")[0],
                        t.getClientId().split("-")[1],
                        t.getDocumentNumber()
                );
                targetEntity = "CancelHeader";
            } else {
                //invalid forged data
                return;
            }

            //computar el segmento del set que es variable
            String setPart = responses.entrySet().stream()
                    .map(d -> "d." + d.getKey() + " = :" + d.getKey())
                    .reduce(null, (a, b) -> a == null ? b : a + ", " + b);

            logger.info(() -> this.tm("sending " + responses));
            final String queryString = "UPDATE " + targetEntity + " d SET " + setPart + " WHERE d.id = :id";
//            logger.info(() -> this.tm("queryString " + queryString));

            Query query = e.createQuery(queryString);
            responses.forEach((k, v) -> query.setParameter(k, v));
            query.setParameter("id", id);
            query.executeUpdate();
        });

        this.findTasksSingle = t -> privateManager.seek(e -> e
                .createQuery(
                        "SELECT o FROM DocumentResponse o WHERE o.replicate = TRUE AND o.document = :document",
                        DocumentResponse.class
                )
                .setParameter("document", t)
                .getResultList()
        );

        this.tryLockSingle = t -> privateManager.seek(e -> e
                .createNamedQuery("DocumentResponse.tryLock")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );
    }

    private static boolean isCancelSummary(Document t) {
        return t.getDocumentNumber().startsWith("RA");
    }

    private static boolean isInvoiceSummary(Document t) {
        return t.getDocumentNumber().startsWith("RC");
    }

    private static boolean isInvoice(Document t) {
        return t.getDocumentNumber().startsWith("F") | t.getDocumentNumber().startsWith("B");
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
