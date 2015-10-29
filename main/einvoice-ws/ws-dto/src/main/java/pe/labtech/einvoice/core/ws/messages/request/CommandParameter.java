/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws.messages.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
* Clase CommandParameter.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
@XmlType(propOrder = {"value", "name"})
public class CommandParameter {

    private String name;
    private String value;

    public CommandParameter() {
    }

    public CommandParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
