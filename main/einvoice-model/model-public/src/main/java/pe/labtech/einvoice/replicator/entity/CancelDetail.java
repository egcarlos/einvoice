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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_CANCELDETAIL")
public class CancelDetail implements Serializable {

    @EmbeddedId
    private CancelDetailPK id;

    @Size(max = 2)
    @NotNull
    @Basic(optional = false)
    @Column(name = "tipoDocumento", length = 2)
    private String tipoDocumento;
    @Size(max = 4)
    @NotNull
    @Basic(optional = false)
    @Column(name = "serieDocumentoBaja", length = 4)
    private String serieDocumentoBaja;
    @Size(max = 8)
    @NotNull
    @Basic(optional = false)
    @Column(name = "numeroDocumentoBaja", length = 8)
    private String numeroDocumentoBaja;

    @Size(max = 200)
    @NotNull
    @Basic
    @Column(name = "motivoBaja", length = 200)
    private String motivoBaja;

    public CancelDetail() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final CancelDetail other = (CancelDetail) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public CancelDetailPK getId() {
        return id;
    }

    public void setId(CancelDetailPK id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerieDocumentoBaja() {
        return serieDocumentoBaja;
    }

    public void setSerieDocumentoBaja(String serieDocumentoBaja) {
        this.serieDocumentoBaja = serieDocumentoBaja;
    }

    public String getNumeroDocumentoBaja() {
        return numeroDocumentoBaja;
    }

    public void setNumeroDocumentoBaja(String numeroDocumentoBaja) {
        this.numeroDocumentoBaja = numeroDocumentoBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

}
