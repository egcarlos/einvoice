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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Clase ItemAttribute.
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
@Entity
@Table(name = "BL_ITEM_ATTR")
@XmlType(
        name = "itmAttribute",
        propOrder = {
            "name"
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemAttribute implements Serializable, Named, ValueHolder {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID"),
        @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    })
    @XmlTransient
    private Item item;

    @Id
    @Column(name = "ATTR_NAME", length = 50)
    @XmlAttribute(name = "name")
    private String name;

    @Column(name = "ATTR_VALUE", length = 1000)
    @XmlValue
    private String value;

    public ItemAttribute() {
    }

    public ItemAttribute(String name, String value) {
        this.name = name;
        if (value != null) {
            this.value = value.trim();
            if ("".equals(this.value)) {
                this.value = null;
            }
        }
    }

    public ItemAttribute(String name, Character value) {
        this.name = name;
        if (value != null) {
            this.value = "" + value;
        }
    }

    public ItemAttribute(String name, BigDecimal value) {
        this.name = name;
        if (value != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setGroupingUsed(false);
            this.value = df.format(value).replace(",", ".");
        }
    }

    public ItemAttribute(Item item, String name, String value) {
        this.item = item;
        this.name = name;
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
