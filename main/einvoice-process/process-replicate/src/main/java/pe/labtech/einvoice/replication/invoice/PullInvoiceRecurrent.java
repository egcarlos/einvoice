/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.SeekInvoiceInputLocal;

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
    private SeekInvoiceInputLocal seeker;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        this.findTasks = () -> seeker.pullHeaders("A");
        this.tryLock = t -> seeker.markForProcess(t, "A", "L");
        this.getId = t -> t.getTipoDocumentoEmisor() + "-" + t.getNumeroDocumentoEmisor() + "-" + t.getTipoDocumento() + "-" + t.getSerieNumero();
        this.consumer = t -> {
            DocumentHeader head = seeker.findById(t);
            System.out.println(head);
            List<DocumentDetail> details = seeker.findDetails(t);
            details.forEach(d -> System.out.println(d));
            task.replicate(head, details);
        };
    }

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }
}
