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
 * @author Carlos
 */
@Embeddable
public class DocumentDetailPK implements Serializable {

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
    @Size(min = 1, max = 3)
    @Column(name = "NUM_LINEA", nullable = false, length = 3)
    private String numeroOrdenItem;

    public DocumentDetailPK() {
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

    public String getNumeroOrdenItem() {
        return numeroOrdenItem;
    }

    public void setNumeroOrdenItem(String numeroOrdenItem) {
        this.numeroOrdenItem = numeroOrdenItem;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.hv_compania);
        hash = 19 * hash + Objects.hashCode(this.hv_localidad);
        hash = 19 * hash + Objects.hashCode(this.hv_tipoRecaudacion);
        hash = 19 * hash + this.hv_recaudacion;
        hash = 19 * hash + Objects.hashCode(this.numeroOrdenItem);
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
        if (!Objects.equals(this.numeroOrdenItem, other.numeroOrdenItem)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DocumentDetailPK{" + "hv_compania=" + hv_compania + ", hv_localidad=" + hv_localidad + ", hv_tipoRecaudacion=" + hv_tipoRecaudacion + ", hv_recaudacion=" + hv_recaudacion + ", numeroOrdenItem=" + numeroOrdenItem + '}';
    }

}
