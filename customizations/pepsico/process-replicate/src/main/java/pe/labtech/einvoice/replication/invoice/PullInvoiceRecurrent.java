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
    private PublicDatabaseManagerLocal db;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        this.findTasks = () -> db.seek(
                e -> e.createQuery(
                        "SELECT o.headerPK from DocumentHeader o where o.cestado = :estado",
                        DocumentHeaderPK.class
                ).setParameter("estado", 'A')
                .getResultList()
        );
        this.tryLock = t -> db.seek(
                e -> e.createQuery(
                        "UPDATE DocumentHeader o SET o.cestado = :nuevoEstado where o.cestado = :viejoEstado and o.headerPK = :id"
                )
                .setParameter("nuevoEstado", 'L')
                .setParameter("viejoEstado", 'A')
                .setParameter("id", t)
                .executeUpdate() == 1
        );
        this.getId = t -> t.getCempresa().trim() + "-" + t.getCorden();
        this.consumer = t -> task.handle(t);
    }

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/1", persistent = false)
    public void timeout() {
        super.timeout();
    }
}
