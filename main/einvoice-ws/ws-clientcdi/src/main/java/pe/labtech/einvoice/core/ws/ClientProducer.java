/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvokerImplService;

/**
* Clase ClientProducer.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
@ApplicationScoped
public class ClientProducer {

    @Produces
    public EBizGenericInvokerImplService getService() {
        return new EBizGenericInvokerImplService();

    }

}
