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
public class Attachment {

    @XmlElement(namespace = Namespaces.CAC)
    private String ExternalReference;

    @XmlElement(namespace = Namespaces.CBC)
    private String URI;

    public String getExternalReference() {
        return ExternalReference;
    }

    public void setExternalReference(String ExternalReference) {
        this.ExternalReference = ExternalReference;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
