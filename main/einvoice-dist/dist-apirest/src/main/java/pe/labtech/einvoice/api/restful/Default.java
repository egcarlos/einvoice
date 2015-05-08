/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Carlos
 */
@Path("/")
public class Default {

    @GET
    public Response defaultResponse() throws MalformedURLException, URISyntaxException {
        return Response
                .temporaryRedirect(new URL("https://www.dropbox.com/s/g56cqqonurrshbe/Integraci%C3%B3n%20por%20servicio%20-%20esquema.docx?dl=0").toURI())
                .build();
    }

}
