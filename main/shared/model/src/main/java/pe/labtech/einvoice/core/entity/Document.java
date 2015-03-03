/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "BL_DOCUMENT")
@SequenceGenerator(name = "DOCUMENT_ID_GENERATOR", sequenceName = "BL_DOCUMENT_SEQ")
@NamedQueries({
    @NamedQuery(name = "Document.loadForSignature", query = "SELECT o FROM Document o WHERE (o.documentNumber like 'F%' or o.documentNumber like 'B%') and o.step IS NULL"),
    @NamedQuery(name = "Document.updateSignature", query = "UPDATE Document o SET o.status = :status, o.signature = :signature, o.hash = :hash WHERE o.id = :id")
})
@XmlRootElement(name = "document")
@XmlType(
        name = "document",
        propOrder = {
            "documentType",
            "documentNumber",
            "documentDate",
            "clientId",
            "attributes",
            "auxiliars",
            "legends",
            "items",
            "pdfURL",
            "xmlURL",
            "cdrURL",
            "signature",
            "hash",
            "step",
            "status"
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
public class Document implements Serializable {

    @Id
    @Column(name = "DOCUMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENT_ID_GENERATOR")
    @XmlTransient
    private Long id;

    @Column(name = "CLIENT_ID", length = 20, updatable = false)
    private String clientId;

    @Column(name = "DOCUMENT_TYPE", length = 10, updatable = false)
    private String documentType;

    @Column(name = "DOCUMENT_NUMBER", length = 20, updatable = false)
    private String documentNumber;

    @Column(name = "DOCUMENT_DATE", length = 40, updatable = false)
    private String documentDate;

    @Column(name = "SIGNATURE", length = 2000, insertable = false)
    private String signature;

    @Column(name = "DOCUMENT_HASH", length = 1000, insertable = false)
    private String hash;

    @XmlElement(name = "att")
    @OneToMany(orphanRemoval = true, mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentAttribute> attributes;

    @XmlElement(name = "aux")
    @OneToMany(orphanRemoval = true, mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentAuxiliar> auxiliars;

    @XmlElement(name = "leg")
    @OneToMany(orphanRemoval = true, mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentLegend> legends;

    @XmlElement(name = "item")
    @OneToMany(orphanRemoval = true, mappedBy = "document", cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToMany(orphanRemoval = true, mappedBy = "document", cascade = CascadeType.ALL)
    @XmlTransient
    private List<DocumentResponse> responses;

    @OneToMany(orphanRemoval = true, mappedBy = "document", cascade = CascadeType.ALL)
    @XmlTransient
    private List<DocumentData> data;

    @OneToMany(orphanRemoval = true, mappedBy = "document")
    @XmlTransient
    private List<EventTrace> trace;

    @Column(name = "DOCUMENT_STEP")
    private String step;

    @Column(name = "DOCUMENT_STATUS")
    private String status;

    public Document() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Document other = (Document) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<DocumentAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DocumentAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<DocumentAuxiliar> getAuxiliars() {
        return auxiliars;
    }

    public void setAuxiliars(List<DocumentAuxiliar> auxiliars) {
        this.auxiliars = auxiliars;
    }

    public List<DocumentResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<DocumentResponse> responses) {
        this.responses = responses;
    }

    public List<DocumentLegend> getLegends() {
        return legends;
    }

    public void setLegends(List<DocumentLegend> legends) {
        this.legends = legends;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EventTrace> getTrace() {
        return trace;
    }

    public void setTrace(List<EventTrace> trace) {
        this.trace = trace;
    }

    public List<DocumentData> getData() {
        return data;
    }

    public void setData(List<DocumentData> data) {
        this.data = data;
    }

}
