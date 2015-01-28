/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.request;

import pe.labtech.einvoice.core.ws.model.Summary;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"parameters", "documents"})
@XmlRootElement(name = "SignOnLineSummaryCmd")
public class SignSummary {

    @XmlAttribute(name = "declare-sunat", required = false)
    protected String declareSunat;
    @XmlAttribute(required = false)
    protected String replicate;
    @XmlAttribute(required = false)
    protected String output;
    @XmlElement(name = "parameter", nillable = true)
    List<CommandParameter> parameters;
    @XmlElement(name = "documento", nillable = true)
    List<Summary> documents;

    public SignSummary() {
    }

    public SignSummary(String declareSunat, String replicate, String output, List<CommandParameter> parameters, List<Summary> documents) {
        this.declareSunat = declareSunat;
        this.replicate = replicate;
        this.output = output;
        this.parameters = parameters;
        this.documents = documents;
    }

    public String getDeclareSunat() {
        return declareSunat;
    }

    public void setDeclareSunat(String declareSunat) {
        this.declareSunat = declareSunat;
    }

    public String getReplicate() {
        return replicate;
    }

    public void setReplicate(String replicate) {
        this.replicate = replicate;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<CommandParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<CommandParameter> parameters) {
        this.parameters = parameters;
    }

    public List<Summary> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Summary> documents) {
        this.documents = documents;
    }

}
