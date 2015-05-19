package pe.labtech.einvoice.core.ws.messages.request;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Describe al comando de publicación de documentos.
 *
 * @author Labtech (info@labtech.pe)
 * @version 1.00
 * @since jan 30 2015
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
