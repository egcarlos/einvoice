/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import static pe.labtech.einvoice.core.tasks.tools.ServiceCommons.isNotFinishedInSunat;

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

        db.handle(e -> {
            if (responses != null) {
                responses.put("integratedStatus", responses.get("status") + "/" + responses.get("documentStatus") + "/" + responses.get("sunatStatus"));
                responses.forEach((name, value) -> {
                    //protege de un bug de implementación de la plataforma de bizlinks
                    //donde la URL del CDR se reporta aun cuando aun no se ha recibido
                    //la respuesta de sunat
                    if (name.equals("xmlFileSunatUrl") && isNotFinishedInSunat(responses.get("sunatStatus"))) {
                        return;
                    }

                    //primer segmento graba el parámetro de respuesta
                    {
                        List<DocumentResponse> list = e
                                .createNamedQuery("DocumentResponse.findById", DocumentResponse.class)
                                .setParameter("document", d)
                                .setParameter("name", name)
                                .getResultList();
                        if (list.isEmpty()) {
                            DocumentResponse dr = new DocumentResponse(d, name, value);
                            checkReplicable(name, dr);
                            e.persist(dr);
                        } else {
                            DocumentResponse dr = list.get(0);
                            if (!Objects.equals(value, dr.getValue())) {
                                checkReplicable(name, dr);
                                dr.setValue(value);
                            }
                        }
                    }

                    //segundo segmento solo aplica si contiene el texto URL
                    if (!name.toUpperCase().contains("URL")) {
                        return;
                    }
                    {
                        List<DocumentData> list = e
                                .createNamedQuery(
                                        "DocumentData.findById",
                                        DocumentData.class
                                )
                                .setParameter("document", d)
                                .setParameter("name", name)
                                .getResultList();
                        if (list.isEmpty()) {
                            e.persist(new DocumentData(d, name, value, null, "MISSING"));
                        }
                        //TODO considerar que la URL puede cambiar según el parámetro
                    }
                });
            }
            if (step != null) {
                e
                        .createQuery("UPDATE Document O SET o.step = :value WHERE O.id = :id")
                        .setParameter("value", step)
                        .setParameter("id", id)
                        .executeUpdate();
            }
            if (status != null) {
                e
                        .createQuery("UPDATE Document O SET o.status = :value WHERE O.id = :id")
                        .setParameter("value", status)
                        .setParameter("id", id)
                        .executeUpdate();
            }
        });
    }

    /**
     * Checks if the parameter is candidate for replication.
     *
     * @param parameter
     * @param documentResponse
     */
    public static void checkReplicable(String parameter, DocumentResponse documentResponse) {
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
                documentResponse.setReplicate(true);
                break;
            default:
                documentResponse.setReplicate(false);
        }
    }
}
