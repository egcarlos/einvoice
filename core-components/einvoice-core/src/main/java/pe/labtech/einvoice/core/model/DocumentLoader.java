/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.EventTrace;

/**
 *
 * @author Carlos
 */
@Stateless
public class DocumentLoader implements DocumentLoaderLocal {

    @PersistenceContext(unitName = "einvoice_PU")
    EntityManager em;

    @Override
    public List<Document> loadForSignature() {
        TypedQuery<Document> query = em.createQuery("SELECT o FROM Document o WHERE o.step IS NULL or (o.step = :step and o.status = :status)", Document.class);
        query.setParameter("step", "SIGN");
        query.setParameter("status", "FIXED");
        List<Document> documents = query.getResultList();

        documents.forEach(d -> {
            d.setStep("SIGN");
            d.setStatus("DISPATCHING");
            createEvent(d, "INFO", "Locked for signature");
        });
        return documents;
    }

    @Override
    public List<Document> loadForSync() {
        TypedQuery<Document> query = em.createQuery("SELECT o FROM Document o WHERE o.status = :status", Document.class);
        query.setParameter("status", "SYNC");
        List<Document> documents = query.getResultList();

        documents.forEach(d -> {
            d.setStatus("SYNCING");
        });
        return documents;
    }

    @Override
    public List<Document> loadForDeclare() {
        TypedQuery<Document> query = em.createQuery("SELECT o FROM Document o WHERE o.step = :step AND o.status = :status", Document.class);
        query.setParameter("step", "SIGN");
        query.setParameter("status", "COMPLETE");
        List<Document> documents = query.getResultList();

        documents.forEach(d -> {
            d.setStep("DECLARE");
            d.setStatus("DISPATCHING");
            createEvent(d, "INFO", "Locked for declare");
        });
        return documents;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createEvent(Document d, final String eventType, final String eventMessage) {
        EventTrace trace = new EventTrace();
        trace.setDocument(d);
        trace.setDate(new Date());
        trace.setType(eventType);
        trace.setMessage(eventMessage);
        em.persist(trace);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Document loadForWork(Long id, Consumer<Document> action) {
        final Document d = em.find(Document.class, id);
        d.setStatus("WORKING");
        createEvent(d, "INFO", "working on " + d.getStep());

        if (action != null) {
            action.accept(d);
        }

        return d;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Document markAsError(Long id) {
        final Document d = em.find(Document.class, id);
        d.setStatus("ERROR");
        createEvent(d, "INFO", "error on " + d.getStep());
        return d;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Document markAsError(Long id, Exception ex) {
        final Document d = em.find(Document.class, id);
        d.setStatus("ERROR");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.write(ex.getMessage() + "\n");
        ex.printStackTrace(pw);
        pw.flush();
        pw.close();
        createEvent(d, "INFO", "error on " + d.getStep() + "\n" + sw.toString());
        return d;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void markSigned(Long id, String pdfURL, String xmlURL, String signature, String hash) {
        final Document d = em.find(Document.class, id);
        d.setStatus("COMPLETE");
        d.setPdfURL(pdfURL);
        d.setXmlURL(xmlURL);
        d.setSignature(signature);
        d.setHash(hash);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void markForSync(Long id) {
        final Document d = em.find(Document.class, id);
        d.setStatus("SYNC");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void markDeclared(Long id) {
        final Document d = em.find(Document.class, id);
        d.setStatus("COMPLETE");
    }

}
