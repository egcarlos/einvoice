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
import java.util.Optional;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.core.ws.messages.response.ResponseMessage;
import pe.labtech.einvoice.core.ws.model.DocumentItem;

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

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void handle(Long id) {
        try {
            //validar la presencia de los atributos es un tema
            //genear el mapeo de documento a lo otro
            pe.labtech.einvoice.core.ws.model.Document target = new pe.labtech.einvoice.core.ws.model.Document();

            Document entity = mapDocument(id, target);

            Builder b = new Builder();
            String request = b.buildSign(entity.getClientId(), entity.getDocumentType(), "PDF", true, false, false, "", Arrays.asList(target));
            loader.createEvent(entity, "SIGN_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(entity, "SIGN_RESPONSE", response);
            Response r = b.unmarshall(Response.class, response);

            if (isInvalid(r)) {
                loader.markAsError(id);
                return;
            }

            DocumentInfo di = getDocumentInfo(r);
            if (isSigned(di)) {
                loader.markSigned(entity.getId(), di.getPdfFileUrl(), di.getXmlFileSignUrl(), di.getSignatureValue(), di.getHashCode());
            } else if (wasSignedBefore(di)) {
                loader.markForSync(entity.getId());
            } else {
                loader.markAsError(id);
            }
        } catch (Exception ex) {
            loader.markAsError(id, ex);
        }

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

    private Document mapDocument(Long id, pe.labtech.einvoice.core.ws.model.Document target) {
        Document entity = loader.loadForWork(id, source -> {
            //mapping principal attributes
            if (source.getAttributes() != null) {
                source.getAttributes().forEach(a -> mapAttribute(target, a.getName(), a.getValue()));
            }
            if (source.getAuxiliars() != null) {
                source.getAuxiliars().forEach(a -> mapAuxiliar(target, a.getCode(), a.getLength(), a.getOrder(), a.getValue()));
            }
            if (source.getItems() != null) {
                List<DocumentItem> items = source.getItems().stream()
                        .sorted((a, b) -> a.getId().compareTo(b.getId()))
                        .map(i -> mapItem(i)).collect(Collectors.toList());
                target.setItems(items);
            }
        });
        return entity;
    }

    private DocumentItem mapItem(Item source) {
        DocumentItem target = new DocumentItem();
        if (source.getAttributes() != null) {
            source.getAttributes().forEach(a -> mapAttribute(target, a.getName(), a.getValue()));
        }
        if (source.getAuxiliars() != null) {
            source.getAuxiliars().forEach(a -> mapAuxiliar(target, a.getCode(), a.getLength(), a.getOrder(), a.getValue()));
        }
        return target;
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
