/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.core.entity.Document;

/**
 *
 * @author Carlos
 */
@Stateless
public class InvoiceSeeker implements InvoiceSeekerLocal {

    @PersistenceContext(unitName = "einvoice_PU")
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Document> pullDocuments(String step, String status) {
        return em
                .createQuery(
                        "SELECT o FROM Document o WHERE o.step = :step AND o.status = :status",
                        Document.class
                )
                .setParameter("step", step)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean markSynkronized(Long id, String step, String oldstatus, String newstatus) {
        return em
                .createQuery("UPDATE Document o SET o.status = :newstatus WHERE o.id = :id AND o.status = :oldstatus AND o.step = :step")
                .setParameter("id", id)
                .setParameter("step", step)
                .setParameter("oldstatus", oldstatus)
                .setParameter("newstatus", newstatus)
                .executeUpdate() == 1;
    }

}
