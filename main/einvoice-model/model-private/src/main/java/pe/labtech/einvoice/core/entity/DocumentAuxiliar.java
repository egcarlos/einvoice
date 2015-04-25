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
@Table(name = "BL_DOCUMENT_AUX")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "docAuxiliar",
        propOrder = {
            "code",
            "length",
            "order"
        }
)
public class DocumentAuxiliar implements Serializable, Coded, ValueHolder {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    @XmlTransient
    private Document document;

    @Id
    @Column(name = "AUX_NAME", length = 50)
    @XmlAttribute(name = "code")
    private String code;

    @Column(name = "AUX_LENGTH", length = 50)
    @XmlAttribute(name = "len")
    private String length;

    @Column(name = "AUX_ORDER", length = 50)
    @XmlAttribute(name = "ord")
    private Long order;

    @Column(name = "AUX_VALUE", length = 1000)
    @XmlValue
    private String value;

    public DocumentAuxiliar() {
    }

    public DocumentAuxiliar(String code, String length, String value) {
        this.code = code;
        this.length = length;
        this.value = value;
    }

    public DocumentAuxiliar(String code, String length, Long order, String value) {
        this.code = code;
        this.length = length;
        this.order = order;
        this.value = value;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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
