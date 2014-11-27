/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_ITEM")
public class Item implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Id
    @Column(name = "ITEM_ID")
    private Long id;

    @OneToMany(orphanRemoval = true, mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemAttribute> attributes;

    @OneToMany(orphanRemoval = true, mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemAuxiliar> auxiliars;

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
