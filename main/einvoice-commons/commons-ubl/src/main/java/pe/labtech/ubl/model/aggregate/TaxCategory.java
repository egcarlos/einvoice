/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase TaxCategory.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class TaxCategory {

    @XmlElement(namespace = Namespaces.CBC)
    private String TaxExemptionReasonCode;

    @XmlElement(namespace = Namespaces.CBC)
    private String TierRange;

    @XmlElement(namespace = Namespaces.CAC)
    private TaxScheme TaxScheme;

    public TaxCategory() {
    }

    public TaxCategory(String TaxExemptionReasonCode, String TierRange, TaxScheme TaxScheme) {
        this.TaxExemptionReasonCode = TaxExemptionReasonCode;
        this.TierRange = TierRange;
        this.TaxScheme = TaxScheme;
    }

    public String getTaxExemptionReasonCode() {
        return TaxExemptionReasonCode;
    }

    public void setTaxExemptionReasonCode(String TaxExemptionReasonCode) {
        this.TaxExemptionReasonCode = TaxExemptionReasonCode;
    }

    public String getTierRange() {
        return TierRange;
    }

    public void setTierRange(String TierRange) {
        this.TierRange = TierRange;
    }

    public TaxScheme getTaxScheme() {
        return TaxScheme;
    }

    public void setTaxScheme(TaxScheme TaxScheme) {
        this.TaxScheme = TaxScheme;
    }

}
