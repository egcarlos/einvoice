/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory;

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

}
