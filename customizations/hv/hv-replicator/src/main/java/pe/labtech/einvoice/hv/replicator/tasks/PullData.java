/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.hv.replicator.tasks;

import java.util.concurrent.atomic.AtomicBoolean;
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

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class PullData {

    private AtomicBoolean working;

    @EJB
    PullDataTaskLocal task;

    @PostConstruct
    public void init() {
        Logger.getLogger("PullData").info("Task created");
        working = new AtomicBoolean(false);
    }

    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void handle() {
        if (!working.compareAndSet(false, true)) {
            Logger.getLogger(this.getClass().getSimpleName()).info("Process busy... waiting");
            //already working, just wait until it ends
            return;
        }

        try {
            task.handle();
        } finally {
            working.set(false);
        }
    }
}
