/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.sunat;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalInformation {

    @XmlElement(namespace = Namespaces.SAC)
    private List<AdditionalMonetaryTotal> AdditionalMonetaryTotal;

    @XmlElement(namespace = Namespaces.SAC)
    private List<AdditionalProperty> AdditionalProperty;

    public List<AdditionalMonetaryTotal> getAdditionalMonetaryTotal() {
        if (AdditionalMonetaryTotal == null) {
            AdditionalMonetaryTotal = new LinkedList<>();
        }
        return AdditionalMonetaryTotal;
    }

    public void setAdditionalMonetaryTotal(List<AdditionalMonetaryTotal> AdditionalMonetaryTotal) {
        this.AdditionalMonetaryTotal = AdditionalMonetaryTotal;
    }

    public List<AdditionalProperty> getAdditionalProperty() {
        if (AdditionalProperty == null) {
            AdditionalProperty = new LinkedList<>();
        }
        return AdditionalProperty;
    }

    public void setAdditionalProperty(List<AdditionalProperty> AdditionalProperty) {
        this.AdditionalProperty = AdditionalProperty;
    }

}
