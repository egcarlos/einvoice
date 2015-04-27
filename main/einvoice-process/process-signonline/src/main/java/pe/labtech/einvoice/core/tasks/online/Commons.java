/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.online;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.ws.WebServiceException;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.tasks.Tools;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.replicator.model.DatabaseManager;

/**
 *
 * @author Carlos
 */
public class Commons {

    public static final Builder builder = new Builder();

    public static DocumentInfo handleOnline(DatabaseManager db, DocumentLoaderLocal loader, EBizGenericInvoker invoker, Document document, String request) {

        Tools.saveRequest(db, document, request);
        try {
            loader.createEvent(document, "SIGN_REQUEST", request);
            String response = invoker.invoke(request);
            loader.createEvent(document, "SIGN_RESPONSE", response);
            Response r = builder.unmarshall(Response.class, response);

            if (Tools.isInvalid(r)) {
                loader.createEvent(document, "ERROR", "Invalid response structure");
                loader.markAsError(document.getId());
                return null;
            }

            DocumentInfo di = Tools.getDocumentInfo(r);
            Map<String, String> responses = Tools.describe(di);
            if (Tools.isSigned(di)) {
                loader.markSigned(document.getId(), "SYNC", di.getSignatureValue(), di.getHashCode(), responses);
            } else if (Tools.wasSignedBefore(di)) {
                loader.markForSync(document.getId());
            } else {
                loader.markSigned(document.getId(), "ERROR", di.getSignatureValue(), di.getHashCode(), responses);
            }
            return di;
        } catch (WebServiceException ex) {
            Map<String, String> responses = new HashMap<>();
            responses.put("messages", "Soap Fault raised!");
            loader.markSigned(document.getId(), "RETRY", null, null, responses);
            String message = Tools.exToString(ex, "Document will retry");
            loader.createEvent(document, "WARN", message);
            //TODO create a synthetic document info
            return null;
        } catch (RuntimeException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            loader.markAsError(document.getId(), ex);
            return null;
        }
    }

    public static <D, I> D map(Document source, Supplier<D> d, Supplier<I> i, BiConsumer<D, List<I>> set) {
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

    public static <I> I mapItem(Item source, I target) {
        if (source.getAttributes() != null) {
            source.getAttributes().forEach(a -> mapAttribute(target, a.getName(), a.getValue()));
        }
        if (source.getAuxiliars() != null) {
            source.getAuxiliars().forEach(a -> mapAuxiliar(target, a.getCode(), a.getLength(), a.getOrder(), a.getValue()));
        }
        return target;
    }

    public static void mapAttribute(final Object target, final String attribute, final String value) {
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
            } else if (Long.class.isAssignableFrom(propertyType)) {
                PropertyUtils.setProperty(target, attribute, new Long(value));
            } else {
                //direct
                PropertyUtils.setProperty(target, attribute, value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ParseException ex) {
            //TODO can't handle invalid mapping
            Logger.getLogger(Tools.class.getName()).log(Level.INFO, "Can''t map property " + attribute, ex);
        }
    }

    public static void mapLegend(Object target, String code, Long order, String value, String additional) {
        if (code == null || value == null) {
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
            Logger.getLogger(Tools.class.getName()).log(Level.INFO, "Can''t map legend " + code, ex);
        }

    }

    public static void mapAuxiliar(Object target, String code, String length, Long order, String value) {
        if (code == null || value == null) {
            return;
        }
        try {
            String codeKey = "codigoAuxiliar" + length + "_" + order;
            PropertyUtils.setProperty(target, codeKey, code);
            String textKey = "textoAuxiliar" + length + "_" + order;
            PropertyUtils.setProperty(target, textKey, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.INFO, "Can''t map auxiliar " + code, ex);
        }

    }
}
