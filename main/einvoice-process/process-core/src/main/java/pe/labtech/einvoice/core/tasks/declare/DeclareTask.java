/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.declare;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.tools.ServiceCommons;
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

    @EJB
    PrivateDatabaseManagerLocal prv;

    @Inject
    EBizGenericInvoker invoker;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Long id) {
        Document document = prv.seek(e -> e.find(Document.class, id));
        String request = buildDeclareCommand(document);
        ServiceCommons.declare(prv, loader, invoker, document, request);
    }

    private String buildDeclareCommand(Document document) {
        return new Builder().buildDeclare(document.getClientId().split("-")[1], document.getDocumentType(), document.getDocumentNumber());
    }
}
