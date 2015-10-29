/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.extensions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase UBLExtension.
*
* @author Labtech S.R.L. (info@labtech.pe)
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
