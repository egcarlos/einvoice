/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.bizlinks.replicator.tasks;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.bizlinks.replicator.entity.Detail;
import pe.labtech.einvoice.bizlinks.replicator.entity.Header;

/**
 *
 * @author Carlos
 */
@Stateless
public class PullDataTask implements PullDataTaskLocal {

    private static final String HEADER_QUERY = "Header.findReady";
    private static final String DETAIL_QUERY = "Detail.findByHeader";

    @PersistenceContext(unitName = "bizlinks_PU")
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void handle() {
        Logger.getLogger(this.getClass().getSimpleName()).fine("Dispatching for data pulling");

        Consumer<Header> forHeaders = (h) -> {
            h.setEstado("L");
            Document document = mapHeader(h);
            List<Item> items = mapItems(h, document);
            document.setItems(items);
            em.persist(document);
        };

        //REDUCING LOG
        List<Header> hs = em.createQuery(HEADER_QUERY, Header.class).getResultList();
        if (hs.isEmpty()) {
            return;
        }

        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "pulling items: {0}", hs.size());
        hs.forEach(forHeaders);
    }

    private Document mapHeader(Header h) {
        Document d = new Document();

        d.setClientId(h.getId().getNumeroDocumentoEmisor());
        d.setDocumentType(h.getId().getTipoDocumento());
        d.setDocumentNumber(h.getId().getSerieNumero());
        //TODO MAPEAR LOS ATRIBUTOS

        List<Item> items = mapItems(h, d);
        items.forEach(child -> child.setDocument(d));
        d.setItems(items);
        return d;
    }

    private List<Item> mapItems(Header h, Document document) {
        //como capturar el detalle
        List<Item> items = em
                .createNamedQuery(DETAIL_QUERY, Detail.class)
                //TODO setear parametros
                .setParameter("tipoDocumentoEmisor", h.getId().getTipoDocumentoEmisor())
                .setParameter("numeroDocumentoEmisor", h.getId().getNumeroDocumentoEmisor())
                .setParameter("tipoDocumento", h.getId().getTipoDocumento())
                .setParameter("serieNumero", h.getId().getSerieNumero())
                .getResultList()
                .stream()
                .map(d -> mapDetailToItem(d, document))
                .collect(Collectors.toList());
        return items;
    }

    private Item mapDetailToItem(Detail detail, Document document) {
        Item item = new Item();
        item.setDocument(document);
        item.setId(Long.parseLong(detail.getDetailPK().getNumeroOrdenItem().trim(), 10));
        //TODO MAPEAR CAMPOS
        return item;
    }

}
