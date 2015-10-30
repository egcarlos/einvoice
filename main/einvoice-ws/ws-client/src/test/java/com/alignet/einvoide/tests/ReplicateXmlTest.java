/*
* Producto elaborado para Alignet S.A.C.
*
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
* Clase ReplicateXmlTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
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

        File f = new File("20501827623-01-FLTE-00000001.xml");
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10240);
        try (ZipOutputStream output = new ZipOutputStream(bos); FileInputStream input = new FileInputStream(f)) {
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

        String ruc = "20501827623";
        final String comand = "<ReplicateXmlCmd declare-sunat=\"1\" declare-direct-sunat=\"0\" publish=\"1\" output=\"PDF\">"
                + "<parametros/>"
                + "<parameter value=\"" + ruc + "\" name=\"idEmisor\"/>"
                + "</ReplicateXmlCmd>";
        System.out.println(comand);
        String response = port.replicateXml(comand,
                bos.toByteArray(),
                null
        );

        System.out.println(response);
    }

}
