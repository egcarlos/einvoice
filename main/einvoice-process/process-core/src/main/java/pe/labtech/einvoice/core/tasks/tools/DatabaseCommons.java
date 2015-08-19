/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.entity.Document_;
import pe.labtech.einvoice.core.entity.DocumentData_;
import pe.labtech.einvoice.core.entity.DocumentResponse_;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import static pe.labtech.einvoice.core.tasks.tools.Tools.isNotFinishedInSunat;

/**
 *
 * @author Carlos
 */
public class DatabaseCommons {

    /**
     * Marks a document as ERROR considering that an exception was raised
     *
     * @param db
     * @param document
     * @param loader
     * @param ex
     */
    public static void markAsRetry(DatabaseManager db, Document document, DocumentLoaderLocal loader, Exception ex) {
        Map<String, String> responses = new HashMap<>();
        responses.put("messages", "Soap Fault raised!");
        mark(db, document.getId(), null, DocumentStatus.RETRY, responses);
        loader.createEvent(document, "WARN", Tools.exToString(ex, "Document will retry"));
    }

    public static void mark(DatabaseManager db, Long id, String step, String status, Object... responses) {
        mark(db, id, step, status, Tools.toMap(String.class, String.class, responses));
    }

    /**
     *
     * @param db reference to database
     * @param id document identifier
     * @param step next step for the document or null to keep current
     * @param status next status for the document or null to keep current
     * @param responses responses or null for empty
     */
    public static void mark(DatabaseManager db, Long id, String step, String status, Map<String, String> responses) {
        Document d = new Document();
        d.setId(id);
        addSynthResponses(responses);
        try {
            responses.forEach((name, value) -> {
                if (parameterShouldSkip(name, responses)) {
                    return;
                }
                if (checkIfResponseExists(db, id, name)) {
                    updateResponse(db, id, name, value);
                } else {
                    createResponse(db, id, name, value);
                }
                if (name.toUpperCase().contains("URL") && !checkIfDataExists(db, id, name)) {
                    db.handle(e -> e.persist(new DocumentData(d, name, value, null, "MISSING")));
                }
            });
        } finally {
            //TODO enviar a un procesador asíncrono, probablemente serializado
            relayResponses(db, id, responses);
            updateStepAndStatus(step, status, db, id);
        }

    }

    /**
     *
     * @param responses
     */
    public static void addSynthResponses(Map<String, String> responses) {
        //Se agrega el campo sintético a las respuestas
        if (responses != null) {
            responses.put(
                    "integratedStatus",
                    responses.get("status") + "/" + responses.get("documentStatus") + "/" + responses.get("sunatStatus")
            );
        }
    }

    private static void updateResponse(DatabaseManager db, Long id, String name, String value) {
        db.handle(e -> {
            CriteriaBuilder cb = e.getCriteriaBuilder();
            CriteriaUpdate<DocumentResponse> cu = cb.createCriteriaUpdate(DocumentResponse.class);
            Root<DocumentResponse> da = cu.from(DocumentResponse.class);
            cu.set(da.get(DocumentResponse_.value), value);
            if (isReplicable(name)) {
                cu.set(da.get(DocumentResponse_.replicate), true);
            }
            cu.where(
                    cb.equal(da.get(DocumentResponse_.document).get(Document_.id), id),
                    cb.equal(da.get(DocumentResponse_.name), name),
                    cb.notEqual(da.get(DocumentResponse_.value), value)
            );
        });
    }

    private static void createResponse(DatabaseManager db, Long id, String name, String value) {
        //si el atributo no existe se debe crear un nuevo registro
        Document d = new Document();
        d.setId(id);
        DocumentResponse dr = new DocumentResponse(d, name, value);
        dr.setReplicate(isReplicable(name));
        db.handle(e -> e.persist(dr));
    }

    private static boolean parameterShouldSkip(String name, Map<String, String> responses) {
        return name.equals("xmlFileSunatUrl") && isNotFinishedInSunat(responses.get("sunatStatus"));
    }

