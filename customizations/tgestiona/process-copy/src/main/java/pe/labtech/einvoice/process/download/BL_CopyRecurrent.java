
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.process.download;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.model.DocumentDataLoaderLocal;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.*;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class BL_CopyRecurrent extends AbstractRecurrentTask<DocumentData> {

    @EJB
    DocumentDataLoaderLocal loader;

    @EJB
    PublicDatabaseManagerLocal db;

    public BL_CopyRecurrent() {
    }

    @EJB
    PublicDatabaseManagerLocal manager;

    private Map<Long, Document> lista;

    @Override
    @PostConstruct
    public void init() {
        lista = new HashMap<>();
        super.init();
        this.findTasks = () -> loader.find(DATA_LOADED);
        this.tryLock = t -> loader.changeStatus(t, DATA_LOADED, DATA_COPYING);
        this.getId = t -> t.getDocument().getClientId() + "-" + t.getDocument().getDocumentType() + "-" + t.getDocument().getDocumentNumber() + "[copy:" + t.getName() + "]";
        this.consumer = t -> {
            try {
                lista.put(t.getDocument().getId(), t.getDocument());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        };
    }

    //TODO ajustar el tiempo de llamada luego de pruebas
    @Override
    @Schedule(hour = "17", minute = "32", second = "00", persistent = false)
    protected void timeout() {
        super.timeout();
        try (BL_HandleFile hf = new BL_HandleFile()) {
            logger.log(Level.WARNING, () -> "tamanio de lsta.............." + lista.size());
            List listDocumentHeader = lista.values().stream().map(d -> getEinvoiceHeader(d)).filter(d -> d != null).collect(Collectors.toList());
            if (listDocumentHeader.isEmpty()) {
                return;
            }
            hf.setListDocument(listDocumentHeader);
            hf.connectToSftp();
            hf.generateFileAndSendFilesToSftp();
            logger.log(Level.WARNING, () -> "tamanio de listDocumentHeader.............." + listDocumentHeader.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex, () -> "error raised while sending via sftp. " + ex);
        } finally {
            lista.clear();
        }
    }

    private Object getEinvoiceHeader(Document t) {
        String targetEntity;
        Object id;

        if (isInvoice(t)) {
            id = new DocumentHeaderPK(
                    t.getClientId().split("-")[0],
                    t.getClientId().split("-")[1],
                    t.getDocumentType(),
                    t.getDocumentNumber()
            );
            targetEntity = "DocumentHeader";
        } else if (isInvoiceSummary(t)) {
            id = new SummaryHeaderPK(
                    t.getClientId().split("-")[0],
                    t.getClientId().split("-")[1],
                    t.getDocumentNumber()
            );
            targetEntity = "SummaryHeader";
        } else if (isCancelSummary(t)) {
            id = new CancelHeaderPK(
                    t.getClientId().split("-")[0],
                    t.getClientId().split("-")[1],
                    t.getDocumentNumber()
            );
            targetEntity = "CancelHeader";
        } else {
            //invalid forged data
            return null;
        }
        switch (targetEntity) {
            case "DocumentHeader":
                return db.seek(e -> e.find(DocumentHeader.class, id));
            case "SummaryHeader":
                return db.seek(e -> e.find(SummaryHeader.class, id));
            case "CancelHeader":
                return db.seek(e -> e.find(CancelHeader.class, id));
        }
        return null;
    }

    private static boolean isCancelSummary(Document t) {
        return t.getDocumentNumber().startsWith("RA");
    }

    private static boolean isInvoiceSummary(Document t) {
        return t.getDocumentNumber().startsWith("RC");
    }

    private static boolean isInvoice(Document t) {
        return t.getDocumentNumber().startsWith("F") | t.getDocumentNumber().startsWith("B");
    }
}
