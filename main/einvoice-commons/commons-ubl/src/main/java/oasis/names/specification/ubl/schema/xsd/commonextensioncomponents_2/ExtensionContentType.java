//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.03.12 a las 09:30:39 PM COT 
//
package oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalInformationType;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalInformation;

/**
 * <p>
 * Clase Java para ExtensionContentType complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="ExtensionContentType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;any processContents='skip' minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "ExtensionContentType",
        propOrder = {
            "additionalInformationSunat",
            "additionalInformationCBC"
        }
)
public class ExtensionContentType {

    @XmlElement(name = "AdditionalInformation", namespace = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1")
    private AdditionalInformation additionalInformationSunat;

    @XmlElement(name = "AdditionalInformation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private AdditionalInformationType additionalInformationCBC;

    public ExtensionContentType() {
    }

    public AdditionalInformation getAdditionalInformationSunat() {
        return additionalInformationSunat;
    }

    public void setAdditionalInformationSunat(AdditionalInformation additionalInformationSunat) {
        this.additionalInformationSunat = additionalInformationSunat;
    }

    public AdditionalInformationType getAdditionalInformationCBC() {
        return additionalInformationCBC;
    }

    public void setAdditionalInformationCBC(AdditionalInformationType additionalInformationCBC) {
        this.additionalInformationCBC = additionalInformationCBC;
    }

}
