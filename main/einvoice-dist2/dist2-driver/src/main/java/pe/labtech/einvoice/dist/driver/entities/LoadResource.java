/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.dist.driver.entities;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Carlos Echeverria
 */
@Path("load")
public class LoadResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoadResource
     */
    public LoadResource() {
    }

    /**
     * Retrieves representation of an instance of
     * pe.labtech.einvoice.dist.driver.entities.LoadResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String launch() {
        return "<loading />";
    }
}
