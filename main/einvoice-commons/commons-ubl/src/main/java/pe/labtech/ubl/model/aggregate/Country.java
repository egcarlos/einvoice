/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author carloseg
 */
public class Country {

    @XmlElement(namespace = Namespaces.CBC)
    private String IdentificationCode;

    public String getIdentificationCode() {
        return IdentificationCode;
    }

    public void setIdentificationCode(String IdentificationCode) {
        this.IdentificationCode = IdentificationCode;
    }

}
