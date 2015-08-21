/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.dist.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import pe.labtech.einvoice.core.entity.Document;

/**
 * REST Web Service
 *
 * @author Carlos Echeverria
 */
@Path("document")
public class DocumentResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DocumentResource
     */
    public DocumentResource() {
    }

    /**
     * PUT method for updating or creating an instance of DocumentResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putXml(Document content) {
        System.out.println("recibido " + content);
    }
}
