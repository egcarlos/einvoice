/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_EINVOICEHEADER")
public class DocumentHeader implements Serializable {

    @EmbeddedId
    private DocumentHeaderPK id;
    @Column(name = "CTIPDOCUMENTOEMISOR")
    private Character tipoDocumentoEmisor;
    @Size(max = 11)
    @Column(name = "CDOCUMENTOEMISOR", length = 11)
    private String numeroDocumentoEmisor;
    @Size(max = 2)
    @Column(name = "CTIPCOMPROBANTE", length = 2)
    private String tipoDocumento;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTE", length = 13)
    private String serieNumero;

    @Size(max = 100)
    @Column(name = "CRSOCIALEMISOR", length = 100)
    private String razonSocialEmisor;
    @Size(max = 100)
    @Column(name = "CNCOMERCIALEMISOR", length = 100)
    private String nombreComercialEmisor;

    @Size(max = 10)
    @Column(name = "CFEMISION", length = 10)
    private String fechaEmision;
    @Size(max = 6)
    @Column(name = "CUBIGEOEMISOR", length = 6)
    private String ubigeoEmisor;
    @Size(max = 100)
    @Column(name = "CDIRECCIONEMISOR", length = 100)
    private String direccionEmisor;
    @Size(max = 25)
    @Column(name = "CURBANIZACIONEMISOR", length = 25)
    private String urbanizacion;
    @Size(max = 30)
    @Column(name = "CDISTRITOEMISOR", length = 30)
    private String distritoEmisor;
    @Size(max = 30)
    @Column(name = "CPROVINCIAEMISOR", length = 30)
    private String provinciaEmisor;
    @Size(max = 30)
    @Column(name = "CDEPARTAMENTOEMISOR", length = 30)
    private String departamentoEmisor;
    @Size(max = 2)
    @Column(name = "CCODPAISEMISOR", length = 2)
    private String paisEmisor;
    @Size(max = 100)
    @Column(name = "CCORREOEMISOR", length = 100)
    private String correoEmisor;
    @Size(max = 2)
    @Column(name = "CTIPNCREDITODEBITO", length = 2)
    private String codigoSerieNumeroAfectado;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTEAFECTO", length = 13)
    private String serieNumeroAfectado;
    @Column(name = "CTIPDOCUMENTOUSUARIO")
    private Character tipoDocumentoAdquiriente;
    @Size(max = 15)
    @Column(name = "CDOCUMENTOUSUARIO", length = 15)
    private String numeroDocumentoAdquiriente;
    @Size(max = 100)
    @Column(name = "CRSOCIALUSUARIO", length = 100)
    private String razonSocialAdquiriente;
    @Size(max = 100)
    @Column(name = "CDIRECCIONUSUARIO", length = 100)
    private String lugarDestino;
    @Size(max = 100)
    @Column(name = "CCORREOUSUARIO", length = 100)
    private String correoAdquiriente;
    @Size(max = 3)
    @Column(name = "CMONEDA", length = 3)
    private String tipoMoneda;
    @Size(max = 500)
    @Column(name = "CMOTNCREDITODEBITO", length = 500)
    private String motivoDocumento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CVVENTAGRAVADA", precision = 19, scale = 2)
    private BigDecimal totalValorVentaNetoOpGravadas;
    @Column(name = "CVVENTANOGRAVADA", precision = 19, scale = 2)
    private BigDecimal totalValorVentaNetoOpNoGravada;
    @Column(name = "CVVENTAEXONERADA", precision = 19, scale = 2)
    private BigDecimal totalValorVentaNetoOpExoneradas;
    @Column(name = "CVVENTAGRATUITA", precision = 19, scale = 2)
    private BigDecimal totalValorVentaNetoOpGratuitas;
    @Column(name = "CSUBTOTAL", precision = 19, scale = 2)
    private BigDecimal subTotal;
    @Column(name = "CIGV", precision = 19, scale = 2)
    private BigDecimal totalIgv;
    @Column(name = "CISC", precision = 19, scale = 2)
    private BigDecimal totalIsc;
    @Column(name = "CTRIBUTO", precision = 19, scale = 2)
    private BigDecimal totalOtrosTributos;
    @Column(name = "CCARGO", precision = 19, scale = 2)
    private BigDecimal totalOtrosCargos;
    @Column(name = "CDESCUENTO", precision = 19, scale = 2)
    private BigDecimal totalDescuentos;
    @Column(name = "CDESCUENTOGLOBAL", precision = 19, scale = 2)
    private BigDecimal descuentosGlobales;
    @Column(name = "CTOTAL", precision = 19, scale = 2)
    private BigDecimal totalVenta;
    @Size(max = 3)
    @Column(name = "CMONEDANCREDITODEBITO", length = 3)
    private String x_tipoMoneda;
    @Size(max = 2)
    @Column(name = "CTIPNCREDITODEBITO1", length = 2)
    private String tipoDocumentoReferenciaPrincipal;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTEAFECTO1", length = 13)
    private String numeroDocumentoReferenciaPrincipal;
    @Size(max = 2)
    @Column(name = "CTIPNCREDITODEBITO2", length = 2)
    private String tipoDocumentoReferenciaCorregido;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTEAFECTO2", length = 13)
    private String numeroDocumentoReferenciaCorregido;
    @Column(name = "CBASEPERCEPCION", precision = 19, scale = 2)
    private BigDecimal baseImponiblePercepcion;
    @Column(name = "CTOTALPERCEPCION", precision = 19, scale = 2)
    private BigDecimal totalPercepcion;
    @Column(name = "CVENTAPERCEPCION", precision = 19, scale = 2)
    private BigDecimal totalVentaConPercepcion;
    @Column(name = "CPORCPERCEPCION", precision = 19, scale = 2)
    private BigDecimal porcentajePercepcion;
    @Column(name = "CTOTALRETENCION", precision = 19, scale = 2)
    private BigDecimal totalRetencion;
    @Column(name = "CPORCRETENCION", precision = 19, scale = 2)
    private BigDecimal porcentajeRetencion;
    @Column(name = "CTOTALDETRACCION", precision = 19, scale = 2)
    private BigDecimal totalDetraccion;
    @Column(name = "CPORCDETRACCION", precision = 19, scale = 2)
    private BigDecimal porcentajeDetraccion;
    @Size(max = 100)
    @Column(name = "CDESCDETRACCION", length = 100)
    private String descripcionDetraccion;
    @Column(name = "CVALORDETRACCION", precision = 19, scale = 2)
    private BigDecimal valorReferencialDetraccion;
    @Column(name = "CBONIFICACION", precision = 19, scale = 2)
    private BigDecimal totalBonificacion;
    @Column(name = "CHABILITADO")
    private Character inHabilitado;
    @Column(name = "CANTICIPO", precision = 19, scale = 2)
    private BigDecimal totalDocumentoAnticipo;
    @Size(max = 200)
    @Transient//siempre 1000:monto en letras
    private String codigoLeyenda_1;
    @Column(name = "CLEY1", length = 200)
    private String textoLeyenda_1;
    @Transient//siempre 1002:mensaje de gratuita
    private String codigoLeyenda_2;
    @Size(max = 200)
    @Column(name = "CLEY2", length = 200)
    private String textoLeyenda_2;
    @Transient//siempre 3000:detracciones
    private String codigoLeyenda_3;
    @Size(max = 200)
    @Column(name = "CLEY3", length = 200)
    private String textoLeyenda_3;
    @Transient//siempre 3001:detracciones
    private String codigoLeyenda_4;
    @Size(max = 200)
    @Column(name = "CLEY4", length = 200)
    private String textoLeyenda_4;

