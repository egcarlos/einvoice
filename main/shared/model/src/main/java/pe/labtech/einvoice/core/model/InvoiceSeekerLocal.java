/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;

/**
 *
 * @author Carlos
 */
@Local
public interface InvoiceSeekerLocal extends Seeker {

    List<DocumentResponse> pullDocumentResponse(Document document);

    String pullDocumentResponse(Document document, String name);
}
