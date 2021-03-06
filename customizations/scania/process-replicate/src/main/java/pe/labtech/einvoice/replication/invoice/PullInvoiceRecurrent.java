/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PullInvoiceRecurrent extends AbstractRecurrentTask<DocumentHeaderPK> {

    @EJB
    private PullInvoiceTaskLocal task;

    @EJB
    private PublicDatabaseManagerLocal pub;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        this.findTasks = () -> pub.seek(e -> e
                .createQuery(
                        "SELECT O.id FROM DocumentHeader O WHERE o.bl_estadoRegistro = 'A' ORDER BY O.tipoDocumento ASC, O.serieNumero ASC",
                        DocumentHeaderPK.class
                )
                .setMaxResults(10000)
                .getResultList()
        );
        this.tryLock = t -> pub.seek(e -> e
                .createQuery(
                        "UPDATE DocumentHeader O SET o.bl_estadoRegistro = 'L' WHERE o.id = :id and o.bl_estadoRegistro = 'A'"
                )
                .setParameter("id", t)
                .executeUpdate() == 1
        );
        this.getId = t -> t.getHv_compania() + "-" + t.getHv_localidad() + "-" + t.getHv_tipoRecaudacion() + "-" + t.getHv_recaudacion();
        this.consumer = t -> task.replicate(t);
    }

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/1", persistent = false)
    public void timeout() {
        super.timeout();
    }
}
