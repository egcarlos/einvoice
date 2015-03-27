/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_DOCUMENT_ATTR")
public class DocumentAttribute implements Serializable, ValueHolder {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Id
    @Column(name = "ATTR_NAME")
    private String name;

    @Column(name = "ATTR_VALUE")
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
            df.setGroupingUsed(false);
            //TODO USAR EL DECIMAL FORMAT SEPARATOR EN LUGAR DEL REPLACE STRING
            //new DecimalFormatSymbols().setDecimalSeparator('.');
            //df.setDecimalFormatSymbols(null);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
