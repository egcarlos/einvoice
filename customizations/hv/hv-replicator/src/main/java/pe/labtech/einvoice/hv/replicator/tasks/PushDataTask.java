/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.hv.replicator.tasks;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.hv.replicator.entity.Header;

/**
 *
 * @author Carlos
 */
@Stateless
public class PushDataTask implements PushDataTaskLocal {

    @PersistenceContext(unitName = "pepsico_PU")
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void handle() {

        Logger.getLogger(this.getClass().getSimpleName()).info("Dispatching for data pushing");
        //accion a ejecutar con cada 
        Consumer<Document> forDocuments = (d) -> {
            d.setStatus("COMPLETE-PUSHED");

            em.createQuery("SELECT o FROM Header o WHERE o.ctipcomprobante = :type AND o.ccomprobante = :number", Header.class)
                    .setParameter("type", d.getDocumentType())
                    .setParameter("number", d.getDocumentNumber())
                    .getResultList()
                    .forEach(h -> {
                        h.setCrutapdf(d.getPdfURL());
                        h.setCrutaxml(d.getXmlURL());
                        h.setLgFirma(d.getSignature());
                        h.setLgFirmaHash(d.getHash());
                        h.setCestado('P');
                    });

        };

        List<Document> hs = em.createQuery("SELECT o FROM Document o where o.status = :status and o.step = :step", Document.class)
                .setParameter("status", "COMPLETE")
                .setParameter("step", "DECLARE")
                .getResultList();

        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "pushing items: {0}", hs.size());
        hs.forEach(forDocuments);

    }
}
