/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.xmlsecurity;

/**
* Clase SignatureException.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class SignatureException extends RuntimeException {

    public SignatureException() {
    }

    public SignatureException(String msg) {
        super(msg);
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

}
