/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.sunat;

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
public class AdditionalMonetaryTotal {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;
    @XmlElement(namespace = Namespaces.CBC)
    private String Name;
    @XmlElement(namespace = Namespaces.SAC)
    private Amount ReferenceAmount;
    @XmlElement(namespace = Namespaces.CBC)
    private Amount PayableAmount;
    @XmlElement(namespace = Namespaces.CBC)
    private String Percent;
    @XmlElement(namespace = Namespaces.SAC)
    private Amount TotalAmount;

    public AdditionalMonetaryTotal() {
    }

    public AdditionalMonetaryTotal(String ID, String Name, Amount ReferenceAmount, Amount PayableAmount, String Percent, Amount TotalAmount) {
        this.ID = ID;
        this.Name = Name;
        this.ReferenceAmount = ReferenceAmount;
        this.PayableAmount = PayableAmount;
        this.Percent = Percent;
        this.TotalAmount = TotalAmount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Amount getReferenceAmount() {
        return ReferenceAmount;
    }

    public void setReferenceAmount(Amount ReferenceAmount) {
        this.ReferenceAmount = ReferenceAmount;
    }

    public Amount getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(Amount PayableAmount) {
        this.PayableAmount = PayableAmount;
    }

    public String getPercent() {
        return Percent;
    }

    public void setPercent(String Percent) {
        this.Percent = Percent;
    }

    public Amount getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Amount TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

}
