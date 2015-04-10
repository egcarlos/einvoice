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

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxCategory {

    @XmlElement(namespace = Namespaces.CBC)
    private String TaxExemptionReasonCode;

    @XmlElement(namespace = Namespaces.CBC)
    private String TierRange;

    @XmlElement(namespace = Namespaces.CAC)
    private TaxScheme TaxScheme;

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
