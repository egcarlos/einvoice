/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.hv.replicator.entity.old;

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
public class DetailPK implements Serializable {

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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "NUM_LINEA")
    private String did;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DCODPRODUCTO")
    private String dcodproducto;

    public DetailPK() {
    }

    public DetailPK(String codigoCompania, String codigoLocalidad, String codigoTiposRecaudacion, Integer numeroRecaudacion, String did, String dcodproducto) {
        this.codigoCompania = codigoCompania;
        this.codigoLocalidad = codigoLocalidad;
        this.codigoTiposRecaudacion = codigoTiposRecaudacion;
        this.numeroRecaudacion = numeroRecaudacion;
        this.did = did;
        this.dcodproducto = dcodproducto;
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

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDcodproducto() {
        return dcodproducto;
    }

    public void setDcodproducto(String dcodproducto) {
        this.dcodproducto = dcodproducto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigoCompania);
        hash = 53 * hash + Objects.hashCode(this.codigoLocalidad);
        hash = 53 * hash + Objects.hashCode(this.codigoTiposRecaudacion);
        hash = 53 * hash + Objects.hashCode(this.numeroRecaudacion);
        hash = 53 * hash + Objects.hashCode(this.did);
        hash = 53 * hash + Objects.hashCode(this.dcodproducto);
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
        final DetailPK other = (DetailPK) obj;
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
        if (!Objects.equals(this.did, other.did)) {
            return false;
        }
        if (!Objects.equals(this.dcodproducto, other.dcodproducto)) {
            return false;
        }
        return true;
    }

}
