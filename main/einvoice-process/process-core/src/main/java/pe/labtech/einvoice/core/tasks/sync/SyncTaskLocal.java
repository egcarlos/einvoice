/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.sync;

import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;

/**
 * Clase SyncTaskLocal.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface SyncTaskLocal {

    /**
     * Sincroniza un documento.
     *
     * @param id identificador del documento.
     */
    void handle(Long id);

    /**
     * Sincroniza un documento.
     *
     * @param document documento
     */
    void handle(Document document);

}
