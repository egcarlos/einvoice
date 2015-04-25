/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
@Database("public")
@Stateless
public class PublicDatabaseManager extends DatabaseManagerImpl implements PublicDatabaseManagerLocal {

    @PersistenceContext(unitName = "replicator_PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
