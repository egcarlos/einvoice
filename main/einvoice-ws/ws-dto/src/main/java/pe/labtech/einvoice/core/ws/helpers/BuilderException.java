/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.ws.helpers;

/**
 * Clase BuilderException.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
public class BuilderException extends RuntimeException {

    public BuilderException() {
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

}
