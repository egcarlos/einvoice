/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.security;

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
public class SecurityManager implements Serializable {

    @Inject
    private SecurityBeanLocal bean;

    private String entityId;

    private String password;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void loadEntity() {
        bean.updateSecurityPassword(entityId, password);
    }

    public void loadJKS() {
        bean.updateStorePassword(entityId, password);
    }
}
