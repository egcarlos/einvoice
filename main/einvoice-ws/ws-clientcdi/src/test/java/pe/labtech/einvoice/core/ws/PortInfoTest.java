/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws;

import java.io.File;
import org.junit.Test;

/**
* Clase PortInfoTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
public class PortInfoTest {

    public PortInfoTest() {
    }

    @Test
    public void testPersist() {
        new File("portinfo.json").delete();
        PortInfo portInfo = new PortInfo();
        portInfo.init();
        portInfo.persist();
        portInfo.init();
    }

}
