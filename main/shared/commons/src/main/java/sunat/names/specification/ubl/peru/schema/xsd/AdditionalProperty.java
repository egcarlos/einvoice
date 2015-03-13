/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sunat.names.specification.ubl.peru.schema.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueType;

/**
 *
 * @author Carlos
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalProperty {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private IDType id;
    @XmlElement(name = "Value", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private ValueType value;

    public AdditionalProperty() {
    }

    public AdditionalProperty(String id, String value) {
        this(id, value, null);
    }

    public AdditionalProperty(String id, String value, String language) {
        this.id = new IDType();
        this.id.setValue(id);

        this.value = new ValueType();
        this.value.setValue(value);
        this.value.setLanguageID(language);
    }

    public IDType getId() {
        return id;
    }

    public void setId(IDType id) {
        this.id = id;
    }

    public ValueType getValue() {
        return value;
    }

    public void setValue(ValueType value) {
        this.value = value;
    }

}
