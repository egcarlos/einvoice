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
    @Size(min = 0, max = 15)
    @Column(name = "COD_COMPANIA", length = 15)
    private String codigoCompania;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 5)
    @Column(name = "COD_LOCALIDAD", length = 5)
    private String codigoLocalidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 5)
    @Column(name = "COD_TIPRECAUDACION", length = 5)
    private String codigoTiposRecaudacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_RECAUDACION")
    private Integer numeroRecaudacion;

    public DocumentHeaderPK() {
    }

    public DocumentHeaderPK(String codigoCompania, String codigoLocalidad, String codigoTiposRecaudacion, Integer numeroRecaudacion) {
        this.codigoCompania = codigoCompania;
        this.codigoLocalidad = codigoLocalidad;
        this.codigoTiposRecaudacion = codigoTiposRecaudacion;
        this.numeroRecaudacion = numeroRecaudacion;
    }

    public String getCodigoCompania() {
        return codigoCompania;
    }

    public void setCodigoCompania(String codigoCompania) {
        this.codigoCompania = codigoCompania;
    }

    public String getCodigoLocalidad() {
        return codigoLocalidad;
    }

    public void setCodigoLocalidad(String codigoLocalidad) {
        this.codigoLocalidad = codigoLocalidad;
    }

    public String getCodigoTiposRecaudacion() {
        return codigoTiposRecaudacion;
    }

    public void setCodigoTiposRecaudacion(String codigoTiposRecaudacion) {
        this.codigoTiposRecaudacion = codigoTiposRecaudacion;
    }

    public Integer getNumeroRecaudacion() {
        return numeroRecaudacion;
    }

    public void setNumeroRecaudacion(Integer numeroRecaudacion) {
        this.numeroRecaudacion = numeroRecaudacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.codigoCompania);
        hash = 47 * hash + Objects.hashCode(this.codigoLocalidad);
        hash = 47 * hash + Objects.hashCode(this.codigoTiposRecaudacion);
        hash = 47 * hash + Objects.hashCode(this.numeroRecaudacion);
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
        if (!Objects.equals(this.codigoCompania, other.codigoCompania)) {
            return false;
        }
        if (!Objects.equals(this.codigoLocalidad, other.codigoLocalidad)) {
            return false;
        }
        if (!Objects.equals(this.codigoTiposRecaudacion, other.codigoTiposRecaudacion)) {
            return false;
        }
        if (!Objects.equals(this.numeroRecaudacion, other.numeroRecaudacion)) {
            return false;
        }
        return true;
    }

}
