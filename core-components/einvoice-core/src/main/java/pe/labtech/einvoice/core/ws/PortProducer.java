/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws;

import com.alignet.einvoice.ebiz.ws.generated.EBizGenericInvoker;
import com.alignet.einvoice.ebiz.ws.generated.EBizGenericInvokerImplService;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Carlos
 */
@Dependent
public class PortProducer {

    private static final Logger logger = Logger.getLogger("PortProducer");

    @Inject
    EBizGenericInvokerImplService service;

    @Inject
    PortInfo info;

    ThreadLocal<EBizGenericInvoker> localPort;

    @PostConstruct
    public void init() {
        localPort = new ThreadLocal<EBizGenericInvoker>() {
            @Override
            protected EBizGenericInvoker initialValue() {
                EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();
//                Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
                logger.info("port built for thread");
                return port;
            }
        };

    }

    @Produces
    public EBizGenericInvoker getPort() {
        final EBizGenericInvoker port = localPort.get();
        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, info.getEndpoint());
        rc.put(BindingProvider.USERNAME_PROPERTY, info.getUser());
        rc.put("javax.xml.ws.client.connectionTimeout", info.getConnectionTimeout());
        rc.put("javax.xml.ws.client.receiveTimeout", info.getReceiveTimeout());
        rc.put(BindingProvider.PASSWORD_PROPERTY, info.getPassword());
        return port;
    }

}
