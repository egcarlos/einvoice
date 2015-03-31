/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons;

/**
 *
 * @author Carlos
 */
public class DocumentStatus {

    /**
     * Representa el estado para los documentos en proceso de ingreso al
     * sistema.
     */
    public static final String PULL = "PULL";
    /**
     * Representa el estado para los documentos en proceso de firma.
     */
    public static final String SIGN = "SIGN";
    /**
     * Representa el estado para los documentos en proceso de declaración.
     */
    public static final String DECLARE = "DECLARE";

}
