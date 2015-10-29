/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replication.summary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.model.ModelTools;
import pe.labtech.einvoice.commons.model.RecurrentHelper;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.SummaryDatabaseManagerLocal;

/**
 * Clase PushSummaryDataRecurrent.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushSummaryDataRecurrent extends AbstractRecurrentTask<DocumentData> {

    @EJB
    SummaryDatabaseManagerLocal manager;

    @EJB
    PrivateDatabaseManagerLocal privateManager;

    /**
     * Funcion recurrente.
     */
    @Override
    @Schedule(hour = "*/1", minute = "0", second = "0", persistent = false)
    public void timeout() {
        super.timeout();
    }

    /**
     * Inicializador.
     */
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

        this.getId = t -> RecurrentHelper.buildId(t.getDocument().getId(), "replicateData", t.getName());

        this.consumer = t -> manager.handle(e -> {
            String targetField = ModelTools.mapDataName(t.getName());
            if (targetField == null) {
                return;
            }

            final Document d = t.getDocument();

            SummaryHeaderPK id = new SummaryHeaderPK(
                    d.getClientId().split("-")[0],
                    d.getClientId().split("-")[1],
                    d.getDocumentNumber()
            );

            Map<String, byte[]> responses = new HashMap<>();
            responses.put(targetField, t.getData());

            RecurrentHelper.sendResponses(manager, id, responses);
        });
    }

}
