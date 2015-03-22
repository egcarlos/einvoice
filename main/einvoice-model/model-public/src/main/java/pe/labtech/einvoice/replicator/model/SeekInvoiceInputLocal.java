/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface SeekInvoiceInputLocal extends Seeker<DocumentHeader, DocumentHeaderPK, DocumentDetail> {

}
