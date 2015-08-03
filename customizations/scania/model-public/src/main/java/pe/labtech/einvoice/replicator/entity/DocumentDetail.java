/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_eInvoiceDetail")
public class DocumentDetail implements Serializable {

    @Id
    @Column(name = "DOCTYPE")
    private String tipoDocumento;
    @Id
    @Column(name = "DOCNUM")
    private String serieNumero;
    @Id
    @Column(name = "DDORD")
    private String numeroOrdenItem;

    //Private tracking data
    @Column(name = "SPESITE")
    private String site;
    @Column(name = "DDRTYPE")
    private String type;

    //general data (should be common for most clients
    @Column(name = "DAORDER")
    private String productionOrder;
    @Column(name = "DDITEMNO")
//    @Ebiz("codigoProducto")
    private String codigoProducto;
    @Column(name = "DDDESC")
//    @Ebiz("descripcion")
    private String descripcion;
    @Column(name = "DDNUM")
//    @Ebiz("cantidad")
    private String cantidad;
    @Column(name = "DDUNIT")
//    @Ebiz("unidadMedida")
    private String unidadMedida;
    @Column(name = "DDUNPR")
//    @Ebiz("importeUnitarioSinImpuesto")
    private String importeUnitarioSinImpuesto;
    @Column(name = "DDUPIVA")
//    @Ebiz("importeUnitarioConImpuesto")
    private String importeUnitarioConImpuesto;
    @Column(name = "DDCODUP")
//    @Ebiz("codigoImporteUnitarioConImpuesto")
    private String codigoImporteUnitarioConImpuesto;
    @Column(name = "DDTOTAL")
//    @Ebiz("importeTotalSinImpuesto")
    private String importeTotalSinImpuesto;
    @Column(name = "DDDISC")
//    @Ebiz("importeDescuento")
    private String importeDescuento;
    @Column(name = "DDCODEX")
//    @Ebiz("codigoRazonExoneracion")
    private String codigoRazonExoneracion;
    @Column(name = "DDIVA")
//    @Ebiz("importeIgv")
    private String importeIgv;
    @Column(name = "DDIMPREF")
//    @Ebiz("importeReferencial")
    private String importeReferencial;
    @Column(name = "DDCODIR")
//    @Ebiz("codigoImporteReferencial")
    private String codigoImporteReferencial;

    @Transient
    private String codigoAuxiliar40_1 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_1;

    @Transient
    private String codigoAuxiliar40_2 = "9038";
    @Column(name = "DDMODELO")
    private String textoAuxiliar40_2;

    @Transient
    private String codigoAuxiliar40_3 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_3;

    @Transient
    private String codigoAuxiliar40_4 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_4;

    @Transient
    private String codigoAuxiliar40_5 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_5;

    @Transient
    private String codigoAuxiliar40_6 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_6;

    @Transient
    private String codigoAuxiliar40_7 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_7;

    @Transient
    private String codigoAuxiliar40_8 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_8;

    @Transient
    private String codigoAuxiliar40_9 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_9;

    @Transient
    private String codigoAuxiliar40_10 = "9037";
    @Column(name = "DDMARCA")
    private String textoAuxiliar40_10;

//    @Column(name = "DDMODELO")
//    @Auxiliar("9038")
//    @Ebiz("40_2")
//    private String model;
//    @Column(name = "DDSERIE")
//    @Auxiliar("9009")
//    @Ebiz("40_3")
//    private String serial;
//    @Column(name = "DDCOLOR")
//    @Auxiliar("9041")
//    @Ebiz("40_4")
//    private String color;
//    @Column(name = "DDASIENTO")
//    @Auxiliar("9020")
//    @Ebiz("40_5")
//    private String sit;
//    @Column(name = "DDCHASIS")
//    @Auxiliar("9039")
//    @Ebiz("40_6")
//    private String chasis;
//    @Column(name = "DDVERSION")
//    @Auxiliar("9040")
//    @Ebiz("40_7")
//    private String version;
//    @Column(name = "DDAFAB")
//    @Auxiliar("9042")
//    @Ebiz("40_8")
//    private String factoryYear;
//    @Column(name = "DDAMOD")
//    @Auxiliar("9043")
//    @Ebiz("40_9")
//    private String modelYear;
//    @Column(name = "DDCLASE")
//    @Auxiliar("9044")
//    @Ebiz("40_10")
//    private String clazz;
//    @Column(name = "DDMOTOR")
//    @Auxiliar("9047")
//    @Ebiz("100_1")
//    private String engine;
//    @Column(name = "DDDESC1")
//    @Auxiliar("8999")
//    @Ebiz("500_1")
//    private String description1;
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getNumeroOrdenItem() {
        return numeroOrdenItem;
    }

    public void setNumeroOrdenItem(String numeroOrdenItem) {
        this.numeroOrdenItem = numeroOrdenItem;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductionOrder() {
        return productionOrder;
    }

    public void setProductionOrder(String productionOrder) {
        this.productionOrder = productionOrder;
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

}
