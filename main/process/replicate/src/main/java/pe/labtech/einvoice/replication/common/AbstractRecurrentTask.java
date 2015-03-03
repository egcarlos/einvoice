/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.common;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;

/**
 *
 * @author Carlos
 */
public abstract class AbstractRecurrentTask {

    private AtomicBoolean flag;

    @PostConstruct
    public void init() {
        flag = new AtomicBoolean(false);
    }

    protected void timeout() {
        if (!flag.compareAndSet(false, true)) {
            //already busy
            return;
        }
        try {
            doWork();
        } finally {
            //finish work
            flag.set(false);
        }
    }

    public abstract void doWork();

}
