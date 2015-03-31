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
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushResponseRecurrent extends AbstractRecurrentTask<DocumentResponse> {

    @EJB
    private PublicDatabaseManagerLocal publicDB;

    @EJB
    private PrivateDatabaseManagerLocal privateDB;

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();

        this.findTasks = () -> privateDB.seek(e -> e
                .createNamedQuery(
                        "DocumentResponse.findPending",
                        DocumentResponse.class
                )
                .getResultList()
        );

        this.tryLock = t -> privateDB.seek(e -> e
                .createNamedQuery("DocumentResponse.tryLock")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );

        this.getId = t -> t.getDocument().getClientId() + "-" + t.getDocument().getDocumentType() + "-" + t.getDocument().getDocumentNumber() + "[" + t.getName() + "replicate] <- " + t.getValue();
        this.consumer = t -> publicDB.handle(e -> {
            String targetField = mapName(t);
            if (targetField == null) {
                return;
            }

            String targetEntity;
            Object id;
            if (isInvoice(t)) {
                String tde = t.getDocument().getClientId().split("-")[0];
                String nde = t.getDocument().getClientId().split("-")[1];
                String td = t.getDocument().getDocumentType();
                String nd = t.getDocument().getDocumentNumber();

                e.createQuery("UPDATE DocumentHeader o SET o." + targetField + " = :param WHERE o.ctipdocumentoemisor = :tde and o.cdocumentoemisor = :nde and o.ctipcomprobante = :td and o.ccomprobante = :nd")
                        .setParameter("param", "cestado".equals(targetField) ? t.getValue().charAt(0) : t.getValue())
                        .setParameter("tde", tde.charAt(0))
                        .setParameter("nde", nde)
                        .setParameter("td", td)
                        .setParameter("nd", nd)
                        .executeUpdate();
            } else if (isInvoiceSummary(t)) {
                id = new SummaryHeaderPK(
                        t.getDocument().getClientId().split("-")[0],
                        t.getDocument().getClientId().split("-")[1],
                        t.getDocument().getDocumentNumber()
                );
                targetEntity = "SummaryHeader";
                e
                        .createQuery("UPDATE " + targetEntity + " d SET d." + targetField + " = :param WHERE d.id = :id")
                        .setParameter("param", t.getValue())
                        .setParameter("id", id)
                        .executeUpdate();
            } else if (isCancelSummary(t)) {
                id = new CancelHeaderPK(
                        t.getDocument().getClientId().split("-")[0],
                        t.getDocument().getClientId().split("-")[1],
                        t.getDocument().getDocumentNumber()
                );
                targetEntity = "CancelHeader";
                e
                        .createQuery("UPDATE " + targetEntity + " d SET d." + targetField + " = :param WHERE d.id = :id")
                        .setParameter("param", t.getValue())
                        .setParameter("id", id)
                        .executeUpdate();
            } else {
                //invalid forged data
                return;
            }

        });
    }

    private static boolean isCancelSummary(DocumentResponse t) {
        return t.getDocument().getDocumentNumber().startsWith("RA");
    }

    private static boolean isInvoiceSummary(DocumentResponse t) {
        return t.getDocument().getDocumentNumber().startsWith("RC");
    }

    private static boolean isInvoice(DocumentResponse t) {
        return t.getDocument().getDocumentNumber().startsWith("F") | t.getDocument().getDocumentNumber().startsWith("B");
    }

    private String mapName(DocumentResponse t) {

        switch (t.getName()) {
            case "hashCode":
                return "lgFirmaHash";
            case "integratedStatus":
                return "lgRecordStatus";
            case "recordStatus":
                return "cestado";
            case "pdfFileUrl":
                return "crutapdf";
            case "signatureValue":
                return "lgFirma";
            case "xmlFileSignUrl":
                return "crutaxml";
//            case "xmlFileSunatUrl":
//                return "bl_urlcdr";
            case "sunatMessage":
                return "lgServiceResponse";
            case "messages":
                return "lgLoadMessages";
        }
        return null;
    }

}
