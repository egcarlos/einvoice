/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.online;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.xml.ws.soap.SOAPFaultException;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.Tools;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.core.ws.model.DocumentItem;
import pe.labtech.einvoice.replicator.model.DatabaseManager;

/**
 *
 * @author Carlos
 */
@Stateless
@LocalBean
public class OnlineInvoice {

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private PrivateDatabaseManagerLocal db;

    @Inject
    private EBizGenericInvoker invoker;

    public DocumentInfo handle(Document document) {
        String request = buildSignCommand(document.getId());
        return Commons.handleOnline(db, loader, invoker, document, request);
    }

    private String buildSignCommand(Long id) {
        pe.labtech.einvoice.core.ws.model.Document target = new pe.labtech.einvoice.core.ws.model.Document();
        Document entity = loader.loadForWork(id, source -> {
            Commons.map(source, () -> target, () -> new DocumentItem(), (d, il) -> d.setItems(il));
        });
        return Commons.builder.buildSign(
                Tools.buildClientID(entity.getClientId()), entity.getDocumentType(),
                "PDF", true, true, false, "",
                Arrays.asList(target)
        );
    }
}
