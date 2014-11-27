/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws;

import com.alignet.einvoice.ebiz.ws.generated.EBizGenericInvokerImplService;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Carlos
 */
@ApplicationScoped
public class ClientProducer {

    @Produces
    EBizGenericInvokerImplService service;

    @PostConstruct
    public void init() {
        service = new EBizGenericInvokerImplService();

    }

}
