/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.replicate;

import javax.ejb.Local;

/**
 * Clase ReplicateXmlTaskLocal.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface ReplicateXmlTaskLocal {

    /**
     * Replica un documento.
     *
     * @param t identificador del documento.
     */
    void handle(Long t);

}
