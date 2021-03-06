/*
 * Producto elaborado para Alignet S.A.C.
 *
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
import pe.labtech.einvoice.commons.model.ModelTools;
import pe.labtech.einvoice.commons.model.RecurrentHelper;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 * Clase PushDocumentDataRecurrent.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushDocumentDataRecurrent extends AbstractRecurrentTask<DocumentData> {

    @EJB
    PublicDatabaseManagerLocal pub;

    @EJB
    PrivateDatabaseManagerLocal prv;

    /**
     * Funcion recurrente.
     */
    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
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

        this.findTasks = () -> prv.seekNT(e -> e
                .createQuery(
                        "SELECT o FROM DocumentData o WHERE (o.document.documentNumber LIKE 'F%' OR o.document.documentNumber LIKE 'B%') AND o.replicate = TRUE AND o.data <> NULL",
                        DocumentData.class
                )
                .getResultList()
        );

        this.tryLock = t -> prv.seek(e -> e
                .createQuery("UPDATE DocumentData o SET o.replicate = FALSE WHERE o.replicate = TRUE AND o.document = :document AND o.name = :name")
                .setParameter("document", t.getDocument())
                .setParameter("name", t.getName())
                .executeUpdate() == 1
        );

        this.getId = t -> RecurrentHelper.buildId(t.getDocument().getId(), "replicateData", t.getName());

        this.consumer = t -> pub.handle(e -> {
            String targetField = ModelTools.mapDataName(t.getName());
            if (targetField == null) {
                return;
            }

            final Document d = t.getDocument();

            DocumentHeaderPK id = new DocumentHeaderPK(
                    d.getClientId().split("-")[0],
                    d.getClientId().split("-")[1],
                    d.getDocumentType(),
                    d.getDocumentNumber()
            );

            e
                    .createQuery("UPDATE DocumentHeader d SET d." + targetField + " = :param WHERE d.id = :id")
                    .setParameter("param", t.getData())
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

}
