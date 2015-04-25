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
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface PullInvoiceTaskLocal {

    /**
     * Async method for document replication.
     *
     * @param id
     */
    void replicate(DocumentHeaderPK id);

    /**
     * Async method for document replication.
     *
     * @param id
     * @param step
     * @param status
     */
    void replicate(DocumentHeaderPK id, String step, String status);

    /**
     * Sync method for document replication.
     *
     * @param header
     * @param details
     */
    void replicate(DocumentHeader header, List<DocumentDetail> details);

    /**
     * Sync method for document replication.
     *
     * @param header
     * @param details
     * @param step
     * @param status
     */
    void replicate(DocumentHeader header, List<DocumentDetail> details, String step, String status);

}
