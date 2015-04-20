/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pe.labtech.einvoice.core.entity.DocumentResponse;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.commons.BLResponse;
import pe.labtech.einvoice.replicator.commons.Header;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.model.PublicDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Stateless
public class RestHelper implements RestHelperLocal {

    @EJB
    private PublicDatabaseManagerLocal pub;
    @EJB
    private PublicDatabaseManagerLocal priv;

    @Override
    public DocumentInfo buildDocumentInfo(Long id) {
        //construir la respuesta
        DocumentInfo di = new DocumentInfo();
        priv
                .handle(e
                        -> e.createQuery(
                                "SELECT R FROM DocumentResponse R WHERE R.document.id = :id",
                                DocumentResponse.class
                        )
                        .setParameter("id", id)
                        .getResultList()
                        .forEach(r -> RestHelperLocal.tryset(di, r.getName(), r.getValue())));
        return di;
    }

    @Override
    public Long findDocumentId(String issuerType, String issuerId, String documentType, String documentNumber) {
        //buscar el id del documento interno, si no existe el mapeo no se pudo realizar y hay que devolver invalid
        Long id = priv.seek(e -> e
                .createQuery(
                        "SELECT MAX(D.id) FROM Document D WHERE D.clientId = :clientId AND D.documentType = :documentType AND D.documentNumber = :documentNumber",
                        Long.class
                )
                .setParameter("clientId", issuerType + "-" + issuerId)
                .setParameter("documentType", documentType)
                .setParameter("documentNumber", documentNumber)
                .getSingleResult());
        return id;
    }

    @Override
    public <T> T findPublic(Class<T> clazz, Object id) {
        return pub.seek(e -> e.find(clazz, id));
    }

    @Override
    public void saveNewData(Header content) {
        //Grabar los nuevos datos en la BD
        pub.handle(e -> {
            //insertar los nuevos datos
            BLResponse.configureForCreate(content);
            List<DocumentDetail> dd = content.getItem();
            content.setItem(null);
            e.persist(content);
            dd.forEach(d -> e.persist(d));
        });
    }

    @Override
    public void cleanOldData(DocumentHeaderPK id) {
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
