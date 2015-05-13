/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.sign;

import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;

/**
 *
 * @author Carlos
 */
@Local
public interface SignTaskLocal {

    void handle(Document document);

    DocumentInfo handle(Long id);
}
