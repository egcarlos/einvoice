/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.io.StringWriter;
import java.util.List;
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
import javax.xml.bind.JAXB;
import pe.labtech.einvoice.core.tasks.SignTaskLocal;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replication.invoice.PullInvoiceTaskLocal;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import static pe.labtech.einvoice.api.restful.Tools.*;
import pe.labtech.einvoice.replicator.entity.DocumentDetailPK;

/**
 * REST Web Service
 *
 * @author Carlos
 */
@Stateless
@LocalBean
@Path("invoice")
@TransactionManagement(TransactionManagementType.BEAN)
public class InvoiceResource {

    @EJB
    private PullInvoiceTaskLocal pull;
    @EJB
    private SignTaskLocal sign;
    @EJB
    private RestHelperLocal help;

    /**
     * Creates a new instance of GenericResource
     */
    public InvoiceResource() {
    }

    /**
     * Retrieves representation of an instance of
     * pe.labtech.einvoice.api.restful.GenericResource
     *
     * @param issuerType
     * @param issuerId
     * @param documentType
     * @param documentNumber
     * @return an instance of pe.labtech.einvoice.core.ws.model.Document
     */
    @GET
    @Path("{issuerType}/{issuerId}/{documentType}/{documentNumber}")
    @Produces("application/xml")
    public DocumentInfo getXml(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentType") String documentType,
            @PathParam("documentNumber") String documentNumber
    ) {
        //buscar el documento
        DocumentHeaderPK dhp = new DocumentHeaderPK(issuerType, issuerId, documentType, documentNumber);
        DocumentHeader dh = help.findPublic(DocumentHeader.class, dhp);
        //si no se encuentra retornar missing
        if (dh == null) {
            return invalid(issuerType, issuerId, documentType, documentNumber, "MISSING");
        }
        //buscar el mapeo de datos
        Long id = help.findDocumentId(issuerType, issuerId, documentType, documentNumber);
        //si no se encuentra retornar invalid
        if (id == null) {
            return invalid(issuerType, issuerId, documentType, documentNumber, "INVALID");
        }
        //retornar la respuesta
        return help.buildDocumentInfo(id);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param issuerType
     * @param issuerId
     * @param documentType
     * @param documentNumber
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{issuerType}/{issuerId}/{documentType}/{documentNumber}")
    @Consumes({"application/xml"})
    @Produces("application/xml")
    public DocumentInfo putXml(
            @PathParam("issuerType") String issuerType,
            @PathParam("issuerId") String issuerId,
            @PathParam("documentType") String documentType,
            @PathParam("documentNumber") String documentNumber,
            DocumentHeader content
    ) {
        configureId(content, issuerType, issuerId, documentType, documentNumber);
        List<DocumentDetail> details = content.getItem();

        //Se trata de localizar el registro anterior
        DocumentHeader old = help.findPublic(DocumentHeader.class, content.getId());
        //criterio si el anterior existe y el hash es valido hacer retorno de corto
        if (old != null && old.getBl_hashFirma() != null && !old.getBl_hashFirma().isEmpty()) {
            return invalid(
                    issuerType,
                    issuerId,
                    documentType,
                    documentNumber,
                    "DUPLICATED"
            );
        }
        //si el anterior existe pero el hash esta vacio limpiar los datos
        if (old != null) {
            help.cleanOldData(old.getId());
        }

        //grabar los nuevos datos en la tabla
        help.saveNewData(content);

        //replicar
        pull.replicate(content, details, "PULL", "LOADED-WS");

        //firmar
        Long id = help.findDocumentId(issuerType, issuerId, documentType, documentNumber);
        sign.handle(id);

        //retornar la respuesta
        return help.buildDocumentInfo(id);
    }

    private void configureId(DocumentHeader content, String issuerType, String issuerId, String documentType, String documentNumber) {
        //Se arma el identificador del registro
        //se descarta cualquiera que se haya enviado en el contenido
        content.setId(
                new DocumentHeaderPK(
                        issuerType,
                        issuerId,
                        documentType,
                        documentNumber
                )
        );
        long i = 0l;
        for (DocumentDetail item : content.getItem()) {
            item.setId(buildDocumentDetailPK(content.getId(), ++i));
        }
    }

    private DocumentDetailPK buildDocumentDetailPK(DocumentHeaderPK id, long i) {
        DocumentDetailPK pk = new DocumentDetailPK();
        pk.setTipoDocumentoEmisor(id.getTipoDocumentoEmisor());
        pk.setNumeroDocumentoEmisor(id.getNumeroDocumentoEmisor());
        pk.setTipoDocumento(id.getTipoDocumento());
        pk.setSerieNumero(id.getSerieNumero());
        pk.setNumeroOrdenItem("" + i);
        return pk;
    }
}
