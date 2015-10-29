/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.recurrent;

/**
* Clase RecurrentTask.
*
* @author Labtech S.R.L. (info@labtech.pe)
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
