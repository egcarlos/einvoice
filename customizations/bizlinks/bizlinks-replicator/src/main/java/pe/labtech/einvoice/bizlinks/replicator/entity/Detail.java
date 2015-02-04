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
@Table(name = "SPE_EINVOICEDETAIL")
@NamedQueries({
    @NamedQuery(name = "Detail.findAll", query = "SELECT d FROM Detail d")})
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DetailPK detailPK;
    @Size(max = 30)
    @Column(name = "codigoProducto", length = 30)
    private String codigoProducto;
    @NotNull
    @Size(min = 1, max = 1700)
    @Basic(optional = false)
    @Column(name = "descripcion", length = 1700, nullable = false)
    private String descripcion;
    @NotNull
    @Size(min = 1, max = 25)
    @Basic(optional = false)
    @Column(name = "cantidad", length = 25, nullable = false)
    private String cantidad;
    @NotNull
    @Size(min = 1, max = 3)
    @Basic(optional = false)
    @Column(name = "unidadMedida", length = 3, nullable = false)
    private String unidadMedida;
    @NotNull
    @Size(min = 1, max = 23)
    @Basic(optional = false)
    @Column(name = "importeUnitarioSinImpuesto", length = 23, nullable = false)
    private String importeUnitarioSinImpuesto;
    @NotNull
    @Size(min = 1, max = 35)
    @Basic(optional = false)
    @Column(name = "importeUnitarioConImpuesto", length = 35, nullable = false)
    private String importeUnitarioConImpuesto;
    @NotNull
    @Size(min = 1, max = 2)
    @Basic(optional = false)
    @Column(name = "codigoImporteUnitarioConImpuesto", length = 2, nullable = false)
    private String codigoImporteUnitarioConImpuesto;
    @Size(max = 15)
    @Column(name = "importeReferencial", length = 15)
    private String importeReferencial;
    @Size(max = 2)
    @Column(name = "codigoImporteReferencial", length = 2)
    private String codigoImporteReferencial;
    @NotNull
    @Size(min = 1, max = 15)
    @Basic(optional = false)
    @Column(name = "importeTotalSinImpuesto", length = 15, nullable = false)
    private String importeTotalSinImpuesto;
    @Size(max = 15)
    @Column(name = "importeDescuento", length = 15)
    private String importeDescuento;
    @Size(max = 15)
    @Column(name = "importeCargo", length = 15)
    private String importeCargo;
    @Size(max = 2)
    @Column(name = "codigoRazonExoneracion", length = 2)
    private String codigoRazonExoneracion;
    @Size(max = 15)
    @Column(name = "importeIgv", length = 15)
    private String importeIgv;
    @Size(max = 2)
    @Column(name = "tipoSistemaImpuestoISC", length = 2)
    private String tipoSistemaImpuestoISC;
    @Size(max = 15)
    @Column(name = "importeIsc", length = 15)
    private String importeIsc;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_1", length = 4)
    private String codigoAuxiliar100_1;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_1", length = 100)
    private String textoAuxiliar100_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_2", length = 4)
    private String codigoAuxiliar100_2;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_2", length = 100)
    private String textoAuxiliar100_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_3", length = 4)
    private String codigoAuxiliar100_3;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_3", length = 100)
    private String textoAuxiliar100_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_4", length = 4)
    private String codigoAuxiliar100_4;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_4", length = 100)
    private String textoAuxiliar100_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_5", length = 4)
    private String codigoAuxiliar100_5;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_5", length = 100)
    private String textoAuxiliar100_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_6", length = 4)
    private String codigoAuxiliar100_6;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_6", length = 100)
    private String textoAuxiliar100_6;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_7", length = 4)
    private String codigoAuxiliar100_7;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_7", length = 100)
    private String textoAuxiliar100_7;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_8", length = 4)
    private String codigoAuxiliar100_8;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_8", length = 100)
    private String textoAuxiliar100_8;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_9", length = 4)
    private String codigoAuxiliar100_9;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_9", length = 100)
    private String textoAuxiliar100_9;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_10", length = 4)
    private String codigoAuxiliar100_10;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_10", length = 100)
    private String textoAuxiliar100_10;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_1", length = 4)
    private String codigoAuxiliar40_1;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_1", length = 40)
    private String textoAuxiliar40_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_2", length = 4)
    private String codigoAuxiliar40_2;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_2", length = 40)
    private String textoAuxiliar40_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_3", length = 4)
    private String codigoAuxiliar40_3;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_3", length = 40)
    private String textoAuxiliar40_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_4", length = 4)
    private String codigoAuxiliar40_4;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_4", length = 40)
    private String textoAuxiliar40_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_5", length = 4)
    private String codigoAuxiliar40_5;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_5", length = 40)
    private String textoAuxiliar40_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_6", length = 4)
    private String codigoAuxiliar40_6;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_6", length = 40)
    private String textoAuxiliar40_6;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_7", length = 4)
    private String codigoAuxiliar40_7;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_7", length = 40)
    private String textoAuxiliar40_7;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_8", length = 4)
    private String codigoAuxiliar40_8;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_8", length = 40)
    private String textoAuxiliar40_8;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_9", length = 4)
    private String codigoAuxiliar40_9;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_9", length = 40)
    private String textoAuxiliar40_9;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_10", length = 4)
    private String codigoAuxiliar40_10;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_10", length = 40)
    private String textoAuxiliar40_10;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_1", length = 4)
    private String codigoAuxiliar500_1;
    @Size(max = 250)
    @Column(name = "textoAuxiliar500_1", length = 250)
    private String textoAuxiliar500_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_2", length = 4)
    private String codigoAuxiliar500_2;
    @Size(max = 250)
    @Column(name = "textoAuxiliar500_2", length = 250)
    private String textoAuxiliar500_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_3", length = 4)
    private String codigoAuxiliar500_3;
    @Size(max = 250)
    @Column(name = "textoAuxiliar500_3", length = 250)
    private String textoAuxiliar500_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_4", length = 4)
    private String codigoAuxiliar500_4;
    @Size(max = 250)
    @Column(name = "textoAuxiliar500_4", length = 250)
    private String textoAuxiliar500_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_5", length = 4)
    private String codigoAuxiliar500_5;
    @Size(max = 250)
    @Column(name = "textoAuxiliar500_5", length = 250)
    private String textoAuxiliar500_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_1", length = 4)
    private String codigoAuxiliar250_1;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_1", length = 250)
    private String textoAuxiliar250_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_2", length = 4)
    private String codigoAuxiliar250_2;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_2", length = 250)
    private String textoAuxiliar250_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_3", length = 4)
    private String codigoAuxiliar250_3;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_3", length = 250)
    private String textoAuxiliar250_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_4", length = 4)
    private String codigoAuxiliar250_4;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_4", length = 250)
    private String textoAuxiliar250_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_5", length = 4)
    private String codigoAuxiliar250_5;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_5", length = 250)
    private String textoAuxiliar250_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_6", length = 4)
    private String codigoAuxiliar250_6;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_6", length = 250)
    private String textoAuxiliar250_6;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_7", length = 4)
    private String codigoAuxiliar250_7;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_7", length = 250)
    private String textoAuxiliar250_7;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_8", length = 4)
    private String codigoAuxiliar250_8;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_8", length = 250)
    private String textoAuxiliar250_8;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_9", length = 4)
    private String codigoAuxiliar250_9;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_9", length = 250)
    private String textoAuxiliar250_9;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_10", length = 4)
    private String codigoAuxiliar250_10;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_10", length = 250)
    private String textoAuxiliar250_10;

    public Detail() {
    }

    public Detail(DetailPK detailPK) {
        this.detailPK = detailPK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.detailPK);
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
        final Detail other = (Detail) obj;
        if (!Objects.equals(this.detailPK, other.detailPK)) {
            return false;
        }
        return true;
    }

    public DetailPK getDetailPK() {
        return detailPK;
    }

    public void setDetailPK(DetailPK detailPK) {
        this.detailPK = detailPK;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getImporteUnitarioSinImpuesto() {
        return importeUnitarioSinImpuesto;
    }

    public void setImporteUnitarioSinImpuesto(String importeUnitarioSinImpuesto) {
        this.importeUnitarioSinImpuesto = importeUnitarioSinImpuesto;
    }

    public String getImporteUnitarioConImpuesto() {
        return importeUnitarioConImpuesto;
    }

    public void setImporteUnitarioConImpuesto(String importeUnitarioConImpuesto) {
        this.importeUnitarioConImpuesto = importeUnitarioConImpuesto;
    }

    public String getCodigoImporteUnitarioConImpuesto() {
        return codigoImporteUnitarioConImpuesto;
    }

    public void setCodigoImporteUnitarioConImpuesto(String codigoImporteUnitarioConImpuesto) {
        this.codigoImporteUnitarioConImpuesto = codigoImporteUnitarioConImpuesto;
    }

    public String getImporteReferencial() {
        return importeReferencial;
    }

    public void setImporteReferencial(String importeReferencial) {
        this.importeReferencial = importeReferencial;
    }

    public String getCodigoImporteReferencial() {
        return codigoImporteReferencial;
    }

    public void setCodigoImporteReferencial(String codigoImporteReferencial) {
        this.codigoImporteReferencial = codigoImporteReferencial;
    }

    public String getImporteTotalSinImpuesto() {
        return importeTotalSinImpuesto;
    }

    public void setImporteTotalSinImpuesto(String importeTotalSinImpuesto) {
        this.importeTotalSinImpuesto = importeTotalSinImpuesto;
    }

    public String getImporteDescuento() {
        return importeDescuento;
    }

    public void setImporteDescuento(String importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public String getImporteCargo() {
        return importeCargo;
    }

    public void setImporteCargo(String importeCargo) {
        this.importeCargo = importeCargo;
    }

    public String getCodigoRazonExoneracion() {
        return codigoRazonExoneracion;
    }

    public void setCodigoRazonExoneracion(String codigoRazonExoneracion) {
        this.codigoRazonExoneracion = codigoRazonExoneracion;
    }

    public String getImporteIgv() {
        return importeIgv;
    }

    public void setImporteIgv(String importeIgv) {
        this.importeIgv = importeIgv;
    }

    public String getTipoSistemaImpuestoISC() {
        return tipoSistemaImpuestoISC;
    }

    public void setTipoSistemaImpuestoISC(String tipoSistemaImpuestoISC) {
        this.tipoSistemaImpuestoISC = tipoSistemaImpuestoISC;
    }

    public String getImporteIsc() {
        return importeIsc;
    }

    public void setImporteIsc(String importeIsc) {
        this.importeIsc = importeIsc;
    }

    public String getCodigoAuxiliar100_1() {
        return codigoAuxiliar100_1;
    }

    public void setCodigoAuxiliar100_1(String codigoAuxiliar100_1) {
        this.codigoAuxiliar100_1 = codigoAuxiliar100_1;
    }

    public String getTextoAuxiliar100_1() {
        return textoAuxiliar100_1;
    }

    public void setTextoAuxiliar100_1(String textoAuxiliar100_1) {
        this.textoAuxiliar100_1 = textoAuxiliar100_1;
    }

    public String getCodigoAuxiliar100_2() {
        return codigoAuxiliar100_2;
    }

    public void setCodigoAuxiliar100_2(String codigoAuxiliar100_2) {
        this.codigoAuxiliar100_2 = codigoAuxiliar100_2;
    }

    public String getTextoAuxiliar100_2() {
        return textoAuxiliar100_2;
    }

    public void setTextoAuxiliar100_2(String textoAuxiliar100_2) {
        this.textoAuxiliar100_2 = textoAuxiliar100_2;
    }

    public String getCodigoAuxiliar100_3() {
        return codigoAuxiliar100_3;
    }

    public void setCodigoAuxiliar100_3(String codigoAuxiliar100_3) {
        this.codigoAuxiliar100_3 = codigoAuxiliar100_3;
    }

    public String getTextoAuxiliar100_3() {
        return textoAuxiliar100_3;
    }

    public void setTextoAuxiliar100_3(String textoAuxiliar100_3) {
        this.textoAuxiliar100_3 = textoAuxiliar100_3;
    }

    public String getCodigoAuxiliar100_4() {
        return codigoAuxiliar100_4;
    }

    public void setCodigoAuxiliar100_4(String codigoAuxiliar100_4) {
        this.codigoAuxiliar100_4 = codigoAuxiliar100_4;
    }

    public String getTextoAuxiliar100_4() {
        return textoAuxiliar100_4;
    }

    public void setTextoAuxiliar100_4(String textoAuxiliar100_4) {
        this.textoAuxiliar100_4 = textoAuxiliar100_4;
    }

    public String getCodigoAuxiliar100_5() {
        return codigoAuxiliar100_5;
    }

    public void setCodigoAuxiliar100_5(String codigoAuxiliar100_5) {
        this.codigoAuxiliar100_5 = codigoAuxiliar100_5;
    }

    public String getTextoAuxiliar100_5() {
        return textoAuxiliar100_5;
    }

    public void setTextoAuxiliar100_5(String textoAuxiliar100_5) {
        this.textoAuxiliar100_5 = textoAuxiliar100_5;
    }

    public String getCodigoAuxiliar100_6() {
        return codigoAuxiliar100_6;
    }

    public void setCodigoAuxiliar100_6(String codigoAuxiliar100_6) {
        this.codigoAuxiliar100_6 = codigoAuxiliar100_6;
    }

    public String getTextoAuxiliar100_6() {
        return textoAuxiliar100_6;
    }

    public void setTextoAuxiliar100_6(String textoAuxiliar100_6) {
        this.textoAuxiliar100_6 = textoAuxiliar100_6;
    }

    public String getCodigoAuxiliar100_7() {
        return codigoAuxiliar100_7;
    }

    public void setCodigoAuxiliar100_7(String codigoAuxiliar100_7) {
        this.codigoAuxiliar100_7 = codigoAuxiliar100_7;
    }

    public String getTextoAuxiliar100_7() {
        return textoAuxiliar100_7;
    }

    public void setTextoAuxiliar100_7(String textoAuxiliar100_7) {
        this.textoAuxiliar100_7 = textoAuxiliar100_7;
    }

    public String getCodigoAuxiliar100_8() {
        return codigoAuxiliar100_8;
    }

    public void setCodigoAuxiliar100_8(String codigoAuxiliar100_8) {
        this.codigoAuxiliar100_8 = codigoAuxiliar100_8;
    }

    public String getTextoAuxiliar100_8() {
        return textoAuxiliar100_8;
    }

    public void setTextoAuxiliar100_8(String textoAuxiliar100_8) {
        this.textoAuxiliar100_8 = textoAuxiliar100_8;
    }

    public String getCodigoAuxiliar100_9() {
        return codigoAuxiliar100_9;
    }

    public void setCodigoAuxiliar100_9(String codigoAuxiliar100_9) {
        this.codigoAuxiliar100_9 = codigoAuxiliar100_9;
    }

    public String getTextoAuxiliar100_9() {
        return textoAuxiliar100_9;
    }

    public void setTextoAuxiliar100_9(String textoAuxiliar100_9) {
        this.textoAuxiliar100_9 = textoAuxiliar100_9;
    }

    public String getCodigoAuxiliar100_10() {
        return codigoAuxiliar100_10;
    }

    public void setCodigoAuxiliar100_10(String codigoAuxiliar100_10) {
        this.codigoAuxiliar100_10 = codigoAuxiliar100_10;
    }

    public String getTextoAuxiliar100_10() {
        return textoAuxiliar100_10;
    }

    public void setTextoAuxiliar100_10(String textoAuxiliar100_10) {
        this.textoAuxiliar100_10 = textoAuxiliar100_10;
    }

    public String getCodigoAuxiliar40_1() {
        return codigoAuxiliar40_1;
    }

    public void setCodigoAuxiliar40_1(String codigoAuxiliar40_1) {
        this.codigoAuxiliar40_1 = codigoAuxiliar40_1;
    }

    public String getTextoAuxiliar40_1() {
        return textoAuxiliar40_1;
    }

    public void setTextoAuxiliar40_1(String textoAuxiliar40_1) {
        this.textoAuxiliar40_1 = textoAuxiliar40_1;
    }

    public String getCodigoAuxiliar40_2() {
        return codigoAuxiliar40_2;
    }

    public void setCodigoAuxiliar40_2(String codigoAuxiliar40_2) {
        this.codigoAuxiliar40_2 = codigoAuxiliar40_2;
    }

    public String getTextoAuxiliar40_2() {
        return textoAuxiliar40_2;
    }

    public void setTextoAuxiliar40_2(String textoAuxiliar40_2) {
        this.textoAuxiliar40_2 = textoAuxiliar40_2;
    }

    public String getCodigoAuxiliar40_3() {
        return codigoAuxiliar40_3;
    }

    public void setCodigoAuxiliar40_3(String codigoAuxiliar40_3) {
        this.codigoAuxiliar40_3 = codigoAuxiliar40_3;
    }

    public String getTextoAuxiliar40_3() {
        return textoAuxiliar40_3;
    }

    public void setTextoAuxiliar40_3(String textoAuxiliar40_3) {
        this.textoAuxiliar40_3 = textoAuxiliar40_3;
    }

    public String getCodigoAuxiliar40_4() {
        return codigoAuxiliar40_4;
    }

    public void setCodigoAuxiliar40_4(String codigoAuxiliar40_4) {
        this.codigoAuxiliar40_4 = codigoAuxiliar40_4;
    }

    public String getTextoAuxiliar40_4() {
        return textoAuxiliar40_4;
    }

    public void setTextoAuxiliar40_4(String textoAuxiliar40_4) {
        this.textoAuxiliar40_4 = textoAuxiliar40_4;
    }

    public String getCodigoAuxiliar40_5() {
        return codigoAuxiliar40_5;
    }

    public void setCodigoAuxiliar40_5(String codigoAuxiliar40_5) {
        this.codigoAuxiliar40_5 = codigoAuxiliar40_5;
    }

    public String getTextoAuxiliar40_5() {
        return textoAuxiliar40_5;
    }

    public void setTextoAuxiliar40_5(String textoAuxiliar40_5) {
        this.textoAuxiliar40_5 = textoAuxiliar40_5;
    }

    public String getCodigoAuxiliar40_6() {
        return codigoAuxiliar40_6;
    }

    public void setCodigoAuxiliar40_6(String codigoAuxiliar40_6) {
        this.codigoAuxiliar40_6 = codigoAuxiliar40_6;
    }

    public String getTextoAuxiliar40_6() {
        return textoAuxiliar40_6;
    }

    public void setTextoAuxiliar40_6(String textoAuxiliar40_6) {
        this.textoAuxiliar40_6 = textoAuxiliar40_6;
    }

    public String getCodigoAuxiliar40_7() {
        return codigoAuxiliar40_7;
    }

    public void setCodigoAuxiliar40_7(String codigoAuxiliar40_7) {
        this.codigoAuxiliar40_7 = codigoAuxiliar40_7;
    }

    public String getTextoAuxiliar40_7() {
        return textoAuxiliar40_7;
    }

    public void setTextoAuxiliar40_7(String textoAuxiliar40_7) {
        this.textoAuxiliar40_7 = textoAuxiliar40_7;
    }

    public String getCodigoAuxiliar40_8() {
        return codigoAuxiliar40_8;
    }

    public void setCodigoAuxiliar40_8(String codigoAuxiliar40_8) {
        this.codigoAuxiliar40_8 = codigoAuxiliar40_8;
    }

    public String getTextoAuxiliar40_8() {
        return textoAuxiliar40_8;
    }

    public void setTextoAuxiliar40_8(String textoAuxiliar40_8) {
        this.textoAuxiliar40_8 = textoAuxiliar40_8;
    }

    public String getCodigoAuxiliar40_9() {
        return codigoAuxiliar40_9;
    }

    public void setCodigoAuxiliar40_9(String codigoAuxiliar40_9) {
        this.codigoAuxiliar40_9 = codigoAuxiliar40_9;
    }

    public String getTextoAuxiliar40_9() {
        return textoAuxiliar40_9;
    }

    public void setTextoAuxiliar40_9(String textoAuxiliar40_9) {
        this.textoAuxiliar40_9 = textoAuxiliar40_9;
    }

    public String getCodigoAuxiliar40_10() {
        return codigoAuxiliar40_10;
    }

    public void setCodigoAuxiliar40_10(String codigoAuxiliar40_10) {
        this.codigoAuxiliar40_10 = codigoAuxiliar40_10;
    }

    public String getTextoAuxiliar40_10() {
        return textoAuxiliar40_10;
    }

    public void setTextoAuxiliar40_10(String textoAuxiliar40_10) {
        this.textoAuxiliar40_10 = textoAuxiliar40_10;
    }

    public String getCodigoAuxiliar500_1() {
        return codigoAuxiliar500_1;
    }

    public void setCodigoAuxiliar500_1(String codigoAuxiliar500_1) {
        this.codigoAuxiliar500_1 = codigoAuxiliar500_1;
    }

    public String getTextoAuxiliar500_1() {
        return textoAuxiliar500_1;
    }

    public void setTextoAuxiliar500_1(String textoAuxiliar500_1) {
        this.textoAuxiliar500_1 = textoAuxiliar500_1;
    }

    public String getCodigoAuxiliar500_2() {
        return codigoAuxiliar500_2;
    }

    public void setCodigoAuxiliar500_2(String codigoAuxiliar500_2) {
        this.codigoAuxiliar500_2 = codigoAuxiliar500_2;
    }

    public String getTextoAuxiliar500_2() {
        return textoAuxiliar500_2;
    }

    public void setTextoAuxiliar500_2(String textoAuxiliar500_2) {
        this.textoAuxiliar500_2 = textoAuxiliar500_2;
    }

    public String getCodigoAuxiliar500_3() {
        return codigoAuxiliar500_3;
    }

    public void setCodigoAuxiliar500_3(String codigoAuxiliar500_3) {
        this.codigoAuxiliar500_3 = codigoAuxiliar500_3;
    }

    public String getTextoAuxiliar500_3() {
        return textoAuxiliar500_3;
    }

    public void setTextoAuxiliar500_3(String textoAuxiliar500_3) {
        this.textoAuxiliar500_3 = textoAuxiliar500_3;
    }

    public String getCodigoAuxiliar500_4() {
        return codigoAuxiliar500_4;
    }

    public void setCodigoAuxiliar500_4(String codigoAuxiliar500_4) {
        this.codigoAuxiliar500_4 = codigoAuxiliar500_4;
    }

    public String getTextoAuxiliar500_4() {
        return textoAuxiliar500_4;
    }

    public void setTextoAuxiliar500_4(String textoAuxiliar500_4) {
        this.textoAuxiliar500_4 = textoAuxiliar500_4;
    }

    public String getCodigoAuxiliar500_5() {
        return codigoAuxiliar500_5;
    }

    public void setCodigoAuxiliar500_5(String codigoAuxiliar500_5) {
        this.codigoAuxiliar500_5 = codigoAuxiliar500_5;
    }

    public String getTextoAuxiliar500_5() {
        return textoAuxiliar500_5;
    }

    public void setTextoAuxiliar500_5(String textoAuxiliar500_5) {
        this.textoAuxiliar500_5 = textoAuxiliar500_5;
    }

    public String getCodigoAuxiliar250_1() {
        return codigoAuxiliar250_1;
    }

    public void setCodigoAuxiliar250_1(String codigoAuxiliar250_1) {
        this.codigoAuxiliar250_1 = codigoAuxiliar250_1;
    }

    public String getTextoAuxiliar250_1() {
        return textoAuxiliar250_1;
    }

    public void setTextoAuxiliar250_1(String textoAuxiliar250_1) {
        this.textoAuxiliar250_1 = textoAuxiliar250_1;
    }

    public String getCodigoAuxiliar250_2() {
        return codigoAuxiliar250_2;
    }

    public void setCodigoAuxiliar250_2(String codigoAuxiliar250_2) {
        this.codigoAuxiliar250_2 = codigoAuxiliar250_2;
    }

    public String getTextoAuxiliar250_2() {
        return textoAuxiliar250_2;
    }

    public void setTextoAuxiliar250_2(String textoAuxiliar250_2) {
        this.textoAuxiliar250_2 = textoAuxiliar250_2;
    }

    public String getCodigoAuxiliar250_3() {
        return codigoAuxiliar250_3;
    }

    public void setCodigoAuxiliar250_3(String codigoAuxiliar250_3) {
        this.codigoAuxiliar250_3 = codigoAuxiliar250_3;
    }

    public String getTextoAuxiliar250_3() {
        return textoAuxiliar250_3;
    }

    public void setTextoAuxiliar250_3(String textoAuxiliar250_3) {
        this.textoAuxiliar250_3 = textoAuxiliar250_3;
    }

    public String getCodigoAuxiliar250_4() {
        return codigoAuxiliar250_4;
    }

    public void setCodigoAuxiliar250_4(String codigoAuxiliar250_4) {
        this.codigoAuxiliar250_4 = codigoAuxiliar250_4;
    }

    public String getTextoAuxiliar250_4() {
        return textoAuxiliar250_4;
    }

    public void setTextoAuxiliar250_4(String textoAuxiliar250_4) {
        this.textoAuxiliar250_4 = textoAuxiliar250_4;
    }

    public String getCodigoAuxiliar250_5() {
        return codigoAuxiliar250_5;
    }

    public void setCodigoAuxiliar250_5(String codigoAuxiliar250_5) {
        this.codigoAuxiliar250_5 = codigoAuxiliar250_5;
    }

    public String getTextoAuxiliar250_5() {
        return textoAuxiliar250_5;
    }

    public void setTextoAuxiliar250_5(String textoAuxiliar250_5) {
        this.textoAuxiliar250_5 = textoAuxiliar250_5;
    }

    public String getCodigoAuxiliar250_6() {
        return codigoAuxiliar250_6;
    }

    public void setCodigoAuxiliar250_6(String codigoAuxiliar250_6) {
        this.codigoAuxiliar250_6 = codigoAuxiliar250_6;
    }

    public String getTextoAuxiliar250_6() {
        return textoAuxiliar250_6;
    }

    public void setTextoAuxiliar250_6(String textoAuxiliar250_6) {
        this.textoAuxiliar250_6 = textoAuxiliar250_6;
    }

    public String getCodigoAuxiliar250_7() {
        return codigoAuxiliar250_7;
    }

    public void setCodigoAuxiliar250_7(String codigoAuxiliar250_7) {
        this.codigoAuxiliar250_7 = codigoAuxiliar250_7;
    }

    public String getTextoAuxiliar250_7() {
        return textoAuxiliar250_7;
    }

    public void setTextoAuxiliar250_7(String textoAuxiliar250_7) {
        this.textoAuxiliar250_7 = textoAuxiliar250_7;
    }

    public String getCodigoAuxiliar250_8() {
        return codigoAuxiliar250_8;
    }

    public void setCodigoAuxiliar250_8(String codigoAuxiliar250_8) {
        this.codigoAuxiliar250_8 = codigoAuxiliar250_8;
    }

    public String getTextoAuxiliar250_8() {
        return textoAuxiliar250_8;
    }

    public void setTextoAuxiliar250_8(String textoAuxiliar250_8) {
        this.textoAuxiliar250_8 = textoAuxiliar250_8;
    }

    public String getCodigoAuxiliar250_9() {
        return codigoAuxiliar250_9;
    }

    public void setCodigoAuxiliar250_9(String codigoAuxiliar250_9) {
        this.codigoAuxiliar250_9 = codigoAuxiliar250_9;
    }

    public String getTextoAuxiliar250_9() {
        return textoAuxiliar250_9;
    }

    public void setTextoAuxiliar250_9(String textoAuxiliar250_9) {
        this.textoAuxiliar250_9 = textoAuxiliar250_9;
    }

    public String getCodigoAuxiliar250_10() {
        return codigoAuxiliar250_10;
    }

    public void setCodigoAuxiliar250_10(String codigoAuxiliar250_10) {
        this.codigoAuxiliar250_10 = codigoAuxiliar250_10;
    }

    public String getTextoAuxiliar250_10() {
        return textoAuxiliar250_10;
    }

    public void setTextoAuxiliar250_10(String textoAuxiliar250_10) {
        this.textoAuxiliar250_10 = textoAuxiliar250_10;
    }

}
