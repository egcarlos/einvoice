/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;

/**
 * REST Web Service
 *
 * @author Carlos
 */
@Stateless
@LocalBean
@Path("{issuerType}/{issuerId}")
@TransactionManagement(TransactionManagementType.BEAN)
public class InvoiceResource {

    @EJB
    private RestHelperLocal help;

    /**
     * Creates a new instance of GenericResource
     */
    public InvoiceResource() {
    }

    @GET
    @Path("{documentType}/{documentNumber}")
    @Produces("application/xml")
    public DocumentInfo get(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentType") String documentType,
            @PathParam("documentNumber") String documentNumber) {
        if (documentType.startsWith("R")) {
            documentNumber = documentType + "-" + documentNumber;
        }
        return help.find(issuerType, issuerId, documentType, documentNumber);
    }

    /**
     *
     *
     * @param issuerType
     * @param issuerId
     * @param documentNumber
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("01/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo sign_01(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            //            @PathParam("documentType") String documentType,
            @PathParam("documentNumber") String documentNumber,
            DocumentHeader content
    ) {
        Logger.getLogger(this.getClass().getName()).info("sign_01");
        return help.sign(issuerType, issuerId, "01", documentNumber, content);
    }

    /**
     *
     *
     * @param issuerType
     * @param issuerId
     * @param documentNumber
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("03/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo sign_03(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            //            @PathParam("documentType") String documentType,
            @PathParam("documentNumber") String documentNumber,
            DocumentHeader content
    ) {
        Logger.getLogger(this.getClass().getName()).info("sign_03");
        return help.sign(issuerType, issuerId, "03", documentNumber, content);
    }

    /**
     *
     *
     * @param issuerType
     * @param issuerId
     * @param documentNumber
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("07/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo sign_07(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentNumber") String documentNumber,
            DocumentHeader content
    ) {
        Logger.getLogger(this.getClass().getName()).info("sign_07");
        return help.sign(issuerType, issuerId, "07", documentNumber, content);
    }

    /**
     *
     *
     * @param issuerType
     * @param issuerId
     * @param documentNumber
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("08/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo sign_08(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentNumber") String documentNumber,
            DocumentHeader content
    ) {
        Logger.getLogger(this.getClass().getName()).info("sign_08");
        return help.sign(issuerType, issuerId, "08", documentNumber, content);
    }

    @PUT
    @Path("RC/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo sign_RC(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentNumber") String documentNumber,
            SummaryHeader content
    ) {
        Logger.getLogger(this.getClass().getName()).info("sign_RC");
        content.setInHabilitado("1");
        return help.sign(issuerType, issuerId, "RC", documentNumber, content);
    }

    @PUT
    @Path("RA/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo sign_RA(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentNumber") String documentNumber,
            CancelHeader content
    ) {
        Logger.getLogger(this.getClass().getName()).info("sign_RA");
        content.setInHabilitado("1");
        return help.sign(issuerType, issuerId, "RA", documentNumber, content);
    }
}
