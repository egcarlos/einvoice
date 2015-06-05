/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.sunat;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import static pe.labtech.ubl.model.Namespaces.CBC;
import static pe.labtech.ubl.model.Namespaces.SAC;

/**
 *
 * @author Carlos Echeverria
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "LineID",
    "DocumentTypeCode",
    "DocumentSerialID",
    "DocumentNumberID",
    "VoidReasonDescription"
})
public class VoidedDocumentsLine {

    @XmlElement(namespace = CBC)
    private String LineID;
    @XmlElement(namespace = CBC)
    private String DocumentTypeCode;
    @XmlElement(namespace = SAC)
    private String DocumentSerialID;
    @XmlElement(namespace = SAC)
    private String DocumentNumberID;
    @XmlElement(namespace = SAC)
//    @XmlCDATA
    private String VoidReasonDescription;

    public VoidedDocumentsLine() {
    }

    public String getLineID() {
        return LineID;
    }

    public void setLineID(String LineID) {
        this.LineID = LineID;
    }

    public String getDocumentTypeCode() {
        return DocumentTypeCode;
    }

    public void setDocumentTypeCode(String DocumentTypeCode) {
        this.DocumentTypeCode = DocumentTypeCode;
    }

    public String getDocumentSerialID() {
        return DocumentSerialID;
    }

    public void setDocumentSerialID(String DocumentSerialID) {
        this.DocumentSerialID = DocumentSerialID;
    }

    public String getDocumentNumberID() {
        return DocumentNumberID;
    }

    public void setDocumentNumberID(String DocumentNumberID) {
        this.DocumentNumberID = DocumentNumberID;
    }

    public String getVoidReasonDescription() {
        return VoidReasonDescription;
    }

    public void setVoidReasonDescription(String VoidReasonDescription) {
        this.VoidReasonDescription = VoidReasonDescription;
    }

}
