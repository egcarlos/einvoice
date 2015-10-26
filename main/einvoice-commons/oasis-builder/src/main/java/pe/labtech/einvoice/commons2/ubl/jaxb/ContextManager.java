/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import java.util.function.BiFunction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Carlos Echeverria
 */
public interface ContextManager<R> {

    JAXBContext getContext();

    Marshaller getMarshaller();

    Marshaller getMarshaller(String charsetName);

    JAXBElement<R> getElement(R reference);

    <T> T doWork(BiFunction<ContextManager<R>, Marshaller, T> function);
}
