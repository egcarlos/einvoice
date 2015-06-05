/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.copy;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Carlos
 */
@Named
@RequestScoped
public class CopyManager implements Serializable {

    @Inject
    private CopyBeanLocal bean;

    public void invoke() {
        bean.invoke();
    }
}
