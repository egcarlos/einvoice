/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtils;
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
    public void handle(Document document) {
        try {

            Builder b = new Builder();

            String request = b.buildQuery(document.getClientId().split("-")[1], document.getDocumentType(), document.getDocumentNumber());
            loader.createEvent(document, "QUERY_REQUEST", request);

            String response = invoker.invoke(request);
            loader.createEvent(document, "QUERY_RESPONSE", response);

            Response r = b.unmarshall(Response.class, response);

            if (isInvalid(r)) {
                loader.markAsError(document.getId());
                return;
            }

            DocumentInfo di = getDocumentInfo(r);
            Map<String, String> responses = describe(di);
            if (isSigned(di)) {
                final String status = di.getSunatStatus();
                final String step = document.getStep();
                if (step == null || "SIGN".equals(step)) {
                    //was in sign phase and now it's completed
                    loader.markSigned(document.getId(), "COMPLETE", di.getSignatureValue(), di.getHashCode(), responses);
                } else if ("DECLARE".equals(step)) {
                    if ((status != null && (status.startsWith("AC") || status.startsWith("RC")))) {
                        responses.put("recordStatus", "P");
                        loader.markSigned(document.getId(), "COMPLETE", di.getSignatureValue(), di.getHashCode(), responses);
                    } else {
                        //longtime sync since is not completed
                        loader.markSigned(document.getId(), "SYNC", di.getSignatureValue(), di.getHashCode(), responses);
                    }
                }
                //any other case is inconsistent and presumes model modified while running
            } else {
                loader.markSigned(document.getId(), "ERROR", di.getSignatureValue(), di.getHashCode(), responses);
            }
        } catch (Exception ex) {
            loader.markAsError(document.getId(), ex);
        }

    }

    private Map<String, String> describe(DocumentInfo di) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Map<String, String> responses = BeanUtils.describe(di).entrySet().stream()
                .filter(e -> !e.getKey().equals("class"))
                .filter(e -> e.getValue() != null)
                .collect(
                        Collectors.toMap(
                                e -> e.getKey(),
                                e -> e.getValue()
                        )
                );
        return responses;
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
