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
import static pe.labtech.einvoice.commons.model.DocumentStatus.REPLICATING;
import static pe.labtech.einvoice.commons.model.DocumentStep.REPLICATE;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.buildId;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.lock;
import static pe.labtech.einvoice.commons.model.RecurrentHelper.lookup;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.model.AsyncWrapperLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.replicate.ReplicateXmlTaskLocal;

/**
 * Clase ReplicateXmlRecurrent.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReplicateXmlRecurrent extends AbstractRecurrentTask<Long> {

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @EJB
    private ReplicateXmlTaskLocal task;

    @EJB
    private AsyncWrapperLocal asw;

    /**
     * Inicializador.
     */
    @PostConstruct
    @Override
    public void init() {
        super.init();
        super.findTasks = () -> lookup(prv, REPLICATE, NEEDED, q -> q.setMaxResults(10000));
        super.tryLock = t -> lock(prv, t, REPLICATE, NEEDED, REPLICATE, REPLICATING);
        super.getId = t -> buildId(t, "declare");
        super.consumer = t -> asw.perform(() -> task.handle(t));
    }

    /**
     * Funcion recurrente.
     */
    @Schedule(hour = "*", minute = "*", second = "*/15", persistent = false)
    @Override
    protected void timeout() {
        super.timeout();
    }

}
