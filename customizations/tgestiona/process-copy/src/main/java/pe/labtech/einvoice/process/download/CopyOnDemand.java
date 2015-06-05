/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.process.download;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author Carlos Echeverria
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CopyOnDemand implements CopyOnDemandLocal {

    @EJB
    BL_CopyRecurrent recurrent;

    @Override
    @Asynchronous
    public void invoke() {
        recurrent.timeout();
    }

}
