/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import javax.ejb.Local;

/**
 *
 * @author Carlos
 */
@Local
public interface DeclareTaskLocal {

    void handle(Long id);
}
