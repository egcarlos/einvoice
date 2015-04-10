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

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UBLExtension {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;
    @XmlElement(namespace = Namespaces.EXT)
    private ExtensionContent ExtensionContent;

    public UBLExtension() {
    }

    public UBLExtension(ExtensionContent ExtensionContent) {
        this.ExtensionContent = ExtensionContent;
    }

    public UBLExtension(String ID) {
        this.ID = ID;
    }

    public UBLExtension(String ID, ExtensionContent ExtensionContent) {
        this.ID = ID;
        this.ExtensionContent = ExtensionContent;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ExtensionContent getExtensionContent() {
        return ExtensionContent;
    }

    public void setExtensionContent(ExtensionContent ExtensionContent) {
        this.ExtensionContent = ExtensionContent;
    }

}
