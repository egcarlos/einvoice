/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import oasis.names.specification.ubl.schema.xsd.debitnote_2.ObjectFactory;
import oasis.names.specification.ubl.schema.xsd.debitnote_2.DebitNoteType;

/**
 *
 * @author Carlos Echeverria
 */
public class DebitNoteContext extends AbstractContextManager<DebitNoteType> implements ContextManager<DebitNoteType> {

    public static final ContextManager<DebitNoteType> reference = new DebitNoteContext();
    private static final ObjectFactory factory = new ObjectFactory();

    protected DebitNoteContext() {
        super(DebitNoteType.class, i -> factory.createDebitNote(i));
    }

}
