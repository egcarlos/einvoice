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

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_ITEM_AUX")
public class ItemAuxiliar implements Serializable {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID"),
        @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    })
    private Item item;

    @Id
    @Column(name = "AUX_NAME")
    private String code;

    @Column(name = "AUX_LENGTH")
    private String length;

    @Column(name = "AUX_ORDER")
    private Long order;

    @Column(name = "AUX_VALUE")
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

    public String getCode() {
        return code;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
