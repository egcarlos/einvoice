/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.model;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityManager;

/**
* Clase DatabaseManager.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public interface DatabaseManager {

    <T> T seek(Function<EntityManager, T> function);

    void handle(Consumer<EntityManager> consumer);

    <T> T seekNT(Function<EntityManager, T> function);

    void handleNT(Consumer<EntityManager> consumer);

}
