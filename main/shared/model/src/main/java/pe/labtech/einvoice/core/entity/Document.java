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
    @NamedQuery(name = "Document.loadForSignature", query = "SELECT o FROM Document o WHERE (o.documentNumber like 'F%' or o.documentNumber like 'B%') and o.step IS NULL")
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

    @Column(name = "DOCUMENT_TYPE", length = 10)
    private String documentType;

    @Column(name = "DOCUMENT_NUMBER", length = 20)
    private String documentNumber;

    @Column(name = "DOCUMENT_DATE", length = 40)
    private String documentDate;

    @Column(name = "CLIENT_ID", length = 20)
    private String clientId;

    @Column(name = "PDF_URL", length = 1000)
    private String pdfURL;

    @Column(name = "XML_URL", length = 1000)
    private String xmlURL;

    @Column(name = "CDR_URL", length = 1000)
    private String cdrURL;

    @Column(name = "SIGNATURE", length = 2000)
    private String signature;

    //TODO rename column
    @Column(name = "HASH", length = 1000)
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

    @Column(name = "DOCUMENT_STEP")
    private String step;

    @Column(name = "DOCUMENT_STATUS")
    private String status;

    @OneToMany(orphanRemoval = true, mappedBy = "document")
    @XmlTransient
    private List<EventTrace> trace;

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

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public String getXmlURL() {
        return xmlURL;
    }

    public void setXmlURL(String xmlURL) {
        this.xmlURL = xmlURL;
    }

    public String getCdrURL() {
        return cdrURL;
    }

    public void setCdrURL(String cdrURL) {
        this.cdrURL = cdrURL;
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

}
