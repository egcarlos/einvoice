/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * Interfaz PullInvoiceTaskLocal. Comportamiento en la implementación.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface PullInvoiceTaskLocal {

    /**
     *
     * Replica un documento de la base privada a la pública.
     *
     * @param id identificador del documento
     */
    void replicate(DocumentHeaderPK id);

    /**
     *
     * Replica un documento de la base privada a la pública.
     *
     * @param id identificador del documento
     * @param step paso interno
     * @param status estado interno
     */
    void replicate(DocumentHeaderPK id, String step, String status);

    /**
     *
     * Replica un documento de la base privada a la pública.
     *
     * @param header cabecera del documento
     * @param details detalle del documento
     */
    void replicate(DocumentHeader header, List<DocumentDetail> details);

    /**
     * Replica un documento de la base privada a la pública.
     *
     * @param header cabecera del documento
     * @param details detalle del documento
     * @param step paso interno
     * @param status estado interno
     */
    void replicate(DocumentHeader header, List<DocumentDetail> details, String step, String status);

}