    private static void relayResponses(DatabaseManager db, Long id, Map<String, String> responses) {
        Document d1 = db.seekNT(e -> e.find(Document.class, id));
        if (d1.getHash() != null && !d1.getHash().equals("##NONE")) {
            WebTarget target = RestCommons.driverTarget(d1.getHash());

            if (target == null) {
                return;
            }

            responses.put("tde", d1.getClientId().split("-")[0]);
            responses.put("nde", d1.getClientId().split("-")[1]);
            responses.put("td", d1.getDocumentType());
            responses.put("sn", d1.getDocumentNumber().startsWith("R") ? d1.getDocumentNumber().substring(3) : d1.getDocumentNumber());

            target
                    .request()
                    .buildPut(Entity.json(responses))
                    .submit();
        }
    }

    private static void updateStepAndStatus(String step, String status, DatabaseManager db, Long id) {
        if (step != null || status != null) {
            db.handle(e -> {
                CriteriaBuilder cb = e.getCriteriaBuilder();
                CriteriaUpdate<Document> cu = cb.createCriteriaUpdate(Document.class);
                Root<Document> r = cu.from(Document.class);
                if (step != null) {
                    cu.set(r.get(Document_.step), step);
                }
                if (status != null) {
                    cu.set(r.get(Document_.status), status);
                }
                cu.where(cb.equal(r.get(Document_.id), id));
                Query q = e.createQuery(cu);
                q.executeUpdate();
            });
        }
    }

    private static boolean checkIfResponseExists(DatabaseManager db, Long id, String name) {
        Long count = db.seekNT(e -> {
            CriteriaBuilder cb = e.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<DocumentResponse> dd = cq.from(DocumentResponse.class);
            cq.select(cb.count(dd.get(DocumentResponse_.name)));
            cq.where(
                    cb.equal(dd.get(DocumentResponse_.document).get(Document_.id), id),
                    cb.equal(dd.get(DocumentResponse_.name), name)
            );
            return e.createQuery(cq).getSingleResult();
        });
        return count != 0;
    }

    private static boolean checkIfDataExists(DatabaseManager db, Long id, String name) {
        Long count = db.seekNT(e -> {
            CriteriaBuilder cb = e.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<DocumentData> dd = cq.from(DocumentData.class);
            cq.select(cb.count(dd.get(DocumentData_.name)));
            cq.where(
                    cb.equal(dd.get(DocumentData_.document).get(Document_.id), id),
                    cb.equal(dd.get(DocumentData_.name), name)
            );
            return e.createQuery(cq).getSingleResult();
        });
        return count != 0;
    }

    /**
     * Checks if the parameter is candidate for replication.
     *
     * @param parameter
     * @return
     */
    public static boolean isReplicable(String parameter) {
        //TODO trasladar a la base de datos
        switch (parameter) {
            case "pdfFileUrl":
            case "xmlFileSignUrl":
            case "xmlFileSunatUrl":
            case "integratedStatus":
            case "hashCode":
            case "signatureValue":
            case "messages":
            case "sunatMessage":
            case "recordStatus":
                return true;
        }
        return false;
    }

    /**
     * Checks if document has a local signature in the server.
     *
     * @param db
     * @param id
     * @return
     */
    public static boolean hasLocalSignature(DatabaseManager db, Long id) {
        return db.seekNT(e -> e
                .createQuery(
                        "SELECT COUNT(o) FROM DocumentResponse o WHERE o.document.id = :id AND o.name = :name",
                        Long.class
                )
                .setParameter("id", id)
                .setParameter("name", "signed")
                .getSingleResult()
        ) == 1l;
    }

    /**
     *
     * @param db
     * @param document
     * @param name
     * @return
     */
    public static String getAttributeValue(DatabaseManager db, Document document, String name) {
        return db.seekNT(e -> e
                .createQuery(
                        "SELECT O.value FROM DocumentAttribute O WHERE O.document = :document AND O.name = :name",
                        String.class
                )
                .setParameter("document", document)
                .setParameter("name", name)
                .getSingleResult()
        );
    }

}
