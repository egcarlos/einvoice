/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 * TODO llevar a un paquete de utilitarios y ordenar.
 *
 * @author Carlos
 */
@Stateless
public class AsyncWrapper implements AsyncWrapperLocal {

    @Override
    @Asynchronous
    public void perform(Runnable r) {
        r.run();
    }

}
