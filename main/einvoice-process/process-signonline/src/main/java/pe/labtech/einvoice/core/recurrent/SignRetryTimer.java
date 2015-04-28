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
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.SignTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class SignRetryTimer extends AbstractRecurrentTask<Long> {

    @EJB
    private SignTaskLocal task;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        super.findTasks = () -> prv.seek(e -> e
                .createQuery(
                        "SELECT D.id FROM Document D WHERE d.step = 'SIGN' AND d.status = 'RETRY'",
                        Long.class
                )
                .setMaxResults(10000)
                .getResultList()
        );
        super.tryLock = t -> prv.seek(e -> e
                .createQuery(
                        "UPDATE Document D SET D.step = 'SIGN', D.status = 'SIGNING' WHERE D.id = :id AND D.step = 'SIGN' AND D.status = 'RETRY'"
                )
                .setParameter("id", t)
                .executeUpdate() == 1
        );
        super.getId = t -> "Document[id:" + t + "].sign()";
        super.consumer = t -> task.handle(t);
    }

    @Override
    @Schedule(hour = "*", minute = "*/1", second = "0", persistent = false)
    public void timeout() {
        super.timeout();
    }

}
