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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
* Clase DocumentResponse.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@Entity
@Table(name = "BL_DOCUMENT_RESP")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "docResponse",
        propOrder = {
            "name"
        }
)
@NamedQueries({
    @NamedQuery(
            name = "DocumentResponse.findById",
            query = "SELECT o FROM DocumentResponse o WHERE o.document = :document AND o.name = :name"
    ),
    @NamedQuery(
            name = "DocumentResponse.findPending",
            query = "SELECT o FROM DocumentResponse o WHERE o.replicate = TRUE"
    ),
    @NamedQuery(
            name = "DocumentResponse.tryLock",
            query = "UPDATE DocumentResponse o SET o.replicate = FALSE WHERE o.replicate = TRUE AND o.document = :document AND o.name = :name"
    )
})
public class DocumentResponse implements Named, ValueHolder, Serializable {

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

    @Column(name = "REPLICATE")
    @XmlTransient
    private Boolean replicate;

    public DocumentResponse() {
    }

    public DocumentResponse(String name, String value) {
        this.name = name;
        if (value != null) {
            this.value = value.trim();
            if ("".equals(this.value)) {
                this.value = null;
            }
        }
    }

    public DocumentResponse(String name, Character value) {
        this.name = name;
        if (value != null) {
            this.value = "" + value;
        }
    }

    public DocumentResponse(String name, BigDecimal value) {
        this.name = name;
        if (value != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            df.setGroupingUsed(false);
            this.value = df.format(value).replace(",", ".");
        }
    }

    public DocumentResponse(Document document, String name, String value) {
        this.document = document;
        this.name = name;
        this.value = value;
    }

    public DocumentResponse(Document document, String name, String value, boolean replicate) {
        this.document = document;
        this.name = name;
        this.value = value;
        this.replicate = replicate;
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

    public Boolean isReplicate() {
        return replicate;
    }

    public void setReplicate(Boolean replicate) {
        this.replicate = replicate;
    }

}
