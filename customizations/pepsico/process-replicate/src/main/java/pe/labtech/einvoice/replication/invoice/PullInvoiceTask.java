/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pe.labtech.einvoice.commons.entity.ValueHolder;
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

    @EJB
    private PublicDatabaseManagerLocal publicDB;

    @EJB
    private PrivateDatabaseManagerLocal privateDB;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(DocumentHeaderPK t) {
        DocumentHeader h = publicDB.seek(e -> e.find(DocumentHeader.class, t));
        Document document = mapHeader(h);
        List<Item> items = mapItems(h, document);
        document.setItems(items);
        document.setStep(DocumentStep.SIGN);
        document.setStatus(DocumentStatus.NEEDED);
        privateDB.handle(e -> e.persist(document));
    }

    private Document mapHeader(DocumentHeader h) {
        Document d = new Document();
        d.setClientId(h.getCtipdocumentoemisor() + "-" + h.getCdocumentoemisor().trim());
        d.setDocumentType(h.getCtipcomprobante().trim());
        d.setDocumentNumber(h.getCcomprobante().trim());
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
                new DocumentAttribute("tipoMoneda", h.getCmoneda() != null ? h.getCmoneda() : h.getCmonedancreditodebito()),
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
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList());

        //CLEY1 -> 1000
        //CLEY2 -> 1002
        //CLEY3 -> 2000
        List<DocumentLegend> dl = Arrays.asList(
                new DocumentLegend("1000", 1l, h.getCley1()),
                new DocumentLegend("1002", 1l, h.getCley2()),
                new DocumentLegend("2000", 1l, h.getCley3())
        )
                .stream()
                .filter(a -> updateValue(a) != null)
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList());

        List<DocumentAuxiliar> dx = Arrays.asList(
                new DocumentAuxiliar("9103", "40", 1l, h.getCaux1()),
                new DocumentAuxiliar("9064", "40", 2l, h.getCaux2()),
                new DocumentAuxiliar("9021", "40", 3l, h.getCaux3()),
                new DocumentAuxiliar("9024", "40", 4l, h.getCaux4()),
                new DocumentAuxiliar("", "40", 5l, h.getCaux5()),
                new DocumentAuxiliar("9018", "40", 6l, h.getCaux6()),
                new DocumentAuxiliar("9019", "40", 7l, h.getCaux7()),
                new DocumentAuxiliar("9020", "40", 8l, h.getCaux8()),
                new DocumentAuxiliar("9025", "40", 9l, h.getCaux9()),
                new DocumentAuxiliar("9041", "40", 10l, h.getCaux10()),
                new DocumentAuxiliar("9043", "40", 11l, h.getCaux11()),
                new DocumentAuxiliar("9143", "40", 12l, h.getCaux12()),
                new DocumentAuxiliar("9144", "40", 13l, h.getCaux13()),
                new DocumentAuxiliar("9107", "40", 14l, h.getCaux14()),
                new DocumentAuxiliar("9022", "40", 15l, h.getCaux15()),
                new DocumentAuxiliar("9023", "40", 16l, h.getCaux16()),
                new DocumentAuxiliar("9152", "40", 17l, h.getCaux17()),
                new DocumentAuxiliar("9027", "40", 18l, h.getCaux18()),
                new DocumentAuxiliar("9153", "40", 19l, h.getCaux19()),
                new DocumentAuxiliar("9154", "40", 20l, h.getCaux20()),
                new DocumentAuxiliar("", "100", 1l, h.getCaux21()),
                new DocumentAuxiliar("", "100", 2l, h.getCaux22()),
                new DocumentAuxiliar("9146", "100", 3l, h.getCaux23()),
                new DocumentAuxiliar("9075", "100", 4l, h.getCaux24()),
                new DocumentAuxiliar("9151", "100", 5l, h.getCaux25()),
                new DocumentAuxiliar("", "100", 6l, h.getCaux26())
        )
                .stream()
                .filter(a -> !a.getCode().isEmpty())
                .filter(a -> updateValue(a) != null)
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList());

        d.setAttributes(da);
        d.setLegends(dl);
        d.setAuxiliars(dx);

        d.setItems(
                mapItems(h, d)
        );
        return d;
    }

    private List<Item> mapItems(DocumentHeader h, Document document) {
        //como capturar el detalle
        List<Item> items = publicDB.seek(e -> e
                .createQuery("SELECT o  FROM DocumentDetail o WHERE  o.detailPK.cempresa = :empresa  and o.detailPK.corden = :orden ORDER BY o.detailPK.did", DocumentDetail.class)
                .setParameter("empresa", h.getHeaderPK().getCempresa())
                .setParameter("orden", h.getHeaderPK().getCorden())
                .getResultList()
                .stream()
                .map(a -> mapDetailToItem(a))
                .map(a -> {
                    a.setDocument(document);
                    return a;
                })
                .collect(Collectors.toList())
        );
        return items;
    }

    private Item mapDetailToItem(DocumentDetail detail) {
        Item item = new Item();
//        item.setDocument(document);
        item.setId(Long.parseLong(detail.getDetailPK().getDid().trim(), 10));

        List<ItemAttribute> attr = Arrays.asList(
                new ItemAttribute("numeroOrdenItem", detail.getDetailPK().getDid()),
                new ItemAttribute("codigoProducto", detail.getDetailPK().getDcodproducto()),
                new ItemAttribute("descripcion", detail.getDdesproducto() + " " + detail.getDaux3()),
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

//        List<ItemAuxiliar> aux = Arrays.asList(
//                new ItemAuxiliar("9000", "100", 1l, detail.getDaux1()),
//                new ItemAuxiliar("9001", "100", 2l, detail.getDaux2()),
//                new ItemAuxiliar("9147", "100", 3l, detail.getDaux3()),
//                new ItemAuxiliar("9148", "100", 4l, detail.getDaux4())
//        )
//                .stream()
//                .filter(a -> a.getValue() != null)
//                .collect(Collectors.toList());
//        aux.forEach(child -> child.setItem(item));
//        item.setAuxiliars(aux);
        item.setAttributes(attr);
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
