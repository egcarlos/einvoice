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
import static pe.labtech.einvoice.commons.model.DocumentStatus.RETRY;
import static pe.labtech.einvoice.commons.model.DocumentStatus.SIGNING;
import static pe.labtech.einvoice.commons.model.DocumentStep.SIGN;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.buildId;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.lock;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.lookup;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.model.AsyncWrapperLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.sign.SignTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
@Deprecated
public class SignRetryTimer extends AbstractRecurrentTask<Long> {

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @EJB
    private SignTaskLocal task;

    @EJB
    private AsyncWrapperLocal asw;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        super.findTasks = () -> lookup(prv, SIGN, RETRY, q -> q.setMaxResults(10000));
        super.tryLock = t -> lock(prv, t, SIGN, RETRY, SIGN, SIGNING);
        super.getId = t -> buildId(t, "sign");
        super.consumer = t -> asw.perform(() -> task.handle(t));
    }

    @Override
//    @Schedule(hour = "*", minute = "*/1", persistent = false)
    public void timeout() {
        super.timeout();
    }

}
