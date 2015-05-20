/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Item",
        propOrder = {
            "indicador",
            "numeroOrdenItem",
            "codigoProducto",
            "descripcion",
            "cantidad",
            "unidadMedida",
            "importeUnitarioSinImpuesto",
            "importeUnitarioConImpuesto",
            "codigoImporteUnitarioConImpuesto",
            "importeTotalSinImpuesto",
            "importeDescuento",
            "importeCargo",
            "codigoRazonExoneracion",
            "importeIgv",
            "tipoSistemaImpuestoISC",
            "importeIsc",
            "codigoImporteReferencial",
            "importeReferencial",
            "codigoAuxiliar100_1",
            "textoAuxiliar100_1",
            "codigoAuxiliar100_2",
            "textoAuxiliar100_2",
            "codigoAuxiliar100_3",
            "textoAuxiliar100_3",
            "codigoAuxiliar100_4",
            "textoAuxiliar100_4",
            "codigoAuxiliar100_5",
            "textoAuxiliar100_5",
            "codigoAuxiliar100_6",
            "textoAuxiliar100_6",
            "codigoAuxiliar100_7",
            "textoAuxiliar100_7",
            "codigoAuxiliar100_8",
            "textoAuxiliar100_8",
            "codigoAuxiliar100_9",
            "textoAuxiliar100_9",
            "codigoAuxiliar100_10",
            "textoAuxiliar100_10",
            "codigoAuxiliar40_1",
            "textoAuxiliar40_1",
            "codigoAuxiliar40_2",
            "textoAuxiliar40_2",
            "codigoAuxiliar40_3",
            "textoAuxiliar40_3",
            "codigoAuxiliar40_4",
            "textoAuxiliar40_4",
            "codigoAuxiliar40_5",
            "textoAuxiliar40_5",
            "codigoAuxiliar40_6",
            "textoAuxiliar40_6",
            "codigoAuxiliar40_7",
            "textoAuxiliar40_7",
            "codigoAuxiliar40_8",
            "textoAuxiliar40_8",
            "codigoAuxiliar40_9",
            "textoAuxiliar40_9",
            "codigoAuxiliar40_10",
            "textoAuxiliar40_10",
            "codigoAuxiliar250_1",
            "textoAuxiliar250_1",
            "codigoAuxiliar250_2",
            "textoAuxiliar250_2",
            "codigoAuxiliar250_3",
            "textoAuxiliar250_3",
            "codigoAuxiliar250_4",
            "textoAuxiliar250_4",
            "codigoAuxiliar250_5",
            "textoAuxiliar250_5",
            "codigoAuxiliar250_6",
            "textoAuxiliar250_6",
            "codigoAuxiliar250_7",
            "textoAuxiliar250_7",
            "codigoAuxiliar250_8",
            "textoAuxiliar250_8",
            "codigoAuxiliar250_9",
            "textoAuxiliar250_9",
            "codigoAuxiliar250_10",
            "textoAuxiliar250_10",
            "codigoAuxiliar500_1",
            "textoAuxiliar500_1",
            "codigoAuxiliar500_2",
            "textoAuxiliar500_2",
            "codigoAuxiliar500_3",
            "textoAuxiliar500_3",
            "codigoAuxiliar500_4",
            "textoAuxiliar500_4",
            "codigoAuxiliar500_5",
            "textoAuxiliar500_5"
        })
public class DocumentItem {

