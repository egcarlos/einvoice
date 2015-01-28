/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.request;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = "PublishCmd")
public class PublishDocument {

    private String declareSunat;

    private List<CommandParameter> parameters;

    public PublishDocument() {
    }

    public PublishDocument(String declareSunat, List<CommandParameter> parameters) {
        this.declareSunat = declareSunat;
        this.parameters = parameters;
    }

    @XmlAttribute(name = "declare-sunat")
    public String getDeclareSunat() {
        return declareSunat;
    }

    public void setDeclareSunat(String declareSunat) {
        this.declareSunat = declareSunat;
    }

    @XmlElement(name = "parameter")
    public List<CommandParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<CommandParameter> parameters) {
        this.parameters = parameters;
    }

}
