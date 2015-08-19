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
     * @param content
     * @param source
     * @return
     */
    DocumentInfo sign(DocumentHeader content, String source);

    /**
     *
     * @param content
     * @param source
     * @return
     */
    DocumentInfo sign(SummaryHeader content, String source);

    /**
     *
     * @param content
     * @param source
     * @return
     */
    DocumentInfo sign(CancelHeader content, String source);

}
