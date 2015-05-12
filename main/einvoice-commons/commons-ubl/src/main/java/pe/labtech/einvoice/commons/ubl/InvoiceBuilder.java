/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import java.math.BigDecimal;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.w3c.dom.Document;
import pe.labtech.ubl.model.Invoice;
import pe.labtech.ubl.model.InvoicePrefixMapper;
import pe.labtech.ubl.model.aggregate.AccountingParty;
import pe.labtech.ubl.model.aggregate.Address;
import pe.labtech.ubl.model.aggregate.Attachment;
import pe.labtech.ubl.model.aggregate.BillingReference;
import pe.labtech.ubl.model.aggregate.Country;
import pe.labtech.ubl.model.aggregate.DiscrepancyResponse;
import pe.labtech.ubl.model.aggregate.DocumentReference;
import pe.labtech.ubl.model.aggregate.ExternalReference;
import pe.labtech.ubl.model.aggregate.InvoiceDocumentReference;
import pe.labtech.ubl.model.aggregate.InvoiceLine;
import pe.labtech.ubl.model.aggregate.LegalMonetaryTotal;
import pe.labtech.ubl.model.aggregate.Party;
import pe.labtech.ubl.model.aggregate.PartyLegalEntity;
import pe.labtech.ubl.model.aggregate.PartyName;
import pe.labtech.ubl.model.aggregate.Signature;
import pe.labtech.ubl.model.aggregate.TaxCategory;
import pe.labtech.ubl.model.aggregate.TaxScheme;
import pe.labtech.ubl.model.aggregate.TaxSubtotal;
import pe.labtech.ubl.model.aggregate.TaxTotal;
import pe.labtech.ubl.model.basic.Amount;
import pe.labtech.ubl.model.extensions.ExtensionContent;
import pe.labtech.ubl.model.extensions.UBLExtension;
import pe.labtech.ubl.model.extensions.UBLExtensions;
import pe.labtech.ubl.model.sunat.AdditionalInformation;
import pe.labtech.ubl.model.sunat.AdditionalMonetaryTotal;
import pe.labtech.ubl.model.sunat.AdditionalProperty;

/**
 *
 * @author Carlos
 */
public class InvoiceBuilder implements Builder<Invoice> {

    private static final JAXBContext JAXB_CONTEXT;
    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

    private Invoice invoice;

