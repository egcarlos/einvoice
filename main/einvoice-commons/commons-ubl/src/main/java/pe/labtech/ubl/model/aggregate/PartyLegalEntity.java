/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

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
public class PartyLegalEntity {

    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String RegistrationName;

    public PartyLegalEntity() {
    }

    public PartyLegalEntity(String RegistrationName) {
        this.RegistrationName = RegistrationName;
    }

    public String getRegistrationName() {
        return RegistrationName;
    }

    public void setRegistrationName(String RegistrationName) {
        this.RegistrationName = RegistrationName;
    }

}
