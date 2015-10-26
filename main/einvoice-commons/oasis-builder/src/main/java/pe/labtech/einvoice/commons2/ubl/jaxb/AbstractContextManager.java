/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.jaxb;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Carlos Echeverria
 */
public class AbstractContextManager<R> implements ContextManager<R> {

    private final JAXBContext context;
    private final Deque<Marshaller> deque;
    private final Function<R, JAXBElement<R>> wrapper;

    public AbstractContextManager(Class<R> type, Function<R, JAXBElement<R>> wrapper) {
        this.deque = new ConcurrentLinkedDeque<>();
        this.wrapper = wrapper;
        try {
            context = JAXBContext.newInstance(type);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JAXBContext getContext() {
        return context;
    }

    @Override
    public Marshaller getMarshaller() {
        return this.getMarshaller("UTF-8");
    }

    @Override
    public Marshaller getMarshaller(String charsetName) {
        try {
            Marshaller marshaller = getContext().createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charsetName);
            return marshaller;
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JAXBElement<R> getElement(R reference) {
        return wrapper.apply(reference);
    }

    @Override
    public <T> T doWork(BiFunction<ContextManager<R>, Marshaller, T> function) {
        Marshaller m = deque.poll();
        if (m == null) {
            m = getMarshaller();
        }
        try {
            return function.apply(this, m);
        } finally {
            deque.addLast(m);
        }
    }

}
