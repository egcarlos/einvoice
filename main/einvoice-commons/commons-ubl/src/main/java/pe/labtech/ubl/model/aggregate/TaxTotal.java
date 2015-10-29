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
* Clase TaxTotal.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class TaxTotal {

    @XmlElement(namespace = Namespaces.CBC)
    private Amount TaxAmount;

    @XmlElement(namespace = Namespaces.CAC)
    private TaxSubtotal TaxSubtotal;

    public TaxTotal() {
    }

    public TaxTotal(Amount TaxAmount, TaxSubtotal TaxSubtotal) {
        this.TaxAmount = TaxAmount;
        this.TaxSubtotal = TaxSubtotal;
    }

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
