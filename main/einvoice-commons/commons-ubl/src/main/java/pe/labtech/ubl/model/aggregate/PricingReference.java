/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PricingReference {

    @XmlElement(namespace = Namespaces.CAC)
    private List<AlternativeConditionPrice> AlternativeConditionPrice;

    public PricingReference() {
    }

    public List<AlternativeConditionPrice> getAlternativeConditionPrice() {
        if (this.AlternativeConditionPrice == null) {
            this.AlternativeConditionPrice = new LinkedList<>();
        }
        return AlternativeConditionPrice;
    }

    public void setAlternativeConditionPrice(List<AlternativeConditionPrice> AlternativeConditionPrice) {
        this.AlternativeConditionPrice = AlternativeConditionPrice;
    }

}
