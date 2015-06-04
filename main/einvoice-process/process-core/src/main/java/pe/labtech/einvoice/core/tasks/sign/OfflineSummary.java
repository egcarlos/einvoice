/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.*;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.commons.ext.ZipTools;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import pe.labtech.einvoice.commons.model.DocumentStep;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsBuilder;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsLineBuilder;
import pe.labtech.einvoice.commons.ubl.VoidedDocumentsBuilder;
import pe.labtech.einvoice.commons.ubl.VoidedDocumentsLineBuilder;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.entity.SecurityValues;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.DATA_LOADED;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import static pe.labtech.einvoice.core.tasks.tools.DatabaseCommons.mark;
import static pe.labtech.einvoice.core.tasks.tools.SecurityCommons.extractCertificate;
import static pe.labtech.einvoice.core.tasks.tools.SecurityCommons.extractKey;
import static pe.labtech.einvoice.core.tasks.tools.ServiceCommons.DIGISIGN;
import static pe.labtech.einvoice.core.tasks.tools.ServiceCommons.synthDocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;

/**
 *
 * @author Carlos Echeverria
 */
@Stateless
@LocalBean
public class OfflineSummary {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String XML_SUFFIX = ".xml";
    private static final String ZIP_SUFFIX = ".zip";

    @EJB
    private PrivateDatabaseManagerLocal prv;

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
            return sign(document, entryName, zipName);
        } else {
            return buildDocumentInfo(document);
        }
    }

    private String buildEntryName(Document document) {
        //document type and number
        return document.getClientId() + "-" + document.getDocumentNumber();
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

    private DocumentInfo sign(Document document, String entryName, String zipName) {
        org.w3c.dom.Document xml;
        if ("RC".equals(document.getDocumentType())) {
            SummaryDocumentsBuilder sb = mapSummary(document.getId());
            xml = sb.document(DEFAULT_ENCODING);
        } else {
            VoidedDocumentsBuilder vb = mapVoided(document.getId());
            xml = vb.document(DEFAULT_ENCODING);
        }
        signDocument(document.getClientId(), xml);
        byte[] signedDocument = DIGISIGN.createRepresentation(xml, DEFAULT_ENCODING);

        //compress data
        byte[] compressedData = ZipTools.compress(entryName, signedDocument);

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
        //create the fake response
        DocumentInfo di = synthDocumentInfo(xml);

        Map<String, String> diresponses = new HashMap<>();
        diresponses.put("status", di.getStatus());
        diresponses.put("hashCode", di.getHashCode());
        diresponses.put("signatureValue", di.getSignatureValue());

        mark(prv, document.getId(), DocumentStep.REPLICATE, DocumentStatus.NEEDED, diresponses);
        return di;
    }

    private SummaryDocumentsBuilder mapSummary(Long id) {
        return prv.seek(e -> {
            SummaryDocumentsBuilder builder = new SummaryDocumentsBuilder();
            Document d = e.find(Document.class, id);
            Map<String, String> da = d.getAttributes().stream().collect(toMap(t -> t.getName(), t -> t.getValue()));

            builder.init(
                    da.get("serieNumero"),
                    da.get("fechaEmisionComprobante"),
                    da.get("fechaGeneracionResumen"),
                    da.get("tipoDocumentoEmisor"),
                    da.get("numeroDocumentoEmisor"),
                    da.get("razonSocialEmisor")
            );

            d.getItems().stream()
                    .map(i -> i.getAttributes().stream())
                    .map(i -> i.collect(toMap(t -> t.getName(), t -> t.getValue())))
                    .map(i -> new SummaryDocumentsLineBuilder()
                            .init(
                                    i.get("numeroFila"),
                                    i.get("tipoDocumento"),
                                    i.get("serieGrupoDocumento"),
                                    i.get("numeroCorrelativoInicio"),
                                    i.get("numeroCorrelativoFin"),
                                    i.get("tipoMoneda"),
                                    i.get("totalVenta")
                            )
                            .addBillingPayment("01", i.get("totalValorVentaOpGravadasConIgv"))
                            .addBillingPayment("02", i.get("totalValorVentaOpExoneradasIgv"))
                            .addBillingPayment("03", i.get("totalValorVentaOpInafectasIgv"))
                            .addBillingPayment("05", i.get("totalValorVentaOpGratuitas"))
                            .addAllowance(Boolean.TRUE, i.get("totalOtrosCargos"))
                            .addTax("2000", "ISC", "EXC", i.get("totalIsc"), null, null)
                            .addTax("9999", "OTROS", "OTH", i.get("totalOtrosTributos"), null, null)
                            .addTax("1000", "IGV", "VAT", i.get("totalIgv"), null, null)
                            .compile()
                    );

            return builder;
        });
    }

    private VoidedDocumentsBuilder mapVoided(Long id) {
        return prv.seek(e -> {
            VoidedDocumentsBuilder builder = new VoidedDocumentsBuilder();
            Document d = e.find(Document.class, id);
            Map<String, String> da = d.getAttributes().stream().collect(toMap(t -> t.getName(), t -> t.getValue()));

            builder.init(
                    da.get("serieNumero"),
                    da.get("fechaEmisionComprobante"),
                    da.get("fechaGeneracionResumen"),
                    da.get("tipoDocumentoEmisor"),
                    da.get("numeroDocumentoEmisor"),
                    da.get("razonSocialEmisor")
            );

            d.getItems().stream()
                    .map(i -> i.getAttributes().stream())
                    .map(i -> i.collect(toMap(t -> t.getName(), t -> t.getValue())))
                    .map(i -> new VoidedDocumentsLineBuilder()
                            .init(
                                    i.get("numeroFila"),
                                    i.get("tipoDocumento"),
                                    i.get("serieDocumentoBaja"),
                                    i.get("numeroDocumentoBaja"),
                                    i.get("motivoBaja")
                            )
                            .compile()
                    )
                    .forEach(i -> builder.addLine(i));

            return builder;
        });
    }

    private void tryset(DocumentInfo di, Object name, Object value) {
        try {
            PropertyUtils.setProperty(di, name.toString(), value);
        } catch (Exception ex) {
            //irrelevant since only valid properties will be mapped
            Logger.getLogger(this.getClass().getName()).log(Level.FINEST, () -> "Invalid property " + name + " in DocumentInfo. " + ex.getMessage());
        }
    }

    private void signDocument(String id, org.w3c.dom.Document xml) {
        SecurityValues sv = prv.seek(e -> e.find(SecurityValues.class, id));

        KeyStore ks = open(sv.getKeystoreReference().getData(), sv.getKeystoreReference().getProtection());

        Key key = extractKey(ks, sv);
        X509Certificate cert = extractCertificate(ks, sv);

        DIGISIGN.sign(xml, key, cert);
    }

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
