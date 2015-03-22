/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sunat.names.specification.ubl.peru.schema.xsd;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class AdditionalInformation {

    @XmlElement(name = "AdditionalMonetaryTotal", namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1")
    private List<AdditionalAmount> additionalMonetaryTotals;
    @XmlElement(name = "AdditionalProperty", namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1")
    private List<AdditionalProperty> additionalProperties;

    public AdditionalInformation() {
    }

    public List<AdditionalAmount> getAdditionalMonetaryTotals() {
        if (additionalMonetaryTotals == null) {
            additionalMonetaryTotals = new LinkedList<>();
        }
        return additionalMonetaryTotals;
    }

    public void setAdditionalMonetaryTotals(List<AdditionalAmount> additionalMonetaryTotals) {
        this.additionalMonetaryTotals = additionalMonetaryTotals;
    }

    public List<AdditionalProperty> getAdditionalProperties() {
        if (additionalProperties == null) {
            additionalProperties = new LinkedList<>();
        }
        return additionalProperties;
    }

    public void setAdditionalProperties(List<AdditionalProperty> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}
