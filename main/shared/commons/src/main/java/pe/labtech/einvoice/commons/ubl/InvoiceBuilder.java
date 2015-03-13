/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import java.math.BigDecimal;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalInformationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalAmount;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalInformation;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalProperty;

/**
 *
 * @author Carlos
 */
public class InvoiceBuilder {

    private InvoiceType invoice;

    private AdditionalInformation sunatAdditionalInformation;

    public void init() {
        invoice = new InvoiceType();

        //building ubl extensions part
        invoice.setUBLExtensions(new UBLExtensionsType());
        
        //SUNAT extension
        final UBLExtensionType sunatExtension = new UBLExtensionType("SUNAT");
        sunatAdditionalInformation = new AdditionalInformation();
        sunatExtension.setExtensionContent(new ExtensionContentType());
        sunatExtension.getExtensionContent().setAdditionalInformationSunat(sunatAdditionalInformation);
        invoice.getUBLExtensions().getUBLExtension().add(sunatExtension);

        //ebiz extension
        final UBLExtensionType ebizExtension = new UBLExtensionType("EBIZ");
        invoice.getUBLExtensions().getUBLExtension().add(ebizExtension);
        ebizExtension.setExtensionContent(new ExtensionContentType());
        ebizExtension.getExtensionContent().setAdditionalInformationCBC(new AdditionalInformationType());

        //signature holder
        final UBLExtensionType signExtension = new UBLExtensionType();
        signExtension.setExtensionContent(new ExtensionContentType());
        invoice.getUBLExtensions().getUBLExtension().add(signExtension);
        
    }

    public InvoiceBuilder initialize() {
        this.init();
        return this;
    }

    public InvoiceBuilder setCurrency(String currency) {
        DocumentCurrencyCodeType c = new DocumentCurrencyCodeType();
        c.setValue(currency);
        invoice.setDocumentCurrencyCode(c);
        return this;
    }

    public InvoiceBuilder addAmountTotal(String id, String currency, String value) {
        if (value != null) {
            sunatAdditionalInformation.getAdditionalMonetaryTotals().add(
                    new AdditionalAmount(id, currency, new BigDecimal(value))
            );
        }
        return this;
    }

    public InvoiceBuilder addAmountTotal(String id, String value) {
        return this.addAmountTotal(
                id,
                this.getInvoice().getDocumentCurrencyCode().getValue(),
                value
        );
    }

    public InvoiceBuilder addAmountProperty(String id, String value) {
        if (value != null) {
            sunatAdditionalInformation.getAdditionalProperties().add(
                    new AdditionalProperty(id, value)
            );
        }
        return this;
    }

    public InvoiceType getInvoice() {
        return invoice;
    }

}
