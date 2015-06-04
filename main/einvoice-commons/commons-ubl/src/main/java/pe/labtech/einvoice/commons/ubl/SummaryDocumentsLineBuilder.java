/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import java.math.BigDecimal;
import pe.labtech.ubl.model.aggregate.AllowanceCharge;
import pe.labtech.ubl.model.aggregate.TaxCategory;
import pe.labtech.ubl.model.aggregate.TaxScheme;
import pe.labtech.ubl.model.aggregate.TaxSubtotal;
import pe.labtech.ubl.model.aggregate.TaxTotal;
import pe.labtech.ubl.model.basic.Amount;
import pe.labtech.ubl.model.sunat.BillingPayment;
import pe.labtech.ubl.model.sunat.SummaryDocumentsLine;

/**
 *
 * @author Carlos Echeverria
 */
public class SummaryDocumentsLineBuilder implements Builder<SummaryDocumentsLine> {

    private SummaryDocumentsLine item;

    public SummaryDocumentsLineBuilder init(
            String lineNumber,
            String documentType,
            String documentSerial,
            String startNumber,
            String endNumber,
            String currency,
            String totalAmount
    ) {
        this.item = new SummaryDocumentsLine();
        this.item.setLineID(lineNumber);
        this.item.setDocumentTypeCode(documentType);
        this.item.setDocumentSerialID(documentSerial);
        this.item.setStartDocumentNumberID(startNumber);
        this.item.setEndDocumentNumberID(endNumber);
        this.item.setTotalAmount(new Amount(currency, new BigDecimal(totalAmount)));
        return this;
    }

    public SummaryDocumentsLineBuilder addBillingPayment(String instruction, String amount) {
        if (amount != null && instruction != null) {
            this.item.getBillingPayment().add(new BillingPayment(
                    new Amount(this.item.getTotalAmount().getCurrencyID(), new BigDecimal(amount)),
                    instruction
            ));
        }
        return this;
    }

    public SummaryDocumentsLineBuilder addAllowance(Boolean charge, String amount) {
        if (charge != null && amount != null) {
            this.item.getAllowanceCharge().add(
                    new AllowanceCharge(
                            charge.toString(),
                            new Amount(this.item.getTotalAmount().getCurrencyID(), new BigDecimal(amount))
                    )
            );
        }
        return this;
    }

    public SummaryDocumentsLineBuilder addTax(String taxID, String taxName, String taxCode, String amount, String erc, String tr) {
        String currency = this.item.getTotalAmount().getCurrencyID();
        if (amount != null) {
            this.item.getTaxTotal().add(
                    new TaxTotal(
                            new Amount(currency, new BigDecimal(amount)),
                            new TaxSubtotal(
                                    new Amount(currency, new BigDecimal(amount)),
                                    new TaxCategory(
                                            erc,
                                            tr,
                                            new TaxScheme(taxID, taxName, taxCode)
                                    )
                            )
                    )
            );
        }
        return this;
    }

    @Override
    public SummaryDocumentsLine compile() {
        return item;
    }

}
