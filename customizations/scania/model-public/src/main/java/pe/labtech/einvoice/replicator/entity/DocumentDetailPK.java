/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Carlos Echeverria
 */
public class DocumentDetailPK implements Serializable {

    private String documentType;
    private String documentNumber;
    private String numeroOrdenItem;

    public DocumentDetailPK(String documentType, String documentNumber, String numeroOrdenItem) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.numeroOrdenItem = numeroOrdenItem;
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

    public String getNumeroOrdenItem() {
        return numeroOrdenItem;
    }

    public void setNumeroOrdenItem(String numeroOrdenItem) {
        this.numeroOrdenItem = numeroOrdenItem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.documentType);
        hash = 79 * hash + Objects.hashCode(this.documentNumber);
        hash = 79 * hash + Objects.hashCode(this.numeroOrdenItem);
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
        final DocumentDetailPK other = (DocumentDetailPK) obj;
        if (!Objects.equals(this.documentType, other.documentType)) {
            return false;
        }
        if (!Objects.equals(this.documentNumber, other.documentNumber)) {
            return false;
        }
        if (!Objects.equals(this.numeroOrdenItem, other.numeroOrdenItem)) {
            return false;
        }
        return true;
    }

}
