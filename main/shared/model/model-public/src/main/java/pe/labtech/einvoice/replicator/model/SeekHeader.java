/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
@Stateless
public class SeekHeader extends AbstractSeeker<DocumentHeader, DocumentHeaderPK, DocumentDetail> implements SeekHeaderLocal {

    @PersistenceContext(unitName = "replicator_PU")
    private EntityManager em;

    public SeekHeader() {
        super(DocumentHeader.class, DocumentHeaderPK.class, DocumentDetail.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected void setDetailParameters(TypedQuery<DocumentDetail> q, DocumentHeaderPK id) {
        q
                .setParameter("tipoDocumentoEmisor", id.getTipoDocumentoEmisor())
                .setParameter("numeroDocumentoEmisor", id.getNumeroDocumentoEmisor())
                .setParameter("tipoDocumento", id.getTipoDocumento())
                .setParameter("serieNumero", id.getSerieNumero());
    }

}
