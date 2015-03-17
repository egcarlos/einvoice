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
import pe.labtech.einvoice.core.entity.DocumentResponse;

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
                .createNamedQuery(
                        "Document.findByStepAndStatus",
                        Document.class
                )
                .setParameter("step", step)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentResponse> pullDocumentResponse(Document document) {
        return em
                .createQuery(
                        "SELECT o FROM DocumentResponse o WHERE o.document = :document",
                        DocumentResponse.class
                )
                .setParameter("document", document)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String pullDocumentResponse(Document document, String name) {
        List<String> s = em
                .createQuery(
                        "SELECT o.value FROM DocumentResponse o WHERE o.document = :document AND o.name = :name",
                        String.class
                )
                .setParameter("document", document)
                .setParameter("name", name)
                .getResultList();
        if (s.isEmpty()) {
            return null;
        }
        return s.get(0);
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

    @Override
    public boolean markSynkronized(Long id, String oldstep, String oldstatus, String newstep, String newstatus) {
        return em
                .createQuery("UPDATE Document o SET o.step = :newstep, o.status = :newstatus WHERE o.step = :oldstep AND o.id = :id AND o.status = :oldstatus")
                .setParameter("id", id)
                .setParameter("oldstep", oldstep)
                .setParameter("oldstatus", oldstatus)
                .setParameter("newstep", newstep)
                .setParameter("newstatus", newstatus)
                .executeUpdate() == 1;
    }

}
