/*
 * Producto elaborado para Alignet S.A.C.
 *
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
import static pe.labtech.einvoice.commons.model.DocumentStatus.NEEDED;
import static pe.labtech.einvoice.commons.model.DocumentStatus.SYNCING;
import static pe.labtech.einvoice.commons.model.DocumentStep.SYNC;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.buildId;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.lock;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.lookup;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.model.AsyncWrapperLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.sync.SyncTaskLocal;

/**
 * Clase SyncTimer.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class SyncTimer extends AbstractRecurrentTask<Long> {

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @EJB
    private SyncTaskLocal task;

    @EJB
    private AsyncWrapperLocal asw;

    /**
     * Inicializador.
     */
    @PostConstruct
    @Override
    public void init() {
        super.init();
        super.findTasks = () -> lookup(prv, SYNC, NEEDED, q -> q.setMaxResults(10000));
        super.tryLock = t -> lock(prv, t, SYNC, NEEDED, SYNC, SYNCING);
        super.getId = t -> buildId(t, "sign");
        super.consumer = t -> asw.perform(() -> task.handle(t));
    }

    /**
     * Funcion recurrente.
     */
    @Override
    @Schedule(hour = "*", minute = "*/1", persistent = false)
    public void timeout() {
        super.timeout();
    }

}
