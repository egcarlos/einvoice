/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
public class Tools {

    public static DocumentInfo invalid(String issuerType, String issuerId, String documentType, String documentNumber, String status) {
        //sin header setear el estado a missing y esperar
        DocumentInfo di = new DocumentInfo();
        di.setTipoDocumentoEmisor(issuerType);
        di.setNumeroDocumentoEmisor(issuerId);
        di.setDocumentType(documentType);
        di.setDocumentNumber(documentNumber);
        di.setStatus(status);
        return di;
    }

    public static void tryset(DocumentInfo di, String name, String value) {
        try {
            PropertyUtils.setProperty(di, name, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            //irrelevant since only valid properties will be mapped
            Logger.getLogger(RestHelper.class.getName()).log(Level.FINEST, ex, () -> "Invalid property " + name + " in DocumentInfo");
        }
    }

    public static void configureId(DocumentHeader content, String issuerType, String issuerId, String documentType, String documentNumber) {
        //Se arma el identificador del registro
        //se descarta cualquiera que se haya enviado en el contenido
        content.setId(
                new DocumentHeaderPK(
                        issuerType,
                        issuerId,
                        documentType,
                        documentNumber
                )
        );
    }
}
