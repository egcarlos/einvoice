/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.builder;

import pe.labtech.einvoice.commons2.ubl.api.BaseBuilder;
import pe.labtech.einvoice.commons2.ubl.jaxb.InvoiceContext;
import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ExternalReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.OrderReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SignatureType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AllowanceTotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChargeTotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CityNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CitySubdivisionNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CountrySubentityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomerAssignedAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DistrictType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IdentificationCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoiceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrepaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StreetNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UBLVersionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.URIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import org.w3c.dom.Document;
import pe.labtech.einvoice.commons2.ubl.api.HeaderBuilder;
import pe.labtech.einvoice.commons2.ubl.jaxb.AdditionalInformationContext;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalInformationType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalMonetaryTotalType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalPropertyType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;

/**
 *
 * @author Carlos
 */
public class InvoiceBuilder extends BaseBuilder<InvoiceType> implements HeaderBuilder<InvoiceType, InvoiceLineType> {

    private CurrencyCodeContentType currency;
    private AdditionalInformationType sunatElements;
    private AdditionalInformationType otherElements;

    public InvoiceBuilder() {
        super(InvoiceType::new);
    }

    @Override
    public InvoiceBuilder init() {
        super.init();
        sunatElements = new AdditionalInformationType();
        otherElements = new AdditionalInformationType();
        reference.setUBLExtensions(init(UBLExtensionsType::new, exts -> {
            exts.getUBLExtension().add(init(UBLExtensionType::new, ref -> {
                ref.setID(id("SUNAT"));
                ref.setExtensionContent(init(ExtensionContentType::new));
            }));
            exts.getUBLExtension().add(init(UBLExtensionType::new, ref -> {
                ref.setID(id("EBIZ"));
                ref.setExtensionContent(new ExtensionContentType());
            }));
        }));
        reference.setUBLVersionID(identifier(UBLVersionIDType::new, "2.0"));
        reference.setCustomizationID(identifier(CustomizationIDType::new, "1.0"));
        return this;
    }

    public InvoiceBuilder addCurrency(String currency) {
        this.currency = CurrencyCodeContentType.valueOf(currency);
        reference.setDocumentCurrencyCode(init(DocumentCurrencyCodeType::new, DocumentCurrencyCodeType::setValue, currency));
        return this;
    }

    public InvoiceBuilder addIdentification(String invoiceTypeCode, String invoiceNumber, String invoiceDate) {
        reference.setID(id(invoiceNumber));
        reference.setInvoiceTypeCode(code(InvoiceTypeCodeType::new, invoiceTypeCode));
        reference.setIssueDate(init(IssueDateType::new, ref -> ref.setValue(toXMLGregorianCalendar(invoiceDate))));
        return this;
    }

    public InvoiceBuilder addSignatureElement(String supplierId, String supplierName) {
        reference.getSignature().add(init(SignatureType::new, st -> {
            st.setID(id("IDSignKG"));
            st.setSignatoryParty(init(PartyType::new, pt -> {
                pt.getPartyIdentification().add(init(PartyIdentificationType::new, PartyIdentificationType::setID, id(supplierId)));
                pt.getPartyName().add(init(PartyNameType::new, PartyNameType::setName, name(NameType::new, supplierName)));
            }));
            st.setDigitalSignatureAttachment(init(AttachmentType::new, at -> {
                at.setExternalReference(init(
                        ExternalReferenceType::new,
                        ExternalReferenceType::setURI,
                        init(URIType::new, URIType::setValue, "#signatureKG")
                ));
            }));
        }));
        return this;
    }

    public InvoiceBuilder addSupplierInformation(String supplierType, String supplierId, String supplierName) {
        reference.setAccountingSupplierParty(init(SupplierPartyType::new, spt -> {
            spt.getAdditionalAccountID().add(identifier(AdditionalAccountIDType::new, supplierType));
            spt.setCustomerAssignedAccountID(identifier(CustomerAssignedAccountIDType::new, supplierId));
            spt.setParty(init(PartyType::new, pt -> {
                pt.setPostalAddress(new AddressType());
                pt.getPartyLegalEntity().add(init(
                        PartyLegalEntityType::new,
                        PartyLegalEntityType::setRegistrationName,
                        name(RegistrationNameType::new, supplierName)
                ));
            }));
        }));
        return this;
    }

