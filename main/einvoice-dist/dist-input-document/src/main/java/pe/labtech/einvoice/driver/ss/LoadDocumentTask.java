/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.driver.ss;

import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import pe.labtech.einvoice.commons.jndi.JNDI;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK_;
import pe.labtech.einvoice.replicator.entity.DocumentHeader_;

/**
 *
 * @author Carlos Echeverria
 */
public class LoadDocumentTask implements Runnable {

    private int batch;
    private String source;
    private String target;

    public LoadDocumentTask() {
        this(1000, "A", "L");
    }

    public LoadDocumentTask(int batch, String source, String target) {
        this.batch = batch;
        this.source = source;
        this.target = target;
    }

    @Override
    public void run() {
        DatabaseManager db = JNDI.getInstance().lookup("java:app/model-public/PublicDatabaseManager");
        ExecutorService executor = JNDI.getInstance().lookup("java:global/einvoice/executor/dispatch");

        List<DocumentHeaderPK> pks = db.seekNT(e -> e
                .createQuery(createCriteriaFindKeys(e))
                .setMaxResults(this.batch)
                .getResultList()
        );

        if (pks.isEmpty()) {
            return;
        }

        pks.stream()
                .filter(
                        pk -> db.seek(e -> e
                                .createQuery(createCirteriaLock(e, pk))
                                .executeUpdate() == 1
                        )
                )
                .map(FillTask::new)
                .forEach(executor::submit);

    }

    private CriteriaQuery<DocumentHeaderPK> createCriteriaFindKeys(EntityManager e) {
        CriteriaBuilder cb = e.getCriteriaBuilder();
        CriteriaQuery<DocumentHeaderPK> cq = cb.createQuery(DocumentHeaderPK.class);
        Root<DocumentHeader> h = cq.from(DocumentHeader.class);
        cq.select(h.get(DocumentHeader_.id));
        cq.where(cb.equal(h.get(DocumentHeader_.bl_estadoRegistro), this.source));
        cq.orderBy(
                cb.asc(h.get(DocumentHeader_.id).get(DocumentHeaderPK_.tipoDocumentoEmisor)),
                cb.asc(h.get(DocumentHeader_.id).get(DocumentHeaderPK_.numeroDocumentoEmisor)),
                cb.asc(h.get(DocumentHeader_.id).get(DocumentHeaderPK_.tipoDocumento)),
                cb.asc(h.get(DocumentHeader_.id).get(DocumentHeaderPK_.serieNumero))
        );
        return cq;
    }

    private CriteriaUpdate<DocumentHeader> createCirteriaLock(EntityManager e, DocumentHeaderPK pk) {
        CriteriaBuilder cb = e.getCriteriaBuilder();
        CriteriaUpdate<DocumentHeader> cq = cb.createCriteriaUpdate(DocumentHeader.class);
        Root<DocumentHeader> h = cq.from(DocumentHeader.class);
        cq.set(h.get(DocumentHeader_.bl_estadoRegistro), this.target);
        cq.where(
                cb.equal(h.get(DocumentHeader_.id), pk),
                cb.equal(h.get(DocumentHeader_.bl_estadoRegistro), this.source)
        );
        return cq;
    }

}
