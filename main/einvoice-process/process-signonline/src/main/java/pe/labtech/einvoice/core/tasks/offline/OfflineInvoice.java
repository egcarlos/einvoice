/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.offline;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import pe.labtech.einvoice.commons.ubl.InvoiceBuilder;
import pe.labtech.einvoice.commons.ubl.InvoiceLineBuilder;
import pe.labtech.einvoice.commons.xmlsecurity.DigitalSign;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.entity.SecurityValues;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.DATA_LOADED;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
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

    @EJB
    private DocumentLoaderLocal loader;

    private final DigitalSign ds = new DigitalSign();

    public DocumentInfo handle(Document document) {
        //create the UBL structure
        Invoice invoice = mapInvoice(document.getId());

        //move to the xml representation
        org.w3c.dom.Document xml = Commons.toXmlDocument(invoice);

        //sign
        String unsignedDocument = ds.createTextRepresentation(xml);
        signDocument(document.getClientId(), xml);

        //represent as text
        //FIXES A BUG IN PLATFORM WHERE DOCUMENTS ARE REJECTED IF NOT
        //ALL NAMESPACES ARE DECLARED WITH THE SAME PREFIXES
//        String signedDocument = ds.createTextRepresentation(xml).replace(
//                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" xmlns:sac=\"urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1\">",
//                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" xmlns:ns10=\"urn:sunat:names:specification:ubl:peru:schema:xsd:SummaryDocuments-1\" xmlns:ns11=\"urn:sunat:names:specification:ubl:peru:schema:xsd:VoidedDocuments-1\" xmlns:ns6=\"urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2\" xmlns:ns7=\"urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2\" xmlns:qdt=\"urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2\" xmlns:sac=\"urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1\">"
//        );
        //log to disc
        String signedDocument = ds.createTextRepresentation(xml);
        Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, "SIGNED DOCUMENT\n{0}", signedDocument);

        //create the fake response
        DocumentInfo di = synthDocumentInfo(xml);

        //save the data as localUbl... do not replicate
        prv.handle(e -> {
            final DocumentData data = new DocumentData();
            data.setDocument(document);
            data.setName("localUBL");
            data.setData(signedDocument.getBytes());
            data.setStatus(DATA_LOADED);
            data.setReplicate(Boolean.FALSE);
            e.persist(data);
        });
        prv.handle(e -> {
            final DocumentData data = new DocumentData();
            data.setDocument(document);
            data.setName("localUBLUnsigned");
            data.setData(unsignedDocument.getBytes());
            data.setStatus(DATA_LOADED);
            data.setReplicate(Boolean.FALSE);
            e.persist(data);
        });

        Map<String, String> diresponses = new HashMap<>();
        diresponses.put("status", di.getStatus());
        diresponses.put("hashCode", di.getHashCode());
        diresponses.put("signatureValue", di.getSignatureValue());

        //TODO proceder a declarar usando el proceso de envio especial
        loader.markSigned(document.getId(), "SIGNED-LOCAL", di.getHashCode(), di.getSignatureValue(), diresponses);
        return di;
    }

    private Invoice mapInvoice(Long id) {
        Invoice invoice = prv.seek(e -> {
            Document d = e.find(Document.class, id);

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
        return invoice;
    }

    private void signDocument(String id, org.w3c.dom.Document xml) {
        SecurityValues sv = prv.seek(e -> e.find(SecurityValues.class, id));

        KeyStore ks = open(sv.getKeystoreReference().getData(), sv.getKeystoreReference().getProtection());

        Key key = extractKey(ks, sv);
        X509Certificate cert = extractCertificate(ks, sv);

        ds.sign(xml, key, cert);
    }

    private DocumentInfo synthDocumentInfo(org.w3c.dom.Document xml) {
        String[] responses = ds.getResponses(xml);
        DocumentInfo di = new DocumentInfo();
        di.setHashCode(responses[0]);
        di.setSignatureValue(responses[1]);
        di.setStatus("SIGNED");
        return di;
    }

    private X509Certificate extractCertificate(KeyStore ks, SecurityValues sv) {
        try {
            return (X509Certificate) ks.getCertificate(sv.getAlias());
        } catch (KeyStoreException ex) {
            Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Key extractKey(KeyStore ks, SecurityValues sv) {
        try {
            return ks.getKey(sv.getAlias(), sv.getProtection().toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static BigDecimal buildNumber(String amount) {
        if (amount == null) {
            return null;
        }
        return new BigDecimal(amount);
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

}
