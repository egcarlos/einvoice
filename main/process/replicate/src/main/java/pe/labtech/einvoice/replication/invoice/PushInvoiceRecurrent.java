/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.InvoiceSeekerLocal;
import pe.labtech.einvoice.replication.common.AbstractRecurrentTask;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.SeekHeaderLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushInvoiceRecurrent extends AbstractRecurrentTask {

    @EJB
    private PullInvoiceTaskLocal task;

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
    public void doWork() {
        List<Document> documents = seeker.pullDocuments("DECLARE", "COMPLETE");
        documents.forEach(document -> {
            if (seeker.markSynkronized(document.getId(), "DECLARE", "COMPLETE", "COMPLETE-PUSHED")) {
                DocumentHeaderPK id = new DocumentHeaderPK(
                        document.getClientId().split("-")[0],
                        document.getClientId().split("-")[1],
                        document.getDocumentType(),
                        document.getDocumentNumber()
                );
                Map<String, String> response = new LinkedHashMap<>();
                response.put("bl_urlpdf", document.getPdfURL());
                response.put("bl_urlxmlubl", document.getXmlURL());
                response.put("bl_estadoRegistro", "P");
                response.put("bl_estadoProceso", "DECLARE COMPLETE-PUSHED");
                response.put("bl_firma", document.getSignature());
                response.put("bl_hashFirma", document.getHash());
                pusher.update(id, response);
            }
        });
    }

}
