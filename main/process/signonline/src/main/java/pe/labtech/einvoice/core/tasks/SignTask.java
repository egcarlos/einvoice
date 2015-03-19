/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.InvoiceSeekerLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.core.ws.messages.response.ResponseMessage;
import pe.labtech.einvoice.core.ws.model.DocumentItem;
import pe.labtech.einvoice.core.ws.model.SummaryItem;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.*;

/**
 *
 * @author Carlos
 */
@Stateless
public class SignTask implements SignTaskLocal {

    @EJB
    DocumentLoaderLocal loader;

    @Inject
    EBizGenericInvoker invoker;

    @EJB
    InvoiceSeekerLocal seeker;

    @EJB
    PrivateDatabaseManagerLocal db;

    Builder b = new Builder();

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Document document) {
        String number = document.getDocumentNumber();
        String request;
        if (number.startsWith("F") || number.startsWith("B")) {
            request = buildSignCommand(document.getId());
        } else if (number.startsWith("RC") || number.startsWith("RA")) {
            request = buildSignSummaryCommand(document.getId());
        } else {
            return; //invalid markar el error del documento
        }

        saveRequest(document, request);

        try {
            loader.createEvent(document, "SIGN_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(document, "SIGN_RESPONSE", response);
            Response r = b.unmarshall(Response.class, response);

            if (isInvalid(r)) {
                loader.markAsError(document.getId());
                return;
            }

            DocumentInfo di = getDocumentInfo(r);
            Map<String, String> responses = describe(di);
            if (isSigned(di)) {
                loader.markSigned(document.getId(), "SYNC", di.getSignatureValue(), di.getHashCode(), responses);
            } else if (wasSignedBefore(di)) {
                loader.markForSync(document.getId());
            } else {                
                loader.markSigned(document.getId(), "ERROR", di.getSignatureValue(), di.getHashCode(), responses);
            }
        } catch (RuntimeException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            loader.markAsError(document.getId(), ex);
        }

    }

    private Map<String, String> describe(DocumentInfo di) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
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

    private void saveRequest(Document document, String request) {
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

    private String buildSignCommand(Long id) {
        pe.labtech.einvoice.core.ws.model.Document target = new pe.labtech.einvoice.core.ws.model.Document();
        Document entity = loader.loadForWork(id, source -> {
            this.map(source, () -> target, () -> new DocumentItem(), (d, il) -> d.setItems(il));
        });
        return b.buildSign(
                buildClientID(entity.getClientId()), entity.getDocumentType(),
                "PDF", true, false, false, "",
                Arrays.asList(target)
        );
    }

    private String buildSignSummaryCommand(Long id) {
        pe.labtech.einvoice.core.ws.model.Summary target = new pe.labtech.einvoice.core.ws.model.Summary();
        Document entity = loader.loadForWork(id, source -> {
            this.map(source, () -> target, () -> new SummaryItem(), (d, il) -> d.setItems(il));
        });
        return b.buildSignSummary(buildClientID(entity.getClientId()), entity.getDocumentType(), "", target);
    }

    private <D, I> D map(Document source, Supplier<D> d, Supplier<I> i, BiConsumer<D, List<I>> set) {
        D target = d.get();
        if (source.getAttributes() != null) {
            source.getAttributes().forEach(a -> mapAttribute(target, a.getName(), a.getValue()));
        }
        if (source.getAuxiliars() != null) {
            source.getAuxiliars().forEach(a -> mapAuxiliar(target, a.getCode(), a.getLength(), a.getOrder(), a.getValue()));
        }
        if (source.getLegends() != null) {
            source.getLegends().forEach(a -> mapLegend(target, a.getCode(), a.getOrder(), a.getValue(), a.getAdditional()));
        }
        if (source.getItems() != null) {
            List<I> items = source.getItems().stream()
                    .sorted((a, b) -> a.getId().compareTo(b.getId()))
                    .map(item -> mapItem(item, i.get()))
                    .collect(Collectors.toList());
            set.accept(target, items);
        }
        return target;
    }

    private <I> I mapItem(Item source, I target) {
        if (source.getAttributes() != null) {
            source.getAttributes().forEach(a -> mapAttribute(target, a.getName(), a.getValue()));
        }
        if (source.getAuxiliars() != null) {
            source.getAuxiliars().forEach(a -> mapAuxiliar(target, a.getCode(), a.getLength(), a.getOrder(), a.getValue()));
        }
        return target;
    }

    private static boolean isInvalid(Response response) {
        return response.getResponseBody() == null
                || response.getResponseBody().getXml() == null
                || response.getResponseBody().getXml().getDocuments() == null
                || response.getResponseBody().getXml().getDocuments().isEmpty();
    }

    /**
     *
     * @param di
     * @return
     */
    private static boolean isSigned(DocumentInfo di) {
        return "SIGNED".equals(di.getStatus());
    }

    /**
     *
     * @param di
     * @return
     */
    private boolean wasSignedBefore(DocumentInfo di) {
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

    //Metodos de validacion de respuesta
    private static DocumentInfo getDocumentInfo(Response response) {
        return response.getResponseBody().getXml().getDocuments().get(0);
    }

    private String buildClientID(String clientId) {
        if (clientId.contains("-")) {
            return clientId.split("-")[1];
        }
        return clientId;
    }

    private void mapAttribute(final Object target, final String attribute, final String value) {
        if (value == null || "".equals(value.trim())) {
            //can't assign null values
            return;
        }
        try {
            Class<?> propertyType = PropertyUtils.getPropertyType(target, attribute);
            if (propertyType == null) {
                //raise error and cancel for no mappeableProperty
            } else if (BigDecimal.class.isAssignableFrom(propertyType)) {
                PropertyUtils.setProperty(target, attribute, new BigDecimal(value));
            } else if (Date.class.isAssignableFrom(propertyType)) {
                //handle the date formats
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
                PropertyUtils.setProperty(target, attribute, sdf.parse(value));
            } else {
                //direct
                PropertyUtils.setProperty(target, attribute, value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ParseException ex) {
            //TODO can't handle invalid mapping
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Can''t map property " + attribute, ex);
        }
    }

    private void mapLegend(Object target, String code, Long order, String value, String additional) {
        if (value == null) {
            return;
        }
        try {
            String codeKey = "codigoLeyenda_" + order;
            PropertyUtils.setProperty(target, codeKey, code);
            String textKey = "textoLeyenda_" + order;
            PropertyUtils.setProperty(target, textKey, value);
            String addiKey = "textoAdicionalLeyenda_" + order;
            PropertyUtils.setProperty(target, addiKey, additional);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Can''t map legend " + code, ex);
        }

    }

    private void mapAuxiliar(Object target, String code, String length, Long order, String value) {
        if (value == null) {
            return;
        }
        try {
            String codeKey = "codigoAuxiliar" + length + "_" + order;
            PropertyUtils.setProperty(target, codeKey, code);
            String textKey = "textoAuxiliar" + length + "_" + order;
            PropertyUtils.setProperty(target, textKey, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Can''t map auxiliar " + code, ex);
        }

    }

}
