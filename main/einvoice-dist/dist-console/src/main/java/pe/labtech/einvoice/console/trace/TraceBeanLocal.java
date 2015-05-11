/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.trace;

import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.EventTrace;

/**
 *
 * @author Carlos
 */
@Local
public interface TraceBeanLocal {

    List<EventTrace> search(String query);
}
