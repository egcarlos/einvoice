/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.model;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Resumen",
        propOrder = {
            "indicador",
            "tipoDocumentoEmisor",
            "numeroDocumentoEmisor",
            "resumenId",
            "fechaEmisionComprobante",
            "fechaGeneracionResumen",
            "razonSocialEmisor",
            "correoEmisor",
            "resumenTipo",
            "inHabilitado",
            "items"
        })
@XmlRootElement(name = "Resumen")
public class Summary {

    //TODO change language???
    public String indicador;
    public String numeroDocumentoEmisor;
    public String tipoDocumentoEmisor;
    private String resumenId;
    private Date fechaEmisionComprobante;
    private Date fechaGeneracionResumen;
    public String razonSocialEmisor;
    public String correoEmisor;
    public String resumenTipo;
    public String inHabilitado;
    @XmlElement(name = "ResumenItem", nillable = true)
    List<SummaryItem> items;

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getNumeroDocumentoEmisor() {
        return numeroDocumentoEmisor;
    }

    public void setNumeroDocumentoEmisor(String numeroDocumentoEmisor) {
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
    }

    public String getTipoDocumentoEmisor() {
        return tipoDocumentoEmisor;
    }

    public void setTipoDocumentoEmisor(String tipoDocumentoEmisor) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
    }

    public String getResumenId() {
        return resumenId;
    }

    public void setResumenId(String resumenId) {
        this.resumenId = resumenId;
    }

    public Date getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(Date fechaEmisionComprobante) {
        this.fechaEmisionComprobante = fechaEmisionComprobante;
    }

    public Date getFechaGeneracionResumen() {
        return fechaGeneracionResumen;
    }

    public void setFechaGeneracionResumen(Date fechaGeneracionResumen) {
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

    public List<SummaryItem> getItems() {
        return items;
    }

    public void setItems(List<SummaryItem> items) {
        this.items = items;
    }

}
