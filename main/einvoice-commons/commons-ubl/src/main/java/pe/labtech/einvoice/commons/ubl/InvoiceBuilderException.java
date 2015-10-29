/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ubl;

/**
* Clase InvoiceBuilderException.
*
* @author Labtech S.R.L. (info@labtech.pe)
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
