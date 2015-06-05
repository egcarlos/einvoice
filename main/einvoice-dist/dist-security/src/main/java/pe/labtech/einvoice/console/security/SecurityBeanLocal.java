/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.security;

import javax.ejb.Local;

/**
 *
 * @author Carlos
 */
@Local
public interface SecurityBeanLocal {

    boolean updateSecurityPassword(String id, String password);

    boolean updateStorePassword(String id, String password);

}
