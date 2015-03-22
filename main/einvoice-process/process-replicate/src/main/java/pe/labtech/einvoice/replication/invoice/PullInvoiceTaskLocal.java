/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;

/**
 *
 * @author Carlos
 */
@Local
public interface PullInvoiceTaskLocal {

    void replicate(DocumentHeader header, List<DocumentDetail> details);

}
