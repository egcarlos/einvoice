/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.web;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Carlos
 */
@Path("{ruc}")
public class EinvoiceWebDriver {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public EinvoiceWebDriver() {
    }

    /**
     * Retrieves representation of an instance of
     * pe.labtech.einvoice.web.GenericResource
     *
     * @param ruc
     * @param tipo
     * @param serie
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("application/xml")
    @Path("{tipo}/{serie}/sign")
    public String sign(
            @PathParam("ruc") String ruc,
            @PathParam("tipo") String tipo,
            @PathParam("serie") String serie) {
         
    }

    @POST
    @Produces("application/xml")
    @Path("{tipo}/{serie}/sign")
    public String declare(
            @PathParam("ruc") String ruc,
            @PathParam("tipo") String tipo,
            @PathParam("serie") String serie) {
        
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
