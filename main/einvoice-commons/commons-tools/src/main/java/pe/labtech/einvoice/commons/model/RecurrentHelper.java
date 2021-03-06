/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.model;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import pe.labtech.einvoice.commons.ext.StringTools;

/**
* Clase RecurrentHelper.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class RecurrentHelper {

    /**
     * Noop unary operator for typed queries
     */
    private static final UnaryOperator<TypedQuery<Long>> noop = q -> q;

    /**
     *
     * @param db
     * @param step
     * @param status
     * @param uo
     * @return
     */
    public static List<Long> lookup(DatabaseManager db, String step, String status, UnaryOperator<TypedQuery<Long>> uo) {
        UnaryOperator<TypedQuery<Long>> safeUo = (uo == null ? noop : uo);
        return db.seekNT(e -> safeUo
                .apply(
                        e.createQuery(
                                "SELECT O.id FROM Document O WHERE O.step = :step AND O.status = :status",
                                Long.class
                        )
                )
                .setParameter("step", step)
                .setParameter("status", status)
                .getResultList()
        );
    }

    /**
     *
     * @param db
     * @param step
     * @param status
     * @return
     */
    public static List<Long> lookup(DatabaseManager db, String step, String status) {
        return lookup(db, step, status, null);
    }

    /**
     *
     * @param t
     * @param action
     * @return
     */
    public static String buildId(Long t, String action) {
        return MessageFormat.format("Document[id:{0}].{1}()", t, action);
    }

    /**
     *
     * @param t
     * @param action
     * @param args
     * @return
     */
    public static String buildId(Long t, String action, String... args) {
        return MessageFormat.format("Document[id:{0}].{1}(" + StringTools.join(args, ", ", "'") + ")", t, action);
    }

    /**
     *
     * @param db
     * @param id
     * @param oldStep
     * @param oldStatus
     * @param newStep
     * @param newStatus
     * @return
     */
    public static boolean lock(DatabaseManager db, Long id, String oldStep, String oldStatus, String newStep, String newStatus) {
        return db.seek(e -> e
                .createQuery(
                        "UPDATE Document o SET o.step = :newStep, o.status = :newStatus WHERE o.id = :id AND o.step = :oldStep AND o.status = :oldStatus"
                )
                .setParameter("id", id)
                .setParameter("oldStep", oldStep)
                .setParameter("oldStatus", oldStatus)
                .setParameter("newStep", newStep)
                .setParameter("newStatus", newStatus)
                .executeUpdate() == 1
        );
    }

    /**
     *
     * @param db
     * @param source
     * @param numberPrefixes
     * @return
     */
    public static List<Long> lookupAllSourcedResponses(DatabaseManager db, String source, String... numberPrefixes) {
        List<String> l = new LinkedList<>();
        for (int i = 0; i < numberPrefixes.length; i++) {
            l.add("o.document.documentNumber LIKE (:p" + i + " ||'%')");
        }
        String variable = l.stream().reduce(null, (a, b) -> a == null ? b : a + " OR " + b);
        StringBuilder sb = new StringBuilder("SELECT DISTINCT o.document.id FROM DocumentResponse o WHERE o.replicate = TRUE AND o.document.hash = :source");
        sb.append(" AND (").append(variable).append(")");
        String adjustedQuery = sb.toString();
        return db.seekNT(e -> {
            TypedQuery<Long> q = e
                    .createQuery(adjustedQuery, Long.class)
                    .setParameter("source", source);
            for (int i = 0; i < numberPrefixes.length; i++) {
                q.setParameter("p" + i, numberPrefixes[i]);
            }
            //Se establece el máximo de respuestas concurrentes
            return q.setMaxResults(50).getResultList();
        });
    }

    /**
     *
     * @param db
     * @param numberPrefixes
     * @return
     */
    public static List<Long> lookupAllResponses(DatabaseManager db, String... numberPrefixes) {
        List<String> l = new LinkedList<>();
        for (int i = 0; i < numberPrefixes.length; i++) {
            l.add("o.document.documentNumber LIKE (:p" + i + " ||'%')");
        }
        String variable = l.stream().reduce(null, (a, b) -> a == null ? b : a + " OR " + b);
        StringBuilder sb = new StringBuilder("SELECT DISTINCT o.document.id FROM DocumentResponse o WHERE o.replicate = TRUE");
        sb.append(" AND (").append(variable).append(")");
        String adjustedQuery = sb.toString();
        return db.seekNT(e -> {
            TypedQuery<Long> q = e.createQuery(adjustedQuery, Long.class);
            for (int i = 0; i < numberPrefixes.length; i++) {
                q.setParameter("p" + i, numberPrefixes[i]);
            }
            return q.getResultList();
        });
    }

    /**
     *
     * @param db
     * @param id
     * @param name
     * @return
     */
    public static boolean lockResponse(DatabaseManager db, Long id, String name) {
        return db.seek(e -> e
                .createQuery(
                        "UPDATE DocumentResponse o SET o.replicate = FALSE WHERE o.replicate = TRUE AND o.document.id = :id AND o.name = :name"
                )
                .setParameter("id", id)
                .setParameter("name", name)
                .executeUpdate() == 1
        );
    }

    /**
     *
     * @param <T>
     * @param db
     * @param clazz
     * @param id
     * @return
     */
    public static <T> List<T> lookupResponse(DatabaseManager db, Class<T> clazz, Long id) {
        return db.seekNT(e -> e
                .createQuery(
                        "SELECT o FROM DocumentResponse o WHERE o.replicate = TRUE AND o.document.id = :id",
                        clazz
                )
                .setParameter("id", id)
                .getResultList()
        );
    }

    /**
     *
     * @param <T>
     * @param db
     * @param id
     * @param responses
     */
    public static final <T> void sendResponses(DatabaseManager db, T id, Map<String, ? extends Object> responses) {
        String entity = id.getClass().getSimpleName().replace("PK", "");
        String setPart = responses.entrySet().stream()
                .map(d -> "d." + d.getKey() + " = :" + d.getKey())
                .reduce(null, (a, b) -> a == null ? b : a + ", " + b);
        db.handle(e -> {
            Query query = e.createQuery(
                    "UPDATE " + entity + " d SET " + setPart + " WHERE d.id = :id"
            );
            responses
                    .forEach((k, v) -> query.setParameter(k, v));
            query
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }
}
