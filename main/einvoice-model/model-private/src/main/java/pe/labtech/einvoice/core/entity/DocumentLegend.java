/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_DOCUMENT_LEG")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "docLegend",
        propOrder = {
            "code",
            "order",
            "additional"
        }
)
public class DocumentLegend implements Serializable, ValueHolder {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    @XmlTransient
    private Document document;

    @Id
    @Column(name = "AUX_NAME", length = 50)
    @XmlAttribute(name = "code")
    private String code;

    @Column(name = "AUX_ORDER", length = 50)
    @XmlAttribute(name = "ord")
    private Long order;

    @Column(name = "AUX_VALUE", length = 1000)
    @XmlValue
    private String value;

    @Column(name = "AUX_VALUE_ADD", length = 1000)
    @XmlAttribute(name = "add")
    private String additional;

    public DocumentLegend() {
    }

    public DocumentLegend(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public DocumentLegend(String code, Long order, String value) {
        this.code = code;
        this.order = order;
        this.value = value;
    }

    public DocumentLegend(String code, Long order, String value, String additional) {
        this.code = code;
        this.order = order;
        this.value = value;
        this.additional = additional;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.document);
        hash = 37 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentLegend other = (DocumentLegend) obj;
        if (!Objects.equals(this.document, other.document)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

}
