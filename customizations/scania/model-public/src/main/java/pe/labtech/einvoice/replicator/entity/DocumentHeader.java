/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

//import com.alignet.einvoice.integrator.annotations.Ebiz;
//import com.alignet.einvoice.integrator.annotations.Auxiliar;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_eInvoiceHeader")
@IdClass(DocumentHeaderPK.class)
public class DocumentHeader implements Serializable {

//    @Id
    @Column(name = "SPEDOCTYPE")
    private String tipoDocumentoEmisor;
//    @Id
    @Column(name = "SPEDOCNUM")
    private String numeroDocumentoEmisor;
    @Id
    @Column(name = "DOCTYPE")
    private String tipoDocumento;
    @Id
    @Column(name = "DOCNUM")
    private String serieNumero;

    //datos del emisor
    @Column(name = "SPENAME")
    //@Ebiz("razonSocialEmisor")
    private String razonSocialEmisor;
    @Column(name = "SPENAMEC")
    //@Ebiz("nombreComercialEmisor")
    private String nombreComercialEmisor;
    @Column(name = "SPEADDR")
    //@Ebiz("direccionEmisor")
    private String direccionEmisor;
    @Column(name = "SPEURB")
    private String urbanizacion;
    @Column(name = "SPEDIST")
    private String distritoEmisor;
    @Column(name = "SPECITY")
    private String departamentoEmisor;
    @Column(name = "SPEPROV")
    private String provinciaEmisor;
    @Column(name = "SPECOUNTRY")
    private String paisEmisor;
    @Column(name = "SPEEMAIL")
    private String correoEmisor;
    @Column(name = "SPEUBIGEO")
    private String ubigeoEmisor;

    //reverted document in case of doctype 07 or 08
    @Column(name = "DOCCREDTYPE")
    private String codigoSerieNumeroAfectado;
    @Column(name = "DOCCREDNUM")
    private String serieNumeroAfectado;
    @Column(name = "DOCTYPEMOD")
    private String tipoDocumentoReferenciaPrincipal;
    @Column(name = "DOCNUMMOD")
    private String numeroDocumentoReferenciaPrincipal;

    //datos del cliente
    @Column(name = "CUSTDOCTYPE")
    private String tipoDocumentoAdquiriente;
    @Column(name = "CUSTDOCNUM")
    private String numeroDocumentoAdquiriente;
    @Column(name = "CUSTNAME")
    private String razonSocialAdquiriente;
    @Column(name = "CUSTADDR")
    private String lugarDestino;
    @Column(name = "CUSTEMAIL")
    private String correoAdquiriente;

    //datos de la cabecera    
    @Column(name = "DOCREASON")
    private String motivoDocumento;
    @Column(name = "DOCDATE")
    private String fechaEmision;
    @Column(name = "DOCCURR")
    private String tipoMoneda;
    @Column(name = "DOCNETG")
    private String netAmountSubjectToIVA;
    @Column(name = "DOCNETNG")
    private String totalValorVentaNetoOpNoGravada;
    @Column(name = "DOCNETEX")
    private String totalValorVentaNetoOpExoneradas;
    @Column(name = "DOCNETGRA")
    private String totalValorVentaNetoOpGratuitas;
    @Column(name = "DOCSUBTOT")
    private String subTotal;
    @Column(name = "DOCIVA")
    private String totalIgv;
    @Column(name = "DOCISC")
    private String totalIsc;
    @Column(name = "DOCDISC")
    private String totalDescuentos;
    @Column(name = "DOCGLODISC")
    private String descuentosGlobales;
    @Column(name = "DOCTOTAL")
    private String totalVenta;
    @Column(name = "STATUS")
    private String status;

