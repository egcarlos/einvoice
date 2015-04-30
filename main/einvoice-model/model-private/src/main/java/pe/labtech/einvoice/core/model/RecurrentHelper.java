/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.persistence.TypedQuery;
import pe.labtech.einvoice.replicator.model.DatabaseManager;

/**
 *
 * @author Carlos
 */
public class RecurrentHelper {

    public static List<Long> lookup(DatabaseManager db, String step, String status, UnaryOperator<TypedQuery<Long>> uo) {
        return db.seek(e -> uo
                .apply(
                        e.createQuery(
                                "SELECT O FROM Document O WHERE O.step = :step AND O.status = :status",
                                Long.class
                        )
                )
                .setParameter("step", step)
                .setParameter("status", status)
                .getResultList()
        );
    }

    public static List<Long> lookup(DatabaseManager db, String step, String status) {
        return lookup(db, step, status, e -> e);
    }

    public static String buildId(Long t, String action) {
        return MessageFormat.format("Document[id:{0}].{1}()", t, action);
    }

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

}
