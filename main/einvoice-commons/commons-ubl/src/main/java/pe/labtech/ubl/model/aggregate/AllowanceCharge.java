/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.basic.Amount;

/**
* Clase AllowanceCharge.
*
* @author Labtech S.R.L. (info@labtech.pe)
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
