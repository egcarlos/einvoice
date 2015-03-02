/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Carlos
 */
public abstract class AbstractSeeker<H, P, D> implements Seeker<H, P, D> {

    protected final Class<H> headerClass;
    protected final Class<P> keyClass;
    protected final Class<D> detailClass;

    public AbstractSeeker(Class<H> headerClass, Class<P> keyClass, Class<D> detailClass) {
        this.headerClass = headerClass;
        this.keyClass = keyClass;
        this.detailClass = detailClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<P> pullHeaders(String status) {
        TypedQuery<P> q = getEntityManager().createNamedQuery(
                headerClass.getSimpleName() + ".findIdWithState",
                keyClass
        ).setParameter("status", status);
        return q.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean markForProcess(P id, String oldstatus, String newstatus) {
        Query q = getEntityManager().createNamedQuery(headerClass.getSimpleName() + ".safeUpdateStatus");
        q.setParameter("newstatus", newstatus);
        q.setParameter("oldstatus", oldstatus);
        q.setParameter("id", id);
        int i = q.executeUpdate();
        return i == 1;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public H findById(P id) {
        return getEntityManager().find(headerClass, id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<D> findDetails(P id) {
        TypedQuery<D> q = getEntityManager().createNamedQuery(detailClass.getSimpleName() + ".findForHeaderId", detailClass);
        setDetailParameters(q, id);
        return q.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void update(P id, Map<String, String> responses) {
        if (responses.isEmpty()) {
            return;
        }
        List<String> args = responses.keySet().stream()
                .map(k -> "o." + k + " = :" + k)
                .collect(Collectors.toList());
        String query = MessageFormat.format(
                "UPDATE {0} o SET {1} WHERE o.id = :id",
                headerClass.getSimpleName(),
                StringUtils.join(args, ", ")
        );

        Query q = getEntityManager().createQuery(query);
        responses.forEach((k, v) -> q.setParameter(k, v));
        q.executeUpdate();
    }

    protected abstract void setDetailParameters(TypedQuery<D> q, P id);

}
