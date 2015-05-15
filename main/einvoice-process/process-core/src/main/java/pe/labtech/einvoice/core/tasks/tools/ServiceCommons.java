/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.xml.ws.WebServiceException;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.commons.model.DocumentStatus;
import static pe.labtech.einvoice.commons.model.DocumentStatus.NEEDED;
import static pe.labtech.einvoice.commons.model.DocumentStatus.RETRY;
import pe.labtech.einvoice.commons.model.DocumentStep;
import static pe.labtech.einvoice.commons.model.DocumentStep.SIGN;
import static pe.labtech.einvoice.commons.model.DocumentStep.SYNC;
import pe.labtech.einvoice.commons.xmlsecurity.DigitalSign;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import static pe.labtech.einvoice.core.tasks.tools.DatabaseCommons.mark;
import static pe.labtech.einvoice.core.tasks.tools.DatabaseCommons.markAsRetry;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;

/**
 *
 * @author Carlos
 */
public class ServiceCommons {

    public static final Builder BUILDER = new Builder();

    public static final DigitalSign DIGISIGN = new DigitalSign();

    /**
     * Synthesise a document info object from a signed XML document.
     *
     * @param xml signed xml document
     * @return fake DocumentInfo
     */
    public static DocumentInfo synthDocumentInfo(org.w3c.dom.Document xml) {
        String[] responses = DIGISIGN.getResponses(xml);
        DocumentInfo di = new DocumentInfo();
        di.setHashCode(responses[0]);
        di.setSignatureValue(responses[1]);
        di.setStatus("SIGNED");
        return di;
    }

    /**
     * Executes the command sign online or sync in the einvoice platform.
     *
     * @param db database reference
     * @param loader loader reference
     * @param invoker ws port
     * @param document documento to handle
     * @param request request to send
     * @return
     */
    public static DocumentInfo signOnlineOrSync(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request) {
        Tools.saveRequest(db, document, request);
        try {
            //TODO mejorar el mensaje del log
            loader.createEvent(document, "SIGN_OR_SYNC_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(document, "SIGN_OR_SYNC_RESPONSE", response);

            Response r = BUILDER.unmarshall(Response.class, response);

            //este caso corresponde a una inicialización inválida
            if (Tools.noRecordsFound(r)) {
                //Este caso se agrega para considerar un registro que tuvo una
                //solicitud de replicación o firma pero no retorno respuesta y
                //no se grabó en el servidor
                loader.createEvent(document, "WARN", "Document will restart process.");
                mark(db, document.getId(), SIGN, NEEDED, Tools.toMap(String.class, String.class, "messages", "Document not found in platform... signing and retrying whole process."));
                return null;
            }
            if (Tools.isInvalid(r)) {
                loader.createEvent(document, "ERROR", "Invalid response structure");
                loader.markAsError(document.getId());
                return null;
            }

            DocumentInfo di = Tools.getDocumentInfo(r);
            controlDocumentInfo(di, db, document);
            return di;
        } catch (WebServiceException ex) {
            //Se elimina el flujo normal y se prefiere retornar a SYNC RETRY
            //para reingrear al flujo y sincronizar estados o refirmar
            //markAsRetry(db, document, loader, ex);
            loader.createEvent(document, "WARN", Tools.exToString(ex, "Document will retry."));
            mark(db, document.getId(), SYNC, RETRY, Tools.toMap(String.class, String.class, "messages", "Document will retry."));
            return null;
        } catch (RuntimeException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            loader.markAsError(document.getId(), ex);
            return null;
        }
    }

    public static DocumentInfo declare(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request) {
        try {
            //TODO mejorar el mensaje del log
            loader.createEvent(document, "DECLARE_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(document, "DECLARE_RESPONSE", response);

            Response r = BUILDER.unmarshall(Response.class, response);

            mark(db, document.getId(), DocumentStep.SYNC, DocumentStatus.NEEDED, null);
            return null;
        } catch (WebServiceException ex) {
            markAsRetry(db, document, loader, ex);
            return null;
        } catch (RuntimeException ex) {
            loader.markAsError(document.getId(), ex);
            return null;
        }
    }

    public static DocumentInfo replicateXml(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request, byte[] zip) {
        Tools.saveRequest(db, document, request);
        try {
            //TODO mejorar el mensaje del log
            loader.createEvent(document, "REPLICATE_XML_REQUEST", request);
            String response = invoker.replicateXml(request, zip, null);
            loader.createEvent(document, "REPLICATE_XML_RESPONSE", response);

            Response r = BUILDER.unmarshall(Response.class, response);

            //este caso corresponde a una inicialización inválida
            if (Tools.isInvalid(r)) {
                loader.createEvent(document, "ERROR", "Invalid response structure");
                loader.markAsError(document.getId());
                return null;
            }
            DocumentInfo di = Tools.getDocumentInfo(r);
            controlDocumentInfo(di, db, document);
            return di;
        } catch (WebServiceException ex) {
            //Se elimina el flujo normal y se prefiere retornar a SYNC RETRY
            //para reingrear al flujo y sincronizar estados o refirmar
            //markAsRetry(db, document, loader, ex);
            loader.createEvent(document, "WARN", Tools.exToString(ex, "Document will retry."));
            mark(db, document.getId(), SYNC, RETRY, Tools.toMap(String.class, String.class, "messages", "Document will retry."));
            return null;
        } catch (RuntimeException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            loader.markAsError(document.getId(), ex);
            return null;
        }
    }

    public static void controlDocumentInfo(DocumentInfo di, DatabaseManager db, Document document) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (Tools.wasSignedBefore(di)) {
            //si fue firmado con anterioridad entonces hay que sincronizarlo
            //la sincronización pura se da en el step sync
            mark(db, document.getId(), DocumentStep.SYNC, DocumentStatus.NEEDED, null);
        } else {
            Map<String, String> responses = Tools.describe(di);
            if (Tools.isSigned(di)) {
                if (di.getSunatStatus() == null) {
                    mark(db, document.getId(), DocumentStep.DECLARE, DocumentStatus.NEEDED, responses);
                } else if (isFinishedInSunat(di.getSunatStatus())) {
                    //no hay nada más que hacer... se puede marcar como completado
                    responses.put("recordStatus", "P");
                    mark(db, document.getId(), DocumentStep.FINAL, DocumentStatus.COMPLETED, responses);
                } else {
                    //tiene estado sunat... eso quiere decir que esta declarado,
                    //pero esta en un estado intermedio entonces debe sincronizar
                    mark(db, document.getId(), DocumentStep.SYNC, DocumentStatus.NEEDED, responses);
                }
            } else {
                //there was an error while signing mark as error
                //this means an structure error
                mark(db, document.getId(), null, DocumentStatus.SYNCING, responses);
            }
        }
    }

    /**
     * Checks if sunatStatus is not in a terminal state
     *
     * @param sunatStatus
     * @return
     */
    public static boolean isNotFinishedInSunat(String sunatStatus) {
        return sunatStatus == null || !(isFinishedInSunat(sunatStatus));
    }

    /**
     * Checks if the status is finished in sunat
     *
     * @param sunatStatus
     * @return
     */
    public static boolean isFinishedInSunat(String sunatStatus) {
        return sunatStatus.startsWith("RC") || sunatStatus.startsWith("AC");
    }
}
