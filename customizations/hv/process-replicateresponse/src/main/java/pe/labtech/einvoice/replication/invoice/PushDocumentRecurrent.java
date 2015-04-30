/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.LinkedHashMap;
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
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushDocumentRecurrent extends AbstractRecurrentTask<Document> {

    private static final String DOCUMENT_QUERY = "SELECT DISTINCT o.document FROM DocumentResponse o WHERE (o.document.documentNumber LIKE 'F%' OR o.document.documentNumber LIKE 'B%') AND o.replicate = TRUE";

    private static final String DOCUMENT_RESPONSE_QEURY = "SELECT o FROM DocumentResponse o WHERE o.replicate = TRUE AND o.document = :document";

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
    @Schedule(hour = "*", minute = "*", second = "*/1", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();
        this.findTasks = () -> privateManager.seek(e -> e.createQuery(DOCUMENT_QUERY, Document.class).setMaxResults(10000).getResultList());
        this.tryLock = t -> true;
        this.getId = t -> RecurrentTask.buildTaskId(t.getClientId(), t.getDocumentType(), t.getDocumentNumber(), "replicate response");
        this.consumer = t -> manager.handle(e -> {
            Map<String, Object> responses = this.findTasksSingle.apply(t).stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> mapName(r) != null)
                    .collect(Collectors.toMap(r -> mapName(r), r -> r.getValue()));

            if (responses.isEmpty()) {
                return;
            }

            Map<String, Object> where = new LinkedHashMap<>();
            where.put("ctipdocumentoemisor", t.getClientId().split("-")[0]);
            where.put("cdocumentoemisor", t.getClientId().split("-")[1]);
            where.put("ctipcomprobante", t.getDocumentType());
            where.put("ccomprobante", t.getDocumentNumber());

            Function<Map.Entry<String, Object>, String> mapKey = d -> "d." + d.getKey() + " = :" + d.getKey();

            String setPart = responses.entrySet().stream()
                    .map(mapKey)
                    .reduce(null, (a, b) -> a == null ? b : a + ", " + b);

            String wherePart = where.entrySet().stream()
                    .map(mapKey)
                    .reduce(null, (a, b) -> a == null ? b : a + " AND " + b);

            logger.info(() -> this.tm("sending " + responses));

            Query query = e.createQuery(
                    "UPDATE DocumentHeader d SET " + setPart + " WHERE " + wherePart
            );
            responses.forEach((k, v) -> query.setParameter(k, v));
            where.forEach((k, v) -> query.setParameter(k, v));
            query.executeUpdate();
        });

        this.findTasksSingle = t -> privateManager.seek(e -> e.createQuery(DOCUMENT_RESPONSE_QEURY, DocumentResponse.class).setParameter("document", t).getResultList());

        this.tryLockSingle = t -> privateManager.seek(e -> e
                .createNamedQuery("DocumentResponse.tryLock")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );
    }

    private String mapName(DocumentResponse t) {

        switch (t.getName()) {
            case "lgFirmaHash":
                return "bl_hashFirma";
            case "recordStatus":
                return "cestado";
            case "pdfFileUrl":
                return "crutapdf";
            case "xmlFileSignUrl":
                return "crutaxml";
            case "signatureValue":
                return "lgFirma";
        }
        return null;
    }

}
