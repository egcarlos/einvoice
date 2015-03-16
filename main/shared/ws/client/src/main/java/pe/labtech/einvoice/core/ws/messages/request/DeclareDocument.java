package pe.labtech.einvoice.core.ws.messages.request;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Describe un comando de declaración de documentos.
 *
 * @author Labtech (info@labtech.pe)
 * @version 1.00
 * @since jan 30 2015
 */
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
