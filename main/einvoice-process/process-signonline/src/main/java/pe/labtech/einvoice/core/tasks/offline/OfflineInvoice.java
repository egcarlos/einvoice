/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.offline;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;

/**
 *
 * @author Carlos
 */
@Stateless
@LocalBean
public class OfflineInvoice {

    public DocumentInfo handle(Document document) {
        return null;
    }

}
