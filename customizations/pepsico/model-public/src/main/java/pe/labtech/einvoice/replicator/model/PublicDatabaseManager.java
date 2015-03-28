/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
@Stateless
public class PublicDatabaseManager implements PublicDatabaseManagerLocal {

    @PersistenceContext(unitName = "replicator_PU")
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T seek(Function<EntityManager, T> function) {
        return function.apply(em);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void handle(Consumer<EntityManager> consumer) {
        consumer.accept(em);
    }
}
