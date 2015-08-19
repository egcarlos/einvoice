/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.driver.ss;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import pe.labtech.einvoice.commons.jndi.JNDI;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentDetailPK_;
import pe.labtech.einvoice.replicator.entity.DocumentDetail_;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos Echeverria
 */
class FillTask implements Runnable {

    private static final Logger logger = Logger.getLogger(FillTask.class.getSimpleName());
    private final DocumentHeaderPK id;

    public FillTask(DocumentHeaderPK id) {
        this.id = id;
    }

    @Override
    public void run() {
        //recupera los datos del registro por medio de la base de datos privada
        DatabaseManager db = JNDI.getInstance().lookup("java:app/model-public/PublicDatabaseManager");
        DocumentHeader h = db.seekNT(e -> findHeader(e, id));

        logger.info(() -> MessageFormat.format(
                "Dispatching {0}-{1}-{2} (items:{3})",
                id.getNumeroDocumentoEmisor(),
                id.getTipoDocumento(),
                id.getSerieNumero(),
                h.getItem().size()
        ));

        //Servicio de ejecución para el encadenamiento asíncrono del
        //requerimiento migrar a un esquema de control centralizado
        ExecutorService remote = JNDI.getInstance().lookup("java:global/einvoice/executor/remote");
        //marca el origen del registro el cual es utilizado para devolver los
        //datos por medio del servicio web de entrada
        String source = JNDI.getInstance().lookup("java:global/einvoice/config/source");
        remote.submit(new DispatchTask(id, source, h));

    }

    private DocumentHeader findHeader(EntityManager e, DocumentHeaderPK pk) {
        DocumentHeader dh = e.find(DocumentHeader.class, pk);
        List<DocumentDetail> details = e.createQuery(createCriteriaFindDetails(e, pk)).getResultList();
        dh.setItem(details);
        return dh;
    }

    private CriteriaQuery<DocumentDetail> createCriteriaFindDetails(EntityManager e, DocumentHeaderPK pk) {
        CriteriaBuilder cb = e.getCriteriaBuilder();
        CriteriaQuery<DocumentDetail> cq = cb.createQuery(DocumentDetail.class);
        Root<DocumentDetail> d = cq.from(DocumentDetail.class);
        cq.where(
                cb.equal(d.get(DocumentDetail_.id).get(DocumentDetailPK_.tipoDocumentoEmisor), pk.getTipoDocumentoEmisor()),
                cb.equal(d.get(DocumentDetail_.id).get(DocumentDetailPK_.numeroDocumentoEmisor), pk.getNumeroDocumentoEmisor()),
                cb.equal(d.get(DocumentDetail_.id).get(DocumentDetailPK_.tipoDocumento), pk.getTipoDocumento()),
                cb.equal(d.get(DocumentDetail_.id).get(DocumentDetailPK_.serieNumero), pk.getSerieNumero())
        );
        cq.orderBy(cb.asc(d.get(DocumentDetail_.id).get(DocumentDetailPK_.numeroOrdenItem)));
        return cq;
    }

}
