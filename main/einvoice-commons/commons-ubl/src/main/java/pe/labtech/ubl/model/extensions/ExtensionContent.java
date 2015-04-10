/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.extensions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.sunat.AdditionalInformation;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtensionContent {

    @XmlElement(name = "AdditionalInformation", namespace = Namespaces.SAC)
    private AdditionalInformation SunatAdditionalInformation;

    @XmlElement(name = "AdditionalInformation", namespace = Namespaces.CBC)
    private AdditionalInformation BasicAdditionalInformation;

    public ExtensionContent() {
    }

    public ExtensionContent(AdditionalInformation SunatAdditionalInformation, AdditionalInformation BasicAdditionalInformation) {
        this.SunatAdditionalInformation = SunatAdditionalInformation;
        this.BasicAdditionalInformation = BasicAdditionalInformation;
    }

    public AdditionalInformation getSunatAdditionalInformation() {
        return SunatAdditionalInformation;
    }

    public void setSunatAdditionalInformation(AdditionalInformation SunatAdditionalInformation) {
        this.SunatAdditionalInformation = SunatAdditionalInformation;
    }

    public AdditionalInformation getBasicAdditionalInformation() {
        return BasicAdditionalInformation;
    }

    public void setBasicAdditionalInformation(AdditionalInformation BasicAdditionalInformation) {
        this.BasicAdditionalInformation = BasicAdditionalInformation;
    }

}
