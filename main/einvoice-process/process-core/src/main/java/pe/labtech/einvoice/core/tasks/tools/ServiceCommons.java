/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.util.Map;
import javax.xml.ws.WebServiceException;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import static pe.labtech.einvoice.commons.model.DocumentStatus.COMPLETED;
import static pe.labtech.einvoice.commons.model.DocumentStatus.ERROR;
import static pe.labtech.einvoice.commons.model.DocumentStatus.NEEDED;
import static pe.labtech.einvoice.commons.model.DocumentStatus.RETRY;
import static pe.labtech.einvoice.commons.model.DocumentStep.DECLARE;
import static pe.labtech.einvoice.commons.model.DocumentStep.FINAL;
import static pe.labtech.einvoice.commons.model.DocumentStep.REPLICATE;
import static pe.labtech.einvoice.commons.model.DocumentStep.SIGN;
import static pe.labtech.einvoice.commons.model.DocumentStep.SYNC;
import pe.labtech.einvoice.commons.xmlsecurity.DigitalSign;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import static pe.labtech.einvoice.core.tasks.tools.DatabaseCommons.hasLocalSignature;
import static pe.labtech.einvoice.core.tasks.tools.DatabaseCommons.mark;
import static pe.labtech.einvoice.core.tasks.tools.Tools.isFinishedInSunat;
import static pe.labtech.einvoice.core.tasks.tools.Tools.serializeMessages;
import static pe.labtech.einvoice.core.tasks.tools.Tools.wasReplicatedBefore;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;

/**
 *
 * @author Carlos
 */
public class ServiceCommons {

    //TODO make cache of xml marshaller instances
    public static final Builder BUILDER = new Builder();

    public static final DigitalSign DIGISIGN = new DigitalSign();

    public static DocumentInfo synthDocumentInfo(org.w3c.dom.Document xml) {
        String[] responses = DIGISIGN.getResponses(xml);
        DocumentInfo di = new DocumentInfo();
        di.setHashCode(responses[0]);
        di.setSignatureValue(responses[1]);
        di.setStatus("SIGNED");
        return di;
    }

