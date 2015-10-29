package pe.labtech.einvoice.core.ws.messages.request;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeclareCmd")
public class DeclareDocument {

    private List<CommandParameter> parameters;

    public DeclareDocument() {
    }

    public DeclareDocument(List<CommandParameter> parameters) {
        this.parameters = parameters;
    }

    @XmlElement(name = "parameter")
    public List<CommandParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<CommandParameter> parameters) {
        this.parameters = parameters;
    }

}