    @Size(max = 40)
    @Column(name = "CAUX1", length = 40)
    private String textoAuxiliar40_1;
    //indicador de factura es anticipo padre
    @Size(max = 40)
    @Column(name = "CAUX8", length = 40)
    private String textoAuxiliar40_2;
    //numero de ERP para uso futuro
    @Transient//siempre 9512:numero de ERP
    private String codigoAuxiliar40_3;
    @Size(max = 40)
    @Column(name = "CAUX9", length = 40)
    private String textoAuxiliar40_3;
    @Size(max = 40)
    @Column(name = "CAUX10", length = 40)
    private String textoAuxiliar40_4;

    //bloque de observaciones
    @Transient//siempre 9606:Observaciones
    private String codigoAuxiliar250_1;
    @Size(max = 250)
    @Column(name = "CAUX36", length = 250)
    private String textoAuxiliar250_1;
    @Transient//siempre 9618:Observaciones
    private String codigoAuxiliar250_2;
    @Size(max = 250)
    @Column(name = "CAUX19", length = 250)
    private String textoAuxiliar250_2;
    @Transient//siempre 9998:Observaciones
    private String codigoAuxiliar250_3;
    @Size(max = 250)
    @Column(name = "CAUX20", length = 250)
    private String textoAuxiliar250_3;

