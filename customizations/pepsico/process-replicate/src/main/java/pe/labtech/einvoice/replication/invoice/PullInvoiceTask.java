/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.commons.lang3.StringUtils;
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
                //referidos a ND, NC
                new DocumentAttribute("codigoSerieNumeroAfectado", h.getCtipncreditodebito()),
                new DocumentAttribute("serieNumeroAfectado", h.getCcomprobanteafecto()),
                new DocumentAttribute("tipoDocumentoReferenciaPrincipal", h.getCtipncreditodebito1()),
                new DocumentAttribute("numeroDocumentoReferenciaPrincipal", h.getCcomprobanteafecto1()),
                new DocumentAttribute("tipoDocumentoReferenciaCorregido", h.getCtipncreditodebito2()),
                new DocumentAttribute("numeroDocumentoReferenciaCorregido", h.getCcomprobanteafecto2()),
                //percepcion
                new DocumentAttribute("totalPercepcion", h.getCtotalpercepcion()),
                new DocumentAttribute("baseImponiblePercepcion", h.getCbasepercepcion()),
                new DocumentAttribute("totalVentaConPercepcion", h.getCventapercepcion()),
                new DocumentAttribute("porcentajePercepcion", h.getCporcpercepcion()),
                //retencion
                new DocumentAttribute("totalRetencion", h.getCtotalretencion()),
                new DocumentAttribute("porcentajeRetencion", h.getCporcretencion()),
                //detraccion
                new DocumentAttribute("totalDetraccion", h.getCtotaldetraccion()),
                new DocumentAttribute("porcentajeDetraccion", h.getCporcdetraccion()),
                new DocumentAttribute("descripcionDetraccion", h.getCdescdetraccion()),
                new DocumentAttribute("valorReferencialDetraccion", h.getCvalordetraccion()),
                new DocumentAttribute("totalBonificacion", h.getCbonificacion()),
                new DocumentAttribute("inHabilitado", h.getChabilitado())
        )
                .stream()
                .map(a -> handleOptionalNumber(a))
                .map(a -> updateValue(a))
                .filter(a -> a.getValue() != null)
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList());

        //sanitize the data for correlated null values
        conditionalRemove(da, lda, "totalPercepcion",
                "baseImponiblePercepcion",
                "totalVentaConPercepcion",
                "porcentajePercepcion"
        );
        conditionalRemove(da, lda, "totalDetraccion",
                "porcentajeDetraccion",
                "descripcionDetraccion",
                "valorReferencialDetraccion"
        );
        conditionalRemove(da, lda, "totalRetencion",
                "porcentajeRetencion"
        );

        //sanitize document type
        if ("01".equals(h.getCtipcomprobante()) || "03".equals(h.getCtipcomprobante())) {
            remove(da, lda,
                    "codigoSerieNumeroAfectado",
                    "serieNumeroAfectado",
                    "tipoDocumentoReferenciaPrincipal",
                    "numeroDocumentoReferenciaPrincipal",
                    "tipoDocumentoReferenciaCorregido",
                    "numeroDocumentoReferenciaCorregido"
            );
        }

        List<DocumentLegend> dl = Arrays.asList(
                new DocumentLegend("1000", 1l, h.getCley1()),
                new DocumentLegend("1002", 1l, h.getCley2()),
                new DocumentLegend("2000", 1l, h.getCley3())
        )
                .stream()
                .map(a -> updateValue(a))
                .filter(a -> a.getValue() != null)
                .map(a -> {
                    a.setDocument(d);
                    return a;
                })
                .collect(Collectors.toList());

        List<DocumentAuxiliar> dx = Arrays.asList(
                /*
                 * DATOS PARA LA CABECERA
                 */
                //codigo del cliente
                new DocumentAuxiliar("9511", "40", 1l, h.getCaux4()),
                //numero del erp
                new DocumentAuxiliar("9512", "40", 2l, h.getCaux3()),
                //fecha de vencimiento
                new DocumentAuxiliar("9515", "40", 3l, h.getCaux8()),
                //orden de compra
                new DocumentAuxiliar("9043", "40", 4l, h.getCaux11()),
                //almacen
                new DocumentAuxiliar("9513", "40", 5l, h.getCaux12()),
                //guia de remision
                new DocumentAuxiliar("9513", "40", 6l, h.getCaux28()),
                /*
                 * DATOS PARA LOS TOTALES
                 */
                //total bruto
                new DocumentAuxiliar("9514", "100", 7l, h.getCaux24()),
                //precio venta
                new DocumentAuxiliar("9517", "100", 8l, h.getCaux25()),
                /*
                 * DATOS ADICIONALES QUE SE PRESERVAN
                 */
                //total bultos
                new DocumentAuxiliar("9152", "40", 9l, h.getCaux17()),
                //cajas retornables
                new DocumentAuxiliar("9027", "40", 10l, h.getCaux18()),
                //unidades
                new DocumentAuxiliar("9153", "40", 11l, h.getCaux19()),
                //condicion de pago
                new DocumentAuxiliar("9018", "40", 12l, h.getCaux6()),
                //plazo de pago
                new DocumentAuxiliar("9019", "40", 13l, h.getCaux7())
        )
                .stream()
                .filter(a -> !a.getCode().isEmpty())
                .map(a -> updateValue(a))
                .filter(a -> a.getValue() != null)
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

    private DocumentAttribute handleOptionalNumber(DocumentAttribute a) {
        if (isNumericOptionalAttribute(a) && shouldReduce(a)) {
            a.setValue(null);
        }
        return a;
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
                .map(a -> updateValue(a))
                .filter(a -> a.getValue() != null)
                .collect(Collectors.toList());
        attr.forEach(child -> child.setItem(item));

        ItemAttribute cir = find(attr, a -> a.getName().equals("codigoImporteReferencial"));
        ItemAttribute ir = find(attr, a -> a.getName().equals("importeReferencial"));
        if (cir == null && ir != null) {
            attr.remove(ir);
        }

        item.setAttributes(attr);
        return item;
    }

    //Actualiza los valores leidos de los atributos y auxiliares permitiendo
    //filtrar todos los que tienen valores nulos
    private <T extends ValueHolder> T updateValue(T a) {
        String value = trimToNull(a.getValue());
        a.setValue(value);
        return a;
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

    public static <T> T find(List<T> source, Predicate<? super T> predicate) {
        Optional<T> opt = source.stream().filter(predicate).findAny();
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    public static <T, K> void conditionalRemove(List<T> collection, BiFunction<List<T>, K, T> lookup, K groupKey, K... groupReferences) {
        T key = lookup.apply(collection, groupKey);
        if (key != null) {
            //since there's a key the group is valid
            return;
        }
        remove(collection, lookup, groupReferences);
    }

    public static <T, K> void remove(List<T> collection, BiFunction<List<T>, K, T> lookup, K... groupReferences) {
        Arrays
                .stream(groupReferences)
                .map(r -> lookup.apply(collection, r))
                .filter(r -> r != null)
                .forEach(r -> collection.remove(r));
    }

    private static final List<String> NUMERIC_OPTIONAl;
    private static final BiFunction<List<DocumentAttribute>, String, DocumentAttribute> lda = (l, n) -> {
        return find(l, a -> n.equals(a.getName()));
    };

    static {
        List<String> no = loadConfigFileForNumericOptionals();
        NUMERIC_OPTIONAl = Collections.unmodifiableList(no);
    }

    private static List<String> loadConfigFileForNumericOptionals() {
        List<String> no = new LinkedList<>();
        File config = new File("input-optional.config");
        if (config.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(config))) {
                String s;
                while ((s = br.readLine()) != null) {
                    s = StringUtils.trimToNull(s);
                    //this is a comment
                    if (s == null) {
                        continue;
                    }
                    if (s.startsWith("#")) {
                        continue;
                    }
                    no.add(s);
                }
            } catch (IOException ex) {
                //TODO log error procesing file
            }
        }
        return no;
    }

    private static boolean shouldReduce(DocumentAttribute a) {
        return "0.00".equals(a.getValue());
    }

    private static boolean isNumericOptionalAttribute(DocumentAttribute a) {
        return NUMERIC_OPTIONAl.contains(a.getName());
    }

}
