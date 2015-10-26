/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemPropertyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueType;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.AdditionalPropertyType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.IdentifierType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.QuantityType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.AmountType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.CodeType;
import un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.TextType;

/**
 * Define el comportamiento de un constructor de documentos UBL y las
 * operaciones de trabajo básicas.
 *
 * @author Carlos Echeverria
 * @param <T> tipo del documento o resumen a construir
 */
public abstract class BaseBuilder<T> implements Builder<T> {

    private final Supplier<T> newInstance;

    protected T reference;

    protected BaseBuilder(Supplier<T> supplier) {
        newInstance = supplier;
    }

    @Override
    public BaseBuilder<T> init() {
        reference = newInstance.get();
        return this;
    }

    public static AdditionalPropertyType AdditionalPropertyType(String id, String value) {
        return init(AdditionalPropertyType::new, ref -> {
            if (id != null) {
                ref.setID(id(id));
            }
            if (value != null) {
                ref.setValue(text(ValueType::new, value));
            }
        });
    }

    public static ItemPropertyType itemProperty(String name, String value) {
        return init(ItemPropertyType::new, ref -> {
            if (name != null) {
                ref.setName(name(NameType::new, name));
            }
            if (value != null) {
                ref.setValue(text(ValueType::new, value));
            }
        });
    }

    public static IDType id(String value) {
        return identifier(IDType::new, value);
    }

    public static <T extends IdentifierType, V> T identifier(Supplier<T> supplier, String value) {
        return init(
                supplier,
                t -> t.setValue(value)
        );
    }

    public static <T extends AmountType> T amount(Supplier<T> supplier, CurrencyCodeContentType currency, BigDecimal amount) {
        return init(
                supplier,
                t -> t.setCurrencyID(currency),
                t -> t.setValue(amount)
        );
    }

    public static <T extends un.unece.uncefact.data.specification.unqualifieddatatypesschemamodule._2.NameType> T name(Supplier<T> supplier, String value) {
        return init(
                supplier,
                t -> t.setValue(value)
        );
    }

    public static <T extends TextType> T text(Supplier<T> supplier, String value) {
        return init(
                supplier,
                t -> t.setValue(value)
        );
    }

    public static <T extends CodeType> T code(Supplier<T> supplier, String value) {
        return init(
                supplier,
                t -> t.setValue(value)
        );
    }

    public static <T extends QuantityType> T quantity(Supplier<T> supplier, String unit, BigDecimal quantity) {
        return init(supplier, ref -> {
            ref.setUnitCode(unit);
            ref.setValue(quantity);
        });
    }

    public static <T, V> T init(Supplier<T> supplier, BiConsumer<T, V> consumer, V v) {
        return init(supplier, t -> consumer.accept(t, v));
    }

    public static <T> T init(Supplier<T> supplier, Consumer<T>... consumer) {
        T ref = supplier.get();
        for (Consumer<T> c : consumer) {
            c.accept(ref);
        }
        return ref;
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(String invoiceDate) {
        return toXMLGregorianCalendar(invoiceDate, "yyyy-MM-dd");
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(String invoiceDate, String format) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(toGregorianCalendar(invoiceDate));
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static GregorianCalendar toGregorianCalendar(String invoiceDate) {
        return toGregorianCalendar(invoiceDate, "yyyy-MM-dd");
    }

    public static GregorianCalendar toGregorianCalendar(String invoiceDate, String format) {
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new SimpleDateFormat(format).parse(invoiceDate));
            return gc;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static BigDecimal bigDecimal(String number) {
        if (number == null) {
            return null;
        }
        return new BigDecimal(number);
    }

    public DocumentBuilderFactory getDocumentBuilderFactory() {
        return init(DocumentBuilderFactory::newInstance, f -> f.setNamespaceAware(true));
    }

    public DocumentBuilder getDocumentBuilder() {
        try {
            return getDocumentBuilderFactory().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }

}
