/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.commons;

/**
 *
 * @author Carlos
 */
public class DocumentStatus {

    /**
     * Se asocia al estado de creación del documento.
     */
    public static final String LOADING = "LOADING";

    /**
     * Indicador de fin de carga del documento.
     */
    public static final String LOADED = "LOADED";

    /**
     * Indicador de firma en proceso.
     */
    public static final String SIGNING = "SIGNING";

    /**
     * Indicador de documento firmado en linea.
     */
    public static final String SIGNED = "SIGNED_REMOTE";

    /**
     * Indicador de documento firmado de forma local.
     */
    public static final String SIGNED_LOCAL = "SIGNED_LOCAL";

    /**
     * Indicador de declaración en proceso.
     */
    public static final String DECLARING = "DECLARING";

    /**
     * Indicador de documento declarado.
     */
    public static final String DECLARED = "DECLARED";

    /**
     * Indicador de necesidad de sincronización.
     */
    public static final String NEEDED = "NEEDED";

    /**
     * Indicador de sincronización en proceso.
     */
    public static final String SYNCING = "SYNCING";

    /**
     * Indicador de proceso completado.
     */
    public static final String COMPLETED = "COMPLETED";

    /**
     * Indicador de repetir el paso en curso.
     */
    public static final String RETRY = "RETRY";

    /**
     * Marca al actual proceso en error y no se reintentará.
     */
    public static final String ERROR = "ERROR";

}
