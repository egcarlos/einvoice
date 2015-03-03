/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.pepsico.replicator.entity;

import java.io.Serializable;
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
    @Size(min = 1, max = 30)
    @Column(name = "CEMPRESA")
    private String cempresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORDEN")
    private long corden;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "DID")
    private String did;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DCODPRODUCTO")
    private String dcodproducto;

    public DetailPK() {
    }

    public DetailPK(String cempresa, long corden, String did, String dcodproducto) {
        this.cempresa = cempresa;
        this.corden = corden;
        this.did = did;
        this.dcodproducto = dcodproducto;
    }

    public String getCempresa() {
        return cempresa;
    }

    public void setCempresa(String cempresa) {
        this.cempresa = cempresa;
    }

    public long getCorden() {
        return corden;
    }

    public void setCorden(long corden) {
        this.corden = corden;
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
        int hash = 0;
        hash += (cempresa != null ? cempresa.hashCode() : 0);
        hash += (int) corden;
        hash += (did != null ? did.hashCode() : 0);
        hash += (dcodproducto != null ? dcodproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetailPK)) {
            return false;
        }
        DetailPK other = (DetailPK) object;
        if ((this.cempresa == null && other.cempresa != null) || (this.cempresa != null && !this.cempresa.equals(other.cempresa))) {
            return false;
        }
        if (this.corden != other.corden) {
            return false;
        }
        if ((this.did == null && other.did != null) || (this.did != null && !this.did.equals(other.did))) {
            return false;
        }
        if ((this.dcodproducto == null && other.dcodproducto != null) || (this.dcodproducto != null && !this.dcodproducto.equals(other.dcodproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.labtech.einvoice.core.replicator.entity.DetailPK[ cempresa=" + cempresa + ", corden=" + corden + ", did=" + did + ", dcodproducto=" + dcodproducto + " ]";
    }

}
