/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import static pe.labtech.einvoice.api.restful.RestTools.*;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;
import pe.labtech.einvoice.commons.entity.BLResponse;
import pe.labtech.einvoice.commons.entity.Detail;
import pe.labtech.einvoice.commons.entity.Header;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.sign.SignTaskLocal;
import pe.labtech.einvoice.replication.cancel.PullCancelTaskLocal;
import pe.labtech.einvoice.replication.invoice.PullInvoiceTaskLocal;
import pe.labtech.einvoice.replication.summary.PullSummaryTaskLocal;
import pe.labtech.einvoice.replicator.entity.CancelDetail;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.SummaryDetail;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;

/**
 *
 * @author Carlos
 */
@Stateless
public class RestHelper implements RestHelperLocal {

    @EJB
    private PublicDatabaseManagerLocal pub;
    @EJB
    private PrivateDatabaseManagerLocal priv;
    @EJB
    private PullInvoiceTaskLocal pullInvoice;
    @EJB
    private PullSummaryTaskLocal pullSummary;
    @EJB
    private PullCancelTaskLocal pullCancel;
    @EJB
    private SignTaskLocal sign;

    @Override
    public DocumentInfo find(String issuerType, String issuerId, String documentType, String documentNumber) {
        //buscar el documento
        DocumentHeaderPK dhp = new DocumentHeaderPK(issuerType, issuerId, documentType, documentNumber);
        DocumentHeader dh = findPublic(DocumentHeader.class, dhp);
        //si no se encuentra retornar missing
        if (dh == null) {
            return invalid(issuerType, issuerId, documentType, documentNumber, "MISSING");
        }
        //buscar el mapeo de datos
        Long id = findDocumentId(issuerType, issuerId, documentType, documentNumber);
        //si no se encuentra retornar invalid
        if (id == null) {
            return invalid(issuerType, issuerId, documentType, documentNumber, "INVALID");
        }
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    @Override
    public DocumentInfo sign(String issuerType, String issuerId, String documentType, String documentNumber, DocumentHeader content) {
        configureId(content, issuerType, issuerId, documentType, documentNumber);
        List<DocumentDetail> details = content.getItem();
        //Se trata de localizar el registro anterior
        DocumentHeader old = findPublic(DocumentHeader.class, content.getId());
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
            cleanOldData(old.getId());
        }
        //grabar los nuevos datos en la tabla
        saveNewData(content);
        //replicar
        pullInvoice.replicate(content, details, "REST-API", "LOADED-WS");
        //firmar
        Long id = findDocumentId(issuerType, issuerId, documentType, documentNumber);
        sign.handle(id);
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    @Override
    public DocumentInfo sign(String issuerType, String issuerId, String documentType, String documentNumber, SummaryHeader content) {
        configureId(content, issuerType, issuerId, documentType, documentNumber);
        List<SummaryDetail> details = content.getItem();
        //Se trata de localizar el registro anterior
        DocumentHeader old = findPublic(DocumentHeader.class, content.getId());
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
            cleanOldData(old.getId());
        }
        //grabar los nuevos datos en la tabla
        saveNewData(content);
        //replicar
        pullSummary.replicate(content, details, "REST-API", "LOADED-WS");
        //firmar
        Long id = findDocumentId(issuerType, issuerId, documentType, documentNumber);
        sign.handle(id);
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    @Override
    public DocumentInfo sign(String issuerType, String issuerId, String documentType, String documentNumber, CancelHeader content) {
        configureId(content, issuerType, issuerId, documentType, documentNumber);
        List<CancelDetail> details = content.getItem();
        //Se trata de localizar el registro anterior
        DocumentHeader old = findPublic(DocumentHeader.class, content.getId());
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
            cleanOldData(old.getId());
        }
        //grabar los nuevos datos en la tabla
        saveNewData(content);
        //replicar
        pullCancel.replicate(content, details, "REST-API", "LOADED-WS");
        //firmar
        Long id = findDocumentId(issuerType, issuerId, documentType, documentNumber);
        sign.handle(id);
        //retornar la respuesta
        return buildDocumentInfo(id);
    }

    private <T> T findPublic(Class<T> clazz, Object id) {
        return pub.seek(e -> e.find(clazz, id));
    }

    private DocumentInfo buildDocumentInfo(Long id) {
        //construir la respuesta
        DocumentInfo di = new DocumentInfo();
        priv
                .handle(e
                        -> e.createQuery(
                                "SELECT R.name, R.value FROM DocumentResponse R WHERE R.document.id = :id",
                                Object[].class
                        )
                        .setParameter("id", id)
                        .getResultList()
                        .forEach(r -> tryset(di, r[0], r[1])));
        return di;
    }

    private Long findDocumentId(String issuerType, String issuerId, String documentType, String documentNumber) {
        //buscar el id del documento interno, si no existe el mapeo no se pudo realizar y hay que devolver invalid
        Long id = priv.seek(e -> e
                .createQuery(
                        "SELECT MAX(D.id) FROM Document D WHERE D.clientId = :clientId AND D.documentType = :documentType AND D.documentNumber = :documentNumber",
                        Long.class
                )
                //issuer type and issuer id are built in order to reflect database
                .setParameter("clientId", issuerType + "-" + issuerId)
                .setParameter("documentType", documentType)
                //for summaries prepend the document type
                .setParameter("documentNumber", (documentType.startsWith("R") ? documentType : "") + documentNumber)
                .getSingleResult());
        return id;
    }

    private <T extends Detail> void saveNewData(Header<T> content) {
        //Grabar los nuevos datos en la BD
        pub.handle(e -> {
            //insertar los nuevos datos
            BLResponse.configureForCreate(content);
            List<T> dd = content.getItem();
            content.setItem(null);
            e.persist(content);
            dd.forEach(d -> e.persist(d));
        });
    }

    private void cleanOldData(DocumentHeaderPK id) {
        //si no hay hash se puede volver a generar la firma
        //se debe borrar las existencias anteriores
        pub.handle(e -> {
            e
                    .createQuery(
                            "DELETE FROM DocumentDetail D WHERE D.id.tipoDocumentoEmisor = :tde AND D.id.numeroDocumentoEmisor = :nde AND D.id.tipoDocumento = :td AND D.id.serieNumero = :sn"
                    )
                    .setParameter("tde", id.getTipoDocumentoEmisor())
                    .setParameter("nde", id.getNumeroDocumentoEmisor())
                    .setParameter("td", id.getTipoDocumento())
                    .setParameter("sn", id.getSerieNumero())
                    .executeUpdate();
            e
                    .createQuery(
                            "DELETE FROM DocumentHeader D WHERE D.id = :id"
                    )
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

}
