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
public class DocumentStep {

    /**
     * Representa el estado de entrada de un documento en la base de datos. Se
     * representa el estado utilizado por el proceso de replicación. Los sub
     * valores válidos son:
     * <ul>
     * <li>LOADING</li>
     * <li>LOADED</li>
     * </ul>
     */
    public static final String PULL = "PULL";

    /**
     * Representa el estado de entrada de un documento en la base de datos. Se
     * representa el estado utilizado por el proceso de replicación. Los sub
     * valores válidos son:
     * <ul>
     * <li>SIGNING</li>
     * <li>SIGNED</li>
     * <li>SIGNED-LOCAL</li>
     * <li>RETRY</li>
     * <li>ERROR</li>
     * </ul>
     */
    public static final String SIGN = "SIGN";

    /**
     * Representa el estado de entrada de un documento en la base de datos. Se
     * representa el estado utilizado por el proceso de replicación. Los sub
     * valores válidos son:
     * <ul>
     * <li>DECLARING</li>
     * <li>DECLARED</li>
     * <li>RETRY</li>
     * <li>ERROR</li>
     * </ul>
     */
    public static final String DECALRE = "DECLARE";

    /**
     * Representa el estado de detección para sincronización de documentos. Los
     * sub valores válidos son:
     * <ul>
     * <li>NEEDED</li>
     * <li>SYNCING</li>
     * </ul>
     */
    public static final String SYNC = "SYNC";

    /**
     * Representa el estado de fin de procesos. Al marcar un documento con este
     * estado se indica que ningún proceso recurrente lo va a tomar para
     * proceso. Los sub valores válidos son:
     * <ul>
     * <li>COMPLETED</li>
     * </ul>
     */
    public static final String FINAL = "FINAL";

}
