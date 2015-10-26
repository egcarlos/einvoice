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
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DebitNoteLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PricingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DebitedQuantityType;
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
public class DebitNoteLineBuilder extends BaseBuilder<DebitNoteLineType> implements Builder<DebitNoteLineType> {

    private CurrencyCodeContentType currency;

    public DebitNoteLineBuilder() {
        super(DebitNoteLineType::new);
    }

    @Override
    public DebitNoteLineBuilder init() {
        return (DebitNoteLineBuilder) super.init();
    }

    @Override
    public DebitNoteLineType compile() {
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
    public DebitNoteLineBuilder init(String lineNumber, BigDecimal quantity, String unit, String code, String description, String currency, BigDecimal unitPrice, String alternateUnitPriceCode, BigDecimal alternateUnitPrice, BigDecimal totalAmount) {
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
    public DebitNoteLineBuilder init(String lineNumber, BigDecimal quantity, String unit, String code, String description, CurrencyCodeContentType currency, BigDecimal unitPrice, String alternateUnitPriceCode, BigDecimal alternateUnitPrice, BigDecimal totalAmount) {
        this.currency = currency;
        reference = init(DebitNoteLineType::new, i -> {
            i.setID(id(lineNumber));
            i.setDebitedQuantity(quantity(DebitedQuantityType::new, unit, quantity));

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

    private DebitNoteLineBuilder addDescription(String description) {
        if (description != null) {
            addItemAttribute((e, t) -> e.getDescription().add(t), text(DescriptionType::new, description));
        }
        return this;
    }

    public DebitNoteLineBuilder addAdditionalItemProperty(String code, String value) {
        if (code != null && value != null) {
            addItemAttribute((i, t) -> i.getAdditionalItemProperty().add(t), itemProperty(code, value));
        }
        return this;
    }

    public <T> DebitNoteLineBuilder addItemAttribute(BiConsumer<ItemType, T> consumer, T value) {
        if (this.reference.getItem() == null) {
            this.reference.setItem(new ItemType());
        }
        consumer.accept(this.reference.getItem(), value);
        return this;
    }

    public DebitNoteLineBuilder addAlternativeConditionPrice(String PriceTypeCode, BigDecimal price) {
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

    public DebitNoteLineBuilder addTax(String taxID, String taxName, String taxCode, BigDecimal amount, String erc, String tr) {
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
}
