/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.bizlinks.replicator.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos
 */
@Embeddable
public class DocumentHeaderPK implements Serializable {

    @Size(min = 1, max = 1)
    @NotNull
    @Basic(optional = false)
    @Column(name = "tipoDocumentoEmisor", length = 1, nullable = false, updatable = false)
    private String tipoDocumentoEmisor;
    @Size(min = 1, max = 20)
    @NotNull
    @Basic(optional = false)
    @Column(name = "numeroDocumentoEmisor", length = 20, nullable = false, updatable = false)
    private String numeroDocumentoEmisor;
    @Size(min = 2, max = 12)
    @NotNull
    @Column(name = "tipoDocumento", length = 2, nullable = false, updatable = false)
    private String tipoDocumento;
    @Size(min = 13, max = 13)
    @NotNull
    @Basic(optional = false)
    @Column(name = "serieNumero", length = 13, nullable = false, updatable = false)
    private String serieNumero;

    public DocumentHeaderPK() {
    }

    public DocumentHeaderPK(String tipoDocumentoEmisor, String numeroDocumentoEmisor, String tipoDocumento, String serieNumero) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
        this.tipoDocumento = tipoDocumento;
        this.serieNumero = serieNumero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.tipoDocumentoEmisor);
        hash = 71 * hash + Objects.hashCode(this.numeroDocumentoEmisor);
        hash = 71 * hash + Objects.hashCode(this.tipoDocumento);
        hash = 71 * hash + Objects.hashCode(this.serieNumero);
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
        final DocumentHeaderPK other = (DocumentHeaderPK) obj;
        if (!Objects.equals(this.tipoDocumentoEmisor, other.tipoDocumentoEmisor)) {
            return false;
        }
        if (!Objects.equals(this.numeroDocumentoEmisor, other.numeroDocumentoEmisor)) {
            return false;
        }
        if (!Objects.equals(this.tipoDocumento, other.tipoDocumento)) {
            return false;
        }
        if (!Objects.equals(this.serieNumero, other.serieNumero)) {
            return false;
        }
        return true;
    }

    public String getTipoDocumentoEmisor() {
        return tipoDocumentoEmisor;
    }

    public void setTipoDocumentoEmisor(String tipoDocumentoEmisor) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
    }

    public String getNumeroDocumentoEmisor() {
        return numeroDocumentoEmisor;
    }

    public void setNumeroDocumentoEmisor(String numeroDocumentoEmisor) {
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

}
