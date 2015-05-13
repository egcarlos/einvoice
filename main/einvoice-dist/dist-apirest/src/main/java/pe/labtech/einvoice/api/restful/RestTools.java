/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.api.restful;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.replicator.entity.CancelDetail;
import pe.labtech.einvoice.replicator.entity.CancelDetailPK;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.CancelHeaderPK;
import pe.labtech.einvoice.replicator.entity.DocumentDetail;
import pe.labtech.einvoice.replicator.entity.DocumentDetailPK;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;
import pe.labtech.einvoice.replicator.entity.SummaryDetail;
import pe.labtech.einvoice.replicator.entity.SummaryDetailPK;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeaderPK;

/**
 *
 * @author Carlos
 */
public class RestTools {

    public static DocumentInfo invalid(String issuerType, String issuerId, String documentType, String documentNumber, String status) {
        //sin header setear el estado a missing y esperar
        DocumentInfo di = new DocumentInfo();
        di.setTipoDocumentoEmisor(issuerType);
        di.setNumeroDocumentoEmisor(issuerId);
        di.setDocumentType(documentType);
        di.setDocumentNumber(documentNumber);
        di.setStatus(status);
        return di;
    }

    public static void tryset(DocumentInfo di, Object name, Object value) {
        try {
            PropertyUtils.setProperty(di, name.toString(), value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            //irrelevant since only valid properties will be mapped
            Logger.getLogger(RestHelper.class.getName()).log(Level.FINEST, ex, () -> "Invalid property " + name + " in DocumentInfo");
        }
    }

    public static void configureId(DocumentHeader content, String issuerType, String issuerId, String documentType, String documentNumber) {
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
            item.setId(buildItemId(content.getId(), ++i));
        }
    }

    public static void configureId(SummaryHeader content, String issuerType, String issuerId, String documentType, String documentNumber) {
        content.setId(new SummaryHeaderPK(issuerType, issuerId, documentType + "-" + documentNumber));
        long i = 0l;
        for (SummaryDetail item : content.getItem()) {
            item.setId(buildItemId(content.getId(), ++i));
        }
    }

    public static void configureId(CancelHeader content, String issuerType, String issuerId, String documentType, String documentNumber) {
        content.setId(new CancelHeaderPK(issuerType, issuerId, documentType + "-" + documentNumber));
        long i = 0l;
        for (CancelDetail item : content.getItem()) {
            item.setId(buildItemId(content.getId(), ++i));
        }
    }

    public static DocumentDetailPK buildItemId(DocumentHeaderPK id, long i) {
        DocumentDetailPK pk = new DocumentDetailPK();
        pk.setTipoDocumentoEmisor(id.getTipoDocumentoEmisor());
        pk.setNumeroDocumentoEmisor(id.getNumeroDocumentoEmisor());
        pk.setTipoDocumento(id.getTipoDocumento());
        pk.setSerieNumero(id.getSerieNumero());
        pk.setNumeroOrdenItem("" + i);
        return pk;
    }

    public static SummaryDetailPK buildItemId(SummaryHeaderPK id, long i) {
        SummaryDetailPK pk = new SummaryDetailPK();
        pk.setTipoDocumentoEmisor(id.getTipoDocumentoEmisor());
        pk.setNumeroDocumentoEmisor(id.getNumeroDocumentoEmisor());
        pk.setResumenId(id.getResumenId());
        pk.setNumeroFila("" + i);
        return pk;
    }

    public static CancelDetailPK buildItemId(CancelHeaderPK id, long i) {
        CancelDetailPK pk = new CancelDetailPK();
        pk.setTipoDocumentoEmisor(id.getTipoDocumentoEmisor());
        pk.setNumeroDocumentoEmisor(id.getNumeroDocumentoEmisor());
        pk.setResumenId(id.getResumenId());
        pk.setNumeroFila("" + i);
        return pk;
    }

}
