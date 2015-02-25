/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.bizlinks.replicator.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_SUMMARYHEADER")
public class SummaryHeader implements Serializable {

    @EmbeddedId
    private SummaryHeaderPK id;

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

    //CAMPOS DE RESPUESTA
    @Column(name = "bl_firma", length = 4000)
    private String bl_firma;
    @Column(name = "bl_hashFirma", length = 4000)
    private String bl_hashFirma;
    @Column(name = "bl_estadoProceso", length = 40)
    private String bl_estadoProceso;
    @Column(name = "bl_estadoRegistro", length = 40)
    private String bl_estadoRegistro;
    @Column(name = "bl_mensaje", length = 4000)
    private String bl_mensaje;
    @Column(name = "bl_mensajeSunat", length = 4000)
    private String bl_mensajeSunat;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_pdf")
    private byte[] bl_pdf;
    @Column(name = "bl_urlpdf", length = 4000)
    private String bl_urlpdf;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_xmlubl", length = 4000)
    private byte[] bl_xmlubl;
    @Column(name = "bl_urlxmlubl", length = 4000)
    private String bl_urlxmlubl;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_cdr")
    private byte[] bl_cdr;
    @Column(name = "bl_urlcdr", length = 4000)
    private String bl_urlcdr;

    public SummaryHeader() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final SummaryHeader other = (SummaryHeader) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public SummaryHeaderPK getId() {
        return id;
    }

    public void setId(SummaryHeaderPK id) {
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

    public String getBl_firma() {
        return bl_firma;
    }

    public void setBl_firma(String bl_firma) {
        this.bl_firma = bl_firma;
    }

    public String getBl_hashFirma() {
        return bl_hashFirma;
    }

    public void setBl_hashFirma(String bl_hashFirma) {
        this.bl_hashFirma = bl_hashFirma;
    }

    public String getBl_estadoProceso() {
        return bl_estadoProceso;
    }

    public void setBl_estadoProceso(String bl_estadoProceso) {
        this.bl_estadoProceso = bl_estadoProceso;
    }

    public String getBl_estadoRegistro() {
        return bl_estadoRegistro;
    }

    public void setBl_estadoRegistro(String bl_estadoRegistro) {
        this.bl_estadoRegistro = bl_estadoRegistro;
    }

    public String getBl_mensaje() {
        return bl_mensaje;
    }

    public void setBl_mensaje(String bl_mensaje) {
        this.bl_mensaje = bl_mensaje;
    }

    public String getBl_mensajeSunat() {
        return bl_mensajeSunat;
    }

    public void setBl_mensajeSunat(String bl_mensajeSunat) {
        this.bl_mensajeSunat = bl_mensajeSunat;
    }

    public byte[] getBl_pdf() {
        return bl_pdf;
    }

    public void setBl_pdf(byte[] bl_pdf) {
        this.bl_pdf = bl_pdf;
    }

    public String getBl_urlpdf() {
        return bl_urlpdf;
    }

    public void setBl_urlpdf(String bl_urlpdf) {
        this.bl_urlpdf = bl_urlpdf;
    }

    public byte[] getBl_xmlubl() {
        return bl_xmlubl;
    }

    public void setBl_xmlubl(byte[] bl_xmlubl) {
        this.bl_xmlubl = bl_xmlubl;
    }

    public String getBl_urlxmlubl() {
        return bl_urlxmlubl;
    }

    public void setBl_urlxmlubl(String bl_urlxmlubl) {
        this.bl_urlxmlubl = bl_urlxmlubl;
    }

    public byte[] getBl_cdr() {
        return bl_cdr;
    }

    public void setBl_cdr(byte[] bl_cdr) {
        this.bl_cdr = bl_cdr;
    }

    public String getBl_urlcdr() {
        return bl_urlcdr;
    }

    public void setBl_urlcdr(String bl_urlcdr) {
        this.bl_urlcdr = bl_urlcdr;
    }

}
