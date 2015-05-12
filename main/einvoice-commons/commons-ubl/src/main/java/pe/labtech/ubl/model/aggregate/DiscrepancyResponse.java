/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscrepancyResponse {

    @XmlElement(namespace = Namespaces.CBC)
    private String ReferenceID;
    @XmlElement(namespace = Namespaces.CBC)
    private String ResponseCode;
    @XmlElement(namespace = Namespaces.CBC)
    private String Description;

    public DiscrepancyResponse() {
    }

    public DiscrepancyResponse(String ReferenceID, String ResponseCode, String Description) {
        this.ReferenceID = ReferenceID;
        this.ResponseCode = ResponseCode;
        this.Description = Description;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String ReferenceID) {
        this.ReferenceID = ReferenceID;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

}
