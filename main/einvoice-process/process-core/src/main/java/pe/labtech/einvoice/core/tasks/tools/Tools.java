/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;
import pe.labtech.einvoice.commons.model.DatabaseManager;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.DATA_LOADED;
import pe.labtech.einvoice.core.ws.messages.response.CommonBody;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.core.ws.messages.response.ResponseMessage;

/**
 *
 * @author Carlos
 */
public class Tools {

    public static BigDecimal buildNumber(String amount) {
        if (amount == null) {
            return null;
        }
        return new BigDecimal(amount);
    }

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

    public static Map<String, String> describe(DocumentInfo di) {
        try {
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
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            //this condition should never happen since the bean is always describable
            throw new RuntimeException(ex);
        }
    }

    public static String buildClientID(String clientId) {
        if (clientId.contains("-")) {
            return clientId.split("-")[1];
        }
        return clientId;
    }

    //<editor-fold defaultstate="collapsed" desc="metodos para interpretar las respuestas del servicio">
    public static boolean noRecordsFound(Response r) {
        if (r.getResponseBody() == null) {
            return false;
        }
        CommonBody c = r.getResponseBody().getCommon();
        if (c == null || c.getSummary() == null) {
            //si no hay common body o no hay summary es firma
            return false;
        }
        if (c.getSummary().getTotal() == null) {
            //no hay como comprobar
            return false;
        }
        return c.getSummary().getTotal() == 0;
    }

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

    public static boolean wasReplicatedBefore(Response r) {
        try {
            return r.getResponseBody().getCommon().getMessages().stream()
                    .filter(m -> "400".equals(m.getStatusCode()) && "7213".equals(m.getDetailCode()))
                    .findFirst()
                    .isPresent();
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public static boolean isInvalid(Response response) {
        return response.getResponseBody() == null
                || response.getResponseBody().getXml() == null
                || response.getResponseBody().getXml().getDocuments() == null
                || response.getResponseBody().getXml().getDocuments().isEmpty();
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
        return sunatStatus != null && (sunatStatus.startsWith("RC") || sunatStatus.startsWith("AC"));
    }
//</editor-fold>

    public static String serializeMessages(Response r) {
        try {
            return r.getResponseBody().getCommon().getMessages().stream()
                    .map(m -> "[" + m.getStatusCode() + ":" + m.getStatusDescription() + "] " + m.getDetailCode() + ":" + m.getDetailDescription())
                    .reduce(null, (a, b) -> a == null ? b : a + "\n" + b);
        } catch (RuntimeException ex) {
            return "";
        }
    }

    public static <K, V> Map<K, V> toMap(Class<K> k, Class<V> v, Object... values) {
        Map<K, V> map = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i += 2) {
            map.put((K) values[i], (V) values[i + 1]);
        }
        return map;
    }

}
