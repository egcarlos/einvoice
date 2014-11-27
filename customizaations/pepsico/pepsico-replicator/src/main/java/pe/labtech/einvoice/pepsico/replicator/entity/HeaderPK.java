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
public class HeaderPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CEMPRESA")
    private String cempresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORDEN")
    private long corden;

    public HeaderPK() {
    }

    public HeaderPK(String cempresa, long corden) {
        this.cempresa = cempresa;
        this.corden = corden;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cempresa != null ? cempresa.hashCode() : 0);
        hash += (int) corden;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeaderPK)) {
            return false;
        }
        HeaderPK other = (HeaderPK) object;
        if ((this.cempresa == null && other.cempresa != null) || (this.cempresa != null && !this.cempresa.equals(other.cempresa))) {
            return false;
        }
        if (this.corden != other.corden) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.labtech.einvoice.core.replicator.entity.HeaderPK[ cempresa=" + cempresa + ", corden=" + corden + " ]";
    }
    
}
