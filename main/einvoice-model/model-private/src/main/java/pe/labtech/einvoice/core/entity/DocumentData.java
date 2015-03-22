/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_DOCUMENT_DATA")
@XmlType(
        name = "docAttribute",
        propOrder = {
            "name"
        }
)
@NamedQueries({
    @NamedQuery(
            name = "DocumentData.findById",
            query = "SELECT o FROM DocumentData o WHERE o.document = :document AND o.name = :name"
    ),
    @NamedQuery(
            name = "DocumentData.findPending",
            query = "SELECT o FROM DocumentData o WHERE o.replicate = TRUE AND o.data <> NULL"
    ),
    @NamedQuery(
            name = "DocumentData.tryLock",
            query = "UPDATE DocumentData o SET o.replicate = FALSE WHERE o.replicate = TRUE AND o.document = :document AND o.name = :name"
    )
})
public class DocumentData implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Id
    @Column(name = "DATA_NAME", length = 50)
    private String name;

    @Column(name = "SOURCE", length = 1000)
    private String source;

    @Lob
    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "RAW_DATA")
    private byte[] data;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Column(name = "REPLICATE")
    @XmlTransient
    private Boolean replicate;

    public DocumentData() {
    }

    public DocumentData(Document document, String name, String source, byte[] data, String status) {
        this.document = document;
        this.name = name;
        this.source = source;
        this.data = data;
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.document);
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final DocumentData other = (DocumentData) obj;
        if (!Objects.equals(this.document, other.document)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getReplicate() {
        return replicate;
    }

    public void setReplicate(Boolean replicate) {
        this.replicate = replicate;
    }

}
