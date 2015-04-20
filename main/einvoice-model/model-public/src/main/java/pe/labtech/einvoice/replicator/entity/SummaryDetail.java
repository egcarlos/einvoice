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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import pe.labtech.einvoice.replicator.commons.Detail;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_SUMMARYDETAIL")
@NamedQueries({
    @NamedQuery(
            name = "SummaryDetail.findForHeader",
            query = "SELECT o  FROM SummaryDetail o"
    )
})
public class SummaryDetail implements Serializable, Detail {

    @EmbeddedId
    private SummaryDetailPK id;

    //CAMPOS DE DATOS
    @Size(min = 2, max = 2)
    @NotNull
    @Basic(optional = false)
    @Column(name = "tipoDocumento", length = 2)
    private String tipoDocumento;
    @Size(min = 3, max = 3)
    @NotNull
    @Basic(optional = false)
    @Column(name = "tipoMoneda", length = 3)
    private String tipoMoneda;
    @Size(min = 4, max = 4)
    @NotNull
    @Basic(optional = false)
    @Column(name = "serieGrupoDocumento", length = 4)
    private String serieGrupoDocumento;
    @Size(min = 8, max = 8)
    @NotNull
    @Basic(optional = false)
    @Column(name = "numeroCorrelativoInicio", length = 8)
    private String numeroCorrelativoInicio;
    @Size(min = 8, max = 8)
    @NotNull
    @Basic(optional = false)
    @Column(name = "numeroCorrelativoFin", length = 8)
    private String numeroCorrelativoFin;
    @Size(max = 15)
    @Column(name = "totalValorVentaOpGravadaConIgv", length = 15)
    private String totalValorVentaOpGravadasConIgv;
    @Size(max = 15)
    @Column(name = "totalValorVentaOpExoneradasIgv", length = 15)
    private String totalValorVentaOpExoneradasIgv;
    @Size(max = 15)
    @Column(name = "totalValorVentaOpInafectasIgv", length = 15)
    private String totalValorVentaOpInafectasIgv;
    @Size(max = 15)
    @Column(name = "totalValorVentaOpGratuitas", length = 15)
    private String totalValorVentaOpGratuitas;
    @Size(max = 15)
    @Column(name = "totalOtrosCargos", length = 15)
    private String totalOtrosCargos;
    @Size(max = 15)
    @Column(name = "totalVenta", length = 15)
    private String totalVenta;
    @Size(max = 15)
    @Column(name = "totalIsc", length = 15)
    private String totalIsc;
    @Size(max = 15)
    @Column(name = "totalIgv", length = 15)
    private String totalIgv;
    @Size(max = 15)
    @Column(name = "totalOtrosTributos", length = 15)
    private String totalOtrosTributos;

    public SummaryDetail() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final SummaryDetail other = (SummaryDetail) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public SummaryDetailPK getId() {
        return id;
    }

    public void setId(SummaryDetailPK id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getSerieGrupoDocumento() {
        return serieGrupoDocumento;
    }

    public void setSerieGrupoDocumento(String serieGrupoDocumento) {
        this.serieGrupoDocumento = serieGrupoDocumento;
    }

    public String getNumeroCorrelativoInicio() {
        return numeroCorrelativoInicio;
    }

    public void setNumeroCorrelativoInicio(String numeroCorrelativoInicio) {
        this.numeroCorrelativoInicio = numeroCorrelativoInicio;
    }

    public String getNumeroCorrelativoFin() {
        return numeroCorrelativoFin;
    }

    public void setNumeroCorrelativoFin(String numeroCorrelativoFin) {
        this.numeroCorrelativoFin = numeroCorrelativoFin;
    }

    public String getTotalValorVentaOpGravadasConIgv() {
        return totalValorVentaOpGravadasConIgv;
    }

    public void setTotalValorVentaOpGravadasConIgv(String totalValorVentaOpGravadasConIgv) {
        this.totalValorVentaOpGravadasConIgv = totalValorVentaOpGravadasConIgv;
    }

    public String getTotalValorVentaOpExoneradasIgv() {
        return totalValorVentaOpExoneradasIgv;
    }

    public void setTotalValorVentaOpExoneradasIgv(String totalValorVentaOpExoneradasIgv) {
        this.totalValorVentaOpExoneradasIgv = totalValorVentaOpExoneradasIgv;
    }

    public String getTotalValorVentaOpInafectasIgv() {
        return totalValorVentaOpInafectasIgv;
    }

    public void setTotalValorVentaOpInafectasIgv(String totalValorVentaOpInafectasIgv) {
        this.totalValorVentaOpInafectasIgv = totalValorVentaOpInafectasIgv;
    }

    public String getTotalValorVentaOpGratuitas() {
        return totalValorVentaOpGratuitas;
    }

    public void setTotalValorVentaOpGratuitas(String totalValorVentaOpGratuitas) {
        this.totalValorVentaOpGratuitas = totalValorVentaOpGratuitas;
    }

    public String getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(String totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public String getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(String totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getTotalIsc() {
        return totalIsc;
    }

    public void setTotalIsc(String totalIsc) {
        this.totalIsc = totalIsc;
    }

    public String getTotalIgv() {
        return totalIgv;
    }

    public void setTotalIgv(String totalIgv) {
        this.totalIgv = totalIgv;
    }

    public String getTotalOtrosTributos() {
        return totalOtrosTributos;
    }

    public void setTotalOtrosTributos(String totalOtrosTributos) {
        this.totalOtrosTributos = totalOtrosTributos;
    }

}
