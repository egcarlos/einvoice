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

/**
 *
 * @author Carlos
 */
@Stateless
public class DeclareTask implements DeclareTaskLocal {

    @EJB
    DocumentLoaderLocal loader;

    @Inject
    EBizGenericInvoker invoker;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Long id) {
        try {
            Document document = loader.loadForWork(id, null);

            Builder builder = new Builder();
            String request = builder.buildDeclare(document.getClientId(), document.getDocumentType(), document.getDocumentNumber());

            loader.createEvent(document, "DECLARE_REQUEST", request);

            String response = invoker.invoke(request);

            loader.createEvent(document, "DECLARE_RESPONSE", response);

            //TODO create the document info element and analize
            loader.markDeclared(id);

        } catch (Exception ex) {
            loader.markAsError(id, ex);
        }
    }
}
