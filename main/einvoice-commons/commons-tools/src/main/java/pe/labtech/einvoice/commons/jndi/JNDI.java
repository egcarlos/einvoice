/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.jndi;

import java.util.Map;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import javax.mail.Session;

/**
 *
 * @author Carlos Echeverria
 */
public class JNDI {

    private static JNDI me;
    private final InitialContext ic;
    private final Map<String, Object> cache;

    static {
        try {
            me = new JNDI();
        } catch (NamingException se) {
            throw new RuntimeException(se);
        }
    }

    private JNDI() throws NamingException {
        ic = new InitialContext();
        cache = new ConcurrentHashMap<>();
    }

    public static JNDI getInstance() {
        return me;
    }

    public <T> T lookup(String jndiName) {
        Object cachedObj = cache.get(jndiName);
        if (cachedObj == null) {
            try {
                cachedObj = ic.lookup(jndiName);
                cache.put(jndiName, cachedObj);
            } catch (NamingException ex) {
                Logger.getLogger(JNDI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (T) cachedObj;
    }
}
