package pe.labtech.einvoice.core.ws.messages.request;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Describe al comando de consulta de documentos.
 *
 * @author Labtech (info@labtech.pe)
 * @version 1.00
 * @since jan 30 2015
 */
@XmlRootElement(name = "ConsultCmd")
public class QueryDocument {

    private String output;

    private List<CommandParameter> parameters;

    public QueryDocument() {
    }

    public QueryDocument(String output, List<CommandParameter> parameters) {
        this.output = output;
        this.parameters = parameters;
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

}
