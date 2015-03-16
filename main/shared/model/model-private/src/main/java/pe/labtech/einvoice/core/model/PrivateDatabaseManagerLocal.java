/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author Carlos
 */
@Local
public interface PrivateDatabaseManagerLocal {

    <T> T seek(Function<EntityManager, T> function);

    void handle(Consumer<EntityManager> consumer);

}
