/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.List;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.replication.common.AbstractRecurrentTask;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.SeekHeaderLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PullInvoiceRecurrent extends AbstractRecurrentTask {

    @EJB
    private PullInvoiceTaskLocal task;

    @EJB
    private SeekHeaderLocal seeker;

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    public void doWork() {
        List<DocumentHeaderPK> ids = seeker.pullHeaders("A");
        ids.forEach(h -> {
            System.out.println(h);
            if (seeker.markForProcess(h, "A", "L")) {
                DocumentHeader head = seeker.findById(h);
                System.out.println(head);
                List<DocumentDetail> details = seeker.findDetails(h);
                details.forEach(d -> System.out.println(d));
                task.replicate(head, details);
            }
        });

    }

}
