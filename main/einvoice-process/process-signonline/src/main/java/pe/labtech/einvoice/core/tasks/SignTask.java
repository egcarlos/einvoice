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
import pe.labtech.einvoice.commons.model.InvoiceType;
import static pe.labtech.einvoice.commons.model.InvoiceType.*;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.SecurityValues;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.tasks.offline.OfflineInvoice;
import pe.labtech.einvoice.core.tasks.online.OnlineInvoice;
import pe.labtech.einvoice.core.tasks.online.OnlineSummary;

/**
 *
 * @author Carlos
 */
@Stateless
public class SignTask implements SignTaskLocal {

    @EJB
    PrivateDatabaseManagerLocal db;
//    @EJB
//    private OfflineSummary offlineSummary;
    @EJB
    private OnlineSummary onlineSummary;
    @EJB
    private OfflineInvoice offlineInvoice;
    @EJB
    private OnlineInvoice onlineInvoice;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Document document) {
        sign(document);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DocumentInfo handle(Long id) {
        Document document = db.seek(e -> e.find(Document.class, id));
        return sign(document);
    }

    private DocumentInfo sign(Document document) {
        InvoiceType type = InvoiceType.getType(document.getDocumentNumber());
        SecurityValues sv = db.seek(e -> e.find(SecurityValues.class, document.getClientId()));
        if (sv != null) {
            //hay un keystore... firma offline
            switch (type) {
                case Document:
                    return this.offlineInvoice.handle(document);
                case Summary:
                case Cancel:
                    return this.onlineSummary.handle(document);
            }
        } else {
            //no hay un keystore... firma online
            switch (type) {
                case Document:
                    return this.onlineInvoice.handle(document);
                case Summary:
                case Cancel:
                    return this.onlineSummary.handle(document);
            }
        }
        return null;
    }
}
