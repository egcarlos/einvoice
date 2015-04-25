/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

/**
 *
 * @author Carlos
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

    public abstract EntityManager getEntityManager();
}
