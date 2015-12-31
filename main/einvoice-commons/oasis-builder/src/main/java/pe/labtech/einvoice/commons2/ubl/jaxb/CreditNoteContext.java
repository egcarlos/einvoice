/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.ObjectFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import pe.labtech.einvoice.commons2.ubl.model.CreditNotePrefixMapper;
import pe.labtech.einvoice.commons2.ubl.model.InvoicePrefixMapper;

/**
 *
 * @author Carlos Echeverria
 */
public class CreditNoteContext extends AbstractContextManager<CreditNoteType> implements ContextManager<CreditNoteType> {

    public static final ContextManager<CreditNoteType> reference = new CreditNoteContext();
    private static final ObjectFactory factory = new ObjectFactory();

    protected CreditNoteContext() {
        super(CreditNoteType.class, (CreditNoteType t) -> factory.createCreditNote(t));
    }

    @Override
    public Marshaller getMarshaller(String charsetName) {
        Marshaller m = super.getMarshaller(charsetName);
        try {
            m.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new CreditNotePrefixMapper());
        } catch (PropertyException ex) {
            throw new RuntimeException(ex);
        }
        return m;
    }

}
