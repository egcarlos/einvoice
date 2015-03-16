/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.recurrent;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.InvoiceSeekerLocal;
import pe.labtech.einvoice.core.tasks.SyncTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class LongSyncTimer extends AbstractRecurrentTask<Document> {

    @EJB
    InvoiceSeekerLocal seeker;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        this.findTasks = () -> seeker.pullDocuments("DECLARE", "SYNC");
        this.tryLock = t -> seeker.markSynkronized(t.getId(), "DECLARE", "SYNC", "SYNCING");
        this.getId = t -> t.getClientId() + "-" + t.getDocumentType() + "-" + t.getDocumentNumber() + "[replicate]";
        this.consumer = t -> task.handle(t);
    }

    @EJB
    private SyncTaskLocal task;

    @Override
    @Schedule(hour = "*", minute = "*/15", persistent = false)
    public void timeout() {
        super.timeout();
    }

}
