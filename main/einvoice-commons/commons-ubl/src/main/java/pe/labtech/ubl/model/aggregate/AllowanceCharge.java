/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.basic.Amount;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AllowanceCharge {

    @XmlElement(namespace = Namespaces.CBC)
    private String ChargeIndicator;

    @XmlElement(namespace = Namespaces.CBC)
    private Amount Amount;

    public AllowanceCharge() {
    }

    public AllowanceCharge(String ChargeIndicator, Amount Amount) {
        this.ChargeIndicator = ChargeIndicator;
        this.Amount = Amount;
    }

    public String getChargeIndicator() {
        return ChargeIndicator;
    }

    public void setChargeIndicator(String ChargeIndicator) {
        this.ChargeIndicator = ChargeIndicator;
    }

    public Amount getAmount() {
        return Amount;
    }

    public void setAmount(Amount Amount) {
        this.Amount = Amount;
    }

}
