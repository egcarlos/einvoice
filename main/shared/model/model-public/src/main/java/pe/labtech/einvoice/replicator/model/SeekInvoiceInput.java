/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
@Stateless
public class SeekInvoiceInput extends AbstractSeeker<DocumentHeader, DocumentHeaderPK, DocumentDetail> implements SeekInvoiceInputLocal {

    @PersistenceContext(unitName = "replicator_PU")
    private EntityManager em;

    public SeekInvoiceInput() {
        super(
                DocumentHeader.class,
                DocumentHeaderPK.class,
                DocumentDetail.class,
                (q, id) -> q
                .setParameter("tipoDocumentoEmisor", id.getTipoDocumentoEmisor())
                .setParameter("numeroDocumentoEmisor", id.getNumeroDocumentoEmisor())
                .setParameter("tipoDocumento", id.getTipoDocumento())
                .setParameter("serieNumero", id.getSerieNumero())
        );
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
