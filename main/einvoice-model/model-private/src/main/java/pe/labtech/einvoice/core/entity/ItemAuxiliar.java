/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
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
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_ITEM_AUX")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "itmAuxiliar",
        propOrder = {
            "code",
            "length",
            "order"
        }
)
public class ItemAuxiliar implements Serializable, Coded, ValueHolder {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID"),
        @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    })
    @XmlTransient
    private Item item;

    @Id
    @Column(name = "AUX_NAME")
    @XmlAttribute(name = "code")
    private String code;

    @Column(name = "AUX_LENGTH")
    @XmlAttribute(name = "len")
    private String length;

    @Column(name = "AUX_ORDER")
    @XmlAttribute(name = "ord")
    private Long order;

    @Column(name = "AUX_VALUE")
    @XmlValue
    private String value;

    public ItemAuxiliar() {
    }

    public ItemAuxiliar(String code, String length, Long order, String value) {
        this.code = code;
        this.length = length;
        this.order = order;
        if (value != null && !"".equals(value.trim())) {
            this.value = value.trim();
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
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