    static {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(Invoice.class);
        } catch (JAXBException ex) {
            context = null;
        }
        JAXB_CONTEXT = context;
        DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);

    }

    public InvoiceBuilder init(final String invoiceType, final String invoiceNumber, final String invoiceDate, final String currency, final String supplierType, final String supplierId, final String supplierName, final String clientType, final String clientId, final String clientName) {
        this.invoice = createInvoice(invoiceType, invoiceNumber, invoiceDate, currency, supplierType, supplierId, supplierName, clientType, clientId, clientName);
        return this;
    }

    public InvoiceBuilder addAmount(String id, String amount) {
        if (amount == null) {
            return this;
        }
        return this.addAmount(id, new BigDecimal(amount));
    }

    public InvoiceBuilder addAmount(String id, BigDecimal amount) {
        if (amount != null && id != null) {
            AdditionalMonetaryTotal amt = new AdditionalMonetaryTotal(
                    id,
                    null,
                    null,
                    new Amount(invoice.getDocumentCurrencyCode(), amount),
                    null,
                    null
            );
            invoice
                    .getUBLExtensions()
                    .getUBLExtension()
                    .get(0)
                    .getExtensionContent()
                    .getSunatAdditionalInformation()
                    .getAdditionalMonetaryTotal()
                    .add(amt);
        }
        return this;
    }

    public InvoiceBuilder addNote(String id, String value) {
        if (id != null && value != null) {
            invoice
                    .getUBLExtensions()
                    .getUBLExtension()
                    .get(0)
                    .getExtensionContent()
                    .getSunatAdditionalInformation()
                    .getAdditionalProperty()
                    .add(new AdditionalProperty(id, null, value));
        }
        return this;
    }

    public InvoiceBuilder addCustomNote(String id, String value) {
        if (id != null && value != null) {
            invoice
                    .getUBLExtensions()
                    .getUBLExtension()
                    .get(1)
                    .getExtensionContent()
                    .getBasicAdditionalInformation()
                    .getAdditionalProperty()
                    .add(new AdditionalProperty(id, null, value));
        }
        return this;
    }

    private static Invoice createInvoice(final String invoiceType, final String invoiceNumber, final String invoiceDate, final String currency, final String supplierType, final String supplierId, final String supplierName, final String clientType, final String clientId, final String clientName) {
        Invoice i = new Invoice();
        i.setUBLExtensions(new UBLExtensions());
        i.getUBLExtensions().getUBLExtension().addAll(
                Arrays.asList(
                        new UBLExtension("SUNAT", new ExtensionContent(new AdditionalInformation(), null)),
                        new UBLExtension("EBIZ", new ExtensionContent(null, new AdditionalInformation())),
                        new UBLExtension(new ExtensionContent())
                )
        );
        //valores constantes
        i.setUBLVersionID("2.0");
        i.setCustomizationID("1.0");
        //valores variables pero mandatorios
        i.setID(invoiceNumber);
        i.setIssueDate(invoiceDate);
        i.setInvoiceTypeCode(invoiceType);
        //el valor de la moneda del documento es mandatorio antes de iniciar todo el proceso
        i.setDocumentCurrencyCode(currency);
        //estructura obligatoria
        i.setSignature(new Signature());
        i.getSignature().setID("IDSignKG");
        i.getSignature().setSignatoryParty(new Party(supplierId, supplierName));
        i.getSignature().setDigitalSignatureAttachment(new Attachment(new ExternalReference("#signatureKG")));
        //datos del emisor
        i.setAccountingSupplierParty(
                new AccountingParty(
                        supplierType,
                        supplierId,
                        new Party(
                                new Address(),
                                new PartyLegalEntity(supplierName)
                        )
                )
        );
        //datos del cliente
        i.setAccountingCustomerParty(
                new AccountingParty(
                        clientType,
                        clientId,
                        new Party(
                                null,
                                new PartyLegalEntity(clientName)
                        )
                )
        );
        return i;
    }

    public InvoiceBuilder addTax(String id, String name, String typeCode, String amount) {
        if (amount == null) {
            return this;
        }
        return addTax(id, name, typeCode, new BigDecimal(amount));
    }

    public InvoiceBuilder addTax(String id, String name, String typeCode, BigDecimal amount) {
        TaxTotal tt = new TaxTotal();
        tt.setTaxAmount(new Amount(this.invoice.getDocumentCurrencyCode(), amount));

        TaxSubtotal tst = new TaxSubtotal();
        tst.setTaxAmount(new Amount(this.invoice.getDocumentCurrencyCode(), amount));
        TaxCategory tc = new TaxCategory();
        TaxScheme ts = new TaxScheme();
        ts.setID(id);
        ts.setName(name);
        ts.setTaxTypeCode(typeCode);
        tc.setTaxScheme(ts);
        tst.setTaxCategory(tc);
        tt.setTaxSubtotal(tst);
        this.invoice.getTaxTotal().add(tt);
        return this;
    }

    public InvoiceBuilder addTotalAllowance(String amount) {
        if (amount != null) {
            addTotalAllowance(new BigDecimal(amount));
        }
        return this;
    }

    public InvoiceBuilder addTotalAllowance(BigDecimal amount) {
        if (amount != null) {
            if (this.invoice.getLegalMonetaryTotal() == null) {
                this.invoice.setLegalMonetaryTotal(new LegalMonetaryTotal());
            }
            this.invoice.getLegalMonetaryTotal().setAllowanceTotalAmount(
                    new Amount(
                            this.invoice.getDocumentCurrencyCode(),
                            amount
                    )
            );
        }
        return this;
    }

    public InvoiceBuilder addTotalCharge(String amount) {
        if (amount != null) {
            addTotalCharge(new BigDecimal(amount));
        }
        return this;
    }

    public InvoiceBuilder addTotalCharge(BigDecimal amount) {
        if (amount != null) {
            if (this.invoice.getLegalMonetaryTotal() == null) {
                this.invoice.setLegalMonetaryTotal(new LegalMonetaryTotal());
            }
            this.invoice.getLegalMonetaryTotal().setChargeTotalAmount(
                    new Amount(
                            this.invoice.getDocumentCurrencyCode(),
                            amount
                    )
            );
        }
        return this;
    }

    public InvoiceBuilder addTotalPayable(String amount) {
        if (amount != null) {
            addTotalPayable(new BigDecimal(amount));
        }
        return this;
    }

    public InvoiceBuilder addTotalPayable(BigDecimal amount) {
        if (amount != null) {
            if (this.invoice.getLegalMonetaryTotal() == null) {
                this.invoice.setLegalMonetaryTotal(new LegalMonetaryTotal());
            }
            this.invoice.getLegalMonetaryTotal().setPayableAmount(
                    new Amount(
                            this.invoice.getDocumentCurrencyCode(),
                            amount
                    )
            );
        }
        return this;
    }

    public InvoiceBuilder setIssuerName(String name) {
        if (name == null) {
            return this;
        }
        invoice.getAccountingSupplierParty().getParty().setPartyName(new PartyName(name));
        return this;
    }

    /**
     * Establece la dirección del emisor.
     *
     * @param id ubigeo
     * @param address dirección postal
     * @param zone urbanización o zona
     * @param disctrict distrito
     * @param city provincia
     * @param state departamento
     * @param country código iso del país
     * @return
     */
    public InvoiceBuilder setIssuerAddress(String id, String address, String zone, String disctrict, String city, String state, String country) {

        Address a = invoice.getAccountingSupplierParty().getParty().getPostalAddress();

        a.setID(id);
        a.setStreetName(address);
        a.setCitySubdivisionName(zone);
        a.setDistrict(disctrict);
        a.setCountrySubentity(city);
        a.setCityName(state);

        if (country != null) {
            a.setCountry(new Country());
            a.getCountry().setIdentificationCode(country);
        }

        return this;
    }

    public InvoiceBuilder addPerception(String reference, String payable, String total, String percent) {
        if (payable != null) {
            AdditionalMonetaryTotal amt = new AdditionalMonetaryTotal(
                    "2001",
                    null,
                    reference == null ? null : new Amount(invoice.getDocumentCurrencyCode(), new BigDecimal(reference)),
                    new Amount(invoice.getDocumentCurrencyCode(), new BigDecimal(payable)),
                    percent,
                    total == null ? null : new Amount(invoice.getDocumentCurrencyCode(), new BigDecimal(total))
            );

            invoice
                    .getUBLExtensions()
                    .getUBLExtension()
                    .get(0)
                    .getExtensionContent()
                    .getSunatAdditionalInformation()
                    .getAdditionalMonetaryTotal()
                    .add(amt);
        }
        return this;
    }

    public InvoiceBuilder addRetention(String payable, String percent) {
        if (payable != null) {
            AdditionalMonetaryTotal amt = new AdditionalMonetaryTotal(
                    "2003",
                    null,
                    null,
                    new Amount(invoice.getDocumentCurrencyCode(), new BigDecimal(payable)),
                    percent,
                    null
            );

            invoice
                    .getUBLExtensions()
                    .getUBLExtension()
                    .get(0)
                    .getExtensionContent()
                    .getSunatAdditionalInformation()
                    .getAdditionalMonetaryTotal()
                    .add(amt);
        }
        return this;
    }

    //TODO map description
    public InvoiceBuilder addDetraction(String reference, String payable, String percent, String description) {
        if (payable != null) {
            AdditionalMonetaryTotal amt = new AdditionalMonetaryTotal(
                    "2003",
                    null,
                    reference == null ? null : new Amount(invoice.getDocumentCurrencyCode(), new BigDecimal(reference)),
                    new Amount(invoice.getDocumentCurrencyCode(), new BigDecimal(payable)),
                    percent,
                    null
            );

            invoice
                    .getUBLExtensions()
                    .getUBLExtension()
                    .get(0)
                    .getExtensionContent()
                    .getSunatAdditionalInformation()
                    .getAdditionalMonetaryTotal()
                    .add(amt);
        }
        return this;
    }

    public InvoiceBuilder addDespatchReference(String type, String number) {
        if (type != null && number != null) {
            invoice.getDespatchDocumentReference().add(
                    new DocumentReference(type, number)
            );
        }
        return this;
    }

    public InvoiceBuilder addAdditionalReference(String type, String number) {
        if (type != null && number != null) {
            invoice.getAdditionalDocumentReference().add(
                    new DocumentReference(type, number)
            );
        }
        return this;
    }

    public InvoiceBuilder addDiscrepancyResponse(String ReferenceID, String ResponseCode, String Description) {
        if (ReferenceID != null && ResponseCode != null && Description != null) {
            invoice
                    .getDiscrepancyResponse()
                    .add(
                            new DiscrepancyResponse(ReferenceID, ResponseCode, Description)
                    );
        }
        return this;
    }

    public InvoiceBuilder addInvoiceDocumentReference(String ID, String DocumentTypeCode) {
        if (ID != null && DocumentTypeCode != null) {
            if (invoice.getBillingReference() == null) {
                invoice.setBillingReference(new BillingReference());
            }
            invoice
                    .getBillingReference()
                    .getInvoiceDocumentReference()
                    .add(
                            new InvoiceDocumentReference(ID, DocumentTypeCode)
                    );
        }
        return this;
    }

    public InvoiceBuilder addLine(InvoiceLine line) {
        invoice.getInvoiceLine().add(line);
        return this;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public Invoice compile() {
        return invoice;
    }

    /**
     * Final operation used in order to get the XML representation of the UBL
     * document.
     *
     * @param charsetName
     * @return
     */
    public Document document(String charsetName) {
        try {
            Marshaller marshaller = getMarshaller(charsetName);
            DocumentBuilder db = getDocumentBuilder();
            Document document = db.newDocument();
            marshaller.marshal(this.compile(), document);
            return document;
        } catch (JAXBException ex) {
            throw new InvoiceBuilderException(ex);
        }
    }

    public JAXBContext getContext() {
        return JAXB_CONTEXT;
    }

    //TODO migrate to pooled implementation to enhance memmory usage
    public Marshaller getMarshaller(String charsetName) {
        try {
            Marshaller marshaller = getContext().createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charsetName);
            marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new InvoicePrefixMapper());
            return marshaller;
        } catch (JAXBException ex) {
            throw new InvoiceBuilderException(ex);
        }
    }

    public DocumentBuilderFactory getDocumentBuilderFactory() {
        return DOCUMENT_BUILDER_FACTORY;
    }

    //TODO migrate to pooled implementation to enhance memmory usage
    public DocumentBuilder getDocumentBuilder() {
        try {
            return getDocumentBuilderFactory().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new InvoiceBuilderException(ex);
        }
    }
}
