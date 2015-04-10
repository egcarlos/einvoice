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
public class Signature {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    @XmlElement(namespace = Namespaces.CAC)
    private Party SignatoryParty;

    @XmlElement(namespace = Namespaces.CAC)
    private Attachment DigitalSignatureAttachment;

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
