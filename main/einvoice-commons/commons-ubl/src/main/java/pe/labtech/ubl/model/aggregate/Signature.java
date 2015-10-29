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
* Clase Signature.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class Signature {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    @XmlElement(namespace = Namespaces.CAC)
    private Party SignatoryParty;

    @XmlElement(namespace = Namespaces.CAC)
    private Attachment DigitalSignatureAttachment;

    public Signature() {
    }

    public Signature(String id, String partyIdentification, String partyName, String uri) {
        this.ID = id;
        this.SignatoryParty = new Party(partyIdentification, partyName);
        this.DigitalSignatureAttachment = new Attachment(new ExternalReference(uri));
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Party getSignatoryParty() {
        return SignatoryParty;
    }

    public void setSignatoryParty(Party SignatoryParty) {
        this.SignatoryParty = SignatoryParty;
    }

    public Attachment getDigitalSignatureAttachment() {
        return DigitalSignatureAttachment;
    }

    public void setDigitalSignatureAttachment(Attachment DigitalSignatureAttachment) {
        this.DigitalSignatureAttachment = DigitalSignatureAttachment;
    }

}
