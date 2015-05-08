/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import javax.ejb.Local;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;

/**
 *
 * @author Carlos
 */
@Local
public interface RestHelperLocal {

    /**
     *
     * @param issuerType
     * @param issuerId
     * @param documentType
     * @param documentNumber
     * @return
     */
    DocumentInfo find(String issuerType, String issuerId, String documentType, String documentNumber);

    /**
     *
     * @param issuerType
     * @param issuerId
     * @param documentType
     * @param documentNumber
     * @param content
     * @return
     */
    DocumentInfo sign(String issuerType, String issuerId, String documentType, String documentNumber, DocumentHeader content);

    DocumentInfo sign(String issuerType, String issuerId, String documentType, String documentNumber, SummaryHeader content);

    DocumentInfo sign(String issuerType, String issuerId, String documentType, String documentNumber, CancelHeader content);

}
