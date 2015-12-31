/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import pe.labtech.einvoice.commons2.ubl.model.InvoicePrefixMapper;

/**
 *
 * @author Carlos Echeverria
 */
public class InvoiceContext extends AbstractContextManager<InvoiceType> implements ContextManager<InvoiceType> {

    public static final ContextManager<InvoiceType> reference = new InvoiceContext();
    private static final ObjectFactory factory = new ObjectFactory();

    protected InvoiceContext() {
        super(InvoiceType.class, i -> factory.createInvoice(i));
    }

    @Override
    public Marshaller getMarshaller(String charsetName) {
        Marshaller m = super.getMarshaller(charsetName);
        try {
            m.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new InvoicePrefixMapper());
        } catch (PropertyException ex) {
            throw new RuntimeException(ex);
        }
        return m;
    }

}
