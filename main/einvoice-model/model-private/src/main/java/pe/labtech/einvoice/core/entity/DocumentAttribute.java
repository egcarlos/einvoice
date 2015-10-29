/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.core.entity;

import pe.labtech.einvoice.commons.entity.ValueHolder;
import pe.labtech.einvoice.commons.entity.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
* Clase DocumentAttribute.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@Entity
@Table(name = "BL_DOCUMENT_ATTR")
@XmlType(
        name = "docAttribute",
        propOrder = {
            "name"
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentAttribute implements Serializable, Named, ValueHolder {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    @XmlTransient
    private Document document;

    @Id
    @Column(name = "ATTR_NAME", length = 50)
    @XmlAttribute(name = "name")
    private String name;

    @Column(name = "ATTR_VALUE", length = 1000)
    @XmlValue
    private String value;

    public DocumentAttribute() {
    }

    public DocumentAttribute(String name, String value) {
        this.name = name;
        if (value != null) {
            this.value = value.trim();
            if ("".equals(this.value)) {
                this.value = null;
            }
        }
    }

    public DocumentAttribute(String name, Character value) {
        this.name = name;
        if (value != null) {
            this.value = "" + value;
        }
    }

    public DocumentAttribute(String name, BigDecimal value) {
        this.name = name;
        if (value != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setGroupingUsed(false);
            this.value = df.format(value).replace(",", ".");
        }
    }

    public DocumentAttribute(Document document, String name, String value) {
        this.document = document;
        this.name = name;
        this.value = value;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

}
