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
public class DocumentReference {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    @XmlElement(namespace = Namespaces.CBC)
    private String DocumentTypeCode;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDocumentTypeCode() {
        return DocumentTypeCode;
    }

    public void setDocumentTypeCode(String DocumentTypeCode) {
        this.DocumentTypeCode = DocumentTypeCode;
    }

}
