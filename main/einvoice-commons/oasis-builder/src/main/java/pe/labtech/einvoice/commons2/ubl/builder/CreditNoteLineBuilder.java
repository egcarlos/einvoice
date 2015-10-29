/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.builder;

import pe.labtech.einvoice.commons2.ubl.api.BaseBuilder;
import pe.labtech.einvoice.commons2.ubl.api.Builder;
import java.math.BigDecimal;
import java.util.function.BiConsumer;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CreditNoteLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PricingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CreditedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxExemptionReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TierRangeType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;

/**
 *
 * @author carloseg
 */
public class CreditNoteLineBuilder extends BaseBuilder<CreditNoteLineType> implements Builder<CreditNoteLineType> {

    private CurrencyCodeContentType currency;

    public CreditNoteLineBuilder() {
        super(CreditNoteLineType::new);
    }

    @Override
    public CreditNoteLineBuilder init() {
        return (CreditNoteLineBuilder) super.init();
    }

    @Override
    public CreditNoteLineType compile() {
        return reference;
    }

    /**
     *
     * @param lineNumber id de linea
     * @param quantity cantidad de articulos
     * @param unit tipo de unidad NIU o ZZ
     * @param code codigo del item en el sistema del cliente
     * @param description Descripción principal del articulo
     * @param currency moneda del articulo
     * @param unitPrice precio unitario sin impuesto
     * @param alternateUnitPriceCode codigo de precio unitario referencial
     * @param alternateUnitPrice precio unitario referencial
     * @param totalAmount total de la linea de deatalle incluye subenciones
     * cargos y descuentos
     * @return
     */
    public CreditNoteLineBuilder init(String lineNumber, BigDecimal quantity, String unit, String code, String description, String currency, BigDecimal unitPrice, String alternateUnitPriceCode, BigDecimal alternateUnitPrice, BigDecimal totalAmount) {
        return init(lineNumber, quantity, unit, code, description, CurrencyCodeContentType.valueOf(currency), unitPrice, alternateUnitPriceCode, alternateUnitPrice, totalAmount);
    }

    /**
     *
     * @param lineNumber id de linea
     * @param quantity cantidad de articulos
     * @param unit tipo de unidad NIU o ZZ
     * @param code codigo del item en el sistema del cliente
     * @param description Descripción principal del articulo
     * @param currency moneda del articulo
     * @param unitPrice precio unitario sin impuesto
     * @param alternateUnitPriceCode codigo de precio unitario referencial
     * @param alternateUnitPrice precio unitario referencial
     * @param totalAmount total de la linea de deatalle incluye subenciones
     * cargos y descuentos
     * @return
     */
    public CreditNoteLineBuilder init(String lineNumber, BigDecimal quantity, String unit, String code, String description, CurrencyCodeContentType currency, BigDecimal unitPrice, String alternateUnitPriceCode, BigDecimal alternateUnitPrice, BigDecimal totalAmount) {
        this.currency = currency;
        reference = init(CreditNoteLineType::new, i -> {
            i.setID(id(lineNumber));
            i.setCreditedQuantity(quantity(CreditedQuantityType::new, unit, quantity));

            if (unitPrice != null) {
                i.setPrice(init(PriceType::new, PriceType::setPriceAmount, amount(PriceAmountType::new, currency, unitPrice)));
            }
            if (totalAmount != null) {
                i.setLineExtensionAmount(amount(LineExtensionAmountType::new, currency, totalAmount));
            }
        });
        addAlternativeConditionPrice(alternateUnitPriceCode, alternateUnitPrice);
        addDescription(description);
        addItemAttribute(ItemType::setSellersItemIdentification, init(ItemIdentificationType::new, ItemIdentificationType::setID, id(code)));
        return this;
    }

    private CreditNoteLineBuilder addDescription(String description) {
        if (description != null) {
            addItemAttribute((e, t) -> e.getDescription().add(t), text(DescriptionType::new, description));
        }
        return this;
    }

    public CreditNoteLineBuilder addAdditionalItemProperty(String code, String value) {
        if (code != null && value != null) {
            addItemAttribute((i, t) -> i.getAdditionalItemProperty().add(t), itemProperty(code, value));
        }
        return this;
    }

    public <T> CreditNoteLineBuilder addItemAttribute(BiConsumer<ItemType, T> consumer, T value) {
        if (this.reference.getItem() == null) {
            this.reference.setItem(new ItemType());
        }
        consumer.accept(this.reference.getItem(), value);
        return this;
    }

    public CreditNoteLineBuilder addAlternativeConditionPrice(String PriceTypeCode, BigDecimal price) {
        if (PriceTypeCode != null && currency != null && price != null) {
            if (reference.getPricingReference() == null) {
                reference.setPricingReference(new PricingReferenceType());
            }
            reference.getPricingReference().getAlternativeConditionPrice().add(init(PriceType::new, ref -> {
                ref.setPriceAmount(amount(PriceAmountType::new, currency, price));
                ref.setPriceTypeCode(code(PriceTypeCodeType::new, PriceTypeCode));
            }));
        }
        return this;
    }

    public CreditNoteLineBuilder addTax(String taxID, String taxName, String taxCode, BigDecimal amount, String erc, String tr) {
        if (amount != null) {
            reference.getTaxTotal().add(init(TaxTotalType::new, ref -> {
                ref.setTaxAmount(amount(TaxAmountType::new, currency, amount));
                ref.getTaxSubtotal().add(init(TaxSubtotalType::new, tst -> {
                    tst.setTaxAmount(amount(TaxAmountType::new, currency, amount));
                    tst.setTaxCategory(init(TaxCategoryType::new, tct -> {
                        //aplica para los tres tipos de impuestos
                        tct.setTaxScheme(init(TaxSchemeType::new, tht -> {
                            tht.setID(id(taxID));
                            tht.setName(name(NameType::new, taxName));
                            tht.setTaxTypeCode(code(TaxTypeCodeType::new, taxCode));
                        }));
                        //aplica para el IGV
                        if (tr != null) {
                            tct.setTierRange(text(TierRangeType::new, tr));
                        }
                        //aplica para el ISC
                        if (erc != null) {
                            tct.setTaxExemptionReasonCode(code(TaxExemptionReasonCodeType::new, erc));
                        }
                    }));
                }));
            }));
        }
        return this;
    }

//    public CreditNoteLineBuilder addAllowance(BigDecimal amount) {
//        return addAllowanceCharge(false, amount);
//    }
//
//    public CreditNoteLineBuilder addCharge(BigDecimal amount) {
//        return addAllowanceCharge(true, amount);
//    }
//    public CreditNoteLineBuilder addAllowanceCharge(boolean chargeIndicator, BigDecimal amount) {
//        if (amount != null) {
//            reference.getAllowanceCharge().add(init(AllowanceChargeType::new, ref -> {
//                ref.setAmount(amount(AmountType::new, currency, amount));
//                ref.setChargeIndicator(init(ChargeIndicatorType::new, ChargeIndicatorType::setValue, chargeIndicator));
//            }));
//        }
//        return this;
//    }
}
