/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.ws.sunat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPFaultException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService;
import pe.gob.sunat.servicio.registro.comppago.factura.gem.service.BillService_Service;
//import pe.gob.sunat.service.BillService;

/**
* Clase NewEmptyJUnitTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        PortFactory pf = new PortFactory();

        final String user = "20101705839MODDATOS";
        final String password = "moddatos";
        final String name = "20101705839-01-FLT1-00000001.zip";
        final DataHandler file = new DataHandler(new byte[]{(byte) 12, (byte) 12, (byte) 12, (byte) 12, (byte) 12, (byte) 12, (byte) 12, (byte) 12}, "application/octet-stream");

        byte[] result = pf.invoke((s, h) -> {
            h.setUser(user);
            h.setPassword(password);
            try {
                return s.sendBill(name, file);
            } catch (SOAPFaultException ex) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, () -> faultMessage(ex.getFault()));
                return null;
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }, null);
        System.out.println("Result = " + Arrays.toString(result));
    }

    private String faultMessage(final SOAPFault fault) {
        String faultCode = fault.getFaultCode();
        //sanitize fault code
        if (faultCode.startsWith("soap-env-Server.")) {
            faultCode = faultCode.substring("soap-env-Server.".length());
        }
        return "Error! " + fault.getFaultCode() + ": " + fault.getFaultString() + ", " + description(faultCode);
    }

    private String description(String code) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("pe.labtech.einvoice.ws.sunat.Errors");
            String description = bundle.getString(code);
            return description;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, () -> "Missing fault code " + code);
            return null;
        }
    }
}
