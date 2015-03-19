/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws;

import java.io.File;
import org.junit.Test;

/**
 *
 * @author Carlos
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