    @Column(name = "AR_FEC_CAPTURA")
    private String x_trackingCaptureDate;
    @Column(name = "AR_HOR_CAPTURA")
    private String x_trackingCaptureTime;
    @Column(name = "AR_FEC_RESPUESTA")
    private String x_trackingResponseDate;
    @Column(name = "AR_HOR_RESPUESTA")
    private String x_trackingResponseTime;
    @Column(name = "AR_ESTADO")
    private String x_trackingStatus;
    @Column(name = "AR_COD_RESPUESTA")
    private String x_trackingResponseCode;
    @Column(name = "AR_COD_ERROR")
    private String x_trackingErrorCode;
    @Column(name = "AR_MENSAJE_RESPUESTA")
    private String x_trackingResponseMessage;
    @Column(name = "AR_FEC_DECLARACION")
    private String x_trackingIRSDate;
    @Column(name = "AR_FIRMA")
    private String x_trackingSignature;
    @Column(name = "AR_FIRMA_HASH")
    private String x_trackingSignatureHash;
    @Column(name = "AR_COD_SISTEMA")
    private String x_trackingSystemCode;
    @Column(name = "AR_CAMPO_ADIC1")
    @Lob
    private String x_trackingExtraField1;
    @Lob
    @Column(name = "AR_CAMPO_ADIC2")
    private String x_trackingExtraField2;
    @Column(name = "AR_CAMPO_ADIC3")
    @Lob
    private String x_trackingExtraField3;
    @Column(name = "AR_CAMPO_ADIC4")
    @Lob
    private String x_trackingExtraField4;
    @Column(name = "AR_CAMPO_VALIDACION")
    private String x_trackingValidationField;

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

    public String getDepartamentoEmisor() {
        return departamentoEmisor;
    }

    public void setDepartamentoEmisor(String departamentoEmisor) {
        this.departamentoEmisor = departamentoEmisor;
    }

    public String getProvinciaEmisor() {
        return provinciaEmisor;
    }