    @Transient//siempre 9171:Centro de Costo
    private String codigoAuxiliar100_1;
    @Size(max = 100)
    @Column(name = "CAUX21", length = 100)
    private String textoAuxiliar100_1;

    @Column(name = "CESTADO")
    private Character bl_estadoRegistro;
    @Size(max = 10)
    @Column(name = "CFECHAAUTORIZACION", length = 10)
    private String x_fechaAutorizacion;
    @Size(max = 8)
    @Column(name = "CHORAAUTORIZACION", length = 8)
    private String x_horaAutorizacion;
    @Size(max = 100)
    @Column(name = "CNUMEROAUTORIZACION", length = 100)
    private String x_numeroAutorizacion;
    @Size(max = 1000)
    @Column(name = "CRUTAPDF", length = 1000)
    private String bl_urlpdf;
    @Size(max = 1000)
    @Column(name = "CRUTAXML", length = 1000)
    private String bl_urlxmlubl;
    @Size(max = 2000)
    @Column(name = "LG_FIRMA", length = 2000)
    private String bl_firma;
    @Size(max = 2000)
    @Column(name = "LG_FIRMA_HASH", length = 2000)
    private String bl_hashFirma;
    @Size(max = 2000)
    @Column(name = "LG_LOAD_MESSAGES", length = 2000)
    private String bl_mensaje;
    @Column(name = "LG_PROCESS_STATUS")
    private Character x_recordStatus;
    @Size(max = 20)
    @Column(name = "LG_RECORD_STATUS", length = 20)
    private String bl_estadoProceso;
    @Size(max = 2000)
    @Column(name = "LG_SERVICE_RESPONSE", length = 2000)
    private String bl_mensajeSunat;

    public DocumentHeader() {
    }

    public DocumentHeaderPK getId() {
        return id;
    }

    public void setId(DocumentHeaderPK id) {
        this.id = id;
    }

    public Character getTipoDocumentoEmisor() {
        return tipoDocumentoEmisor;
    }

