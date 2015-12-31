/*
* Producto elaborado para Alignet S.A.C.
*
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
 * Clase DatabaseCommons.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 */
public class DatabaseCommons {

    /**
     * Marca la transacción para reintento.
     *
     * @param db apunta a la base de datos privada
     * @param document documento analizado
     * @param loader
     * @param ex
     */
    @Deprecated
    public static void markAsRetry(DatabaseManager db, Document document, DocumentLoaderLocal loader, Exception ex) {
        markAsRetry(db, document.getId(), loader, ex);
    }

    /**
     * Marca la transacción para reintento.
     *
     * @param db apunta a la base de datos privada
     * @param id identificador de documento analizado
     * @param loader
     * @param ex
     */
    public static void markAsRetry(DatabaseManager db, Long id, DocumentLoaderLocal loader, Exception ex) {
        Map<String, String> responses = new HashMap<>();
        responses.put("messages", "Soap Fault raised!");
        mark(db, id, null, DocumentStatus.RETRY, responses);
        loader.createEvent(id, "WARN", Tools.exToString(ex, "Document will retry"));
    }

    /**
     * Marca un a transacción en un estado y gestiona las respuestas.
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param step paso del proceso
     * @param status estado dentro del paso
     * @param responses
     */
    public static void mark(DatabaseManager db, Long id, String step, String status, Object... responses) {
        mark(db, id, step, status, Tools.toMap(String.class, String.class, responses));
    }

    /**
     * Marca un a transacción en un estado y gestiona las respuestas.
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param step paso del proceso
     * @param status estado dentro del paso
     * @param responses
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
            updateStepAndStatus(db, id, step, status);
        }
    }

    /**
     *
     * @param responses
     */
    public static void addSynthResponses(Map<String, String> responses) {
        //Se agrega el campo sintético a las respuestas
        if (responses != null) {
            String status = responses.get("status") + "/"
                    + responses.get("documentStatus") + "/"
                    + responses.get("sunatStatus");

            if (!"null/null/null".equals(status)) {
                responses.put(
                        "integratedStatus",
                        status
                );
            }
        }
    }

    /**
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param name nombre del atributo de respuesta
     * @param value
     */
    public static void updateResponse(DatabaseManager db, Long id, String name, String value) {
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
            e.createQuery(cu).executeUpdate();
        });
    }

    /**
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param name nombre del atributo de respuesta
     * @param value
     */
    public static void createResponse(DatabaseManager db, Long id, String name, String value) {
        //si el atributo no existe se debe crear un nuevo registro
        Document d = new Document();
        d.setId(id);
        DocumentResponse dr = new DocumentResponse(d, name, value);
        dr.setReplicate(isReplicable(name));
        db.handle(e -> e.persist(dr));
    }

    /**
     *
     * @param name nombre de atributo de respuesta
     * @param responses
     * @return
     */
    public static boolean parameterShouldSkip(String name, Map<String, String> responses) {
        return name.equals("xmlFileSunatUrl") && isNotFinishedInSunat(responses.get("sunatStatus"));
    }

    /**
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param responses
     */
    public static void relayResponses(DatabaseManager db, Long id, Map<String, String> responses) {
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

    /**
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param step paso del proceso
     * @param status estado dentro del paso
     */
    public static void updateStepAndStatus(DatabaseManager db, Long id, String step, String status) {
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

    /**
     *
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param name nombre del atributo de respuesta
     * @return
     */
    public static boolean checkIfResponseExists(DatabaseManager db, Long id, String name) {
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

    /**
     * Verifica si estan cargados los atributos de datos de un documento.
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @param name nombre de atributo de datos
     * @return
     */
    public static boolean checkIfDataExists(DatabaseManager db, Long id, String name) {
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
     * Verifica si un nombre de parámetro es candidato para replicación.
     *
     * NOTA: Trasladar aun archivo externo o a la base de datos.
     *
     * @param parameter nombre del parametro a analizar
     * @return true si el atributo es replicable o false si no lo es
     */
    public static boolean isReplicable(String parameter) {
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
     * Determina si un documento tiene una firma local registrada en la base de
     * datos.
     *
     * @param db apunta a la base de datos privada
     * @param id identificador del documento
     * @return true si hay una firma o false si no
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
     * Obtiene el nombre de atributo asociado a un documento.
     *
     * @param db apunta a la base de datos privada
     * @param document documento analizado
     * @param name nombre de atributo
     * @return valor del atributo
     */
    public static String getAttributeValue(DatabaseManager db, Document document, String name) {
        try {
            return db.seekNT(e -> e
                    .createQuery(
                            "SELECT O.value FROM DocumentAttribute O WHERE O.document = :document AND O.name = :name",
                            String.class
                    )
                    .setParameter("document", document)
                    .setParameter("name", name)
                    .getSingleResult()
            );
        } catch (NonUniqueResultException | NoResultException ex) {
            return null;
        }
    }

}
