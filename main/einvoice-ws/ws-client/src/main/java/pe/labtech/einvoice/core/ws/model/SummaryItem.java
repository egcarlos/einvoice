/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "ResumenItem",
        propOrder = {
            //campos de identificación
            "indicador",
            "numeroFila",
            "tipoDocumento",
            "tipoMoneda",
            //campos para baja de documentos
            "serieDocumentoBaja",
            "numeroDocumentoBaja",
            "motivoBaja",
            //campos para resumen de documentos
            "serieGrupoDocumento",
            "numeroCorrelativoInicio",
            "numeroCorrelativoFin",
            "totalValorVentaOpGravadasConIgv",
            "totalValorVentaOpInafectasIgv",
            "totalValorVentaOpGratuitas",
            "totalOtrosTributos",
            "totalIgv",
            "totalVenta",
            "totalIsc",
            "totalOtrosCargos",
            "totalValorVentaOpExoneradasIgv"
        }
)
public class SummaryItem {

    protected Long numeroFila;
    protected String indicador;
    protected String tipoDocumento;
    protected String serieGrupoDocumento;
    protected String serieDocumentoBaja;
    protected String numeroDocumentoBaja;
    protected String motivoBaja;
    protected String numeroCorrelativoInicio;
    protected String tipoMoneda;
    protected String numeroCorrelativoFin;
    protected BigDecimal totalValorVentaOpGravadasConIgv;
    protected BigDecimal totalValorVentaOpExoneradasIgv;
    protected BigDecimal totalValorVentaOpInafectasIgv;
    protected BigDecimal totalValorVentaOpGratuitas;
    protected BigDecimal totalIsc;
    protected BigDecimal totalIgv;
    public BigDecimal totalOtrosTributos;
    public BigDecimal totalVenta;
    public BigDecimal totalOtrosCargos;

    public SummaryItem() {
    }

    public Long getNumeroFila() {
        return this.numeroFila;
    }

    public void setNumeroFila(Long numeroFila) {
        this.numeroFila = numeroFila;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerieGrupoDocumento() {
        return this.serieGrupoDocumento;
    }

    public void setSerieGrupoDocumento(String serieGrupoDocumento) {
        this.serieGrupoDocumento = serieGrupoDocumento;
    }

    public String getNumeroCorrelativoInicio() {
        return this.numeroCorrelativoInicio;
    }

    public void setNumeroCorrelativoInicio(String numeroCorrelativoInicio) {
        this.numeroCorrelativoInicio = numeroCorrelativoInicio;
    }

    public String getNumeroCorrelativoFin() {
        return this.numeroCorrelativoFin;
    }

    public void setNumeroCorrelativoFin(String numeroCorrelativoFin) {
        this.numeroCorrelativoFin = numeroCorrelativoFin;
    }

    public BigDecimal getTotalValorVentaOpGravadasConIgv() {
        return this.totalValorVentaOpGravadasConIgv;
    }

    public void setTotalValorVentaOpGravadasConIgv(BigDecimal totalValorVentaOpGravadasConIgv) {
        this.totalValorVentaOpGravadasConIgv = totalValorVentaOpGravadasConIgv;
    }

    public String getSerieDocumentoBaja() {
        return this.serieDocumentoBaja;
    }

    public void setSerieDocumentoBaja(String serieDocumentoBaja) {
        this.serieDocumentoBaja = serieDocumentoBaja;
    }

    public String getNumeroDocumentoBaja() {
        return this.numeroDocumentoBaja;
    }

    public void setNumeroDocumentoBaja(String numeroDocumentoBaja) {
        this.numeroDocumentoBaja = numeroDocumentoBaja;
    }

    public String getMotivoBaja() {
        return this.motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public BigDecimal getTotalValorVentaOpExoneradasIgv() {
        return this.totalValorVentaOpExoneradasIgv;
    }

    public void setTotalValorVentaOpExoneradasIgv(BigDecimal totalValorVentaOpExoneradasIgv) {
        this.totalValorVentaOpExoneradasIgv = totalValorVentaOpExoneradasIgv;
    }

    public BigDecimal getTotalValorVentaOpInafectasIgv() {
        return this.totalValorVentaOpInafectasIgv;
    }

    public void setTotalValorVentaOpInafectasIgv(BigDecimal totalValorVentaOpInafectasIgv) {
        this.totalValorVentaOpInafectasIgv = totalValorVentaOpInafectasIgv;
    }

    public BigDecimal getTotalValorVentaOpGratuitas() {
        return totalValorVentaOpGratuitas;
    }

    public void setTotalValorVentaOpGratuitas(BigDecimal totalValorVentaOpGratuitas) {
        this.totalValorVentaOpGratuitas = totalValorVentaOpGratuitas;
    }

    public BigDecimal getTotalIsc() {
        return this.totalIsc;
    }

    public void setTotalIsc(BigDecimal totalIsc) {
        this.totalIsc = totalIsc;
    }

    public BigDecimal getTotalIgv() {
        return this.totalIgv;
    }

    public void setTotalIgv(BigDecimal totalIgv) {
        this.totalIgv = totalIgv;
    }

    public BigDecimal getTotalOtrosTributos() {
        return this.totalOtrosTributos;
    }

    public void setTotalOtrosTributos(BigDecimal totalOtrosTributos) {
        this.totalOtrosTributos = totalOtrosTributos;
    }

    public BigDecimal getTotalVenta() {
        return this.totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getTotalOtrosCargos() {
        return this.totalOtrosCargos;
    }

    public void setTotalOtrosCargos(BigDecimal totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public String getTipoMoneda() {
        return this.tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getIndicador() {
        return this.indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }
}
