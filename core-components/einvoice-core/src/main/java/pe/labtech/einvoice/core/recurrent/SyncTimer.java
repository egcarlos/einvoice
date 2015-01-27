/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.recurrent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.tasks.SyncTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class SyncTimer {

    private AtomicBoolean working;

    @PostConstruct
    public void init() {
        Logger.getLogger("SyncTimer").info("Task created");
        working = new AtomicBoolean(false);
    }

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private SyncTaskLocal task;

    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void doWork() {
        if (!working.compareAndSet(false, true)) {
            Logger.getLogger(this.getClass().getSimpleName()).fine("Process busy... waiting");
            //already working, just wait until it ends
            return;
        }
        Logger.getLogger(this.getClass().getSimpleName()).fine("Dispatching for syncing");
        try {
            List<Document> documents = find();
            documents.forEach(d -> relay(d));
        } finally {
            working.set(false);
        }
    }

    private List<Document> find() {
        return loader.loadForSync();
    }

    private void relay(Document d) {
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "syncing document {0}-{1}", new Object[]{d.getDocumentType(), d.getDocumentNumber()});
        task.handle(d.getId());
    }
}
