/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.driver.ss;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import pe.labtech.einvoice.commons.jndi.JNDI;

/**
 * REST Web Service
 *
 * @author Carlos Echeverria
 */
@Path("save")
public class SaveDocumentResource {

    /**
     * Creates a new instance of AResource
     */
    public SaveDocumentResource() {
    }

    /**
     * PUT method for updating or creating an instance of AResource
     *
     * @param responses representation for the resource
     */
    @PUT
    @Consumes("application/json")
    public void putXml(final HashMap<String, String> responses) {
        final String tde = responses.get("tde");
        final String nde = responses.get("nde");
        final String td = responses.get("td");
        final String sn = responses.get("sn");

        String oss = JNDI.getInstance().lookup("java:global/einvoice/config/oss");
        String dss = JNDI.getInstance().lookup("java:global/einvoice/config/dss");
        ExecutorService executor = JNDI.getInstance().lookup("java:global/einvoice/executor/replicate");

        //adjust responses according to patterns
        if ("yes".equals(oss) && sn.startsWith("B") && responses.containsKey("bl_estadoProceso")) {
            String status = responses.get("bl_estadoProceso");
            status = status.replace("PE_02", "PE_09");
            responses.put("bl_estadoProceso", status);
        }

        //
        if ("yes".equals(dss) && responses.containsKey("bl_estadoRegistro")) {
            String s = responses.get("bl_estadoRegistro");
            if ("NALP".contains(s)) {
                //noop
            } else if ("R".contains(s)) {
                s = "P";
            } else {
                s = "L";
            }
            responses.put("bl_estadoRegistro", s);
        }

        executor.submit(
                new SaveDocumentTask(tde, nde, td, sn, responses)
        );
    }

}
