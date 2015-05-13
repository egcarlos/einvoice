/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.PropertyUtils;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.Item;

/**
 *
 * @author Carlos
 */
public class ParseCommons {

    /**
     * Utility class to convert a document into another target.
     *
     * @param <D> type of the target
     * @param <I> type of the item of the target
     * @param source document to convert
     * @param d creates new instances of the target
     * @param i creates new items of the target
     * @param set sets the item list in the target
     * @return
     */
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

    /**
     * Utility class to convert an item into another target
     *
     * @param <I> type of the target
     * @param source item source
     * @param target item target
     * @return the item target (same as argument)
     */
    public static <I> I mapItem(Item source, I target) {
        if (source.getAttributes() != null) {
            source.getAttributes().forEach(a -> mapAttribute(target, a.getName(), a.getValue()));
        }
        if (source.getAuxiliars() != null) {
            source.getAuxiliars().forEach(a -> mapAuxiliar(target, a.getCode(), a.getLength(), a.getOrder(), a.getValue()));
        }
        return target;
    }

    /**
     * Sets the value of an attribute in a target object
     *
     * @param target
     * @param attribute
     * @param value
     */
    public static void mapAttribute(final Object target, final String attribute, final String value) {
        if (value == null || "".equals(value.trim())) {
            //can't assign null values
            return;
        }
        try {
            Class<?> propertyType = PropertyUtils.getPropertyType(target, attribute);
            if (propertyType == null) {
                //raise error and cancel for no mappeableProperty
                Logger.getLogger(Tools.class.getName()).log(Level.WARNING, () -> "Can''t map property " + attribute);
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
            Logger.getLogger(Tools.class.getName()).log(Level.WARNING, ex, () -> "Can''t map property " + attribute);
        }
    }

    /**
     * Map a legend field
     *
     * @param target
     * @param code
     * @param order
     * @param value
     * @param additional
     */
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

    /**
     * Map an auxiliar field
     *
     * @param target
     * @param code
     * @param length
     * @param order
     * @param value
     */
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
