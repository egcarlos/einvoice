/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.dist.driver.entities;

import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Carlos Echeverria
 */
@Path("save")
public class SaveResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SaveResource
     */
    public SaveResource() {
    }

    @PUT
    @Consumes("application/json")
    public void putXml(String data) {
        
    }
}
