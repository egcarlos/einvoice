/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.ws.sunat;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiFunction;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;

/**
 *
 * @author Carlos Echeverria
 */
public class PortFactory {

    private static final BillService_Service service = new BillService_Service();

    private final Deque<BillService> queue = new ConcurrentLinkedDeque();

    public <T> T invoke(BiFunction<BillService, SecurityHandler, T> consumer, String endpoint) {
        SecurityHandler handler = null;
        BillService port = queue.poll();
        if (port == null) {
            //no available port in cache
            port = PortFactory.service.getBillServicePort();
            handler = new SecurityHandler();
            addHandler(port, handler);
        } else {
            //port in chache chek for handler
            Optional<SecurityHandler> opt = findHandler(port);
            if (opt.isPresent()) {
                handler = opt.get();
            } else {
                //this condition should never raise but just to be sure
                addHandler(port, handler = new SecurityHandler());
            }
        }
        try {
            //we delegate for te client to set the user, password and use the port
            return consumer.apply(port, handler);
        } finally {
            //return the initialized port to the deque
            queue.addLast(port);
        }

    }

    private Optional<SecurityHandler> findHandler(BillService port) {
        BindingProvider provider = (BindingProvider) port;
        Optional<SecurityHandler> opt = provider.getBinding().getHandlerChain().stream()
                .filter(h -> h instanceof SecurityHandler)
                .map(h -> (SecurityHandler) h)
                .findAny();
        return opt;
    }

    private void addHandler(BillService port, SecurityHandler handler) {
        BindingProvider provider = (BindingProvider) port;
        List<Handler> chain = provider.getBinding().getHandlerChain();
        if (chain == null) {
            chain = new LinkedList<>();
        }
        chain.add(handler);
        provider.getBinding().setHandlerChain(chain);
    }

}
