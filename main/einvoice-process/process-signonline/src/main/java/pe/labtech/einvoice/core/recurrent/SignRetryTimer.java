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
import pe.labtech.einvoice.core.tasks.SignTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class SignRetryTimer extends AbstractRecurrentTask<Document> {

    @EJB
    InvoiceSeekerLocal seeker;
    @EJB
    private SignTaskLocal task;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        super.findTasks = () -> seeker.pullDocuments("SIGN", "RETRY");
        super.tryLock = t -> seeker.markSynkronized(t.getId(), "PULL", "LOADED", "SIGN", "SIGNING");
        super.getId = t -> t.getClientId() + "-" + t.getDocumentType() + "-" + t.getDocumentNumber() + "[sign]";
        super.consumer = t -> task.handle(t);
    }

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void timeout() {
        super.timeout();
    }

}
