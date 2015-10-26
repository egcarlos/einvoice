/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.ObjectFactory;

/**
 *
 * @author Carlos Echeverria
 */
public class CreditNoteContext extends AbstractContextManager<CreditNoteType> implements ContextManager<CreditNoteType> {

    public static final ContextManager<CreditNoteType> reference = new CreditNoteContext();
    private static final ObjectFactory factory = new ObjectFactory();

    protected CreditNoteContext() {
        super(CreditNoteType.class, i -> factory.createCreditNote(i));
    }

}
