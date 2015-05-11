/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.trace;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pe.labtech.einvoice.core.entity.EventTrace;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Stateless
public class TraceBean implements TraceBeanLocal {

    @EJB
    PrivateDatabaseManagerLocal prv;

    public List<EventTrace> search(String query) {
        if (query == null || query.isEmpty()) {
            return prv.seek(e -> e
                    .createQuery(
                            "SELECT O FROM EventTrace O ORDER BY O.document.id DESC, O.id DESC",
                            EventTrace.class
                    )
                    .setMaxResults(50)
                    .getResultList()
            );
        } else if (query.matches("^\\d+$")) {
            Long documentId = Long.valueOf(query);
            return prv.seek(e -> e
                    .createQuery(
                            "SELECT O FROM EventTrace O WHERE O.document.id = :id ORDER BY O.document.id DESC, O.id DESC",
                            EventTrace.class
                    )
                    .setParameter("id", documentId)
                    .setMaxResults(50)
                    .getResultList()
            );
        } else {
            return prv.seek(e -> e
                    .createQuery(
                            "SELECT O FROM EventTrace O WHERE O.document.documentNumber = :number ORDER BY O.document.id DESC, O.id DESC",
                            EventTrace.class
                    )
                    .setParameter("number", query)
                    .setMaxResults(50)
                    .getResultList()
            );
        }
    }
}