    public InvoiceBuilder addCustomerInformation(String clientType, String clientId, String clientName) {
        reference.setAccountingCustomerParty(init(CustomerPartyType::new, cpt -> {
            cpt.getAdditionalAccountID().add(identifier(AdditionalAccountIDType::new, clientType));
            cpt.setCustomerAssignedAccountID(identifier(CustomerAssignedAccountIDType::new, clientId));
            cpt.setParty(init(PartyType::new, pt -> {
                pt.setPostalAddress(new AddressType());
                pt.getPartyLegalEntity().add(init(
                        PartyLegalEntityType::new,
                        PartyLegalEntityType::setRegistrationName,
                        name(RegistrationNameType::new, clientName)
                ));
            }));
        }));
        return this;
    }

    public InvoiceBuilder addNote(String id, String value) {
        if (id != null && value != null) {
            sunatElements.getAdditionalProperty().add(init(AdditionalPropertyType::new, ref -> {
                ref.setID(id(id));
                ref.setValue(text(ValueType::new, value));
            }));
        }
        return this;
    }

    public InvoiceBuilder addCustomNote(String id, String value) {
        if (id != null && value != null) {
            otherElements.getAdditionalProperty().add(AdditionalPropertyType(id, value));
        }
        return this;
    }

    public InvoiceBuilder addTax(String id, String name, String typeCode, String amount) {
        if (amount == null) {
            return this;
        }
        return addTax(id, name, typeCode, new BigDecimal(amount));
    }

    public InvoiceBuilder addTax(String id, String name, String typeCode, BigDecimal amount) {
        this.reference.getTaxTotal().add(init(TaxTotalType::new, ttt -> {
            ttt.setTaxAmount(amount(TaxAmountType::new, currency, amount));
            ttt.getTaxSubtotal().add(init(TaxSubtotalType::new, tst -> {
                tst.setTaxAmount(amount(TaxAmountType::new, currency, amount));
                tst.setTaxCategory(init(TaxCategoryType::new, tct -> {
                    tct.setTaxScheme(init(TaxSchemeType::new, tht -> {
                        tht.setID(id(id));
                        tht.setName(name(NameType::new, name));
                        tht.setTaxTypeCode(code(TaxTypeCodeType::new, typeCode));
                    }));
                }));
            }));
        }));
        return this;
    }

    public InvoiceBuilder addTotalAllowance(String amount) {
        if (amount != null) {
            addTotalAllowance(new BigDecimal(amount));
        }
        return this;
    }

    public InvoiceBuilder addTotalAllowance(BigDecimal amount) {
        return this.addLegalMonetaryTotal(
                AllowanceTotalAmountType::new,
                MonetaryTotalType::setAllowanceTotalAmount,
                amount
        );
    }

    public InvoiceBuilder addTotalCharge(String amount) {
        return this.addTotalCharge(bigDecimal(amount));
    }

    public InvoiceBuilder addTotalCharge(BigDecimal amount) {
        return this.addLegalMonetaryTotal(
                ChargeTotalAmountType::new,
                MonetaryTotalType::setChargeTotalAmount,
                amount
        );
    }

    public <M extends un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.AmountType>
            InvoiceBuilder addLegalMonetaryTotal(
                    Supplier<M> supplier,
                    BiConsumer<MonetaryTotalType, M> consumer,
                    BigDecimal amount
            ) {
        if (amount != null) {
            if (this.reference.getLegalMonetaryTotal() == null) {
                this.reference.setLegalMonetaryTotal(new MonetaryTotalType());
            }
            consumer.accept(this.reference.getLegalMonetaryTotal(), amount(supplier, currency, amount));
        }
        return this;
    }

    public InvoiceBuilder addPrepaidPayment(String issuerType, String issuerId, String documentType, String documentNumber, String amount) {
        return addPrepaidPayment(issuerType, issuerId, documentType, documentNumber, new BigDecimal(amount));
    }

    public InvoiceBuilder addPrepaidPayment(String issuerType, String issuerId, String documentType, String documentNumber, BigDecimal amount) {
        reference.getPrepaidPayment().add(init(PaymentType::new,
                ref -> {
                    ref.setInstructionID(init(() -> identifier(InstructionIDType::new, issuerId), i -> i.setSchemeID(issuerType)));
                    ref.setID(init(() -> id(documentNumber), i -> i.setSchemeID(documentType)));
                    ref.setPaidAmount(amount(PaidAmountType::new, currency, amount));
                }
        ));

        return this;
    }

    public InvoiceBuilder fixPrepaidAmount() {
        if (!reference.getPrepaidPayment().isEmpty()) {
            BigDecimal amount = new BigDecimal(0);
            for (PaymentType pt : reference.getPrepaidPayment()) {
                amount = amount.add(pt.getPaidAmount().getValue());
            }
            addPrepaidAmount(amount);
        }
        return this;
    }

    public InvoiceBuilder addPrepaidAmount(String amount) {
        return this.addPrepaidAmount(bigDecimal(amount));
    }

