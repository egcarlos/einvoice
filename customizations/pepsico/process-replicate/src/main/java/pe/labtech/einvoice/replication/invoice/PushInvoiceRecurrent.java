/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.commons.model.ModelTools;
import pe.labtech.einvoice.commons.model.RecurrentHelper;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushInvoiceRecurrent extends AbstractRecurrentTask<Long> {

    @EJB
    private PublicDatabaseManagerLocal pub;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    private Function<DocumentResponse, Boolean> tryLockSingle;

    private Function<Long, List<DocumentResponse>> findTasksSingle;

    @Override
    @Schedule(hour = "*", minute = "*", second = "10,30,50", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();

        this.findTasks = () -> RecurrentHelper.lookupAllResponses(prv, "F", "B");
        this.tryLock = t -> true;
        this.getId = t -> RecurrentHelper.buildId(t, "replicate");

        this.findTasksSingle = t -> RecurrentHelper.lookupResponse(prv, DocumentResponse.class, t);
        this.tryLockSingle = t -> RecurrentHelper.lockResponse(prv, t.getDocument().getId(), t.getName());

        this.consumer = t -> pub.handle(e -> {
            DocumentHeaderPK id = createId(pub, prv.seek(e1 -> e1.find(Document.class, t)));
            Map<String, Object> responses = this.findTasksSingle.apply(t).stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> mapName(r) != null)
                    .collect(
                            Collectors.toMap(
                                    r -> ModelTools.mapResponseName(r.getName()),
                                    r -> "cestado".equals(r.getValue()) ? r.getValue().charAt(0) : r.getValue()
                            )
                    );
            if (responses.isEmpty()) {
                return;
            }
            RecurrentHelper.sendResponses(pub, id, responses);
        });
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
            case "sunatMessage":
                return "lgServiceResponse";
            case "messages":
                return "lgLoadMessages";
        }
        return null;
    }

    private DocumentHeaderPK createId(DatabaseManager pub, Document d) {
        String it = d.getClientId().split("-")[0];
        String ii = d.getClientId().split("-")[1];
        String dt = d.getDocumentType();
        String dn = d.getDocumentNumber();
        List<DocumentHeaderPK> pks = pub.seek(e -> e
                .createQuery(
                        "SELECT O.headerPK FROM DocumentHeader O "
                        + "WHERE O.ctipdocumentoemisor = :it "
                        + "AND O.cdocumentoemisor = :ii "
                        + "AND O.ctipcomprobante = :dt "
                        + "AND O.ccomprobante = :dn",
                        DocumentHeaderPK.class
                )
                .setParameter("it", it)
                .setParameter("ii", ii)
                .setParameter("dt", dt)
                .setParameter("dn", dn)
                .getResultList()
        );
        if (pks.isEmpty()) {
            return null;
        }
        return pks.get(0);
    }

}
