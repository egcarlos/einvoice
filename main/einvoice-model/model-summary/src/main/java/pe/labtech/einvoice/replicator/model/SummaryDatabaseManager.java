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
@Database("summary")
@Stateless
public class SummaryDatabaseManager extends DatabaseManagerImpl implements SummaryDatabaseManagerLocal {

    @PersistenceContext(unitName = "summary_PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
