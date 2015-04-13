/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.summary;

import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface PullSummaryTaskLocal {

    void replicate(SummaryHeaderPK header);

}
