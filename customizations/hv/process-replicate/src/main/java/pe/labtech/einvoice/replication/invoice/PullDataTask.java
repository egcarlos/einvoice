/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentAttribute;
import pe.labtech.einvoice.core.entity.DocumentAuxiliar;
import pe.labtech.einvoice.core.entity.DocumentLegend;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.entity.ItemAttribute;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Stateless
public class PullDataTask implements PullDataTaskLocal {

    private static final String HEADER_QUERY = "SELECT o FROM DocumentHeader o WHERE o.cestado = 'A'";
    private static final String DETAIL_QUERY = "SELECT o "
            + "FROM DocumentDetail o "
            + "WHERE "
            + " o.detailPK.codigoCompania = :codigoCompania "
            + " and o.detailPK.codigoLocalidad = :codigoLocalidad "
            + " and o.detailPK.codigoTiposRecaudacion = :codigoTiposRecaudacion "
            + " and o.detailPK.numeroRecaudacion = :numeroRecaudacion "
            + "ORDER BY"
            + " o.detailPK.did";

    @EJB
    PrivateDatabaseManagerLocal prv;

    @EJB
    PublicDatabaseManagerLocal pub;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle() {
        Logger.getLogger(this.getClass().getSimpleName()).fine("Dispatching for data pulling");

        Consumer<DocumentHeader> forHeaders = (h) -> {
            h.setCestado('L');
            Document document = mapHeader(h);
            List<Item> items = mapItems(h, document);
            document.setItems(items);
            prv.handle(e -> e.persist(document));
        };

        //REDUCING LOG
        List<DocumentHeader> hs = pub.seek(e -> e.createQuery(HEADER_QUERY, DocumentHeader.class).getResultList());
        if (hs.isEmpty()) {
            return;
        }

        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "pulling items: {0}", hs.size());
        hs.forEach(forHeaders);
    }

    private Document mapHeader(DocumentHeader h) {
        Document d = new Document();
        d.setClientId(h.getCtipdocumentoemisor() + "-" + h.getCdocumentoemisor());
        d.setDocumentType(h.getCtipcomprobante());
        d.setDocumentNumber(h.getCcomprobante());
        //creacion de los atributos
        List<DocumentAttribute> da = Arrays.asList(new DocumentAttribute("indicador", "C"),
                new DocumentAttribute("tipoDocumentoEmisor", h.getCtipdocumentoemisor()),
                new DocumentAttribute("numeroDocumentoEmisor", h.getCdocumentoemisor()),
                new DocumentAttribute("razonSocialEmisor", h.getCrsocialemisor()),
                new DocumentAttribute("nombreComercialEmisor", h.getCncomercialemisor()),
                new DocumentAttribute("tipoDocumento", h.getCtipcomprobante()),
                new DocumentAttribute("serieNumero", h.getCcomprobante()),
                new DocumentAttribute("fechaEmision", adjustDate(h)),
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
                new DocumentAttribute("tipoMoneda", h.getCmoneda() == null ? h.getCmonedancreditodebito() : h.getCmoneda()),
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
                new DocumentAttribute("inHabilitado", h.getChabilitado()),
                new DocumentAttribute("totalDocumentoAnticipo", h.getCaux7())
        )
                .stream()
                .filter(a -> a.getValue() != null && !"".equals(a.getValue()))
                .collect(Collectors.toList());
        //agregamos referencias adicionales

        d.setAttributes(da);
        d.getAttributes().forEach(child -> child.setDocument(d));

        BiConsumer<Integer, Function<DocumentHeader, String>> anticipos = (i, r) -> {
            if (!"01".equals(h.getCtipcomprobante()) && !"03".equals(h.getCtipcomprobante())) {
                return;
            }
            String numberValue = r.apply(h);
            if (numberValue == null || (numberValue = numberValue.trim()).isEmpty()) {
                return;
            }

            String typeName = "tipoReferenciaAdicional_" + i;
            String typeValue = "01".equals(h.getCtipcomprobante()) ? "02" : "03";
            String numberName = "numeroDocumentoReferenciaAdicional_" + i;

            d.getAttributes().add(new DocumentAttribute(d, typeName, typeValue));
            d.getAttributes().add(new DocumentAttribute(d, numberName, numberValue));

        };

        anticipos.accept(1, DocumentHeader::getCaux2);
        anticipos.accept(2, DocumentHeader::getCaux3);
        anticipos.accept(3, DocumentHeader::getCaux4);
        anticipos.accept(4, DocumentHeader::getCaux5);
        anticipos.accept(5, DocumentHeader::getCaux6);

        //agregando los campos de leyenda
        d.setLegends(
                Arrays.asList(
                        new DocumentLegend("1000", 1l, h.getCley1()),
                        //TODO consultar con bizlinks la creación del código
                        //new DocumentLegend("    ", 1l, h.getCley1()),
                        new DocumentLegend(
                                "3000",
                                3l,
                                fragment(h.getCley3(), 100, 0),
                                fragment(h.getCley3(), 100, 1)
                        ),
                        new DocumentLegend("3001", 4l, h.getCley4())
                )
                .stream()
                .filter(a -> a.getValue() != null && !"".equals(a.getValue()))
                .collect(Collectors.toList())
        );

        d.getLegends().forEach(child -> child.setDocument(d));

        d.setAuxiliars(
                Arrays.asList(
                        new DocumentAuxiliar("9024", "40", 1l, h.getCaux1()),
                        //TODO consultar con bizlinks la creación del código
                        //new DocumentAuxiliar("    ", "100", 1l, h.getCaux21()),
                        new DocumentAuxiliar(
                                "9999",
                                "250",
                                1l,
                                Arrays.asList(h.getCaux19(), h.getCaux20(), h.getCaux36()).stream()
                                .filter(t -> t != null)
                                .map(t -> t.trim())
                                .filter(t -> !t.isEmpty())
                                .reduce(null, (a, b) -> a == null ? b : a + "\n" + b)
                        )
                )
                .stream()
                .filter(a -> a.getValue() != null && !"".equals(a.getValue()))
                .collect(Collectors.toList())
        );
        d.getAuxiliars().forEach(child -> child.setDocument(d));

        List<Item> items = mapItems(h, d);
        items.forEach(child -> child.setDocument(d));
        d.setItems(items);
        return d;
    }

    //Personalización para HV. Cambio de formato de dd/mm/yyyy a yyyy-mm-dd
    private static String adjustDate(DocumentHeader h) {
        String fecha = h.getCfemision();
        fecha = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
        return fecha;
    }

    private static String fragment(String source, int length, int index) {
        if (length <= 0 || source == null) {
            return null;
        }
        String working = source.trim();
        int beginIndex = index * length;
        int endIndex = Math.min(beginIndex + length, working.length());
        if (endIndex <= beginIndex) {
            return null;
        }
        return working.substring(beginIndex, endIndex);

    }

    private List<Item> mapItems(DocumentHeader h, Document document) {
        //como capturar el detalle
        List<Item> items = pub.seek(e -> e
                .createQuery(DETAIL_QUERY, DocumentDetail.class)
                .setParameter("codigoCompania", h.getHeaderPK().getCodigoCompania())
                .setParameter("codigoLocalidad", h.getHeaderPK().getCodigoLocalidad())
                .setParameter("codigoTiposRecaudacion", h.getHeaderPK().getCodigoTiposRecaudacion())
                .setParameter("numeroRecaudacion", h.getHeaderPK().getNumeroRecaudacion())
                .getResultList()
                .stream()
                .map(d -> mapDetailToItem(d, document))
                .collect(Collectors.toList())
        );
        return items;
    }

    private Item mapDetailToItem(DocumentDetail detail, Document document) {
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
                .filter(a -> a.getValue() != null)
                .collect(Collectors.toList());

        attr.forEach(child -> child.setItem(item));
        item.setAttributes(attr);
        return item;
    }

}