    public void setTipoDocumentoEmisor(Character tipoDocumentoEmisor) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
    }

    public String getNumeroDocumentoEmisor() {
        return numeroDocumentoEmisor;
    }

    public void setNumeroDocumentoEmisor(String numeroDocumentoEmisor) {
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
    }

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

    public String getRazonSocialEmisor() {
        return razonSocialEmisor;
    }

    public void setRazonSocialEmisor(String razonSocialEmisor) {
        this.razonSocialEmisor = razonSocialEmisor;
    }

    public String getNombreComercialEmisor() {
        return nombreComercialEmisor;
    }

    public void setNombreComercialEmisor(String nombreComercialEmisor) {
        this.nombreComercialEmisor = nombreComercialEmisor;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getUbigeoEmisor() {
        return ubigeoEmisor;
    }

    public void setUbigeoEmisor(String ubigeoEmisor) {
        this.ubigeoEmisor = ubigeoEmisor;
    }

    public String getDireccionEmisor() {
        return direccionEmisor;
    }

    public void setDireccionEmisor(String direccionEmisor) {
        this.direccionEmisor = direccionEmisor;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getDistritoEmisor() {
        return distritoEmisor;
    }

    public void setDistritoEmisor(String distritoEmisor) {
        this.distritoEmisor = distritoEmisor;
    }

    public String getProvinciaEmisor() {
        return provinciaEmisor;
    }

    public void setProvinciaEmisor(String provinciaEmisor) {
        this.provinciaEmisor = provinciaEmisor;
    }

    public String getDepartamentoEmisor() {
        return departamentoEmisor;
    }

    public void setDepartamentoEmisor(String departamentoEmisor) {
        this.departamentoEmisor = departamentoEmisor;
    }

    public String getPaisEmisor() {
        return paisEmisor;
    }

    public void setPaisEmisor(String paisEmisor) {
        this.paisEmisor = paisEmisor;
    }

    public String getCorreoEmisor() {
        return correoEmisor;
    }

    public void setCorreoEmisor(String correoEmisor) {
        this.correoEmisor = correoEmisor;
    }

    public String getCodigoSerieNumeroAfectado() {
        return codigoSerieNumeroAfectado;
    }

    public void setCodigoSerieNumeroAfectado(String codigoSerieNumeroAfectado) {
        this.codigoSerieNumeroAfectado = codigoSerieNumeroAfectado;
    }

    public String getSerieNumeroAfectado() {
        return serieNumeroAfectado;
    }

    public void setSerieNumeroAfectado(String serieNumeroAfectado) {
        this.serieNumeroAfectado = serieNumeroAfectado;
    }

    public Character getTipoDocumentoAdquiriente() {
        return tipoDocumentoAdquiriente;
    }

    public void setTipoDocumentoAdquiriente(Character tipoDocumentoAdquiriente) {
        this.tipoDocumentoAdquiriente = tipoDocumentoAdquiriente;
    }

    public String getNumeroDocumentoAdquiriente() {
        return numeroDocumentoAdquiriente;
    }

    public void setNumeroDocumentoAdquiriente(String numeroDocumentoAdquiriente) {
        this.numeroDocumentoAdquiriente = numeroDocumentoAdquiriente;
    }

    public String getRazonSocialAdquiriente() {
        return razonSocialAdquiriente;
    }

    public void setRazonSocialAdquiriente(String razonSocialAdquiriente) {
        this.razonSocialAdquiriente = razonSocialAdquiriente;
    }

    public String getLugarDestino() {
        return lugarDestino;
    }

    public void setLugarDestino(String lugarDestino) {
        this.lugarDestino = lugarDestino;
    }

    public String getCorreoAdquiriente() {
        return correoAdquiriente;
    }

    public void setCorreoAdquiriente(String correoAdquiriente) {
        this.correoAdquiriente = correoAdquiriente;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getMotivoDocumento() {
        return motivoDocumento;
    }

    public void setMotivoDocumento(String motivoDocumento) {
        this.motivoDocumento = motivoDocumento;
    }

    public BigDecimal getTotalValorVentaNetoOpGravadas() {
        return totalValorVentaNetoOpGravadas;
    }

    public void setTotalValorVentaNetoOpGravadas(BigDecimal totalValorVentaNetoOpGravadas) {
        this.totalValorVentaNetoOpGravadas = totalValorVentaNetoOpGravadas;
    }

    public BigDecimal getTotalValorVentaNetoOpNoGravada() {
        return totalValorVentaNetoOpNoGravada;
    }

    public void setTotalValorVentaNetoOpNoGravada(BigDecimal totalValorVentaNetoOpNoGravada) {
        this.totalValorVentaNetoOpNoGravada = totalValorVentaNetoOpNoGravada;
    }

    public BigDecimal getTotalValorVentaNetoOpExoneradas() {
        return totalValorVentaNetoOpExoneradas;
    }

    public void setTotalValorVentaNetoOpExoneradas(BigDecimal totalValorVentaNetoOpExoneradas) {
        this.totalValorVentaNetoOpExoneradas = totalValorVentaNetoOpExoneradas;
    }

    public BigDecimal getTotalValorVentaNetoOpGratuitas() {
        return totalValorVentaNetoOpGratuitas;
    }

    public void setTotalValorVentaNetoOpGratuitas(BigDecimal totalValorVentaNetoOpGratuitas) {
        this.totalValorVentaNetoOpGratuitas = totalValorVentaNetoOpGratuitas;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalIgv() {
        return totalIgv;
    }

    public void setTotalIgv(BigDecimal totalIgv) {
        this.totalIgv = totalIgv;
    }

    public BigDecimal getTotalIsc() {
        return totalIsc;
    }

    public void setTotalIsc(BigDecimal totalIsc) {
        this.totalIsc = totalIsc;
    }

    public BigDecimal getTotalOtrosTributos() {
        return totalOtrosTributos;
    }

    public void setTotalOtrosTributos(BigDecimal totalOtrosTributos) {
        this.totalOtrosTributos = totalOtrosTributos;
    }

    public BigDecimal getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(BigDecimal totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getDescuentosGlobales() {
        return descuentosGlobales;
    }

    public void setDescuentosGlobales(BigDecimal descuentosGlobales) {
        this.descuentosGlobales = descuentosGlobales;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getX_tipoMoneda() {
        return x_tipoMoneda;
    }

    public void setX_tipoMoneda(String x_tipoMoneda) {
        this.x_tipoMoneda = x_tipoMoneda;
    }

    public String getTipoDocumentoReferenciaPrincipal() {
        return tipoDocumentoReferenciaPrincipal;
    }

    public void setTipoDocumentoReferenciaPrincipal(String tipoDocumentoReferenciaPrincipal) {
        this.tipoDocumentoReferenciaPrincipal = tipoDocumentoReferenciaPrincipal;
    }

    public String getNumeroDocumentoReferenciaPrincipal() {
        return numeroDocumentoReferenciaPrincipal;
    }

    public void setNumeroDocumentoReferenciaPrincipal(String numeroDocumentoReferenciaPrincipal) {
        this.numeroDocumentoReferenciaPrincipal = numeroDocumentoReferenciaPrincipal;
    }

    public String getTipoDocumentoReferenciaCorregido() {
        return tipoDocumentoReferenciaCorregido;
    }

    public void setTipoDocumentoReferenciaCorregido(String tipoDocumentoReferenciaCorregido) {
        this.tipoDocumentoReferenciaCorregido = tipoDocumentoReferenciaCorregido;
    }

    public String getNumeroDocumentoReferenciaCorregido() {
        return numeroDocumentoReferenciaCorregido;
    }

    public void setNumeroDocumentoReferenciaCorregido(String numeroDocumentoReferenciaCorregido) {
        this.numeroDocumentoReferenciaCorregido = numeroDocumentoReferenciaCorregido;
    }

    public BigDecimal getBaseImponiblePercepcion() {
        return baseImponiblePercepcion;
    }

    public void setBaseImponiblePercepcion(BigDecimal baseImponiblePercepcion) {
        this.baseImponiblePercepcion = baseImponiblePercepcion;
    }

    public BigDecimal getTotalPercepcion() {
        return totalPercepcion;
    }

    public void setTotalPercepcion(BigDecimal totalPercepcion) {
        this.totalPercepcion = totalPercepcion;
    }

    public BigDecimal getTotalVentaConPercepcion() {
        return totalVentaConPercepcion;
    }

    public void setTotalVentaConPercepcion(BigDecimal totalVentaConPercepcion) {
        this.totalVentaConPercepcion = totalVentaConPercepcion;
    }

    public BigDecimal getPorcentajePercepcion() {
        return porcentajePercepcion;
    }

    public void setPorcentajePercepcion(BigDecimal porcentajePercepcion) {
        this.porcentajePercepcion = porcentajePercepcion;
    }

    public BigDecimal getTotalRetencion() {
        return totalRetencion;
    }

    public void setTotalRetencion(BigDecimal totalRetencion) {
        this.totalRetencion = totalRetencion;
    }

    public BigDecimal getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public BigDecimal getTotalDetraccion() {
        return totalDetraccion;
    }

    public void setTotalDetraccion(BigDecimal totalDetraccion) {
        this.totalDetraccion = totalDetraccion;
    }

    public BigDecimal getPorcentajeDetraccion() {
        return porcentajeDetraccion;
    }

    public void setPorcentajeDetraccion(BigDecimal porcentajeDetraccion) {
        this.porcentajeDetraccion = porcentajeDetraccion;
    }

    public String getDescripcionDetraccion() {
        return descripcionDetraccion;
    }

    public void setDescripcionDetraccion(String descripcionDetraccion) {
        this.descripcionDetraccion = descripcionDetraccion;
    }

    public BigDecimal getValorReferencialDetraccion() {
        return valorReferencialDetraccion;
    }

    public void setValorReferencialDetraccion(BigDecimal valorReferencialDetraccion) {
        this.valorReferencialDetraccion = valorReferencialDetraccion;
    }

    public BigDecimal getTotalBonificacion() {
        return totalBonificacion;
    }

    public void setTotalBonificacion(BigDecimal totalBonificacion) {
        this.totalBonificacion = totalBonificacion;
    }

    public Character getInHabilitado() {
        return inHabilitado;
    }

    public void setInHabilitado(Character inHabilitado) {
        this.inHabilitado = inHabilitado;
    }

    public BigDecimal getTotalDocumentoAnticipo() {
        return totalDocumentoAnticipo;
    }

    public void setTotalDocumentoAnticipo(BigDecimal totalDocumentoAnticipo) {
        this.totalDocumentoAnticipo = totalDocumentoAnticipo;
    }

    public String getCodigoLeyenda_1() {
        codigoLeyenda_1 = textoLeyenda_1 == null ? null : "1000";
        return codigoLeyenda_1;
    }

    public void setCodigoLeyenda_1(String codigoLeyenda_1) {
        this.codigoLeyenda_1 = codigoLeyenda_1;
    }

    public String getTextoLeyenda_1() {
        return textoLeyenda_1;
    }

    public void setTextoLeyenda_1(String textoLeyenda_1) {
        this.textoLeyenda_1 = textoLeyenda_1;
    }

    public String getCodigoLeyenda_2() {
        codigoLeyenda_2 = textoLeyenda_2 == null ? null : "1002";
        return codigoLeyenda_2;
    }

    public void setCodigoLeyenda_2(String codigoLeyenda_2) {
        this.codigoLeyenda_2 = codigoLeyenda_2;
    }

    public String getTextoLeyenda_2() {
        return textoLeyenda_2;
    }

    public void setTextoLeyenda_2(String textoLeyenda_2) {
        this.textoLeyenda_2 = textoLeyenda_2;
    }

    public String getCodigoLeyenda_3() {
        codigoLeyenda_3 = textoLeyenda_3 == null ? null : "3000";
        return codigoLeyenda_3;
    }

    public void setCodigoLeyenda_3(String codigoLeyenda_3) {
        this.codigoLeyenda_3 = codigoLeyenda_3;
    }

    public String getTextoLeyenda_3() {
        return textoLeyenda_3;
    }

    public void setTextoLeyenda_3(String textoLeyenda_3) {
        this.textoLeyenda_3 = textoLeyenda_3;
    }

    public String getCodigoLeyenda_4() {
        codigoLeyenda_4 = textoLeyenda_4 == null ? null : "3001";
        return codigoLeyenda_4;
    }

    public void setCodigoLeyenda_4(String codigoLeyenda_4) {
        this.codigoLeyenda_4 = codigoLeyenda_4;
    }

    public String getTextoLeyenda_4() {
        return textoLeyenda_4;
    }

    public void setTextoLeyenda_4(String textoLeyenda_4) {
        this.textoLeyenda_4 = textoLeyenda_4;
    }

    public String getTextoAuxiliar40_1() {
        return textoAuxiliar40_1;
    }

    public void setTextoAuxiliar40_1(String textoAuxiliar40_1) {
        this.textoAuxiliar40_1 = textoAuxiliar40_1;
    }

    public String getTextoAuxiliar40_2() {
        return textoAuxiliar40_2;
    }

    public void setTextoAuxiliar40_2(String textoAuxiliar40_2) {
        this.textoAuxiliar40_2 = textoAuxiliar40_2;
    }

    public String getCodigoAuxiliar40_3() {
        codigoAuxiliar40_3 = textoAuxiliar40_3 == null ? null : "9512";
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

    public String getTextoAuxiliar40_4() {
        return textoAuxiliar40_4;
    }

    public void setTextoAuxiliar40_4(String textoAuxiliar40_4) {
        this.textoAuxiliar40_4 = textoAuxiliar40_4;
    }

    public String getCodigoAuxiliar250_1() {
        codigoAuxiliar250_1 = textoAuxiliar250_1 == null ? null : "9606";
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
        codigoAuxiliar250_2 = textoAuxiliar250_2 == null ? null : "9618";
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
        codigoAuxiliar250_3 = textoAuxiliar250_3 == null ? null : "9998";
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

    public String getCodigoAuxiliar100_1() {
        codigoAuxiliar100_1 = textoAuxiliar100_1 == null ? null : "9171";
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
        codigoAuxiliar100_1 = textoAuxiliar100_1 == null ? null : "9420";
        return codigoAuxiliar100_1;
    }

    public void setCodigoAuxiliar100_2(String codigoAuxiliar100_1) {
        this.codigoAuxiliar100_1 = codigoAuxiliar100_1;
    }

    public Character getBl_estadoRegistro() {
        return bl_estadoRegistro;
    }

    public void setBl_estadoRegistro(Character bl_estadoRegistro) {
        this.bl_estadoRegistro = bl_estadoRegistro;
    }

    public String getX_fechaAutorizacion() {
        return x_fechaAutorizacion;
    }

    public void setX_fechaAutorizacion(String x_fechaAutorizacion) {
        this.x_fechaAutorizacion = x_fechaAutorizacion;
    }

    public String getX_horaAutorizacion() {
        return x_horaAutorizacion;
    }

    public void setX_horaAutorizacion(String x_horaAutorizacion) {
        this.x_horaAutorizacion = x_horaAutorizacion;
    }

    public String getX_numeroAutorizacion() {
        return x_numeroAutorizacion;
    }

    public void setX_numeroAutorizacion(String x_numeroAutorizacion) {
        this.x_numeroAutorizacion = x_numeroAutorizacion;
    }

    public String getBl_urlpdf() {
        return bl_urlpdf;
    }

    public void setBl_urlpdf(String bl_urlpdf) {
        this.bl_urlpdf = bl_urlpdf;
    }

    public String getBl_urlxmlubl() {
        return bl_urlxmlubl;
    }

    public void setBl_urlxmlubl(String bl_urlxmlubl) {
        this.bl_urlxmlubl = bl_urlxmlubl;
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

    public String getBl_mensaje() {
        return bl_mensaje;
    }

    public void setBl_mensaje(String bl_mensaje) {
        this.bl_mensaje = bl_mensaje;
    }

    public String getBl_estadoProceso() {
        return bl_estadoProceso;
    }

    public void setBl_estadoProceso(String bl_estadoProceso) {
        this.bl_estadoProceso = bl_estadoProceso;
    }

    public Character getX_recordStatus() {
        return x_recordStatus;
    }

    public void setX_recordStatus(Character x_recordStatus) {
        this.x_recordStatus = x_recordStatus;
    }

    public String getBl_mensajeSunat() {
        return bl_mensajeSunat;
    }

    public void setBl_mensajeSunat(String bl_mensajeSunat) {
        this.bl_mensajeSunat = bl_mensajeSunat;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final DocumentHeader other = (DocumentHeader) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DocumentHeader{" + "tipoDocumentoEmisor=" + tipoDocumentoEmisor + ", numeroDocumentoEmisor=" + numeroDocumentoEmisor + ", tipoDocumento=" + tipoDocumento + ", serieNumero=" + serieNumero + '}';
    }

}
