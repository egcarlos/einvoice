/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.recurrent;

/**
 *
 * @author carloseg
 */
public interface RecurrentTask {

    /**
     * Flag that indicates is the process is handling data.
     *
     * @return
     */
    boolean isWorking();

    /**
     * Flag that indicates if the process will handle data.
     *
     * @return
     */
    boolean isEnabled();

    /**
     * Enables or disables the process without needing to rebuild the app.
     *
     * @param enabled
     */
    void setEnabled(boolean enabled);

}