    public static DocumentInfo signOnlineOrSync(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request) {
        Tools.saveRequest(db, document, request);
        try {
            loader.createEvent(document, document.getStep() + "_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(document, document.getStep() + "_RESPONSE", response);

            Response r = BUILDER.unmarshall(Response.class, response);

            if (Tools.noRecordsFound(r)) {
                handleRestartProcess(loader, document, db);
            } else if (Tools.isInvalid(r)) {
                handleInvalidResponse(loader, document, db, r);
            } else {
                DocumentInfo di = Tools.getDocumentInfo(r);
                controlDocumentInfo(loader, r, di, db, document);
                return di;
            }

        } catch (WebServiceException ex) {
            //Se elimina el flujo normal y se prefiere retornar a SYNC RETRY
            //para reingrear al flujo y sincronizar estados o refirmar
            handleWSException(loader, document, ex, db);
        } catch (RuntimeException ex) {
            loader.markAsError(document.getId(), ex);
        }
        return null;
    }

    public static DocumentInfo replicateXml(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request, byte[] zip) {
        Tools.saveRequest(db, document, request);
        try {
            //TODO mejorar el mensaje del log
            loader.createEvent(document, "REPLICATE_XML_REQUEST", request);
            String response = invoker.replicateXml(request, zip, null);
            loader.createEvent(document, "REPLICATE_XML_RESPONSE", response);

            Response r = BUILDER.unmarshall(Response.class, response);

            if (wasReplicatedBefore(r)) {
                handleWasReplicated(loader, document, db, r);
            } else if (Tools.isInvalid(r)) {
                handleInvalidResponse(loader, document, db, r);
            } else {
                DocumentInfo di = Tools.getDocumentInfo(r);
                controlDocumentInfo(loader, r, di, db, document);
                return di;
            }
        } catch (WebServiceException ex) {
            handleWSException(loader, document, ex, db);
        } catch (RuntimeException ex) {
            loader.markAsError(document.getId(), ex);
        }
        return null;
    }

    public static DocumentInfo declare(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request) {
        try {
            //TODO mejorar el mensaje del log
            loader.createEvent(document, "DECLARE_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(document, "DECLARE_RESPONSE", response);

            Response r = BUILDER.unmarshall(Response.class, response);

            mark(db, document.getId(), SYNC, DocumentStatus.NEEDED, "messages", serializeMessages(r));
            return null;
        } catch (WebServiceException ex) {
            //Se elimina el flujo normal y se prefiere retornar a SYNC RETRY
            //para reingrear al flujo y sincronizar estados o refirmar
            handleWSException(loader, document, ex, db);
            return null;
        } catch (RuntimeException ex) {
            loader.markAsError(document.getId(), ex);
            return null;
        }
    }

    private static void controlDocumentInfo(DocumentLoaderLocal loader, Response r, DocumentInfo di, DatabaseManager db, Document document) {
        if (Tools.wasSignedBefore(di)) {
            handleWasSigned(loader, document, db);
        } else {
            Map<String, String> responses = Tools.describe(di);
            if (Tools.isSigned(di)) {
                if (di.getSunatStatus() == null) {
                    //no hay estado de sunat entonces se presume que hay que declararlo
                    mark(db, document.getId(), DECLARE, NEEDED, responses);
                } else if (isFinishedInSunat(di.getSunatStatus())) {
                    //no hay nada más que hacer... se puede marcar como completado
                    if (di.getSunatStatus().startsWith("AC")) {
                        responses.put("recordStatus", "P");
                    } else {
                        responses.put("recordStatus", "R");
                    }
                    mark(db, document.getId(), FINAL, COMPLETED, responses);
                } else {
                    //tiene estado sunat... eso quiere decir que esta declarado,
                    //pero esta en un estado intermedio entonces debe sincronizar
                    responses.put("recordStatus", "Y");
                    mark(db, document.getId(), SYNC, NEEDED, responses);
                }
            } else {
                handleGeneralError(responses, r, db, document);
            }
        }
    }

    private static void handleWasReplicated(DocumentLoaderLocal loader, Document document, DatabaseManager db, Response r) {
        loader.createEvent(document, "WARN", "Document will synchronize.");
        mark(db, document.getId(), SYNC, NEEDED, "messages", serializeMessages(r));
    }

    private static void handleInvalidResponse(DocumentLoaderLocal loader, Document document, DatabaseManager db, Response r) {
        loader.createEvent(document, "ERROR", "Invalid response structure");
        mark(db, document.getId(), null, ERROR, "messages", serializeMessages(r), "recordStatus", "E");
    }

    private static void handleWasSigned(DocumentLoaderLocal loader, Document document, DatabaseManager db) {
        loader.createEvent(document, "WARN", "Document will synchronize.");
        mark(db, document.getId(), SYNC, NEEDED);
    }

    private static void handleWSException(DocumentLoaderLocal loader, Document document, WebServiceException ex, DatabaseManager db) {
        loader.createEvent(document, "WARN", Tools.exToString(ex, "Document will retry."));
        mark(db, document.getId(), SYNC, RETRY, "messages", "Document will retry.");
    }

    private static void handleRestartProcess(DocumentLoaderLocal loader, Document document, DatabaseManager db) {
        loader.createEvent(document, "WARN", "Document will restart process.");
        if (hasLocalSignature(db, document.getId())) {
            mark(db, document.getId(), REPLICATE, NEEDED, Tools.toMap(String.class, String.class, "messages", "Document not found in platform... resending signed UBL."));
        } else {
            mark(db, document.getId(), SIGN, NEEDED, Tools.toMap(String.class, String.class, "messages", "Document not found in platform... signing and retrying whole process."));
        }
    }

    private static void handleGeneralError(Map<String, String> responses, Response r, DatabaseManager db, Document document) {
        responses.put("recordStatus", "E");
        responses.put("messages", serializeMessages(r));
        mark(db, document.getId(), null, ERROR, responses);
    }

}
