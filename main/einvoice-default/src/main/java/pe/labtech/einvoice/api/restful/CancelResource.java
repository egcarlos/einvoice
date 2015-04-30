/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.Path;

/**
 *
 * @author Carlos
 */
@Stateless
@LocalBean
@Path("cancel")
@TransactionManagement(TransactionManagementType.BEAN)
public class CancelResource {
    
}
