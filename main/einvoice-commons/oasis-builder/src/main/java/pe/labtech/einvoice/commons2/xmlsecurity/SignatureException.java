/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.xmlsecurity;

/**
 *
 * @author Carlos
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
