/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.summary;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import static pe.labtech.einvoice.commons.recurrent.RecurrentTask.buildTaskId;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import static pe.labtech.einvoice.replicator.commons.Tools.*;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.SummaryDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushSummaryDataRecurrent extends AbstractRecurrentTask<DocumentData> {

    @EJB
    SummaryDatabaseManagerLocal manager;

    @EJB
    PrivateDatabaseManagerLocal privateManager;

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
                .createQuery(
                        "SELECT o FROM DocumentData o WHERE o.document.documentNumber LIKE 'RC%' AND o.replicate = TRUE AND o.data <> NULL",
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

        this.getId = t -> buildTaskId(
                t.getDocument().getClientId(),
                t.getDocument().getDocumentType(),
                t.getDocument().getDocumentNumber(),
                "replicate",
                t.getName());

        this.consumer = t -> manager.handle(e -> {
            String targetField = mapResponseName(t.getName());
            if (targetField == null) {
                return;
            }

            final Document d = t.getDocument();

            SummaryHeaderPK id = new SummaryHeaderPK(
                    d.getClientId().split("-")[0],
                    d.getClientId().split("-")[1],
                    d.getDocumentNumber()
            );

            e
                    .createQuery("UPDATE SummaryHeader d SET d." + targetField + " = :param WHERE d.id = :id")
                    .setParameter("param", t.getData())
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

}
