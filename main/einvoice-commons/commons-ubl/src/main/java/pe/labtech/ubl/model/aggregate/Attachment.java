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
* Clase Attachment.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class Attachment {

    @XmlElement(namespace = Namespaces.CAC)
    private ExternalReference ExternalReference;

    public Attachment() {
    }

    public Attachment(ExternalReference ExternalReference) {
        this.ExternalReference = ExternalReference;
    }

    public ExternalReference getExternalReference() {
        return ExternalReference;
    }

    public void setExternalReference(ExternalReference ExternalReference) {
        this.ExternalReference = ExternalReference;
    }

}
