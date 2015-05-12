/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_EINVOICEDETAIL", catalog = "salfaconstruccion_261014", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "DocumentDetail.findAll", query = "SELECT d FROM DocumentDetail d")})
public class DocumentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocumentDetailPK id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DCODPRODUCTO", nullable = false, length = 255)
    private String codigoProducto;
    @Size(max = 1700)
    @Column(name = "DDESPRODUCTO", length = 1700)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DCANPRODUCTO", precision = 19, scale = 2)
    private BigDecimal cantidad;
    @Size(max = 3)
    @Column(name = "DUNIPRODUCTO", length = 3)
    private String unidadMedida;
    @Column(name = "DVVENTAUNITARIO", precision = 19, scale = 2)
    private BigDecimal importeUnitarioSinImpuesto;
    @Column(name = "DPVENTAUNITARIO", precision = 19, scale = 2)
    private BigDecimal importeUnitarioConImpuesto;
    @Size(max = 2)
    @Column(name = "DCODPVENTAUNITARIO", length = 2)
    private String codigoImporteUnitarioConImpuesto;
    @Column(name = "DIREFERENCIAUNITARIO", precision = 19, scale = 2)
    private BigDecimal importeReferencial;
    @Size(max = 2)
    @Column(name = "DCODIREFERENCIAUNITARIO", length = 2)
    private String codigoImporteReferencial;
    @Column(name = "DVVENTA", precision = 19, scale = 2)
    private BigDecimal importeTotalSinImpuesto;
    @Column(name = "DCARGO", precision = 19, scale = 2)
    private BigDecimal importeCargo;
    @Column(name = "DDESCUENTO", precision = 19, scale = 2)
    private BigDecimal importeDescuento;
    @Size(max = 2)
    @Column(name = "DTIPIGV", length = 2)
    private String codigoRazonExoneracion;
    @Column(name = "DIGV", precision = 19, scale = 2)
    private BigDecimal importeIgv;
    @Size(max = 2)
    @Column(name = "DTIPISC", length = 2)
    private String tipoSistemaImpuestoISC;
    @Column(name = "DISC", precision = 19, scale = 2)
    private BigDecimal importeIsc;
    @Size(max = 100)
    @Column(name = "DAUX1", length = 100)
    private String x_daux1;
    @Size(max = 100)
    @Column(name = "DAUX2", length = 100)
    private String x_daux2;
    @Size(max = 100)
    @Column(name = "DAUX3", length = 100)
    private String x_daux3;
    @Size(max = 100)
    @Column(name = "DAUX4", length = 100)
    private String x_daux4;
    @Size(max = 100)
    @Column(name = "DAUX5", length = 100)
    private String x_daux5;
    @Size(max = 100)
    @Column(name = "DAUX6", length = 100)
    private String x_daux6;
    @Size(max = 100)
    @Column(name = "DAUX7", length = 100)
    private String x_daux7;
    @Size(max = 100)
    @Column(name = "DAUX8", length = 100)
    private String x_daux8;
    @Size(max = 100)
    @Column(name = "DAUX9", length = 100)
    private String x_daux9;
    @Size(max = 100)
    @Column(name = "DAUX10", length = 100)
    private String x_daux10;
    @Size(max = 100)
    @Column(name = "DAUX11", length = 100)
    private String x_daux11;
    @Size(max = 100)
    @Column(name = "DAUX12", length = 100)
    private String x_daux12;
    @Size(max = 100)
    @Column(name = "DAUX13", length = 100)
    private String x_daux13;
    @Size(max = 100)
    @Column(name = "DAUX14", length = 100)
    private String x_daux14;

    public DocumentDetail() {
    }

    public DocumentDetailPK getId() {
        return id;
    }

    public void setId(DocumentDetailPK id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getImporteUnitarioSinImpuesto() {
        return importeUnitarioSinImpuesto;
    }

    public void setImporteUnitarioSinImpuesto(BigDecimal importeUnitarioSinImpuesto) {
        this.importeUnitarioSinImpuesto = importeUnitarioSinImpuesto;
    }

    public BigDecimal getImporteUnitarioConImpuesto() {
        return importeUnitarioConImpuesto;
    }

    public void setImporteUnitarioConImpuesto(BigDecimal importeUnitarioConImpuesto) {
        this.importeUnitarioConImpuesto = importeUnitarioConImpuesto;
    }

    public String getCodigoImporteUnitarioConImpuesto() {
        return codigoImporteUnitarioConImpuesto;
    }

    public void setCodigoImporteUnitarioConImpuesto(String codigoImporteUnitarioConImpuesto) {
        this.codigoImporteUnitarioConImpuesto = codigoImporteUnitarioConImpuesto;
    }

    public BigDecimal getImporteReferencial() {
        return importeReferencial;
    }

    public void setImporteReferencial(BigDecimal importeReferencial) {
        this.importeReferencial = importeReferencial;
    }

    public String getCodigoImporteReferencial() {
        return codigoImporteReferencial;
    }

    public void setCodigoImporteReferencial(String codigoImporteReferencial) {
        this.codigoImporteReferencial = codigoImporteReferencial;
    }

    public BigDecimal getImporteTotalSinImpuesto() {
        return importeTotalSinImpuesto;
    }

    public void setImporteTotalSinImpuesto(BigDecimal importeTotalSinImpuesto) {
        this.importeTotalSinImpuesto = importeTotalSinImpuesto;
    }

    public BigDecimal getImporteCargo() {
        return importeCargo;
    }

    public void setImporteCargo(BigDecimal importeCargo) {
        this.importeCargo = importeCargo;
    }

    public BigDecimal getImporteDescuento() {
        return importeDescuento;
    }

    public void setImporteDescuento(BigDecimal importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public String getCodigoRazonExoneracion() {
        return codigoRazonExoneracion;
    }

    public void setCodigoRazonExoneracion(String codigoRazonExoneracion) {
        this.codigoRazonExoneracion = codigoRazonExoneracion;
    }

    public BigDecimal getImporteIgv() {
        return importeIgv;
    }

    public void setImporteIgv(BigDecimal importeIgv) {
        this.importeIgv = importeIgv;
    }

    public String getTipoSistemaImpuestoISC() {
        return tipoSistemaImpuestoISC;
    }

    public void setTipoSistemaImpuestoISC(String tipoSistemaImpuestoISC) {
        this.tipoSistemaImpuestoISC = tipoSistemaImpuestoISC;
    }

    public BigDecimal getImporteIsc() {
        return importeIsc;
    }

    public void setImporteIsc(BigDecimal importeIsc) {
        this.importeIsc = importeIsc;
    }

    public String getX_daux1() {
        return x_daux1;
    }

    public void setX_daux1(String x_daux1) {
        this.x_daux1 = x_daux1;
    }

    public String getX_daux2() {
        return x_daux2;
    }

    public void setX_daux2(String x_daux2) {
        this.x_daux2 = x_daux2;
    }

    public String getX_daux3() {
        return x_daux3;
    }

    public void setX_daux3(String x_daux3) {
        this.x_daux3 = x_daux3;
    }

    public String getX_daux4() {
        return x_daux4;
    }

    public void setX_daux4(String x_daux4) {
        this.x_daux4 = x_daux4;
    }

    public String getX_daux5() {
        return x_daux5;
    }

    public void setX_daux5(String x_daux5) {
        this.x_daux5 = x_daux5;
    }

    public String getX_daux6() {
        return x_daux6;
    }

    public void setX_daux6(String x_daux6) {
        this.x_daux6 = x_daux6;
    }

    public String getX_daux7() {
        return x_daux7;
    }

    public void setX_daux7(String x_daux7) {
        this.x_daux7 = x_daux7;
    }

    public String getX_daux8() {
        return x_daux8;
    }

    public void setX_daux8(String x_daux8) {
        this.x_daux8 = x_daux8;
    }

    public String getX_daux9() {
        return x_daux9;
    }

    public void setX_daux9(String x_daux9) {
        this.x_daux9 = x_daux9;
    }

    public String getX_daux10() {
        return x_daux10;
    }

    public void setX_daux10(String x_daux10) {
        this.x_daux10 = x_daux10;
    }

    public String getX_daux11() {
        return x_daux11;
    }

    public void setX_daux11(String x_daux11) {
        this.x_daux11 = x_daux11;
    }

    public String getX_daux12() {
        return x_daux12;
    }

    public void setX_daux12(String x_daux12) {
        this.x_daux12 = x_daux12;
    }

    public String getX_daux13() {
        return x_daux13;
    }

    public void setX_daux13(String x_daux13) {
        this.x_daux13 = x_daux13;
    }

    public String getX_daux14() {
        return x_daux14;
    }

    public void setX_daux14(String x_daux14) {
        this.x_daux14 = x_daux14;
    }

}
