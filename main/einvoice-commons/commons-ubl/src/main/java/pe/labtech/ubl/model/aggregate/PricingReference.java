/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PricingReference {

    @XmlElement(namespace = Namespaces.CAC)
    private AlternativeConditionPrice AlternativeConditionPrice;

    public PricingReference() {
    }

    public PricingReference(String PriceTypeCode, String currencyID, BigDecimal price) {
        if (price != null) {
            this.AlternativeConditionPrice = new AlternativeConditionPrice(PriceTypeCode, currencyID, price);
        }
    }

    public AlternativeConditionPrice getAlternativeConditionPrice() {
        return AlternativeConditionPrice;
    }

    public void setAlternativeConditionPrice(AlternativeConditionPrice AlternativeConditionPrice) {
        this.AlternativeConditionPrice = AlternativeConditionPrice;
    }

}
