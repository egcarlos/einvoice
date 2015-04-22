/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alignet.einvoide.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
    public void test() throws IOException {
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu/ws/invoker");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");

        File f = new File("20502433629-01-FLT3-00000001.xml");
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10240);
        try (
                ZipOutputStream output = new ZipOutputStream(bos);
                FileInputStream input = new FileInputStream(f)) {
            output.putNextEntry(new ZipEntry(f.getName()));

            byte[] buffer = new byte[10240];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.closeEntry();
            output.finish();
            output.close();
        }

        String ruc = "20502433629";
        String response = port.replicateXml(
                "<ReplicateXmlCmd declare-sunat=\"1\" declare-direct-sunat=\"0\" publish=\"1\" output=\"PDF\">"
                + "<parametros/>"
                + "<parameter value=\"" + ruc + "\" name=\"idEmisor\"/>"
                + "</ReplicateXmlCmd>",
                bos.toByteArray(),
                null
        );

        System.out.println(response);
    }

}
