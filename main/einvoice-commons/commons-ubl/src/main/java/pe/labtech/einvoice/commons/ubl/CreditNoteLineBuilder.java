/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ubl;

import java.math.BigDecimal;
import pe.labtech.ubl.model.aggregate.AlternativeConditionPrice;
import pe.labtech.ubl.model.aggregate.CreditNoteLine;
import pe.labtech.ubl.model.aggregate.Item;
import pe.labtech.ubl.model.aggregate.Price;
import pe.labtech.ubl.model.aggregate.PricingReference;
import pe.labtech.ubl.model.aggregate.TaxCategory;
import pe.labtech.ubl.model.aggregate.TaxScheme;
import pe.labtech.ubl.model.aggregate.TaxSubtotal;
import pe.labtech.ubl.model.aggregate.TaxTotal;
import pe.labtech.ubl.model.basic.Amount;
import pe.labtech.ubl.model.basic.Quantity;

/**
* Clase CreditNoteLineBuilder.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class CreditNoteLineBuilder implements Builder<CreditNoteLine> {

    private CreditNoteLine item;

    @Override
    public CreditNoteLine compile() {
        return item;
    }

    public CreditNoteLineBuilder init(
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
        CreditNoteLineBuilder ilb = this;
        CreditNoteLine il = new CreditNoteLine();
        //id de la linea
        il.setID(lineNumber);
        //cantidad de items
        il.setCreditedQuantity(new Quantity(unit, quantity));
        //codigo y descrpcion del articulo
        il.setItem(new Item(code));
        il.getItem().getDescription().add(description);
        //precio unitario sin impuestos
        il.setPrice(new Price(currencyID, unitPrice));
        //precio unitario alternativo
        il.setPricingReference(new PricingReference());
        il.getPricingReference().getAlternativeConditionPrice().add(new AlternativeConditionPrice(alternateUnitPriceCode, currencyID, alternateUnitPrice));
        //total de la línea
        il.setLineExtensionAmount(new Amount(currencyID, totalAmount));
        ilb.item = il;
        return ilb;
    }

    public CreditNoteLineBuilder addAlternativeConditionPrice(String PriceTypeCode, String currencyID, BigDecimal price) {
        if (PriceTypeCode != null && currencyID != null && price != null) {
            this.item.getPricingReference().getAlternativeConditionPrice().add(new AlternativeConditionPrice(PriceTypeCode, currencyID, price));
        }
        return this;
    }

    public CreditNoteLineBuilder addTax(String taxID, String taxName, String taxCode, BigDecimal amount, String erc, String tr) {
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

}
