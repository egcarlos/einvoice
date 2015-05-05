/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.DATA_LOADED;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.core.ws.messages.response.ResponseMessage;

/**
 *
 * @author Carlos
 */
public class Tools {

    public static void saveRequest(DatabaseManager db, Document document, String request) {
        //save request
        db.handle(e -> {
            List<DocumentData> list = e.createNamedQuery("DocumentData.findById", DocumentData.class)
                    .setParameter("document", document)
                    .setParameter("name", "signXml")
                    .getResultList();

            if (list.isEmpty()) {
                final DocumentData data = new DocumentData();
                data.setDocument(document);
                data.setName("signXml");
                data.setData(request.getBytes());
                data.setStatus(DATA_LOADED);
                data.setReplicate(Boolean.TRUE);
                e.persist(data);
            } else {
                final DocumentData data = list.get(0);
                data.setData(request.getBytes());
                data.setReplicate(Boolean.TRUE);
                data.setStatus(DATA_LOADED);
            }
        });
    }

    public static String exToString(Exception ex, String... headers) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            Arrays.stream(headers).forEach(s -> pw.println(s));
            pw.println();
            pw.println(ex.getMessage());
            pw.println();
            pw.println("Stack Trace");
            ex.printStackTrace(pw);
            pw.flush();
            pw.close();
            return sw.toString();
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    public static Map<String, String> describe(DocumentInfo di) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> responses = BeanUtils.describe(di).entrySet().stream()
                .filter(e -> !e.getKey().equals("class"))
                .filter(e -> e.getValue() != null)
                .collect(
                        Collectors.toMap(
                                e -> e.getKey(),
                                e -> e.getValue()
                        )
                );
        return responses;
    }

    public static String buildClientID(String clientId) {
        if (clientId.contains("-")) {
            return clientId.split("-")[1];
        }
        return clientId;
    }

    //<editor-fold defaultstate="collapsed" desc="metodos para interpretar las respuestas del servicio">
    public static DocumentInfo getDocumentInfo(Response response) {
        return response.getResponseBody().getXml().getDocuments().get(0);
    }

    public static boolean isSigned(DocumentInfo di) {
        return "SIGNED".equals(di.getStatus());
    }

    public static boolean wasSignedBefore(DocumentInfo di) {
        //if error check if it was for signed before
        boolean signedBefore = false;
        if (di.getMessages() != null) {
            Optional<ResponseMessage> messageAlreadySigned = di.getMessages().stream()
                    .filter(m -> "400".equals(m.getStatusCode()) && "7074".equals(m.getDetailCode()))
                    .findAny();
            signedBefore = messageAlreadySigned.isPresent();
        }
        return signedBefore;
    }

    public static boolean isInvalid(Response response) {
        return response.getResponseBody() == null
                || response.getResponseBody().getXml() == null
                || response.getResponseBody().getXml().getDocuments() == null
                || response.getResponseBody().getXml().getDocuments().isEmpty();
    }
//</editor-fold>

}
