/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import pe.labtech.einvoice.commons.entity.BLResponse;
import pe.labtech.einvoice.commons.entity.BLResponseImpl;
import pe.labtech.einvoice.commons.entity.Header;

/**
 * Clase CancelHeader
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
@XmlRootElement
@Entity
@Table(name = "SPE_CANCELHEADER")
public class CancelHeader extends BLResponseImpl implements Serializable, BLResponse, Header<CancelDetail> {

    @EmbeddedId
    private CancelHeaderPK id;

    //CAMPOS DE DATOS
    @Size(max = 10)
    @NotNull
    @Basic(optional = false)
    @Column(name = "fechaEmisionComprobante", length = 10)
    private String fechaEmisionComprobante;
    @Size(max = 10)
    @NotNull
    @Basic(optional = false)
    @Column(name = "fechaGeneracionResumen", length = 10)
    private String fechaGeneracionResumen;
    @Size(max = 100)
    @NotNull
    @Basic(optional = false)
    @Column(name = "razonSocialEmisor", length = 100)
    private String razonSocialEmisor;
    @Size(max = 100)
    @NotNull
    @Basic(optional = false)
    @Column(name = "correoEmisor", length = 100)
    private String correoEmisor;
    @Size(min = 2, max = 2)
    @NotNull
    @Basic(optional = false)
    @Column(name = "resumenTipo", length = 2)
    private String resumenTipo;
    @Size(min = 1, max = 1)
    @NotNull
    @Basic(optional = false)
    @Column(name = "inHabilitado", length = 1)
    private String inHabilitado;

    public CancelHeader() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final CancelHeader other = (CancelHeader) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public CancelHeaderPK getId() {
        return id;
    }

    public void setId(CancelHeaderPK id) {
        this.id = id;
    }

    public String getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(String fechaEmisionComprobante) {
        this.fechaEmisionComprobante = fechaEmisionComprobante;
    }

    public String getFechaGeneracionResumen() {
        return fechaGeneracionResumen;
    }

    public void setFechaGeneracionResumen(String fechaGeneracionResumen) {
        this.fechaGeneracionResumen = fechaGeneracionResumen;
    }

    public String getRazonSocialEmisor() {
        return razonSocialEmisor;
    }

    public void setRazonSocialEmisor(String razonSocialEmisor) {
        this.razonSocialEmisor = razonSocialEmisor;
    }

    public String getCorreoEmisor() {
        return correoEmisor;
    }

    public void setCorreoEmisor(String correoEmisor) {
        this.correoEmisor = correoEmisor;
    }

    public String getResumenTipo() {
        return resumenTipo;
    }

    public void setResumenTipo(String resumenTipo) {
        this.resumenTipo = resumenTipo;
    }

    public String getInHabilitado() {
        return inHabilitado;
    }

    public void setInHabilitado(String inHabilitado) {
        this.inHabilitado = inHabilitado;
    }

    //CAMPO EXCLUSIVO DEL XML
    @Transient
    private List<CancelDetail> item;

    @Override
    public List<CancelDetail> getItem() {
        if (item == null) {
            item = new LinkedList<>();
        }
        return item;
    }

    @Override
    public void setItem(List<CancelDetail> item) {
        this.item = item;
    }
}
