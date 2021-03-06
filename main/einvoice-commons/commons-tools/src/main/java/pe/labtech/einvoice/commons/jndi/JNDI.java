/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.jndi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
* Clase JNDI.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class JNDI {

    private static JNDI me;
    private final InitialContext ic;
    private final Map<String, Object> cache;
    private static final Logger logger = Logger.getLogger(JNDI.class.getName());

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
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
        return (T) cachedObj;
    }
}
