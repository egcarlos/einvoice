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
public class DocumentHeaderPK implements Serializable {

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

    public DocumentHeaderPK() {
    }

    public DocumentHeaderPK(String hv_compania, String hv_localidad, String hv_tipoRecaudacion, int hv_recaudacion) {
        this.hv_compania = hv_compania;
        this.hv_localidad = hv_localidad;
        this.hv_tipoRecaudacion = hv_tipoRecaudacion;
        this.hv_recaudacion = hv_recaudacion;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.hv_compania);
        hash = 83 * hash + Objects.hashCode(this.hv_localidad);
        hash = 83 * hash + Objects.hashCode(this.hv_tipoRecaudacion);
        hash = 83 * hash + this.hv_recaudacion;
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
        return true;
    }

    @Override
    public String toString() {
        return "DocumentHeaderPK{" + "hv_compania=" + hv_compania + ", hv_localidad=" + hv_localidad + ", hv_tipoRecaudacion=" + hv_tipoRecaudacion + ", hv_recaudacion=" + hv_recaudacion + '}';
    }

}
