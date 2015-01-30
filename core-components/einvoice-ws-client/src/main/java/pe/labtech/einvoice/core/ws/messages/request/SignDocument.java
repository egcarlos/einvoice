package pe.labtech.einvoice.core.ws.messages.request;

import pe.labtech.einvoice.core.ws.model.Document;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Describe al comando de firma de documentos.
 *
 * @author Labtech (info@labtech.pe)
 * @version 1.00
 * @since jan 30 2015
 */
@XmlRootElement(name = "SignOnLineCmd")
@XmlType(propOrder = {
    "declareSunat",
    "declareDirectSunat",
    "publish",
    "output",
    "parameters",
    "documents"
})
public class SignDocument {

    private String output;
    private String publish;
    private String declareSunat;
    private String declareDirectSunat;
    private List<CommandParameter> parameters;
    private List<Document> documents;

    public SignDocument() {
    }

    public SignDocument(String output, String publish, String declareSunat, String declareDirectSunat, List<CommandParameter> parameters, List<Document> documents) {
        this.output = output;
        this.publish = publish;
        this.declareSunat = declareSunat;
        this.declareDirectSunat = declareDirectSunat;
        this.parameters = parameters;
        this.documents = documents;
    }

    @XmlAttribute(name = "declare-sunat")
    public String getDeclareSunat() {
        return declareSunat;
    }

    public void setDeclareSunat(String declareSunat) {
        this.declareSunat = declareSunat;
    }

    @XmlAttribute(name = "declare-direct-sunat")
    public String getDeclareDirectSunat() {
        return declareDirectSunat;
    }

    public void setDeclareDirectSunat(String declareDirectSunat) {
        this.declareDirectSunat = declareDirectSunat;
    }

    @XmlAttribute(name = "publish")
    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    @XmlAttribute(name = "output")
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @XmlElement(name = "parameter")
    public List<CommandParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<CommandParameter> parameters) {
        this.parameters = parameters;
    }

    @XmlElement(name = "documento")
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

}
