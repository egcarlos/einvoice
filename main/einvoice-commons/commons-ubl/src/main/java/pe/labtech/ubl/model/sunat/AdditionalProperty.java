/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.sunat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalProperty {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;
    @XmlElement(namespace = Namespaces.CBC)
    private String Name;
    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String Value;

    public AdditionalProperty() {
    }

    public AdditionalProperty(String ID, String Name, String Value) {
        this.ID = ID;
        this.Name = Name;
        this.Value = Value;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

}
