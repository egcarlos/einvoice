/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replication.cancel;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.CancelDetail;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;

/**
 * Clase PullCancelTaskLocal.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface PullCancelTaskLocal {

    /**
     * Replica un RA de la base privada a la pública.
     *
     * @param id identificador del RA
     */
    void replicate(CancelHeaderPK id);

    /**
     * Replica un RA de la base privada a la pública.
     *
     * @param id identificador del RA
     * @param step paso interno
     * @param status estado interno
     */
    void replicate(CancelHeaderPK id, String step, String status);

    /**
     * Replica un RA de la base privada a la pública.
     *
     * @param header cabecera del RA
     * @param details detalle del RA
     */
    void replicate(CancelHeader header, List<CancelDetail> details);

    /**
     * Replica un RA de la base privada a la pública.
     *
     * @param header cabecera del RA
     * @param details detalle del RA
     * @param step paso interno
     * @param status estado interno
     */
    void replicate(CancelHeader header, List<CancelDetail> details, String step, String status);

}
