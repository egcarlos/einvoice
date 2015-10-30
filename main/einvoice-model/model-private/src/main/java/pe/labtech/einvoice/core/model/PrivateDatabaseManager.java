/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.commons.model.Database;
import pe.labtech.einvoice.commons.model.DatabaseManagerImpl;

/**
 * Clase PrivateDatabaseManager.
 * 
* @author Labtech S.R.L. (info@labtech.pe)
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