    /**
     * Should allways be D
     */
    @XmlElement(required = true)
    protected String indicador;
    @XmlElement(required = true)
    protected String numeroOrdenItem;
    protected String codigoProducto;
    @XmlElement(required = true)
    protected String descripcion;
    @XmlJavaTypeAdapter(NumberAdapter.class)
    protected BigDecimal cantidad = BigDecimal.ZERO;
    @XmlElement(required = true)
    protected String unidadMedida;
    protected BigDecimal importeUnitarioSinImpuesto;
    protected BigDecimal importeUnitarioConImpuesto;
    protected String codigoImporteUnitarioConImpuesto;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    protected BigDecimal importeTotalSinImpuesto;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    protected BigDecimal importeDescuento;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    protected BigDecimal importeCargo;
    @XmlElement(required = true)
    protected String codigoRazonExoneracion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    protected BigDecimal importeIgv;
    protected String tipoSistemaImpuestoISC;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    protected BigDecimal importeIsc;
    private String codigoImporteReferencial;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal importeReferencial;
    private String codigoAuxiliar100_1;
    private String textoAuxiliar100_1;
    private String codigoAuxiliar100_2;
    private String textoAuxiliar100_2;
    private String codigoAuxiliar100_3;
    private String textoAuxiliar100_3;
    private String codigoAuxiliar100_4;
    private String textoAuxiliar100_4;
    private String codigoAuxiliar100_5;
    private String textoAuxiliar100_5;
    private String codigoAuxiliar100_6;
    private String textoAuxiliar100_6;
    private String codigoAuxiliar100_7;
    private String textoAuxiliar100_7;
    private String codigoAuxiliar100_8;
    private String textoAuxiliar100_8;
    private String codigoAuxiliar100_9;
    private String textoAuxiliar100_9;
    private String codigoAuxiliar100_10;
    private String textoAuxiliar100_10;
    private String codigoAuxiliar40_1;
    private String textoAuxiliar40_1;
    private String codigoAuxiliar40_2;
    private String textoAuxiliar40_2;
    private String codigoAuxiliar40_3;
    private String textoAuxiliar40_3;
    private String codigoAuxiliar40_4;
    private String textoAuxiliar40_4;
    private String codigoAuxiliar40_5;
    private String textoAuxiliar40_5;
    private String codigoAuxiliar40_6;
    private String textoAuxiliar40_6;
    private String codigoAuxiliar40_7;
    private String textoAuxiliar40_7;
    private String codigoAuxiliar40_8;
    private String textoAuxiliar40_8;
    private String codigoAuxiliar40_9;
    private String textoAuxiliar40_9;
    private String codigoAuxiliar40_10;
    private String textoAuxiliar40_10;
    private String codigoAuxiliar500_1;
    private String textoAuxiliar500_1;
    private String codigoAuxiliar500_2;
    private String textoAuxiliar500_2;
    private String codigoAuxiliar500_3;
    private String textoAuxiliar500_3;
    private String codigoAuxiliar500_4;
    private String textoAuxiliar500_4;
    private String codigoAuxiliar500_5;
    private String textoAuxiliar500_5;
    private String codigoAuxiliar250_1;
    private String textoAuxiliar250_1;
    private String codigoAuxiliar250_2;
    private String textoAuxiliar250_2;
    private String codigoAuxiliar250_3;
    private String textoAuxiliar250_3;
    private String codigoAuxiliar250_4;
    private String textoAuxiliar250_4;
    private String codigoAuxiliar250_5;
    private String textoAuxiliar250_5;
    private String codigoAuxiliar250_6;
    private String textoAuxiliar250_6;
    private String codigoAuxiliar250_7;
    private String textoAuxiliar250_7;
    private String codigoAuxiliar250_8;
    private String textoAuxiliar250_8;
    private String codigoAuxiliar250_9;
    private String textoAuxiliar250_9;
    private String codigoAuxiliar250_10;
    private String textoAuxiliar250_10;

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getNumeroOrdenItem() {
        return numeroOrdenItem;
    }

    public void setNumeroOrdenItem(String numeroOrdenItem) {
        this.numeroOrdenItem = numeroOrdenItem;
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

    public BigDecimal getImporteTotalSinImpuesto() {
        return importeTotalSinImpuesto;
    }

    public void setImporteTotalSinImpuesto(BigDecimal importeTotalSinImpuesto) {
        this.importeTotalSinImpuesto = importeTotalSinImpuesto;
    }

    public BigDecimal getImporteDescuento() {
        return importeDescuento;
    }

    public void setImporteDescuento(BigDecimal importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public BigDecimal getImporteCargo() {
        return importeCargo;
    }

    public void setImporteCargo(BigDecimal importeCargo) {
        this.importeCargo = importeCargo;
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

    public String getCodigoImporteReferencial() {
        return codigoImporteReferencial;
    }

    public void setCodigoImporteReferencial(String codigoImporteReferencial) {
        this.codigoImporteReferencial = codigoImporteReferencial;
    }

    public BigDecimal getImporteReferencial() {
        return importeReferencial;
    }

    public void setImporteReferencial(BigDecimal importeReferencial) {
        this.importeReferencial = importeReferencial;
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
