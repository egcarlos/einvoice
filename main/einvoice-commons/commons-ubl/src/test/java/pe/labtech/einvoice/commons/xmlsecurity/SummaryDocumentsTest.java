/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsBuilder;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsLineBuilder;

/**
 *
 * @author Carlos Echeverria
 */
public class SummaryDocumentsTest {

    @Test
    public void test1() {
        final SummaryDocumentsBuilder b = new SummaryDocumentsBuilder();
        final SummaryDocumentsLineBuilder lb = new SummaryDocumentsLineBuilder();

        byte[] data = new DigitalSign().createRepresentation(b
                .init("RC-20150602-001", "2015-06-01", "2015-06-02", "6", "20563330709", "LABTECH SRL")
                .addLine(lb
                        .init(1l, "03", "B001", "00000001", "00000001", "PEN", new BigDecimal("118.00"))
                        .addBillingPayment("01", new BigDecimal("100.00"))
                        .addBillingPayment("02", new BigDecimal("0.00"))
                        .addBillingPayment("03", new BigDecimal("0.00"))
                        //.addBillingPayment("04", new BigDecimal("0.00"))
                        .addAllowance(true, new BigDecimal("0.00"))
                        .addTax("2000", "ISC", "EXC", new BigDecimal("0.00"), null, null)
                        .addTax("9999", "OTROS", "OTH", new BigDecimal("0.00"), null, null)
                        .addTax("1000", "IGV", "VAT", new BigDecimal("18.00"), null, null)
                        .compile()
                )
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
