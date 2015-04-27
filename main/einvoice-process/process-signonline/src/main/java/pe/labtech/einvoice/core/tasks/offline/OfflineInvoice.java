/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.offline;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import pe.labtech.einvoice.commons.ubl.InvoiceBuilder;
import pe.labtech.einvoice.commons.ubl.InvoiceLineBuilder;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.ubl.model.Invoice;

/**
 *
 * @author Carlos
 */
@Stateless
@LocalBean
public class OfflineInvoice {

    @EJB
    private PrivateDatabaseManagerLocal prv;

    public DocumentInfo handle(Document document) {
        Invoice invoice = prv.seek(e -> {
            Document d = e.find(Document.class, document.getId());
            
            Map<String, String> da = d.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
            InvoiceBuilder ib = new InvoiceBuilder()
                    .init(
                            da.get("tipoDocumento"),
                            da.get("serieNumero"),
                            da.get("fechaEmision"),
                            da.get("tipoMoneda"),
                            da.get("tipoDocumentoEmisor"),
                            da.get("numeroDocumentoEmisor"),
                            da.get("razonSocialEmisor"),
                            da.get("tipoDocumentoAdquiriente"),
                            da.get("numeroDocumentoAdquiriente"),
                            da.get("razonSocialAdquiriente")
                    )
                    .setIssuerName(da.get("nombreComercialEmisor"))
                    .setIssuerAddress(
                            da.get("ubigeoEmisor"),
                            da.get("direccionEmisor"),
                            da.get("urbanizacion"),
                            da.get("distritoEmisor"),
                            da.get("provinciaEmisor"),
                            da.get("departamentoEmisor"),
                            da.get("paisEmisor")
                    )
                    .addAmount("1001", da.get("totalValorVentaNetoOpGravadas"))
                    .addAmount("1002", da.get("totalValorVentaNetoOpNoGravada"))
                    .addAmount("1003", da.get("totalValorVentaNetoOpExoneradas"))
                    .addAmount("1004", da.get("totalValorVentaNetoOpGratuitas"))
                    .addPerception(
                            da.get("baseImponiblePercepcion"),
                            da.get("totalPercepcion"),
                            da.get("totalVentaConPercepcion"),
                            da.get("porcentajePercepcion")
                    )
                    .addRetention(
                            da.get("totalRetencion"),
                            da.get("porcentajeRetencion")
                    )
                    .addDetraction(
                            da.get("valorReferencialDetraccion"),
                            da.get("totalDetraccion"),
                            da.get("porcentajeDetraccion"),
                            da.get("descripcionDetraccion")
                    )
                    .addAmount("2004", da.get("totalBonificacion"))
                    .addAmount("2005", da.get("totalDescuentos"))
                    .addAmount("3001", (String) null)
                    .addTax("1000", "IGV", "VAT", da.get("totalIgv"))
                    .addTax("2000", "ISC", "EXC", da.get("totalIsc"))
                    .addTax("9999", "OTROS", "OTH", da.get("totalOtrosTributos"))
                    .addDespatchReference(da.get("tipoReferencia_1"), da.get("numeroDocumentoReferencia_1"))
                    .addDespatchReference(da.get("tipoReferencia_2"), da.get("numeroDocumentoReferencia_2"))
                    .addDespatchReference(da.get("tipoReferencia_3"), da.get("numeroDocumentoReferencia_3"))
                    .addDespatchReference(da.get("tipoReferencia_4"), da.get("numeroDocumentoReferencia_4"))
                    .addDespatchReference(da.get("tipoReferencia_5"), da.get("numeroDocumentoReferencia_5"))
                    .addAdditionalReference(da.get("tipoReferenciaAdicional_1"), da.get("numeroDocumentoReferenciaAdicional_1"))
                    .addAdditionalReference(da.get("tipoReferenciaAdicional_2"), da.get("numeroDocumentoReferenciaAdicional_2"))
                    .addAdditionalReference(da.get("tipoReferenciaAdicional_3"), da.get("numeroDocumentoReferenciaAdicional_3"))
                    .addAdditionalReference(da.get("tipoReferenciaAdicional_4"), da.get("numeroDocumentoReferenciaAdicional_4"))
                    .addAdditionalReference(da.get("tipoReferenciaAdicional_5"), da.get("numeroDocumentoReferenciaAdicional_5"))
                    .addTotalAllowance(da.get("descuentosGlobales"))
                    .addTotalCharge(da.get("totalOtrosCargos"))
                    .addTotalPayable(da.get("totalVenta"));

            if (d.getLegends() != null && !d.getLegends().isEmpty()) {
                d.getLegends().forEach(l -> ib.addNote(l.getCode(), l.getValue()));
            }

            if (d.getAuxiliars() != null && !d.getAuxiliars().isEmpty()) {
                d.getAuxiliars().forEach(l -> ib.addCustomNote(l.getCode(), l.getValue()));
            }

            d.getItems().forEach(i -> {
                Map<String, String> ia = i.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
                InvoiceLineBuilder ilb = new InvoiceLineBuilder()
                        .init(
                                ia.get("numeroOrdenItem"),
                                buildNumber(ia.get("cantidad")),
                                ia.get("unidadMedida"),
                                ia.get("codigoProducto"),
                                ia.get("descripcion"),
                                da.get("tipoMoneda"),
                                buildNumber(ia.get("importeUnitarioSinImpuesto")),
                                ia.get("codigoImporteUnitarioConImpuesto"),
                                buildNumber(ia.get("importeUnitarioConImpuesto")),
                                buildNumber(ia.get("importeTotalSinImpuesto"))
                        )
                        .addTax("1000", "IGV", "VAT", buildNumber(ia.get("importeIgv")), ia.get("codigoRazonExoneracion"), null)
                        .addTax("2000", "ISC", "EXT", buildNumber(ia.get("importeIsc")), null, ia.get("tipoSistemaImpuestoISC"))
                        .addAllowance(buildNumber(ia.get("importeDescuento")));
                
                ib.addLine(ilb.compile());
            });

            return ib.compile();
        });

        org.w3c.dom.Document xml = Commons.toXmlDocument(invoice);

        return null;
    }

    public static BigDecimal buildNumber(String amount) {
        if (amount == null) {
            return null;
        }
        return new BigDecimal(amount);
    }

}
