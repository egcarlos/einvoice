/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;

/**
 *
 * @author Carlos
 */
@Stateless
public class SyncTask implements SyncTaskLocal {

    @EJB
    DocumentLoaderLocal loader;

    @Inject
    EBizGenericInvoker invoker;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Long id) {
        try {

            Document entity = loader.loadForWork(id, null);

            Builder b = new Builder();

            String request = b.buildQuery(entity.getClientId(), entity.getDocumentType(), entity.getDocumentNumber());
            loader.createEvent(entity, "QUERY_REQUEST", request);

            String response = invoker.invoke(request);
            loader.createEvent(entity, "QUERY_RESPONSE", response);

            Response r = b.unmarshall(Response.class, response);

            if (isInvalid(r)) {
                loader.markAsError(id);
                return;
            }

            DocumentInfo di = getDocumentInfo(r);
            if (isSigned(di)) {
                loader.markSigned(entity.getId(), di.getPdfFileUrl(), di.getXmlFileSignUrl(), di.getSignatureValue(), di.getHashCode());
            } else {
                loader.markAsError(id);
            }
        } catch (Exception ex) {
            loader.markAsError(id, ex);
        }

    }

    private static boolean isInvalid(Response response) {
        return response.getResponseBody() == null
                || response.getResponseBody().getXml() == null
                || response.getResponseBody().getXml().getDocuments() == null
                || response.getResponseBody().getXml().getDocuments().isEmpty();
    }

    /**
     *
     * @param di
     * @return
     */
    private static boolean isSigned(DocumentInfo di) {
        return "SIGNED".equals(di.getStatus());
    }

    private static DocumentInfo getDocumentInfo(Response response) {
        return response.getResponseBody().getXml().getDocuments().get(0);
    }

}
