/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import pe.labtech.einvoice.commons2.ubl.model.AdditionalInformationPrefixMapper;
import pe.labtech.einvoice.commons2.ubl.model.InvoicePrefixMapper;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalInformationType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.ObjectFactory;

/**
 *
 * @author Carlos Echeverria
 */
public class AdditionalInformationContext extends AbstractContextManager<AdditionalInformationType> implements ContextManager<AdditionalInformationType> {

    public static final ContextManager<AdditionalInformationType> reference = new AdditionalInformationContext();
    private static final ObjectFactory factory = new ObjectFactory();

    public AdditionalInformationContext() {
        super(AdditionalInformationType.class, i -> factory.createAdditionalInformation(i));
    }

    @Override
    public Marshaller getMarshaller(String charsetName) {
        Marshaller m = super.getMarshaller(charsetName);
        try {
            m.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new AdditionalInformationPrefixMapper());
        } catch (PropertyException ex) {
            throw new RuntimeException(ex);
        }
        return m;
    }
}
