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
import pe.labtech.einvoice.core.tasks.DeclareOfflineTaskLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class DeclareOfflineRecurrent extends AbstractRecurrentTask<Long> {

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @EJB
    private DeclareOfflineTaskLocal task;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        this.findTasks = () -> prv.seek(e -> e
                .createQuery(
                        "SELECT O.id FROM Document O WHERE O.status = 'SIGNED-LOCAL'",
                        Long.class
                )
                .getResultList()
        );
        this.tryLock = t -> prv.seek(e -> e
                .createQuery(
                        "UPDATE Document O SET O.step = 'DECLARE', O.status = 'DISPATCHING-LOCAL' WHERE O.id = :id AND O.status = 'SIGNED-LOCAL'"
                )
                .setParameter("id", t)
                .executeUpdate() == 1
        );
        this.getId = t -> "Document[id:" + t + "].declareOffline()";
        this.consumer = t -> task.handle(t);
    }

    @Schedule(hour = "*", minute = "*", second = "*/15", persistent = false)
    @Override
    protected void timeout() {
        super.timeout();
    }

}
