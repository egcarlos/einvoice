/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replication.summary;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.SummaryDetail;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;

/**
 * Clase PullSummaryTaskLocal.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface PullSummaryTaskLocal {

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param id identificador del RC
     */
    void replicate(SummaryHeaderPK id);

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param id identificador del RC
     * @param step paso interno
     * @param status estado interno
     */
    void replicate(SummaryHeaderPK id, String step, String status);

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param header cabecera del RC
     * @param details detalle del RC
     */
    void replicate(SummaryHeader header, List<SummaryDetail> details);

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param header cabecera del RC
     * @param details detalle del RC
     * @param step paso interno
     * @param status estado interno
     */
    void replicate(SummaryHeader header, List<SummaryDetail> details, String step, String status);

}
