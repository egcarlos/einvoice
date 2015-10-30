/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.model;

import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Clase AsyncWrapper
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class AsyncWrapper implements AsyncWrapperLocal {

    @Override
    @Asynchronous
    public void perform(Runnable r) {
        r.run();
    }

}