    public void setProvinciaEmisor(String provinciaEmisor) {
        this.provinciaEmisor = provinciaEmisor;
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

    public String getUbigeoEmisor() {
        return ubigeoEmisor;
    }

    public void setUbigeoEmisor(String ubigeoEmisor) {
        this.ubigeoEmisor = ubigeoEmisor;
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

    public String getTipoDocumentoAdquiriente() {
        return tipoDocumentoAdquiriente;
    }

    public void setTipoDocumentoAdquiriente(String tipoDocumentoAdquiriente) {
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

    public String getMotivoDocumento() {
        return motivoDocumento;
    }

    public void setMotivoDocumento(String motivoDocumento) {
        this.motivoDocumento = motivoDocumento;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getNetAmountSubjectToIVA() {
        return netAmountSubjectToIVA;
    }

    public void setNetAmountSubjectToIVA(String netAmountSubjectToIVA) {
        this.netAmountSubjectToIVA = netAmountSubjectToIVA;
    }

    public String getTotalValorVentaNetoOpNoGravada() {
        return totalValorVentaNetoOpNoGravada;
    }

    public void setTotalValorVentaNetoOpNoGravada(String totalValorVentaNetoOpNoGravada) {
        this.totalValorVentaNetoOpNoGravada = totalValorVentaNetoOpNoGravada;
    }

    public String getTotalValorVentaNetoOpExoneradas() {
        return totalValorVentaNetoOpExoneradas;
    }

    public void setTotalValorVentaNetoOpExoneradas(String totalValorVentaNetoOpExoneradas) {
        this.totalValorVentaNetoOpExoneradas = totalValorVentaNetoOpExoneradas;
    }

    public String getTotalValorVentaNetoOpGratuitas() {
        return totalValorVentaNetoOpGratuitas;
    }

    public void setTotalValorVentaNetoOpGratuitas(String totalValorVentaNetoOpGratuitas) {
        this.totalValorVentaNetoOpGratuitas = totalValorVentaNetoOpGratuitas;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotalIgv() {
        return totalIgv;
    }

    public void setTotalIgv(String totalIgv) {
        this.totalIgv = totalIgv;
    }

    public String getTotalIsc() {
        return totalIsc;
    }

    public void setTotalIsc(String totalIsc) {
        this.totalIsc = totalIsc;
    }

    public String getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(String totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public String getDescuentosGlobales() {
        return descuentosGlobales;
    }

    public void setDescuentosGlobales(String descuentosGlobales) {
        this.descuentosGlobales = descuentosGlobales;
    }

    public String getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(String totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getX_trackingCaptureDate() {
        return x_trackingCaptureDate;
    }

    public void setX_trackingCaptureDate(String x_trackingCaptureDate) {
        this.x_trackingCaptureDate = x_trackingCaptureDate;
    }

    public String getX_trackingCaptureTime() {
        return x_trackingCaptureTime;
    }

    public void setX_trackingCaptureTime(String x_trackingCaptureTime) {
        this.x_trackingCaptureTime = x_trackingCaptureTime;
    }

    public String getX_trackingResponseDate() {
        return x_trackingResponseDate;
    }

    public void setX_trackingResponseDate(String x_trackingResponseDate) {
        this.x_trackingResponseDate = x_trackingResponseDate;
    }

    public String getX_trackingResponseTime() {
        return x_trackingResponseTime;
    }

    public void setX_trackingResponseTime(String x_trackingResponseTime) {
        this.x_trackingResponseTime = x_trackingResponseTime;
    }

    public String getX_trackingStatus() {
        return x_trackingStatus;
    }

    public void setX_trackingStatus(String x_trackingStatus) {
        this.x_trackingStatus = x_trackingStatus;
    }

    public String getX_trackingResponseCode() {
        return x_trackingResponseCode;
    }

    public void setX_trackingResponseCode(String x_trackingResponseCode) {
        this.x_trackingResponseCode = x_trackingResponseCode;
    }

    public String getX_trackingErrorCode() {
        return x_trackingErrorCode;
    }

    public void setX_trackingErrorCode(String x_trackingErrorCode) {
        this.x_trackingErrorCode = x_trackingErrorCode;
    }

    public String getX_trackingResponseMessage() {
        return x_trackingResponseMessage;
    }

    public void setX_trackingResponseMessage(String x_trackingResponseMessage) {
        this.x_trackingResponseMessage = x_trackingResponseMessage;
    }

    public String getX_trackingIRSDate() {
        return x_trackingIRSDate;
    }

    public void setX_trackingIRSDate(String x_trackingIRSDate) {
        this.x_trackingIRSDate = x_trackingIRSDate;
    }

    public String getX_trackingSignature() {
        return x_trackingSignature;
    }

    public void setX_trackingSignature(String x_trackingSignature) {
        this.x_trackingSignature = x_trackingSignature;
    }

    public String getX_trackingSignatureHash() {
        return x_trackingSignatureHash;
    }

    public void setX_trackingSignatureHash(String x_trackingSignatureHash) {
        this.x_trackingSignatureHash = x_trackingSignatureHash;
    }

    public String getX_trackingSystemCode() {
        return x_trackingSystemCode;
    }

    public void setX_trackingSystemCode(String x_trackingSystemCode) {
        this.x_trackingSystemCode = x_trackingSystemCode;
    }

    public String getX_trackingExtraField1() {
        return x_trackingExtraField1;
    }

    public void setX_trackingExtraField1(String x_trackingExtraField1) {
        this.x_trackingExtraField1 = x_trackingExtraField1;
    }

    public String getX_trackingExtraField2() {
        return x_trackingExtraField2;
    }

    public void setX_trackingExtraField2(String x_trackingExtraField2) {
        this.x_trackingExtraField2 = x_trackingExtraField2;
    }

    public String getX_trackingExtraField3() {
        return x_trackingExtraField3;
    }

    public void setX_trackingExtraField3(String x_trackingExtraField3) {
        this.x_trackingExtraField3 = x_trackingExtraField3;
    }

    public String getX_trackingExtraField4() {
        return x_trackingExtraField4;
    }

    public void setX_trackingExtraField4(String x_trackingExtraField4) {
        this.x_trackingExtraField4 = x_trackingExtraField4;
    }

    public String getX_trackingValidationField() {
        return x_trackingValidationField;
    }

    public void setX_trackingValidationField(String x_trackingValidationField) {
        this.x_trackingValidationField = x_trackingValidationField;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.tipoDocumentoEmisor);
        hash = 17 * hash + Objects.hashCode(this.numeroDocumentoEmisor);
        hash = 17 * hash + Objects.hashCode(this.tipoDocumento);
        hash = 17 * hash + Objects.hashCode(this.serieNumero);
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
        if (!Objects.equals(this.tipoDocumentoEmisor, other.tipoDocumentoEmisor)) {
            return false;
        }
        if (!Objects.equals(this.numeroDocumentoEmisor, other.numeroDocumentoEmisor)) {
            return false;
        }
        if (!Objects.equals(this.tipoDocumento, other.tipoDocumento)) {
            return false;
        }
        if (!Objects.equals(this.serieNumero, other.serieNumero)) {
            return false;
        }
        return true;
    }
}
