/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.recurrent;

import java.text.MessageFormat;

/**
 *
 * @author carloseg
 */
public interface RecurrentTask {

    boolean isWorking();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    static String buildTaskId(String clientId, String documentType, String documentNumber, String taskName) {
        return MessageFormat.format("{0}-{1}-{2}[{3}]", clientId, documentType, documentNumber, taskName);
    }

    static String buildTaskId(String clientId, String documentType, String documentNumber, String taskName, String qualifier) {
        return MessageFormat.format("{0}-{1}-{2}[{3}:{4}]", clientId, documentType, documentNumber, taskName, qualifier);
    }

}
