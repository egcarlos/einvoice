/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import pe.labtech.einvoice.core.ws.model.DocumentAdvance;
import pe.labtech.einvoice.core.ws.model.DocumentItem;

/**
 * Clase ParseCommons.
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
public class ParseCommons {

    /**
     * Convierte un documento a otro tipo de dato.
     *
     * @param <D> tipo del destino
     * @param <I> tipo del elemento de destino
     * @param source documento para convertir
     * @param d creación de nuevas instancias para el destino
     * @param i creación de nuevos elementos para el destino
     * @param set establece la lista de elementos para el destino
     * @return nuevo tipo de dato
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
     * Convierte un documento a otro tipo de dato.
     *
     * @param <D> tipo del destino
     * @param <I> tipo del elemento de destino para los items
     * @param <P> tipo del elemento de destino para los anticipos
     * @param source documento para convertir
     * @param d creación de nuevas instancias para el destino
     * @param i creación de nuevos elementos para el destino
     * @param setI establece la lista de elementos para el destino
     * @param p creación de nuevos elementos para el destino
     * @param setP establece la lista de elementos para el destino
     * @return nuevo tipo de dato
     */
    public static <D, I extends DocumentItem, P extends DocumentAdvance> D map(Document source, Supplier<D> d, Supplier<I> i, BiConsumer<D, List<I>> setI, Supplier<P> p, BiConsumer<D, List<P>> setP) {
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
            setI.accept(target, items);
        }
        if (source.getPrepaids() != null) {
            List<P> prepaids = source.getPrepaids().stream()
                    .map(prepaid -> {
                        P tgt = p.get();
                        tgt.setIndicador("A");
                        tgt.setTipoDocumentoEmisorAnticipo(prepaid.getIssuerType());
                        tgt.setNumeroDocumentoEmisorAnticipo(prepaid.getIssuerId());
                        tgt.setTipoDocumentoAnticipo(prepaid.getType());
                        tgt.setSerieNumeroDocumentoAnticipo(prepaid.getId());
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setGroupingUsed(false);
                        df.getDecimalFormatSymbols().setDecimalSeparator('.');
                        tgt.setTotalPrepagadoAnticipo(df.format(prepaid.getAmount()));
                        return tgt;
                    })
                    .collect(Collectors.toList());
            setP.accept(target, prepaids);
        }
        return target;
    }

    /**
     * Convierte un item en otro tipo de dato
     *
     * @param <I> tipo de objetivo
     * @param source origen del elemento
     * @param target objetivo del elemento
     * @return the item target dato convertido
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
     * Establece el valor de un atributo en un objetivo de destino
     *
     * @param objetivo
     * @param atributo
     * @param valor
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
     * Asignar un campo leyenda
     *
     * @param objetivo
     * @param codigo
     * @param orden
     * @param valor
     * @param adicional
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
     * Asignar un campo auxiliar
     *
     * @param objetivo
     * @param codigo
     * @param longitud del campo
     * @param orden
     * @param valor
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
