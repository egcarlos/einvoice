/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.sign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.commons.ext.ZipTools;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import pe.labtech.einvoice.commons.model.DocumentStep;
import pe.labtech.einvoice.commons2.ubl.builder.*;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.entity.SecurityValues;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.*;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import static pe.labtech.einvoice.core.tasks.tools.DatabaseCommons.mark;
import static pe.labtech.einvoice.core.tasks.tools.SecurityCommons.extractCertificate;
import static pe.labtech.einvoice.core.tasks.tools.SecurityCommons.extractKey;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import static pe.labtech.einvoice.core.tasks.tools.ServiceCommons.*;
import pe.labtech.einvoice.core.tasks.tools.Tools;
import static pe.labtech.einvoice.core.tasks.tools.Tools.buildNumber;

/**
 * Clase OfflineInvoice.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Stateless
@LocalBean
public class OfflineInvoice {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String XML_SUFFIX = ".xml";
    private static final String ZIP_SUFFIX = ".zip";

    @EJB
    private PrivateDatabaseManagerLocal prv;

    //personalizacion agregada porque antonio la para cagando
    @EJB
    private OnlineInvoice online;

    public DocumentInfo handle(Document document) {

        //Check if there is a sign in database... if it's then just don't sign
        //create names
        //SUNAT format for identifying documents
        String name = buildEntryName(document);
        //Name inside zip file
        String entryName = name + XML_SUFFIX;
        //Zip file name in local database
        String zipName = name + ZIP_SUFFIX;

        boolean alreadySigned = prv.seek(e -> e
                .createQuery("SELECT COUNT(O) FROM DocumentData O WHERE O.document = :document AND O.name = :name", Long.class)
                .setParameter("document", document)
                .setParameter("name", zipName)
                .getSingleResult() == 1
        );

        if (!alreadySigned) {
            return signDocument(document, entryName, zipName);
        } else {
            return buildDocumentInfo(document);
        }
    }

    public DocumentInfo signDocument(Document document, String entryName, String zipName) {
        org.w3c.dom.Document xml = null;
        switch (document.getDocumentType()) {
            case "01":
            case "03":
                xml = mapInvoice(document.getId()).document(DEFAULT_ENCODING);
                break;
            case "07":
                xml = mapCreditNote(document.getId()).document(DEFAULT_ENCODING);
                break;
            case "08":
                xml = mapDebitNote(document.getId()).document(DEFAULT_ENCODING);
                break;
            default://TODO mark as insvalid document for current version
                break;
        }

        //sign
        signDocument(document.getClientId(), xml);
        byte[] signedDocument = DIGISIGN.createRepresentation(xml, DEFAULT_ENCODING);

        //compress data
        byte[] compressedData = ZipTools.compress(entryName, signedDocument);

        //create the zipped
        prv.handle(e -> {
            final DocumentData data = new DocumentData();
            data.setDocument(document);
            data.setName(zipName);
            data.setData(compressedData);
            data.setStatus(DATA_LOADED);
            //there is no field for the local UBL replicated data... when zipped...
            data.setReplicate(Boolean.FALSE);
            e.persist(data);
        });

        //create the fake xml request
        //personalizacion agregada porque antonio la para cagando
        Tools.saveRequest(prv, document, online.buildSignCommand(document.getId()));

        //create the fake response
        DocumentInfo di = synthDocumentInfo(xml);

        Map<String, String> diresponses = new HashMap<>();
        diresponses.put("status", di.getStatus());
        diresponses.put("hashCode", di.getHashCode());
        diresponses.put("signatureValue", di.getSignatureValue());

        mark(prv, document.getId(), DocumentStep.REPLICATE, DocumentStatus.NEEDED, diresponses);
        return di;
    }

    public String buildEntryName(Document document) {
        //compress
        String fileName = MessageFormat.format(
                "{0}-{1}-{2}",
                document.getClientId().split("-")[1],
                document.getDocumentType(),
                document.getDocumentNumber()
        );
        return fileName;
    }

    private DebitNoteBuilder mapDebitNote(Long id) {
        return prv.seek(e -> {
            Document d = e.find(Document.class, id);

            Map<String, String> da = d.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
            //add document main data
            DebitNoteBuilder ib = new DebitNoteBuilder()
                    .init()
                    .addIdentification(
                            da.get("tipoDocumento"),
                            da.get("serieNumero"),
                            da.get("fechaEmision")
                    )
                    .addCurrency(da.get("tipoMoneda"))
                    .addSupplierInformation(
                            da.get("tipoDocumentoEmisor"),
                            da.get("numeroDocumentoEmisor"),
                            da.get("razonSocialEmisor")
                    )
                    .addCustomerInformation(
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
                    .setAcquirerAddress(null, da.get("lugarDestino"), null, null, null, null, null)
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
                    .addTotalPayable(da.get("totalVenta"))
                    //fix para notas de credito y debito
                    .addDiscrepancyResponse(da.get("serieNumeroAfectado"), da.get("codigoSerieNumeroAfectado"), da.get("motivoDocumento"))
                    .addBillingReference(da.get("numeroDocumentoReferenciaPrincipal"), da.get("tipoDocumentoReferenciaPrincipal"));

            //add document legend data
            if (d.getLegends() != null && !d.getLegends().isEmpty()) {
                d.getLegends().forEach(l -> ib.addNote(l.getCode(), l.getValue()));
            }

            //add document auxiliar data
            if (d.getAuxiliars() != null && !d.getAuxiliars().isEmpty()) {
                d.getAuxiliars().forEach(l -> ib.addCustomNote(l.getCode(), l.getValue()));
            }

            //add item information
            d.getItems().forEach(i -> {
                Map<String, String> ia = i.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
                DebitNoteLineBuilder ilb = new DebitNoteLineBuilder()
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
                        .addAlternativeConditionPrice(ia.get("codigoImporteReferencial"), buildNumber(ia.get("importeReferencial")))
                        //                        .addAlternativeConditionPrice(ia.get("codigoImporteReferencial"), da.get("tipoMoneda"), buildNumber(ia.get("importeReferencial")))
                        .addTax("1000", "IGV", "VAT", buildNumber(ia.get("importeIgv")), ia.get("codigoRazonExoneracion"), null)
                        .addTax("2000", "ISC", "EXT", buildNumber(ia.get("importeIsc")), null, ia.get("tipoSistemaImpuestoISC"));

                ib.addLine(ilb.compile());
            });

            return ib;
        });
    }

    private CreditNoteBuilder mapCreditNote(Long id) {
        return prv.seek(e -> {
            Document d = e.find(Document.class, id);

            Map<String, String> da = d.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
            //add document main data
            CreditNoteBuilder ib = new CreditNoteBuilder()
                    .init()
                    .addIdentification(
                            da.get("tipoDocumento"),
                            da.get("serieNumero"),
                            da.get("fechaEmision")
                    )
                    .addCurrency(da.get("tipoMoneda"))
                    .addSupplierInformation(
                            da.get("tipoDocumentoEmisor"),
                            da.get("numeroDocumentoEmisor"),
                            da.get("razonSocialEmisor")
                    )
                    .addCustomerInformation(
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
                    .setAcquirerAddress(null, da.get("lugarDestino"), null, null, null, null, null)
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
                    .addTotalPayable(da.get("totalVenta"))
                    //fix para notas de credito y debito
                    .addDiscrepancyResponse(da.get("serieNumeroAfectado"), da.get("codigoSerieNumeroAfectado"), da.get("motivoDocumento"))
                    .addBillingReference(da.get("numeroDocumentoReferenciaPrincipal"), da.get("tipoDocumentoReferenciaPrincipal"));

            //add document legend data
            if (d.getLegends() != null && !d.getLegends().isEmpty()) {
                d.getLegends().forEach(l -> ib.addNote(l.getCode(), l.getValue()));
            }

            //add document auxiliar data
            if (d.getAuxiliars() != null && !d.getAuxiliars().isEmpty()) {
                d.getAuxiliars().forEach(l -> ib.addCustomNote(l.getCode(), l.getValue()));
            }

            //add item information
            d.getItems().forEach(i -> {
                Map<String, String> ia = i.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
                CreditNoteLineBuilder ilb = new CreditNoteLineBuilder()
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
                        .addAlternativeConditionPrice(ia.get("codigoImporteReferencial"), buildNumber(ia.get("importeReferencial")))
                        //                        .addAlternativeConditionPrice(ia.get("codigoImporteReferencial"), da.get("tipoMoneda"), buildNumber(ia.get("importeReferencial")))
                        .addTax("1000", "IGV", "VAT", buildNumber(ia.get("importeIgv")), ia.get("codigoRazonExoneracion"), null)
                        .addTax("2000", "ISC", "EXT", buildNumber(ia.get("importeIsc")), null, ia.get("tipoSistemaImpuestoISC"));

                ib.addLine(ilb.compile());
            });

            return ib;
        });
    }

    private InvoiceBuilder mapInvoice(Long id) {
        return prv.seek(e -> {
            Document d = e.find(Document.class, id);

            Map<String, String> da = d.getAttributes().stream().collect(Collectors.toMap(t -> t.getName(), t -> t.getValue()));
            //add document main data
            InvoiceBuilder ib = new InvoiceBuilder()
                    .init()
                    .addIdentification(
                            da.get("tipoDocumento"),
                            da.get("serieNumero"),
                            da.get("fechaEmision")
                    )
                    .addCurrency(da.get("tipoMoneda"))
                    .addSupplierInformation(
                            da.get("tipoDocumentoEmisor"),
                            da.get("numeroDocumentoEmisor"),
                            da.get("razonSocialEmisor")
                    )
                    .addCustomerInformation(
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
                    .setAcquirerAddress(null, da.get("lugarDestino"), null, null, null, null, null)
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

            //add document legend data
            if (d.getLegends() != null && !d.getLegends().isEmpty()) {
                d.getLegends().forEach(l -> ib.addNote(l.getCode(), l.getValue()));
            }

            //add document auxiliar data
            if (d.getAuxiliars() != null && !d.getAuxiliars().isEmpty()) {
                d.getAuxiliars().forEach(l -> ib.addCustomNote(l.getCode(), l.getValue()));
            }

            //add item information
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
                        .addAlternativeConditionPrice(ia.get("codigoImporteReferencial"), buildNumber(ia.get("importeReferencial")))
                        //                        .addAlternativeConditionPrice(ia.get("codigoImporteReferencial"), da.get("tipoMoneda"), buildNumber(ia.get("importeReferencial")))
                        .addTax("1000", "IGV", "VAT", buildNumber(ia.get("importeIgv")), ia.get("codigoRazonExoneracion"), null)
                        .addTax("2000", "ISC", "EXT", buildNumber(ia.get("importeIsc")), null, ia.get("tipoSistemaImpuestoISC"))
                        .addAllowance(buildNumber(ia.get("importeDescuento")));

                ib.addLine(ilb.compile());
            });

            return ib;
        });
    }

    private void signDocument(String id, org.w3c.dom.Document xml) {
        SecurityValues sv = prv.seek(e -> e.find(SecurityValues.class, id));

        KeyStore ks = open(sv.getKeystoreReference().getData(), sv.getKeystoreReference().getProtection());

        Key key = extractKey(ks, sv);
        X509Certificate cert = extractCertificate(ks, sv);

        DIGISIGN.sign(xml, key, cert);
    }

    //TODO make cache
    private KeyStore open(byte[] data, String protection) {
        try {
            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(
                    new ByteArrayInputStream(data),
                    protection.toCharArray()
            );
            return ks;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private DocumentInfo buildDocumentInfo(Document document) {
        //construir la respuesta
        DocumentInfo di = new DocumentInfo();

        prv.handle(e -> e
                .createQuery(
                        "SELECT R FROM DocumentResponse R WHERE R.document = :document",
                        DocumentResponse.class
                )
                .setParameter("document", document)
                .getResultList()
                .forEach(r -> tryset(di, r.getName(), r.getName()))
        );

        mark(prv, document.getId(), DocumentStep.REPLICATE, DocumentStatus.NEEDED);
        return di;
    }

    private void tryset(DocumentInfo di, Object name, Object value) {
        try {
            PropertyUtils.setProperty(di, name.toString(), value);
        } catch (Exception ex) {
            //irrelevant since only valid properties will be mapped
            Logger.getLogger(this.getClass().getName()).log(Level.FINEST, () -> "Invalid property " + name + " in DocumentInfo. " + ex.getMessage());
        }
    }
}
