/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
* Clase Item.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@Entity
@Table(name = "BL_ITEM")
@XmlType(
        name = "item",
        propOrder = {
            "id",
            "attributes",
            "auxiliars"
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    @XmlTransient
    private Document document;

    @Id
    @Column(name = "ITEM_ID")
    @XmlAttribute(name = "ord")
    private Long id;

    @OneToMany(orphanRemoval = true, mappedBy = "item", cascade = CascadeType.ALL)
    @XmlElement(name = "att")
    private List<ItemAttribute> attributes;

    @OneToMany(orphanRemoval = true, mappedBy = "item", cascade = CascadeType.ALL)
    @XmlElement(name = "aux")
    private List<ItemAuxiliar> auxiliars;

    @Override
    public String toString() {
        return "Item{" + "document=" + document + ", id=" + id + '}';
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ItemAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<ItemAuxiliar> getAuxiliars() {
        return auxiliars;
    }

    public void setAuxiliars(List<ItemAuxiliar> auxiliars) {
        this.auxiliars = auxiliars;
    }

}
