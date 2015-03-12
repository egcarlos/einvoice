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
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.InvoiceSeekerLocal;
import pe.labtech.einvoice.core.tasks.DeclareTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class DeclareRecurrent extends AbstractRecurrentTask<Document> {

    @EJB
    private InvoiceSeekerLocal seeker;

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private DeclareTaskLocal task;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        this.findTasks = () -> seeker.pullDocuments("SIGN", "COMPLETE");
        this.tryLock = t -> seeker.markSynkronized(t.getId(), "SIGN", "COMPLETE", "DECLARE", "DISPATCHING");
        this.getId = t -> t.getClientId() + "-" + t.getDocumentType() + "-" + t.getDocumentNumber() + "[declare]";
        this.consumer = t -> task.handle(t.getId());
    }

    @Schedule(hour = "*", minute = "*", second = "*/15", persistent = false)
    @Override
    protected void timeout() {
        super.timeout();
    }
}
