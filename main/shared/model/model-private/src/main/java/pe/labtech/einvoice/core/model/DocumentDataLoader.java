/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.core.entity.DocumentData;

/**
 *
 * @author Carlos
 */
@Stateless
public class DocumentDataLoader implements DocumentDataLoaderLocal {

    private static final Logger logger = Logger.getLogger("DocumentDataLoader");

    @PersistenceContext(unitName = "einvoice_PU")
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentData> findMissing() {
        return em.createQuery(
                "SELECT d FROM DocumentData d WHERE d.status = 'MISSING'",
                DocumentData.class
        ).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean lock(DocumentData data) {
        return em
                .createQuery(
                        "UPDATE DocumentData d SET d.status = 'LOADING' WHERE d.status = 'MISSING' AND d.document.id = :id AND d.name = :name"
                )
                .setParameter("id", data.getDocument().getId())
                .setParameter("name", data.getName())
                .executeUpdate() == 1;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void release(DocumentData data, byte[] rawdata) {
        data = em.merge(data);
        data.setData(rawdata);
        data.setStatus("LOADED");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public byte[] download(URL url) {
        try (InputStream in = new BufferedInputStream(url.openStream())) {
            int b;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(524288);
            while ((b = in.read()) != -1) {
                bos.write(b);
            }
            return bos.toByteArray();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex, () -> "Unable to download " + url);
            return null;
        }
    }

}
