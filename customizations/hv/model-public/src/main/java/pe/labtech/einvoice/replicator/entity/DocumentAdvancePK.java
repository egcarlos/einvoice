/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos Echeverria
 */
@Embeddable
public class DocumentAdvancePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "COD_COMPANIA", nullable = false, length = 15)
    private String hv_compania;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "COD_LOCALIDAD", nullable = false, length = 5)
    private String hv_localidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "COD_TIPRECAUDACION", nullable = false, length = 5)
    private String hv_tipoRecaudacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_RECAUDACION", nullable = false)
    private int hv_recaudacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTIPDOCUMENTOEMISOR")
    private Character tipoDocumentoEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(max = 11)
    @Column(name = "CDOCUMENTOEMISOR", length = 11)
    private String numeroDocumentoEmisor;
    @Basic(optional = false)
    @NotNull
    @Size(max = 2)
    @Column(name = "CTIPCOMPROBANTE", length = 2)
    private String tipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(max = 13)
    @Column(name = "CCOMPROBANTE", length = 13)
    private String serieNumero;

    public DocumentAdvancePK() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.hv_compania);
        hash = 31 * hash + Objects.hashCode(this.hv_localidad);
        hash = 31 * hash + Objects.hashCode(this.hv_tipoRecaudacion);
        hash = 31 * hash + this.hv_recaudacion;
        hash = 31 * hash + Objects.hashCode(this.tipoDocumentoEmisor);
        hash = 31 * hash + Objects.hashCode(this.numeroDocumentoEmisor);
        hash = 31 * hash + Objects.hashCode(this.tipoDocumento);
        hash = 31 * hash + Objects.hashCode(this.serieNumero);
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
        final DocumentAdvancePK other = (DocumentAdvancePK) obj;
        if (!Objects.equals(this.hv_compania, other.hv_compania)) {
            return false;
        }
        if (!Objects.equals(this.hv_localidad, other.hv_localidad)) {
            return false;
        }
        if (!Objects.equals(this.hv_tipoRecaudacion, other.hv_tipoRecaudacion)) {
            return false;
        }
        if (this.hv_recaudacion != other.hv_recaudacion) {
            return false;
        }
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

    public String getHv_compania() {
        return hv_compania;
    }

    public void setHv_compania(String hv_compania) {
        this.hv_compania = hv_compania;
    }

    public String getHv_localidad() {
        return hv_localidad;
    }

    public void setHv_localidad(String hv_localidad) {
        this.hv_localidad = hv_localidad;
    }

    public String getHv_tipoRecaudacion() {
        return hv_tipoRecaudacion;
    }

    public void setHv_tipoRecaudacion(String hv_tipoRecaudacion) {
        this.hv_tipoRecaudacion = hv_tipoRecaudacion;
    }

    public int getHv_recaudacion() {
        return hv_recaudacion;
    }

    public void setHv_recaudacion(int hv_recaudacion) {
        this.hv_recaudacion = hv_recaudacion;
    }

    public Character getTipoDocumentoEmisor() {
        return tipoDocumentoEmisor;
    }

    public void setTipoDocumentoEmisor(Character tipoDocumentoEmisor) {
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
