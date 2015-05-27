/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import pe.labtech.einvoice.commons.model.DocumentStep;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentAttribute;
import pe.labtech.einvoice.core.entity.DocumentAuxiliar;
import pe.labtech.einvoice.core.entity.DocumentLegend;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.entity.ItemAttribute;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Stateless
public class PullInvoiceTask implements PullInvoiceTaskLocal {

    static final Logger logger = Logger.getLogger(PullInvoiceTask.class.getName());

    @EJB
    private PublicDatabaseManagerLocal pub;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @Resource(lookup = "java:global/einvoice/config/source")
    private String source;

    @Override
    public void replicate(DocumentHeader header, List<DocumentDetail> details, String step, String status) {
        Document document = new Document();
        document.setClientId(header.getId().getTipoDocumentoEmisor() + "-" + header.getId().getNumeroDocumentoEmisor());
        document.setDocumentType(header.getId().getTipoDocumento());
        document.setDocumentNumber(header.getId().getSerieNumero());
        document.setHash(source);

        try {
            List<DocumentAttribute> attrs = new LinkedList<>();
            List<DocumentAuxiliar> auxs = new LinkedList<>();
            List<DocumentLegend> legs = new LinkedList<>();

            //mapeo automático de la llave primaria
            BeanUtils.describe(header.getId()).entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .filter(e -> !"class".equals(e.getKey()))
                    .map(e -> new DocumentAttribute(document, e.getKey(), e.getValue()))
                    .forEach(a -> attrs.add(a));

            //mapeo automático del cuerpo
            final Map<String, String> bean = BeanUtils.describe(header);
            bean.forEach((key, value) -> {
                if (skippedKey(key)) {
                    return;
                }
                if (StringUtils.trimToNull(value) == null) {
                    return;
                }
                logger.info(() -> MessageFormat.format("property({0},{1})", key, value));
                if (key.startsWith("codigoLeyenda")) {
                    Long order = Long.parseLong(key.split("_")[1]);
                    DocumentLegend dl = buildLegend(bean, order, document);
                    if (dl.getValue() != null) {
                        legs.add(dl);
                    }
                } else if (key.startsWith("codigoAuxiliar")) {
                    String s = key.substring(14);
                    String length = s.split("_")[0];
                    Long order = Long.parseLong(s.split("_")[1]);
                    DocumentAuxiliar da = buildAuxiliar(bean, length, order, document);
                    if (da.getValue() != null) {
                        auxs.add(da);
                    }
                } else {
                    final DocumentAttribute da = new DocumentAttribute(document, key, StringUtils.trimToNull(value));
                    if (da.getValue() != null) {
                        attrs.add(da);
                    }
                }
            });

            document.setAttributes(attrs);
            document.setAuxiliars(auxs);
            document.setLegends(legs);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            logger.log(Level.SEVERE, null, ex);
            //TODO mark as error... y decidir que hacer luego con el mapeo!
        }

        List<Item> items = details.stream().map(detail -> {
            Item item = new Item();
            item.setDocument(document);
            item.setId(Long.parseLong(detail.getId().getNumeroOrdenItem().trim(), 10));

            try {
                List<ItemAttribute> attrs = BeanUtils.describe(detail).entrySet().stream()
                        .filter(e -> e.getValue() != null)
                        .filter(e -> !"id".equals(e.getKey()))
                        .filter(e -> !"class".equals(e.getKey()))
                        .filter(e -> !e.getKey().startsWith("bl_"))
                        .map(e -> new ItemAttribute(item, e.getKey(), e.getValue()))
                        .collect(Collectors.toList());
                attrs.add(new ItemAttribute(item, "numeroOrdenItem", "" + item.getId()));
                item.setAttributes(attrs);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                Logger.getLogger(PullInvoiceTask.class.getName()).log(Level.SEVERE, null, ex);
                //TODO mark as error
            }
            return item;
        }).collect(Collectors.toList());
        document.setItems(items);
        document.setStep(step);
        document.setStatus(status);

        prv.handle(e -> e.persist(document));

    }

    public static boolean skippedKey(String k) {
        return "id".equals(k) || "class".equals(k) || k.startsWith("bl_") || k.startsWith("textoLeyenda") || k.startsWith("textoAdicionalLeyenda") || k.startsWith("textoAuxiliar");
    }

    private DocumentAuxiliar buildAuxiliar(final Map<String, String> bean, String length, Long order, Document document) {
        DocumentAuxiliar da = new DocumentAuxiliar(
                bean.get("codigoAuxiliar" + length + "_" + order),
                length,
                order,
                bean.get("textoAuxiliar" + length + "_" + order)
        );
        da.setDocument(document);
        return da;
    }

    private DocumentLegend buildLegend(final Map<String, String> bean, Long order, Document document) {
        DocumentLegend dl = new DocumentLegend(
                bean.get("codigoLeyenda_" + order),
                order,
                bean.get("textoLeyenda_" + order),
                bean.get("textoAdicionalLeyenda_" + order)
        );
        dl.setDocument(document);
        return dl;
    }

    @Override
    public void replicate(DocumentHeaderPK id, String step, String status) {
        DocumentHeader header = pub.seek(e -> e.find(DocumentHeader.class, id));
        List<DocumentDetail> details = pub.seek(e -> e
                .createQuery(
                        "SELECT O FROM DocumentDetail O WHERE O.id.tipoDocumentoEmisor = :tde AND O.id.numeroDocumentoEmisor = :nde AND O.id.tipoDocumento = :td AND O.id.serieNumero = :sn",
                        DocumentDetail.class
                )
                .setParameter("tde", id.getTipoDocumentoEmisor())
                .setParameter("nde", id.getNumeroDocumentoEmisor())
                .setParameter("td", id.getTipoDocumento())
                .setParameter("sn", id.getSerieNumero())
                .getResultList());
        this.replicate(header, details, step, status);
    }

    @Override
    public void replicate(DocumentHeader header, List<DocumentDetail> details) {
        this.replicate(header, details, DocumentStep.SIGN, DocumentStatus.NEEDED);
    }

    @Override
    public void replicate(DocumentHeaderPK id) {
        this.replicate(id, DocumentStep.SIGN, DocumentStatus.NEEDED);
    }

}
