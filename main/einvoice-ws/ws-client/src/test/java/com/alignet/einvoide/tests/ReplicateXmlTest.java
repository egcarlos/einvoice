/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alignet.einvoide.tests;

import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.testng.annotations.Test;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvokerImplService;

/**
 *
 * @author Carlos
 */
public class ReplicateXmlTest {

    @Test
    public void test() {
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu/ws/invoker");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");
        
        
        
        
        port.replicateXml(null, null, null);
    }

}
