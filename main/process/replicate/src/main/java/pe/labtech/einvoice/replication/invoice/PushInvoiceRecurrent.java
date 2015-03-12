/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.InvoiceSeekerLocal;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.SeekHeaderLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushInvoiceRecurrent extends AbstractRecurrentTask<Document> {

    @EJB
    private InvoiceSeekerLocal seeker;

    @EJB
    private SeekHeaderLocal pusher;

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();
        this.findTasks = () -> seeker.pullDocuments("DECLARE", "COMPLETE");
        this.tryLock = t -> seeker.markSynkronized(t.getId(), "DECLARE", "COMPLETE", "COMPLETE-PUSHED");
        this.getId = t -> t.getClientId() + "-" + t.getDocumentType() + "-" + t.getDocumentNumber() + "[replicate]";
        this.consumer = t -> doWork(t);
    }

    private void doWork(Document document) {
        DocumentHeaderPK id = new DocumentHeaderPK(
                document.getClientId().split("-")[0],
                document.getClientId().split("-")[1],
                document.getDocumentType(),
                document.getDocumentNumber()
        );
        Map<String, String> response = new LinkedHashMap<>();

        String pdfFileUrl = seeker.pullDocumentResponse(document, "pdfFileUrl");
        if (pdfFileUrl != null) {
            response.put("bl_urlpdf", pdfFileUrl);
        }

        String xmlFileSignUrl = seeker.pullDocumentResponse(document, "xmlFileSignUrl");
        if (xmlFileSignUrl != null) {
            response.put("bl_urlxmlubl", xmlFileSignUrl);
        }

        response.put("bl_estadoRegistro", "P");
        response.put("bl_estadoProceso", "DECLARE COMPLETE-PUSHED");
        response.put("bl_firma", document.getSignature());
        response.put("bl_hashFirma", document.getHash());
        pusher.update(id, response);
    }

}
