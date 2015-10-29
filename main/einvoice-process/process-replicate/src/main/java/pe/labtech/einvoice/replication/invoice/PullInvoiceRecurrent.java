/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replication.invoice;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
 * Clase PullInvoiceRecurrent.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PullInvoiceRecurrent extends AbstractRecurrentTask<DocumentHeaderPK> {

    @EJB
    private PullInvoiceTaskLocal task;

    @EJB
    private PublicDatabaseManagerLocal pub;

    @Resource(lookup = "java:global/einvoice/config/source")
    private String source;

    /**
     * Inicializador.
     */
    @PostConstruct
    @Override
    public void init() {
        super.init();
        logger.info(() -> tm("Pulling documents from: " + (source == null ? "##DEFAULT" : source)));
        this.findTasks = () -> pub.seekNT(e -> e
                .createQuery(
                        "SELECT O.id FROM DocumentHeader O WHERE o.bl_estadoRegistro = 'A' ORDER BY O.id.tipoDocumento ASC, O.id.serieNumero",
                        DocumentHeaderPK.class
                )
                .setMaxResults(50)
                .getResultList()
        );
        this.tryLock = t -> pub.seek(e -> e
                .createQuery(
                        "UPDATE DocumentHeader O SET o.bl_estadoRegistro = 'L' WHERE o.id = :id and o.bl_estadoRegistro = 'A'"
                )
                .setParameter("id", t)
                .executeUpdate() == 1
        );
        this.getId = t -> t.getTipoDocumentoEmisor() + "-" + t.getNumeroDocumentoEmisor() + "-" + t.getTipoDocumento() + "-" + t.getSerieNumero();
        this.consumer = t -> task.replicate(t);
    }

    /**
     * Funcion recurrente.
     */
    @Override
    @Schedule(hour = "*", minute = "*", second = "*/2", persistent = false)
    public void timeout() {
        super.timeout();
    }
}
