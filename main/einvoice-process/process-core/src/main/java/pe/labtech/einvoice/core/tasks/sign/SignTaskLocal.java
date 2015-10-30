/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.sign;

import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;

/**
 * Clase SignTaskLocal.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Local
public interface SignTaskLocal {

    /**
     * Firma un documento.
     *
     * @param document documento
     */
    void handle(Document document);

    /**
     * Firma un documento.
     *
     * @param id identificador del documento
     * @return informacion del documento
     */
    DocumentInfo handle(Long id);
}
