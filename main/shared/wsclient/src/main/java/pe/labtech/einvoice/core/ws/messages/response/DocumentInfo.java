/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "documentType",
    "documentNumber",
    "status",
    "documentStatus",
    "sunatStatus",
    "signTime",
    "declareTime",
    "signatureValue",
    "emisionTime",
    "message",
    "sunatMessage",
    "messages",
    "hashCode",
    "barCodeFileUrl",
    "pdfFileUrl",
    "xmlFileSignUrl",
    "tipoDocumentoEmisor",
    "numeroDocumentoEmisor",
    "tipoDocumentoAdquiriente",
    "numeroDocumentoAdquiriente",
    "razonSocialAdquiriente",
    "razonSocialEmisor"
})
public class DocumentInfo {

    private String status;
    private String message;

    //datos generales del documento
    private String documentType;
    private String documentNumber;
    private String documentStatus;

    //DATOS DEL EMISOR DE LA FACTURA
    private String tipoDocumentoEmisor;
    private String numeroDocumentoEmisor;

    private String razonSocialEmisor;

    //DATOS DEL RECEPTOR DE LA FACTURA    
    private String tipoDocumentoAdquiriente;
    private String numeroDocumentoAdquiriente;
    private String razonSocialAdquiriente;

    //fechas de trazabilidad del documento
    private String emisionTime;
    private String signTime;
    private String declareTime;

    //datos de la firma digital
    private String signatureValue;
    private String hashCode;

    //estado en el portal de sunat
    private String sunatStatus;
    private String sunatMessage;

    //DOCUMENTOS DERIVADOS DEL PROCESO
    private String barCodeFileUrl;
    private String pdfFileUrl;
    private String xmlFileSignUrl;

    //MENSAJES DEL PROCESO Y VALIDACIONES
    private List<ResponseMessage> messages;

    public DocumentInfo() {
    }

    /**
     *
     * @param status
     * @param message
     * @param documentType
     * @param documentNumber
     * @param documentStatus
     * @param tipoDocumentoEmisor
     * @param numeroDocumentoEmisor
     * @param razonSocialEmisor
     * @param tipoDocumentoAdquiriente
     * @param numeroDocumentoAdquiriente
     * @param razonSocialAdquiriente
     * @param emisionTime
     * @param signTime
     * @param declareTime
     * @param signatureValue
     * @param hashCode
     * @param sunatStatus
     * @param sunatMessage
     * @param barCodeFileUrl
     * @param pdfFileUrl
     * @param xmlFileSignUrl
     * @param messages
     */
    public DocumentInfo(String status, String message, String documentType, String documentNumber, String documentStatus, String tipoDocumentoEmisor, String numeroDocumentoEmisor, String razonSocialEmisor, String tipoDocumentoAdquiriente, String numeroDocumentoAdquiriente, String razonSocialAdquiriente, String emisionTime, String signTime, String declareTime, String signatureValue, String hashCode, String sunatStatus, String sunatMessage, String barCodeFileUrl, String pdfFileUrl, String xmlFileSignUrl, List<ResponseMessage> messages) {
        this.status = status;
        this.message = message;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.documentStatus = documentStatus;
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
        this.razonSocialEmisor = razonSocialEmisor;
        this.tipoDocumentoAdquiriente = tipoDocumentoAdquiriente;
        this.numeroDocumentoAdquiriente = numeroDocumentoAdquiriente;
        this.razonSocialAdquiriente = razonSocialAdquiriente;
        this.emisionTime = emisionTime;
        this.signTime = signTime;
        this.declareTime = declareTime;
        this.signatureValue = signatureValue;
        this.hashCode = hashCode;
        this.sunatStatus = sunatStatus;
        this.sunatMessage = sunatMessage;
        this.barCodeFileUrl = barCodeFileUrl;
        this.pdfFileUrl = pdfFileUrl;
        this.xmlFileSignUrl = xmlFileSignUrl;
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "typeDocument")
    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @XmlElement(name = "idDocument")
    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @XmlElement(name = "statusDocument")
    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

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

    public String getRazonSocialEmisor() {
        return razonSocialEmisor;
    }

    public void setRazonSocialEmisor(String razonSocialEmisor) {
        this.razonSocialEmisor = razonSocialEmisor;
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

    public String getEmisionTime() {
        return emisionTime;
    }

    public void setEmisionTime(String emisionTime) {
        this.emisionTime = emisionTime;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getDeclareTime() {
        return declareTime;
    }

    public void setDeclareTime(String declareTime) {
        this.declareTime = declareTime;
    }

    public String getSignatureValue() {
        return signatureValue;
    }

    public void setSignatureValue(String signatureValue) {
        this.signatureValue = signatureValue;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    @XmlElement(name = "statusSunat")
    public String getSunatStatus() {
        return sunatStatus;
    }

    public void setSunatStatus(String sunatStatus) {
        this.sunatStatus = sunatStatus;
    }

    @XmlElement(name = "messageSunat")
    public String getSunatMessage() {
        return sunatMessage;
    }

    public void setSunatMessage(String sunatMessage) {
        this.sunatMessage = sunatMessage;
    }

    public String getBarCodeFileUrl() {
        return barCodeFileUrl;
    }

    public void setBarCodeFileUrl(String barCodeFileUrl) {
        this.barCodeFileUrl = barCodeFileUrl;
    }

    public String getPdfFileUrl() {
        return pdfFileUrl;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public String getXmlFileSignUrl() {
        return xmlFileSignUrl;
    }

    public void setXmlFileSignUrl(String xmlFileSignUrl) {
        this.xmlFileSignUrl = xmlFileSignUrl;
    }

    public List<ResponseMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ResponseMessage> messages) {
        this.messages = messages;
    }

}
