/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import java.math.BigDecimal;
import java.util.Arrays;
import pe.labtech.ubl.model.Invoice;
import pe.labtech.ubl.model.aggregate.AccountingParty;
import pe.labtech.ubl.model.aggregate.Address;
import pe.labtech.ubl.model.aggregate.InvoiceLine;
import pe.labtech.ubl.model.aggregate.LegalMonetaryTotal;
import pe.labtech.ubl.model.aggregate.Party;
import pe.labtech.ubl.model.aggregate.PartyLegalEntity;
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

    private Invoice invoice;

    public InvoiceBuilder init(final String invoiceType, final String invoiceNumber, final String invoiceDate, final String currency, final String supplierType, final String supplierId, final String supplierName, final String clientType, final String clientId, final String clientName) {
        InvoiceBuilder ib = new InvoiceBuilder();
        ib.invoice = createInvoice(invoiceType, invoiceNumber, invoiceDate, currency, supplierType, supplierId, supplierName, clientType, clientId, clientName);
        return ib;
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

    public Invoice compile() {
        //TODO agregar todo el resto
        return invoice;
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

    public InvoiceBuilder addLine(InvoiceLine line) {
        invoice.getInvoiceLine().add(line);
        return this;
    }
}
