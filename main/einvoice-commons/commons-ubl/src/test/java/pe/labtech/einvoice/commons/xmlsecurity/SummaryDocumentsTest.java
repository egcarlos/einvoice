/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsBuilder;

/**
 *
 * @author Carlos Echeverria
 */
public class SummaryDocumentsTest {

    @Test
    public void test1() {

        byte[] data = new DigitalSign().createRepresentation(
                new SummaryDocumentsBuilder()
                .init("RC-20150602-001", "2015-06-01", "2015-06-02", "6", "20563330709", "LABTECH SRL")
                .document("UTF-8"),
                "UTF-8"
        );

        try (FileOutputStream fos = new FileOutputStream("summarydocuments.xml")) {
            fos.write(data);
        } catch (IOException ex) {
            Logger.getLogger(SummaryDocumentsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
