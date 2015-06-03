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
import pe.labtech.einvoice.commons.ubl.VoidedDocumentsBuilder;
import pe.labtech.einvoice.commons.ubl.VoidedDocumentsLineBuilder;

/**
 *
 * @author Carlos Echeverria
 */
public class VoidedDocumentsTest {

    @Test
    public void test1() {
        final VoidedDocumentsBuilder b = new VoidedDocumentsBuilder();
        final VoidedDocumentsLineBuilder lb = new VoidedDocumentsLineBuilder();

        byte[] data = new DigitalSign().createRepresentation(b
                .init("RC-20150602-001", "2015-06-01", "2015-06-02", "6", "20563330709", "LABTECH SRL")
                .addLine(lb
                        .init(1l, "01", "F001", "00000001", "ERROR EN RUC")
                        .compile()
                )
                .document("UTF-8"),
                "UTF-8"
        );

        try (FileOutputStream fos = new FileOutputStream("voideddocuments.xml")) {
            fos.write(data);
        } catch (IOException ex) {
            Logger.getLogger(VoidedDocumentsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
