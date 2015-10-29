/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws;

import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvokerImplService;

/**
* Clase PortProducer.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
@Dependent
public class PortProducer {

    private static final Logger logger = Logger.getLogger("PortProducer");

    private final EBizGenericInvokerImplService service;

    private final PortInfo info;

    @Inject
    public PortProducer(EBizGenericInvokerImplService service, PortInfo info) {
        this.service = service;
        this.info = info;
    }

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
