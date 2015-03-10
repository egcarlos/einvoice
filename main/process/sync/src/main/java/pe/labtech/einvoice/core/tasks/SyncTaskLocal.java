/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;

/**
 *
 * @author Carlos
 */
@Local
public interface SyncTaskLocal {

    void handle(Document document);

}
