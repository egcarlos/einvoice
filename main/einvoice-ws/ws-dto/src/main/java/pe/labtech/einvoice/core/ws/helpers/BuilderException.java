/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.helpers;

/**
 * Excepción de tipo de tiempo de ejecución emitida en los métodos de utilidad
 * de la clase builder.
 *
 * @author carloseg
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
