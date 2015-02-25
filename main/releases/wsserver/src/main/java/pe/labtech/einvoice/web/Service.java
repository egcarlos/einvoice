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
import pe.labtech.einvoice.core.entity.Document;

/**
 * REST Web Service
 *
 * @author Carlos
 */
@Path("{tipoDocumetoEmisor}/{numeroDocumentoEmisor}")
public class Service {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public Service() {
    }

    @POST
    @Consumes({"application/xml", "text/json"})
    @Produces({"application/xml", "text/json"})
    @Path("{tipoDocumento}/{serieDocumento}")
    public String load(
            @PathParam("tipoDocumetoEmisor") String tipoDocumetoEmisor,
            @PathParam("numeroDocumentoEmisor") String numeroDocumentoEmisor,
            @PathParam("tipoDocumento") String tipoDocumento,
            @PathParam("serieDocumento") String serieDocumento,
            Document document) {
        return null;
    }

}
