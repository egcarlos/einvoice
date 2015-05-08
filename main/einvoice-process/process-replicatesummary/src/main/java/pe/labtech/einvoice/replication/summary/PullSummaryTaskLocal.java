/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.summary;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.SummaryDetail;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface PullSummaryTaskLocal {

    void replicate(SummaryHeaderPK header);

    void replicate(SummaryHeaderPK header, String step, String status);

    void replicate(SummaryHeader header, List<SummaryDetail> details);

    void replicate(SummaryHeader header, List<SummaryDetail> details, String step, String status);

}
