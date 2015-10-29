/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.model;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

/**
* Clase DatabaseManagerImpl.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public abstract class DatabaseManagerImpl implements DatabaseManager {

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T seek(Function<EntityManager, T> function) {
        return function.apply(this.getEntityManager());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void handle(Consumer<EntityManager> consumer) {
        consumer.accept(this.getEntityManager());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> T seekNT(Function<EntityManager, T> function) {
        return function.apply(this.getEntityManager());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void handleNT(Consumer<EntityManager> consumer) {
        consumer.accept(this.getEntityManager());
    }

    public abstract EntityManager getEntityManager();
}
