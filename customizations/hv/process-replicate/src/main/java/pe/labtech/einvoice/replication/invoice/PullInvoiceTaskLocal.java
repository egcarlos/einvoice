/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface PullInvoiceTaskLocal {

    void handle(DocumentHeaderPK id);
}
