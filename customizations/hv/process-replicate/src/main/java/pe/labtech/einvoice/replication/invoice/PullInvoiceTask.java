/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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
@TransactionManagement(TransactionManagementType.BEAN)
public class PullInvoiceTask implements PullInvoiceTaskLocal {

    private static final String DETAIL_QUERY = "SELECT o "
            + "FROM Detail o "
            + "WHERE "
            + " o.id.codigoCompania = :codigoCompania "
            + " and o.id.codigoLocalidad = :codigoLocalidad "
            + " and o.id.codigoTiposRecaudacion = :codigoTiposRecaudacion "
            + " and o.id.numeroRecaudacion = :numeroRecaudacion "
            + "ORDER BY"
            + " o.detailPK.did";

    @EJB
    private PublicDatabaseManagerLocal pub;
    @EJB
    private PrivateDatabaseManagerLocal priv;

    @Asynchronous
    @Override
    public void handle(DocumentHeaderPK id) {
        DocumentHeader header = pub.seek(e -> e.find(DocumentHeader.class, id));
        Document document = mapHeader(header);
        pub.seek(e
                -> e
                .createQuery(DETAIL_QUERY, DocumentDetail.class)
                .setParameter("codigoCompania", id.getCodigoCompania())
                .setParameter("codigoLocalidad", id.getCodigoLocalidad())
                .setParameter("codigoTiposRecaudacion", id.getCodigoTiposRecaudacion())
                .setParameter("numeroRecaudacion", id.getNumeroRecaudacion())
                .getResultList()
        )
                .forEach(i -> mapDetail(i, document));
        priv.handle(e -> e.persist(document));
    }

    private Document mapHeader(DocumentHeader h) {
        Document d = new Document();
        d.setClientId("6-" + h.getCdocumentoemisor());
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
                new DocumentAttribute("fechaEmision", convertFecha(h)),
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
                new DocumentAttribute("inHabilitado", h.getChabilitado())
        )
                .stream()
                .filter(a -> a.getValue() != null && !"".equals(a.getValue()))
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList());

        d.setAttributes(da);

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
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList())
        );

        d.setAuxiliars(
                Arrays.asList(
                        new DocumentAuxiliar("9024", "40", 1l, h.getCaux1()),
                        //TODO consultar con bizlinks la creación del código
                        //new DocumentAuxiliar("    ", "100", 1l, h.getCaux21()),
                        new DocumentAuxiliar("9999", "250", 1l, h.getCaux36())
                )
                .stream()
                .filter(a -> a.getValue() != null && !"".equals(a.getValue()))
                .collect(Collectors.toList())
        );
        d.getAuxiliars().forEach(child -> child.setDocument(d));
        return d;
    }

    //Personalización para HV. Cambio de formato de dd/mm/yyyy a yyyy-mm-dd
    private static String convertFecha(DocumentHeader h) {
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

    private Item mapDetail(DocumentDetail detail, Document document) {
        Item item = new Item();
        document.getItems().add(item);
        item.setDocument(document);
        item.setId(Long.parseLong(detail.getId().getDid().trim(), 10));

        List<ItemAttribute> attr = Arrays.asList(
                new ItemAttribute("numeroOrdenItem", detail.getId().getDid()),
                new ItemAttribute("codigoProducto", detail.getId().getDcodproducto()),
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
                .map(a -> {
                    a.setItem(item);
                    return a;
                })
                .collect(Collectors.toList());

        attr.forEach(child -> child.setItem(item));
        item.setAttributes(attr);
        return item;
    }

}
