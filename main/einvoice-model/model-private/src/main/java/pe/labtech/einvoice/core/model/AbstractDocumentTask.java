/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.util.function.UnaryOperator;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.commons.recurrent.RecurrentTask;
import pe.labtech.einvoice.commons.model.DatabaseManager;

/**
 *
 * @author Carlos
 */
public abstract class AbstractDocumentTask extends AbstractRecurrentTask<Long> implements RecurrentTask {

    protected String action;
    protected String oldStep;
    protected String oldStatus;
    protected String newStep;
    protected String newStatus;
    protected DatabaseManager db;
    protected UnaryOperator<TypedQuery<Long>> uo;

    @Override
    public void init() {
        super.init();
        this.findTasks = () -> RecurrentHelper.lookup(db, oldStep, oldStatus, uo);
        this.tryLock = t -> RecurrentHelper.lock(db, t, oldStep, oldStatus, newStep, newStatus);
        this.getId = t -> RecurrentHelper.buildId(t, action);
    }

}
