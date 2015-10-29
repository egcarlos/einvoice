/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.replicator.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pe.labtech.einvoice.commons.model.Database;
import pe.labtech.einvoice.commons.model.DatabaseManagerImpl;

/**
* Clase SummaryDatabaseManager
*
* @author Labtech S.R.L. (info@labtech.pe)
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
