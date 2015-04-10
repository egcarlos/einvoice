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
public class Party {

    /**
     *
     */
    @XmlElement(namespace = Namespaces.CAC)
    private PartyIdentification PartyIdentification;

    @XmlElement(namespace = Namespaces.CAC)
    private PartyName PartyName;

    @XmlElement(namespace = Namespaces.CAC)
    private Address PostalAddress;

    @XmlElement(namespace = Namespaces.CAC)
    private PartyLegalEntity PartyLegalEntity;

    public Party() {
    }

    public Party(String PartyIdentification, String PartyName) {
        if (PartyIdentification != null) {
            this.PartyIdentification = new PartyIdentification(PartyIdentification);
        }
        if (PartyName != null) {
            this.PartyName = new PartyName(PartyName);
        }
    }

    public Party(Address PostalAddress, PartyLegalEntity PartyLegalEntity) {
        this.PostalAddress = PostalAddress;
        this.PartyLegalEntity = PartyLegalEntity;
    }

    public PartyIdentification getPartyIdentification() {
        return PartyIdentification;
    }

    public void setPartyIdentification(PartyIdentification PartyIdentification) {
        this.PartyIdentification = PartyIdentification;
    }

    public PartyName getPartyName() {
        return PartyName;
    }

    public void setPartyName(PartyName PartyName) {
        this.PartyName = PartyName;
    }

    public Address getPostalAddress() {
        return PostalAddress;
    }

    public void setPostalAddress(Address PostalAddress) {
        this.PostalAddress = PostalAddress;
    }

    public PartyLegalEntity getPartyLegalEntity() {
        return PartyLegalEntity;
    }

    public void setPartyLegalEntity(PartyLegalEntity PartyLegalEntity) {
        this.PartyLegalEntity = PartyLegalEntity;
    }

}
