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
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LegalMonetaryTotal {

    @XmlElement(namespace = Namespaces.CBC)
    private Amount AllowanceTotalAmount;

    /**
     * Importe total de los cargos aplicados al total de la factura.
     */
    @XmlElement(namespace = Namespaces.CBC)
    private Amount ChargeTotalAmount;

    /**
     * Moneda e importe total a pagar.
     */
    @XmlElement(namespace = Namespaces.CBC)
    private Amount PayableAmount;

    public LegalMonetaryTotal() {
    }

    public Amount getAllowanceTotalAmount() {
        return AllowanceTotalAmount;
    }

    public void setAllowanceTotalAmount(Amount AllowanceTotalAmount) {
        this.AllowanceTotalAmount = AllowanceTotalAmount;
    }

    public Amount getChargeTotalAmount() {
        return ChargeTotalAmount;
    }

    public void setChargeTotalAmount(Amount ChargeTotalAmount) {
        this.ChargeTotalAmount = ChargeTotalAmount;
    }

    public Amount getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(Amount PayableAmount) {
        this.PayableAmount = PayableAmount;
    }

}
