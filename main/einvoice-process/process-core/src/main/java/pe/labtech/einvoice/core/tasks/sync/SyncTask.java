/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.sync;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import static pe.labtech.einvoice.core.tasks.tools.ServiceCommons.signOnlineOrSync;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;

/**
 *
 * @author Carlos
 */
@Stateless
public class SyncTask implements SyncTaskLocal {

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private PrivateDatabaseManagerLocal db;

    @Inject
    private EBizGenericInvoker invoker;

    @Override
    public void handle(Long id) {
        Document d = db.seek(e -> e.find(Document.class, id));
        this.handle(d);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Document document) {
        String request = buildSyncCommand(document);
        signOnlineOrSync(db, loader, invoker, document, request);
    }

    public String buildSyncCommand(Document document) {
        return new Builder()
                .buildQuery(
                        document.getClientId().split("-")[1],
                        document.getDocumentType(),
                        document.getDocumentNumber()
                );
    }

}