    public InvoiceBuilder addPrepaidAmount(BigDecimal amount) {
        return this.addLegalMonetaryTotal(PrepaidAmountType::new, MonetaryTotalType::setPrepaidAmount, amount);
    }

    public InvoiceBuilder addTotalPayable(String amount) {
        return this.addTotalPayable(bigDecimal(amount));
    }

    public InvoiceBuilder addTotalPayable(BigDecimal amount) {
        return this.addLegalMonetaryTotal(PayableAmountType::new, MonetaryTotalType::setPayableAmount, amount);
    }

    /**
     *
     * @param name
     * @return
     */
    public InvoiceBuilder setIssuerName(String name) {
        if (name == null) {
            return this;
        }
        reference.getAccountingSupplierParty().getParty().getPartyName().add(init(
                PartyNameType::new,
                ref -> {
                    ref.setName(name(NameType::new, name));
                }
        ));

        return this;
    }

    /**
     * Establece la dirección del emisor.
     *
     * @param id ubigeo
     * @param address dirección postal
     * @param zone urbanización o zona
     * @param district distrito
     * @param city provincia
     * @param state departamento
     * @param country código iso del país
     * @return
     */
    public InvoiceBuilder setIssuerAddress(String id, String address, String zone, String district, String city, String state, String country) {

        AddressType a = reference.getAccountingSupplierParty().getParty().getPostalAddress();
        if (id != null) {
            a.setID(id(id));
        }
        if (address != null) {
            a.setStreetName(name(StreetNameType::new, address));
        }
        if (zone != null) {
            a.setCitySubdivisionName(name(CitySubdivisionNameType::new, zone));
        }
        if (district != null) {
            a.setDistrict(text(DistrictType::new, district));
        }
        if (city != null) {
            a.setCountrySubentity(text(CountrySubentityType::new, city));
        }
        if (state != null) {
            a.setCityName(name(CityNameType::new, state));
        }
        if (country != null) {
            a.setCountry(init(CountryType::new, ref -> ref.setIdentificationCode(code(IdentificationCodeType::new, country))));
        }

        return this;
    }

    /**
     *
     * @param name
     * @return
     */
    public InvoiceBuilder setAcquirerName(String name) {
        if (name == null) {
            return this;
        }
        reference.getAccountingCustomerParty().getParty().getPartyName().add(init(
                PartyNameType::new,
                ref -> {
                    ref.setName(name(NameType::new, name));
                }
        ));

        return this;
    }

    /**
     * Establece la dirección del emisor.
     *
     * @param id ubigeo
     * @param address dirección postal
     * @param zone urbanización o zona
     * @param district distrito
     * @param city provincia
     * @param state departamento
     * @param country código iso del país
     * @return
     */
    public InvoiceBuilder setAcquirerAddress(String id, String address, String zone, String district, String city, String state, String country) {

        AddressType a = reference.getAccountingCustomerParty().getParty().getPostalAddress();
        if (id != null) {
            a.setID(id(id));
        }
        if (address != null) {
            a.setStreetName(name(StreetNameType::new, address));
        }
        if (zone != null) {
            a.setCitySubdivisionName(name(CitySubdivisionNameType::new, zone));
        }
        if (district != null) {
            a.setDistrict(text(DistrictType::new, district));
        }
        if (city != null) {
            a.setCountrySubentity(text(CountrySubentityType::new, city));
        }
        if (state != null) {
            a.setCityName(name(CityNameType::new, state));
        }
        if (country != null) {
            a.setCountry(init(CountryType::new, ref -> ref.setIdentificationCode(code(IdentificationCodeType::new, country))));
        }

        return this;
    }

    /**
     *
     * @param reference
     * @param payable
     * @param total
     * @param percent
     * @return
     */
    public InvoiceBuilder addPerception(String reference, String payable, String total, String percent) {
        return addAmount("2001", payable, reference, total, percent, null);
    }

    /**
     *
     * @param id identificador del tipo de monto
     * @param payable
     * @param reference
     * @param total
     * @param percent
     * @param description
     * @return
     */
    public InvoiceBuilder addAmount(
            String id,
            String payable,
            String reference,
            String total,
            String percent,
            String description
    ) {
        if (payable != null) {
            sunatElements.getAdditionalMonetaryTotal().add(init(
                    AdditionalMonetaryTotalType::new,
                    ref -> {
                        ref.setID(id(id));
                        ref.setPayableAmount(amount(PayableAmountType::new, currency, new BigDecimal(payable)));
                        if (reference != null) {
                            ref.setReferenceAmount(amount(AmountType::new, currency, new BigDecimal(reference)));
                        }
                        if (total != null) {
                            ref.setTotalAmount(amount(AmountType::new, currency, new BigDecimal(total)));
                        }
                        if (percent != null) {
                            ref.setPercent(init(PercentType::new, pt -> pt.setValue(new BigDecimal(percent))));
                        }
                        if (description != null) {
                            ref.setName(name(NameType::new, description));
                        }
                    }
            ));
        }
        return this;
    }

