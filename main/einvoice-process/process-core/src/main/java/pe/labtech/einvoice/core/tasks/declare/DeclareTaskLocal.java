/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.declare;

import javax.ejb.Local;

/**
 * Clase DeclareTaskLocal.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface DeclareTaskLocal {

    /**
     * Declara un documento.
     *
     * @param id identificador del documento
     */
    void handle(Long id);
}
