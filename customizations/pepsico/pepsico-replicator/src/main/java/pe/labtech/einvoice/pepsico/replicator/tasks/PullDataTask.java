/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.pepsico.replicator.tasks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentAttribute;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.entity.ItemAttribute;
import pe.labtech.einvoice.core.entity.ItemAuxiliar;
import pe.labtech.einvoice.core.entity.ValueHolder;
import pe.labtech.einvoice.pepsico.replicator.entity.Detail;
import pe.labtech.einvoice.pepsico.replicator.entity.Header;

/**
 *
 * @author Carlos
 */
@Stateless
public class PullDataTask implements PullDataTaskLocal {

    private static final String HEADER_QUERY = "SELECT o FROM Header o WHERE o.cestado = 'A'";
    private static final String DETAIL_QUERY = "SELECT o "
            + "FROM Detail o "
            + "WHERE "
            + " o.detailPK.cempresa = :empresa "
            + " and o.detailPK.corden = :orden "
            + "ORDER BY"
            + " o.detailPK.did";

    @PersistenceContext(unitName = "pepsico_PU")
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void handle() {
        Logger.getLogger(this.getClass().getSimpleName()).fine("Dispatching for data pulling");
        //accion a ejecutar con cada 
        Consumer<Header> forHeaders = (h) -> {
            h.setCestado('L');
            Document document = mapHeader(h);
            List<Item> items = mapItems(h, document);
            document.setItems(items);
            em.persist(document);
        };

        List<Header> hs = em.createQuery(HEADER_QUERY, Header.class)
                .getResultList();
        if (hs.isEmpty()) {
            return;
        }
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "pulling items: {0}", hs.size());
        hs.forEach(forHeaders);
    }

    private Document mapHeader(Header h) {
        Document d = new Document();
        d.setClientId(h.getCdocumentoemisor());
        d.setDocumentType(h.getCtipcomprobante());
        d.setDocumentNumber(h.getCcomprobante());
        //creacion de los atributos
        List<DocumentAttribute> da = Arrays.asList(
                new DocumentAttribute("indicador", "C"),
                new DocumentAttribute("tipoDocumentoEmisor", h.getCtipdocumentoemisor()),
                new DocumentAttribute("numeroDocumentoEmisor", h.getCdocumentoemisor()),
                new DocumentAttribute("razonSocialEmisor", h.getCrsocialemisor()),
                new DocumentAttribute("nombreComercialEmisor", h.getCncomercialemisor()),
                new DocumentAttribute("tipoDocumento", h.getCtipcomprobante()),
                new DocumentAttribute("serieNumero", h.getCcomprobante()),
                new DocumentAttribute("fechaEmision", h.getCfemision()),
                new DocumentAttribute("ubigeoEmisor", h.getCubigeoemisor()),
                new DocumentAttribute("direccionEmisor", h.getCdireccionemisor()),
                new DocumentAttribute("urbanizacion", h.getCurbanizacionemisor()),
                new DocumentAttribute("distritoEmisor", h.getCdistritoemisor()),
                new DocumentAttribute("provinciaEmisor", h.getCprovinciaemisor()),
                new DocumentAttribute("departamentoEmisor", h.getCdepartamentoemisor()),
                new DocumentAttribute("paisEmisor", h.getCcodpaisemisor()),
                new DocumentAttribute("correoEmisor", h.getCcorreoemisor()),
                new DocumentAttribute("codigoSerieNumeroAfectado", h.getCtipncreditodebito()),
                new DocumentAttribute("serieNumeroAfectado", h.getCcomprobanteafecto()),
                new DocumentAttribute("tipoDocumentoAdquiriente", h.getCtipdocumentousuario()),
                new DocumentAttribute("numeroDocumentoAdquiriente", h.getCdocumentousuario()),
                new DocumentAttribute("razonSocialAdquiriente", h.getCrsocialusuario()),
                new DocumentAttribute("lugarDestino", h.getCdireccionusuario()),
                new DocumentAttribute("correoAdquiriente", h.getCcorreousuario()),
                new DocumentAttribute("tipoMoneda", h.getCmoneda()),
                new DocumentAttribute("motivoDocumento", h.getCmotncreditodebito()),
                new DocumentAttribute("totalValorVentaNetoOpGravadas", h.getCvventagravada()),
                new DocumentAttribute("totalValorVentaNetoOpNoGravada", h.getCvventanogravada()),
                new DocumentAttribute("totalValorVentaNetoOpExoneradas", h.getCvventaexonerada()),
                new DocumentAttribute("totalValorVentaNetoOpGratuitas", h.getCvventagratuita()),
                new DocumentAttribute("subTotal", h.getCsubtotal()),
                new DocumentAttribute("totalIgv", h.getCigv()),
                new DocumentAttribute("totalIsc", h.getCisc()),
                new DocumentAttribute("totalOtrosTributos", h.getCtributo()),
                new DocumentAttribute("totalOtrosCargos", h.getCcargo()),
                new DocumentAttribute("totalDescuentos", h.getCdescuento()),
                new DocumentAttribute("descuentosGlobales", h.getCdescuentoglobal()),
                new DocumentAttribute("totalVenta", h.getCtotal()),
                new DocumentAttribute("tipoMoneda", h.getCmonedancreditodebito()),
                new DocumentAttribute("tipoDocumentoReferenciaPrincipal", h.getCtipncreditodebito1()),
                new DocumentAttribute("numeroDocumentoReferenciaPrincipal", h.getCcomprobanteafecto1()),
                new DocumentAttribute("tipoDocumentoReferenciaCorregido", h.getCtipncreditodebito2()),
                new DocumentAttribute("numeroDocumentoReferenciaCorregido", h.getCcomprobanteafecto2()),
                new DocumentAttribute("baseImponiblePercepcion", h.getCbasepercepcion()),
                new DocumentAttribute("totalPercepcion", h.getCtotalpercepcion()),
                new DocumentAttribute("totalVentaConPercepcion", h.getCventapercepcion()),
                new DocumentAttribute("porcentajePercepcion", h.getCporcpercepcion()),
                new DocumentAttribute("totalRetencion", h.getCtotalretencion()),
                new DocumentAttribute("porcentajeRetencion", h.getCporcretencion()),
                new DocumentAttribute("totalDetraccion", h.getCtotaldetraccion()),
                new DocumentAttribute("porcentajeDetraccion", h.getCporcdetraccion()),
                new DocumentAttribute("descripcionDetraccion", h.getCdescdetraccion()),
                new DocumentAttribute("valorReferencialDetraccion", h.getCvalordetraccion()),
                new DocumentAttribute("totalBonificacion", h.getCbonificacion()),
                new DocumentAttribute("inHabilitado", h.getChabilitado())
        )
                .stream()
                .filter(a -> updateValue(a) != null)
                .collect(Collectors.toList());

        d.setAttributes(da);
        d.getAttributes().forEach(child -> child.setDocument(d));
        List<Item> items = mapItems(h, d);
        items.forEach(child -> child.setDocument(d));
        d.setItems(items);
        return d;
    }

    private List<Item> mapItems(Header h, Document document) {
        //como capturar el detalle
        List<Item> items = em
                .createQuery(DETAIL_QUERY, Detail.class)
                .setParameter("empresa", h.getHeaderPK().getCempresa())
                .setParameter("orden", h.getHeaderPK().getCorden())
                .getResultList()
                .stream()
                .map(d -> mapDetailToItem(d, document))
                .collect(Collectors.toList());
        return items;
    }

    private Item mapDetailToItem(Detail detail, Document document) {
        Item item = new Item();
        item.setDocument(document);
        item.setId(Long.parseLong(detail.getDetailPK().getDid().trim(), 10));

        List<ItemAttribute> attr = Arrays.asList(
                new ItemAttribute("numeroOrdenItem", detail.getDetailPK().getDid()),
                new ItemAttribute("codigoProducto", detail.getDetailPK().getDcodproducto()),
                new ItemAttribute("descripcion", detail.getDdesproducto()),
                new ItemAttribute("cantidad", detail.getDcanproducto()),
                new ItemAttribute("unidadMedida", detail.getDuniproducto()),
                new ItemAttribute("importeUnitarioSinImpuesto", detail.getDvventaunitario()),
                new ItemAttribute("importeUnitarioConImpuesto", detail.getDpventaunitario()),
                new ItemAttribute("codigoImporteUnitarioConImpuesto", detail.getDcodpventaunitario()),
                new ItemAttribute("importeReferencial", detail.getDireferenciaunitario()),
                new ItemAttribute("codigoImporteReferencial", detail.getDcodireferenciaunitario()),
                new ItemAttribute("importeTotalSinImpuesto", detail.getDvventa()),
                new ItemAttribute("importeCargo", detail.getDcargo()),
                new ItemAttribute("importeDescuento", detail.getDdescuento()),
                new ItemAttribute("codigoRazonExoneracion", detail.getDtipigv()),
                new ItemAttribute("importeIgv", detail.getDigv()),
                new ItemAttribute("tipoSistemaImpuestoISC", detail.getDtipisc()),
                new ItemAttribute("importeIsc", detail.getDisc())
        )
                .stream()
                .filter(a -> updateValue(a) != null)
                .collect(Collectors.toList());
        attr.forEach(child -> child.setItem(item));

        ItemAttribute cir = find(attr, a -> a.getName().equals("codigoImporteReferencial"));
        ItemAttribute ir = find(attr, a -> a.getName().equals("importeReferencial"));
        if (cir == null && ir != null) {
            attr.remove(ir);
        }

        List<ItemAuxiliar> aux = Arrays.asList(
                new ItemAuxiliar("9000", "100", 1l, detail.getDaux1()),
                new ItemAuxiliar("9001", "100", 2l, detail.getDaux2()),
                new ItemAuxiliar("9147", "100", 3l, detail.getDaux3()),
                new ItemAuxiliar("9148", "100", 4l, detail.getDaux4())
        )
                .stream()
                .filter(a -> a.getValue() != null)
                .collect(Collectors.toList());

        aux.forEach(child -> child.setItem(item));

        item.setAttributes(attr);
        item.setAuxiliars(aux);
        return item;
    }

    //Actualiza los valores leidos de los atributos y auxiliares permitiendo
    //filtrar todos los que tienen valores nulos
    private String updateValue(ValueHolder a) {
        String value = trimToNull(a.getValue());
        a.setValue(value);
        return value;
    }

    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public <T> T find(List<T> source, Predicate<? super T> predicate) {
        Optional<T> opt = source.stream().filter(predicate).findAny();
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }
}
