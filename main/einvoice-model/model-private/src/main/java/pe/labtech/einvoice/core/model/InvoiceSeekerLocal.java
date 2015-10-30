/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.model;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;

/**
 * Clase InvoiceSeekerLocal.
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
@Local
public interface InvoiceSeekerLocal extends Seeker {

    List<DocumentResponse> pullDocumentResponse(Document document);

    String pullDocumentResponse(Document document, String name);
}
