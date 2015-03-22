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
    public List<DocumentData> find(String status) {
        return em
                .createQuery(
                        "SELECT d FROM DocumentData d WHERE d.status = :status",
                        DocumentData.class
                )
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean changeStatus(DocumentData data, String oldstatus, String newstatus) {
        return em
                .createQuery(
                        "UPDATE DocumentData d SET d.status = :newstatus WHERE d.status = :oldstatus AND d.document.id = :id AND d.name = :name"
                )
                .setParameter("id", data.getDocument().getId())
                .setParameter("name", data.getName())
                .setParameter("oldstatus", oldstatus)
                .setParameter("newstatus", newstatus)
                .executeUpdate() == 1;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public byte[] readData(DocumentData data) {
        return em
                .createQuery(
                        "SELECT d FROM DocumentData d WHERE d.document.id = :id AND d.name = :name",
                        DocumentData.class
                )
                .setParameter("id", data.getDocument().getId())
                .setParameter("name", data.getName())
                .getSingleResult()
                .getData();
    }

    @Override
    public boolean addData(DocumentData data, byte[] rawdata) {
        return em
                .createQuery(
                        "UPDATE DocumentData d SET d.data = :data, d.replicate = TRUE WHERE d.document.id = :id AND d.name = :name"
                )
                .setParameter("id", data.getDocument().getId())
                .setParameter("name", data.getName())
                .setParameter("data", rawdata)
                .executeUpdate() == 1;
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
