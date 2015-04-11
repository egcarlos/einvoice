/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentAttribute;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.entity.ItemAttribute;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;

/**
 *
 * @author Carlos
 */
@Stateless
public class PullInvoiceTask implements PullInvoiceTaskLocal {

    static final Logger logger = Logger.getLogger(PullInvoiceTask.class.getName());

    @EJB
    private DocumentLoaderLocal loader;

    @Override
    @Asynchronous
    public void replicate(DocumentHeader header, List<DocumentDetail> details) {
        Document document = new Document();
        document.setClientId(header.getId().getTipoDocumentoEmisor() + "-" + header.getId().getNumeroDocumentoEmisor());
        document.setDocumentType(header.getId().getTipoDocumento());
        document.setDocumentNumber(header.getId().getSerieNumero());

        try {
            List<DocumentAttribute> attrs = new LinkedList<>();

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
        document.setStep("PULL");
        document.setStatus("LOADED");
        loader.save(document);
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
