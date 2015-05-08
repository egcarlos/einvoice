/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.cancel;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.CancelDetail;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface PullCancelTaskLocal {

    void replicate(CancelHeaderPK id);

    void replicate(CancelHeaderPK id, String step, String status);

    void replicate(CancelHeader header, List<CancelDetail> details);

    void replicate(CancelHeader header, List<CancelDetail> details, String step, String status);

}
