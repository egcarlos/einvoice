/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replication.summary;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import pe.labtech.einvoice.commons.model.DocumentStep;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentAttribute;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.entity.ItemAttribute;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.SummaryDetail;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;
import pe.labtech.einvoice.replicator.model.SummaryDatabaseManagerLocal;

/**
 * Clase PullSummaryTask.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Stateless
public class PullSummaryTask implements PullSummaryTaskLocal {

    static final Logger logger = Logger.getLogger(PullSummaryTask.class.getName());

    @EJB
    private SummaryDatabaseManagerLocal pub;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @Resource(lookup = "java:global/einvoice/config/source")
    private String source;

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param id identificador del RC
     */
    @Override
    public void replicate(SummaryHeaderPK id) {
        replicate(id, DocumentStep.SIGN, DocumentStatus.NEEDED);
    }

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param id identificador del RC
     * @param step paso interno
     * @param status estado interno
     */
    @Override
    public void replicate(SummaryHeaderPK id, String step, String status) {
        SummaryHeader header = pub.seek(e -> e.find(SummaryHeader.class, id));
        List<SummaryDetail> details = pub.seek(e -> e
                .createQuery(
                        "SELECT o FROM SummaryDetail o WHERE o.id.tipoDocumentoEmisor = :tde AND o.id.numeroDocumentoEmisor = :nde AND o.id.resumenId = :ri",
                        SummaryDetail.class
                )
                .setParameter("tde", id.getTipoDocumentoEmisor())
                .setParameter("nde", id.getNumeroDocumentoEmisor())
                .setParameter("ri", id.getResumenId())
                .getResultList()
        );
        this.replicate(header, details, step, status);
    }

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param header cabecera del RC
     * @param details detalle del RC
     */
    @Override
    public void replicate(SummaryHeader header, List<SummaryDetail> details) {
        this.replicate(header, details, DocumentStep.SIGN, DocumentStatus.NEEDED);
    }

    /**
     * Replica un RC de la base privada a la pública.
     *
     * @param header cabecera del RC
     * @param details detalle del RC
     * @param step paso interno
     * @param status estado interno
     */
    @Override
    public void replicate(SummaryHeader header, List<SummaryDetail> details, String step, String status) {
        Document document = new Document();
        document.setClientId(header.getId().getTipoDocumentoEmisor() + "-" + header.getId().getNumeroDocumentoEmisor());
        document.setDocumentType("RC");
        document.setDocumentNumber(header.getId().getResumenId());
        document.setHash(source);

        try {
            List<DocumentAttribute> attrs = new LinkedList<>();

            /*
             * para garantizar que copie los datos del identificador
             */
            BeanUtils.describe(header.getId()).entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .filter(e -> !"class".equals(e.getKey()))
                    .map(e -> new DocumentAttribute(document, e.getKey(), e.getValue()))
                    .forEach(a -> attrs.add(a));

            BeanUtils.describe(header).entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .filter(e -> !"id".equals(e.getKey()))
                    .filter(e -> !"class".equals(e.getKey()))
                    .filter(e -> !e.getKey().startsWith("bl_"))
                    .map(e -> new DocumentAttribute(document, e.getKey(), e.getValue()))
                    .forEach(a -> attrs.add(a));

            document.setAttributes(attrs);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            logger.log(Level.SEVERE, null, ex);
            //TODO mark as error
        }

        List<Item> items = details.stream().map(detail -> {
            Item item = new Item();
            item.setDocument(document);
            item.setId(Long.parseLong(detail.getId().getNumeroFila().trim(), 10));

            try {
                List<ItemAttribute> attrs = BeanUtils.describe(detail).entrySet().stream()
                        .filter(e -> e.getValue() != null)
                        .filter(e -> !"id".equals(e.getKey()))
                        .filter(e -> !"class".equals(e.getKey()))
                        .filter(e -> !e.getKey().startsWith("bl_"))
                        .map(e -> new ItemAttribute(item, e.getKey(), e.getValue()))
                        .collect(Collectors.toList());
                attrs.add(new ItemAttribute(item, "numeroFila", "" + item.getId()));
                item.setAttributes(attrs);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                Logger.getLogger(PullSummaryTask.class.getName()).log(Level.SEVERE, null, ex);
                //TODO mark as error
            }
            return item;
        }).collect(Collectors.toList());

        document.setItems(items);
        document.setStep(step);
        document.setStatus(status);

        prv.handle(e -> e.persist(document));
    }

    DocumentAttribute generate(Object source, String property) {
        try {
            String value = BeanUtils.getProperty(source, property);
            return new DocumentAttribute(property, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            logger.log(Level.WARNING, null, ex);
            return new DocumentAttribute(property, (String) null);
        }
    }

}
