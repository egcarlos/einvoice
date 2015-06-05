/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.summary;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.SummaryDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PushSummaryRecurrent extends AbstractRecurrentTask<Long> {

    @EJB
    private SummaryDatabaseManagerLocal sum;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @Resource(lookup = "java:global/einvoice/config/source")
    private String source;

    @Resource(lookup = "java:global/einvoice/config/oss")
    private String oss;

    @Resource(lookup = "java:global/einvoice/config/dss")
    private String dss;

    /**
     * Bloquear una respuesta de documento para ser replicada
     */
    private Function<DocumentResponse, Boolean> tryLockSingle;

    /**
     * Retorna los document response pendientes de repplicar en un documento.
     */
    private Function<Long, List<DocumentResponse>> findTasksSingle;

    @Override
    @Schedule(hour = "*", minute = "*/1", second = "0", persistent = false)
    public void timeout() {
        super.timeout();
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();
        logger.info(() -> tm("Pushing documents to: " + (source == null ? "##DEFAULT" : source)));
        this.findTasks = () -> RecurrentHelper.lookupAllSourcedResponses(prv, source, "RC");
        this.findTasksSingle = t -> RecurrentHelper.lookupResponse(prv, DocumentResponse.class, t);
        this.tryLock = t -> true;
        this.tryLockSingle = t -> RecurrentHelper.lockResponse(prv, t.getDocument().getId(), t.getName());
        this.getId = t -> RecurrentHelper.buildId(t, "replicateSummary");
        this.consumer = t -> {
            SummaryHeaderPK id = createId(prv.seek(e -> e.find(Document.class, t)));
            Map<String, String> responses = this.findTasksSingle.apply(t).stream()
                    .filter(r -> this.tryLockSingle.apply(r))
                    .filter(r -> ModelTools.mapResponseName(r.getName()) != null)
                    .collect(Collectors.toMap(r -> ModelTools.mapResponseName(r.getName()), r -> r.getValue()));
            if (responses.isEmpty()) {
                return;
            }
            if ("yes".equals(dss) && responses.containsKey("bl_estadoRegistro")) {
                String s = responses.get("bl_estadoRegistro");
                if ("NALP".contains(s)) {
                    //noop
                } else if ("R".contains(s)) {
                    s = "P";
                } else {
                    s = "L";
                }
                responses.put("bl_estadoRegistro", s);
            }
            RecurrentHelper.sendResponses(sum, id, responses);
        };

    }

    private SummaryHeaderPK createId(Document t) {
        SummaryHeaderPK id = new SummaryHeaderPK(
                t.getClientId().split("-")[0],
                t.getClientId().split("-")[1],
                t.getDocumentNumber()
        );
        return id;
    }

}
