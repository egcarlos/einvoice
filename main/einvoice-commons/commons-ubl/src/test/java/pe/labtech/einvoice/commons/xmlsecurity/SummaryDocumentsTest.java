/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.w3c.dom.Document;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsBuilder;
import pe.labtech.einvoice.commons.ubl.SummaryDocumentsLineBuilder;

/**
 *
 * @author Carlos Echeverria
 */
public class SummaryDocumentsTest {

    @Test
    public void test1() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        final SummaryDocumentsBuilder b = new SummaryDocumentsBuilder();
        final SummaryDocumentsLineBuilder lb = new SummaryDocumentsLineBuilder();
        final DigitalSign ds = new DigitalSign();

        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(new FileInputStream("avinka.jks"), "3b1zl4t1n$FE".toCharArray());

        Key pk = ks.getKey("daniel crisostomo (sunat)", "3b1zl4t1n$AVINKA".toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate("daniel crisostomo (sunat)");

        final Document document = b
                .init("RC-20150602-001", "2015-06-01", "2015-06-02", "6", "20563330709", "LABTECH SRL")
                .addLine(lb
                        .init("1", "03", "B001", "00000001", "00000001", "PEN", "118.00")
                        .addBillingPayment("01", "100.00")
                        .addBillingPayment("02", "0.00")
                        .addBillingPayment("03", "0.00")
                        //.addBillingPayment("04", new BigDecimal("0.00"))
                        .addAllowance(true, "0.00")
                        .addTax("2000", "ISC", "EXC", "0.00", null, null)
                        .addTax("9999", "OTROS", "OTH", "0.00", null, null)
                        .addTax("1000", "IGV", "VAT", "18.00", null, null)
                        .compile()
                )
                .document("UTF-8");

        ds.sign(document, pk, cert);

        byte[] data = ds.createRepresentation(document,
                "UTF-8"
        );

        try (FileOutputStream fos = new FileOutputStream("summarydocuments.xml")) {
            fos.write(data);
        } catch (IOException ex) {
            Logger.getLogger(SummaryDocumentsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