    /**
     *
     * @param id
     * @param amount
     * @return
     */
    public InvoiceBuilder addAmount(String id, String amount) {
        return this.addAmount(id, amount, null, null, null, null);
    }

    /**
     *
     * @param payable
     * @param percent
     * @return
     */
    public InvoiceBuilder addRetention(String payable, String percent) {
        return addAmount("2002", payable, null, null, percent, null);
    }

    /**
     *
     * @param reference
     * @param payable
     * @param percent
     * @param description
     * @return
     */
    public InvoiceBuilder addDetraction(String reference, String payable, String percent, String description) {
        return addAmount("2003", payable, reference, null, percent, description);
    }

    /**
     *
     * @param type
     * @param number
     * @return
     */
    public InvoiceBuilder addDespatchReference(String type, String number) {
        if (type != null && number != null) {
            DocumentReferenceType drt = new DocumentReferenceType();
            drt.setDocumentTypeCode(new DocumentTypeCodeType());
            drt.getDocumentTypeCode().setValue(type);
            drt.setID(new IDType());
            drt.getID().setValue(type);
            reference.getDespatchDocumentReference().add(drt);
        }
        return this;
    }

    /**
     *
     * @param type
     * @param number
     * @return
     */
    public InvoiceBuilder addAdditionalReference(String type, String number) {
        if (type != null && number != null) {
            DocumentReferenceType drt = new DocumentReferenceType();
            drt.setDocumentTypeCode(new DocumentTypeCodeType());
            drt.getDocumentTypeCode().setValue(type);
            drt.setID(new IDType());
            drt.getID().setValue(type);
            reference.getAdditionalDocumentReference().add(drt);
        }
        return this;
    }

    /**
     *
     * @param id
     * @return
     */
    public InvoiceBuilder addOrderReference(String id) {
        if (id != null) {
            reference.setOrderReference(init(
                    OrderReferenceType::new,
                    ref -> ref.setID(id(id))
            ));
        }
        return this;
    }

    public InvoiceBuilder addLine(InvoiceLineType line) {
        reference.getInvoiceLine().add(line);
        return this;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public InvoiceType compile() {
        DocumentBuilder db = getDocumentBuilder();
        ExtensionContentType ext = reference.getUBLExtensions().getUBLExtension().get(0).getExtensionContent();
        ExtensionContentType ebz = reference.getUBLExtensions().getUBLExtension().get(1).getExtensionContent();

        ext.setAny(AdditionalInformationContext.reference.doWork((c, m) -> {
            if (sunatElements.getAdditionalMonetaryTotal().isEmpty() && sunatElements.getAdditionalProperty().isEmpty()) {
                return null;
            }
            Document document = db.newDocument();
            JAXBElement<AdditionalInformationType> ie = c.getElement(sunatElements);
            try {
                m.marshal(ie, document);

            } catch (JAXBException ex) {
                Logger.getLogger(InvoiceBuilder.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            return document.getDocumentElement();
        }));

        ebz.setAny(AdditionalInformationContext.reference.doWork((c, m) -> {
            if (otherElements.getAdditionalProperty().isEmpty()) {
                return null;
            }
            Document document = db.newDocument();
            JAXBElement<AdditionalInformationType> ie = c.getElement(otherElements);
            try {
                m.marshal(ie, document);

            } catch (JAXBException ex) {
                Logger.getLogger(InvoiceBuilder.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            document.renameNode(document.getDocumentElement(), "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", "AdditionalInformation");
            document.getDocumentElement().setPrefix("cbc");

            return document.getDocumentElement();
        }));
        return reference;
    }

    /**
     * Final operation used in order to get the XML representation of the UBL
     * document.
     *
     * @param charsetName
     * @return
     */
    public Document document(String charsetName) {
        DocumentBuilder db = getDocumentBuilder();
        InvoiceType compiled = this.compile();

        return InvoiceContext.reference.doWork((c, m) -> {
            Document document = db.newDocument();
            JAXBElement<InvoiceType> ie = c.getElement(compiled);
            try {
                m.marshal(ie, document);

            } catch (JAXBException ex) {
                Logger.getLogger(InvoiceBuilder.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            return document;
        });
    }
}
