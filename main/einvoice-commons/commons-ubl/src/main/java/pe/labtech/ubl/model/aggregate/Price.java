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
import pe.labtech.ubl.model.basic.Amount;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Price {

    @XmlElement(namespace = Namespaces.CBC)
    private Amount PriceAmount;

    public Price() {
    }

    public Price(String currencyID, BigDecimal value) {
        this.PriceAmount = new Amount(currencyID, value);
    }

    public Amount getPriceAmount() {
        return PriceAmount;
    }

    public void setPriceAmount(Amount PriceAmount) {
        this.PriceAmount = PriceAmount;
    }

}
