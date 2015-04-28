/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.sunat.AdditionalProperty;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private List<String> Description;

    @XmlElement(namespace = Namespaces.CAC)
    private Identification SellersItemIdentification;

    @XmlElement(namespace = Namespaces.CAC)
    private List<AdditionalProperty> AdditionalItemProperty;

    public Item() {
    }

    public Item(String ID) {
        this.SellersItemIdentification = new Identification(ID);
    }

    public List<String> getDescription() {
        if (Description == null) {
            this.Description = new LinkedList<>();
        }
        return Description;
    }

    public void setDescription(List<String> Description) {
        this.Description = Description;
    }

    public Identification getSellersItemIdentification() {
        return SellersItemIdentification;
    }

    public void setSellersItemIdentification(Identification SellersItemIdentification) {
        this.SellersItemIdentification = SellersItemIdentification;
    }

    public List<AdditionalProperty> getAdditionalItemProperty() {
        if (AdditionalItemProperty == null) {
            AdditionalItemProperty = new LinkedList<>();
        }
        return AdditionalItemProperty;
    }

    public void setAdditionalItemProperty(List<AdditionalProperty> AdditionalItemProperty) {
        this.AdditionalItemProperty = AdditionalItemProperty;
    }

}
