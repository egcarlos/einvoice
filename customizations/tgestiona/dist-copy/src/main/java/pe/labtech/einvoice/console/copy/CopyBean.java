/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.copy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import pe.labtech.einvoice.process.download.CopyOnDemandLocal;

/**
 *
 * @author Carlos
 */
@Stateless
public class CopyBean implements CopyBeanLocal {

    @EJB
    CopyOnDemandLocal ref;

    @Override
    public void invoke() {
        ref.invoke();
    }

}
