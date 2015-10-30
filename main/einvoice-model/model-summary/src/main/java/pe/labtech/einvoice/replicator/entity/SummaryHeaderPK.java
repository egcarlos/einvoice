/*
 * Producto elaborado para Alignet S.A.C.
 *
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
 * Clase SummaryHeaderPK
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
@Embeddable
public class SummaryHeaderPK implements Serializable {

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
    @Size(min = 1, max = 17)
    @NotNull
    @Basic(optional = false)
    @Column(name = "resumenId", length = 17, nullable = false, updatable = false)
    private String resumenId;

    public SummaryHeaderPK() {
    }

    public SummaryHeaderPK(String tipoDocumentoEmisor, String numeroDocumentoEmisor, String tipoDocumento, String numeroDocumento) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
        this.resumenId = tipoDocumento + "-" + numeroDocumento;
    }

    public SummaryHeaderPK(String tipoDocumentoEmisor, String numeroDocumentoEmisor, String resumenId) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
        this.resumenId = resumenId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.tipoDocumentoEmisor);
        hash = 19 * hash + Objects.hashCode(this.numeroDocumentoEmisor);
        hash = 19 * hash + Objects.hashCode(this.resumenId);
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
        final SummaryHeaderPK other = (SummaryHeaderPK) obj;
        if (!Objects.equals(this.tipoDocumentoEmisor, other.tipoDocumentoEmisor)) {
            return false;
        }
        if (!Objects.equals(this.numeroDocumentoEmisor, other.numeroDocumentoEmisor)) {
            return false;
        }
        if (!Objects.equals(this.resumenId, other.resumenId)) {
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

    public String getResumenId() {
        return resumenId;
    }

    public void setResumenId(String resumenId) {
        this.resumenId = resumenId;
    }

}
