/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase DocumentReference.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentReference {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    @XmlElement(namespace = Namespaces.CBC)
    private String DocumentTypeCode;

    public DocumentReference() {
    }

    public DocumentReference(String ID, String DocumentTypeCode) {
        this.ID = ID;
        this.DocumentTypeCode = DocumentTypeCode;
    }

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
