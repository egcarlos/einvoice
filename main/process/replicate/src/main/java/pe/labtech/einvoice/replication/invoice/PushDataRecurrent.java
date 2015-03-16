/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.model.InvoiceSeekerLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.model.SeekInvoiceInputLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushDataRecurrent extends AbstractRecurrentTask<DocumentData> {

    @EJB
    PublicDatabaseManagerLocal manager;

    @EJB
    PrivateDatabaseManagerLocal privateManager;

    @EJB
    private InvoiceSeekerLocal seeker;

    @EJB
    private SeekInvoiceInputLocal pusher;

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();

        this.findTasks = () -> privateManager.seek(e -> e
                .createNamedQuery(
                        "DocumentData.findPending",
                        DocumentData.class
                )
                .getResultList()
        );

        this.tryLock = t -> privateManager.seek(e -> e
                .createNamedQuery("DocumentData.tryLock")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );

        this.getId = t -> t.getDocument().getClientId() + "-" + t.getDocument().getDocumentType() + "-" + t.getDocument().getDocumentNumber() + "[" + t.getName() + "replicate]";
        this.consumer = t -> manager.handle(e -> {
            String targetField = mapName(t);
            if (targetField == null) {
                return;
            }

            String targetEntity;
            Object id;
            if (isInvoice(t)) {
                id = new DocumentHeaderPK(
                        t.getDocument().getClientId().split("-")[0],
                        t.getDocument().getClientId().split("-")[1],
                        t.getDocument().getDocumentType(),
                        t.getDocument().getDocumentNumber()
                );
                targetEntity = "DocumentHeader";
            } else if (isInvoiceSummary(t)) {
                id = new SummaryHeaderPK(
                        t.getDocument().getClientId().split("-")[0],
                        t.getDocument().getClientId().split("-")[1],
                        t.getDocument().getDocumentNumber()
                );
                targetEntity = "SummaryHeader";
            } else if (isCancelSummary(t)) {
                id = new CancelHeaderPK(
                        t.getDocument().getClientId().split("-")[0],
                        t.getDocument().getClientId().split("-")[1],
                        t.getDocument().getDocumentNumber()
                );
                targetEntity = "CancelHeader";
            } else {
                //invalid forged data
                return;
            }

            e
                    .createQuery("UPDATE " + targetEntity + " d SET d." + targetField + " = :param WHERE d.id = :id")
                    .setParameter("param", t.getData())
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

    private static boolean isCancelSummary(DocumentData t) {
        return t.getDocument().getDocumentNumber().startsWith("RA");
    }

    private static boolean isInvoiceSummary(DocumentData t) {
        return t.getDocument().getDocumentNumber().startsWith("RC");
    }

    private static boolean isInvoice(DocumentData t) {
        return t.getDocument().getDocumentNumber().startsWith("F") | t.getDocument().getDocumentNumber().startsWith("B");
    }

    private String mapName(DocumentData t) {
        switch (t.getName()) {
            case "pdfFileUrl":
                return "bl_pdf";
            case "xmlFileSignUrl":
                return "bl_xmlubl";
            case "xmlFileSunatUrl":
                return "bl_cdr";
            case "xmlText":
                return "bl_xml";
        }
        return null;
    }

}
