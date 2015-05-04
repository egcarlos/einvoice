/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.commons.model.Database;
import pe.labtech.einvoice.commons.model.DatabaseManagerImpl;

/**
 *
 * @author Carlos
 */
@Database("private")
@Stateless
public class PrivateDatabaseManager extends DatabaseManagerImpl implements PrivateDatabaseManagerLocal {

    @PersistenceContext(unitName = "einvoice_PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
