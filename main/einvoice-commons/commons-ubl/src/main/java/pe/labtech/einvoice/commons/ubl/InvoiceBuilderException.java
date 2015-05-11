/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

/**
 *
 * @author Carlos
 */
public class InvoiceBuilderException extends RuntimeException {

    public InvoiceBuilderException() {
    }

    public InvoiceBuilderException(String message) {
        super(message);
    }

    public InvoiceBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoiceBuilderException(Throwable cause) {
        super(cause);
    }

}
