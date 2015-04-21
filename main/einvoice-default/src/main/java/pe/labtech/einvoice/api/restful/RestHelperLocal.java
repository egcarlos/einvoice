/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import javax.ejb.Local;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.commons.Header;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos
 */
@Local
public interface RestHelperLocal {

    DocumentInfo buildDocumentInfo(Long id);

    Long findDocumentId(String issuerType, String issuerId, String documentType, String documentNumber);

    <T> T findPublic(Class<T> clazz, Object id);

    void cleanOldData(DocumentHeaderPK id);

    void saveNewData(Header content);

}
