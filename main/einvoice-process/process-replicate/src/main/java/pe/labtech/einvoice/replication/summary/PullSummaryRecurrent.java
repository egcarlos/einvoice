/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.summary;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PullSummaryRecurrent extends AbstractRecurrentTask<SummaryHeaderPK> {

    @EJB
    private PullSummaryTaskLocal task;

    @EJB
    private PublicDatabaseManagerLocal db;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        this.findTasks = () -> db.seek(e -> e
                .createQuery(
                        "SELECT o.id FROM SummaryHeader o WHERE o.bl_estadoRegistro = 'A'",
                        SummaryHeaderPK.class
                )
                .getResultList());
        this.tryLock = t -> db.seek(e -> e
                .createQuery(
                        "UPDATE SummaryHeader o SET o.bl_estadoRegistro = 'L' WHERE o.bl_estadoRegistro = 'A' AND o.id = :id"
                )
                .setParameter("id", t)
                .executeUpdate() == 1
        );
        this.getId = t -> t.getTipoDocumentoEmisor() + "-" + t.getNumeroDocumentoEmisor() + "-" + t.getResumenId();
        this.consumer = t -> {
            task.replicate(t);
        };
    }

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }
}
