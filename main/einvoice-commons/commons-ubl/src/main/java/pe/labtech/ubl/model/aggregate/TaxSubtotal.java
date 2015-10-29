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
* Clase TaxSubtotal.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class TaxSubtotal {

    @XmlElement(namespace = Namespaces.CBC)
    private Amount TaxAmount;

    @XmlElement(namespace = Namespaces.CAC)
    private TaxCategory TaxCategory;

    public TaxSubtotal() {
    }

    public TaxSubtotal(Amount TaxAmount, TaxCategory TaxCategory) {
        this.TaxAmount = TaxAmount;
        this.TaxCategory = TaxCategory;
    }

    public Amount getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(Amount TaxAmount) {
        this.TaxAmount = TaxAmount;
    }

    public TaxCategory getTaxCategory() {
        return TaxCategory;
    }

    public void setTaxCategory(TaxCategory TaxCategory) {
        this.TaxCategory = TaxCategory;
    }

}
