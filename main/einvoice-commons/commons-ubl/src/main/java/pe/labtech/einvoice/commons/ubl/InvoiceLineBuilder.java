/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import java.math.BigDecimal;
import pe.labtech.ubl.model.aggregate.AllowanceCharge;
import pe.labtech.ubl.model.aggregate.InvoiceLine;
import pe.labtech.ubl.model.aggregate.Item;
import pe.labtech.ubl.model.aggregate.Price;
import pe.labtech.ubl.model.aggregate.PricingReference;
import pe.labtech.ubl.model.aggregate.TaxCategory;
import pe.labtech.ubl.model.aggregate.TaxScheme;
import pe.labtech.ubl.model.aggregate.TaxSubtotal;
import pe.labtech.ubl.model.aggregate.TaxTotal;
import pe.labtech.ubl.model.basic.Amount;
import pe.labtech.ubl.model.basic.Quantity;
import pe.labtech.ubl.model.sunat.AdditionalProperty;

/**
 *
 * @author carloseg
 */
public class InvoiceLineBuilder implements Builder<InvoiceLine> {

    private InvoiceLine item;

    @Override
    public InvoiceLine compile() {
        return item;
    }

    public InvoiceLineBuilder init(
            //id de linea
            String lineNumber,
            //cantidad de articulos
            BigDecimal quantity,
            //tipo de unidad NIU o ZZ
            String unit,
            //codigo del item en el sistema del cliente
            String code,
            //Descripción principal del articulo
            String description,
            //Moneda del articulo
            String currencyID,
            //precio unitario sin impuesto
            BigDecimal unitPrice,
            //codigo de precio unitario referencial
            String alternateUnitPriceCode,
            //precio unitario alternativo
            BigDecimal alternateUnitPrice,
            //total de la linea de deatalle incluye subenciones cargos y descuentos
            BigDecimal totalAmount) {
        InvoiceLineBuilder ilb = new InvoiceLineBuilder();
        InvoiceLine il = new InvoiceLine();
        //id de la linea
        il.setID(lineNumber);
        //cantidad de items
        il.setInvoicedQuantity(new Quantity(unit, quantity));
        //codigo y descrpcion del articulo
        il.setItem(new Item(code));
        il.getItem().getAdditionalItemProperty().add(new AdditionalProperty(null, "9005", unit));
        il.getItem().getDescription().add(description);
        //precio unitario sin impuestos
        il.setPrice(new Price(currencyID, unitPrice));
        //precio unitario alternativo
        il.setPricingReference(new PricingReference(alternateUnitPriceCode, currencyID, alternateUnitPrice));
        //total de la línea
        il.setLineExtensionAmount(new Amount(currencyID, totalAmount));
        ilb.item = il;
        return ilb;
    }

    public InvoiceLineBuilder addTax(String taxID, String taxName, String taxCode, BigDecimal amount, String erc, String tr) {
        String currency = this.item.getLineExtensionAmount().getCurrencyID();
        if (amount != null) {
            this.item.getTaxTotal().add(
                    new TaxTotal(
                            new Amount(currency, amount),
                            new TaxSubtotal(
                                    new Amount(currency, amount),
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

    public InvoiceLineBuilder addAllowance(BigDecimal allowance) {
        if (allowance != null) {
            item.setAllowanceCharge(new AllowanceCharge("false", new Amount(item.getLineExtensionAmount().getCurrencyID(), allowance)));
        }
        return this;
    }
}
