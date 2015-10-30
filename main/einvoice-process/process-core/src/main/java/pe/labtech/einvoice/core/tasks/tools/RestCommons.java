/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.core.tasks.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import pe.labtech.einvoice.commons.jndi.JNDI;

/**
* Clase RestCommons.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class RestCommons {

    private static final Client client = ClientBuilder.newClient();
    private static final Map<String, WebTarget> targets = new ConcurrentHashMap<>();

    public static WebTarget driverTarget(String driverId) {
        WebTarget target = targets.get(driverId);
        if (target == null) {
            String targetURL = JNDI.getInstance().lookup("java:global/einvoice/remote/" + driverId);
            if (targetURL == null) {
                return null;
            }
            target = client.target(targetURL);
            targets.putIfAbsent(driverId, target);
        }
        return target;
    }

    public static WebTarget target(String targetURL) {
        WebTarget target = targets.get(targetURL);
        if (target == null) {
            target = client.target(targetURL);
            targets.putIfAbsent(targetURL, target);
        }
        return target;
    }
}
