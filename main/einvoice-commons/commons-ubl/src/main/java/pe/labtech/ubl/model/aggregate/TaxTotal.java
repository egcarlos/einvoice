/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.basic.Amount;

/**
 *
 * @author carloseg
 */
public class TaxTotal {

    @XmlElement(namespace = Namespaces.CBC)
    private Amount TaxAmount;

    @XmlElement(namespace = Namespaces.CAC)
    private TaxSubtotal TaxSubtotal;

    public Amount getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(Amount TaxAmount) {
        this.TaxAmount = TaxAmount;
    }

    public TaxSubtotal getTaxSubtotal() {
        return TaxSubtotal;
    }

    public void setTaxSubtotal(TaxSubtotal TaxSubtotal) {
        this.TaxSubtotal = TaxSubtotal;
    }

}
