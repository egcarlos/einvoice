/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Echeverria
 */
@Entity
@Table(name = "BL_PREPAID")
public class Prepaid implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    @XmlTransient
    private Document document;

    @Id
    @Column(name = "ISSUER_TYPE", length = 1)
    private String issuerType;

    @Id
    @Column(name = "ISSUER_ID", length = 20)
    private String issuerId;

    @Id
    @Column(name = "ADVANCE_TYPE", length = 2)
    private String type;

    @Id
    @Column(name = "ADVANCE_ID", length = 20)
    private String id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.document);
        hash = 89 * hash + Objects.hashCode(this.issuerType);
        hash = 89 * hash + Objects.hashCode(this.issuerId);
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Prepaid other = (Prepaid) obj;
        if (!Objects.equals(this.document, other.document)) {
            return false;
        }
        if (!Objects.equals(this.issuerType, other.issuerType)) {
            return false;
        }
        if (!Objects.equals(this.issuerId, other.issuerId)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getIssuerType() {
        return issuerType;
    }

    public void setIssuerType(String issuerType) {
        this.issuerType = issuerType;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
