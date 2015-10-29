/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import pe.labtech.einvoice.commons.ubl.VoidedDocumentsBuilder;
import pe.labtech.einvoice.commons.ubl.VoidedDocumentsLineBuilder;

/**
* Clase VoidedDocumentsTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class VoidedDocumentsTest {

    @Test
    public void test1() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        final VoidedDocumentsBuilder b = new VoidedDocumentsBuilder();
        final VoidedDocumentsLineBuilder lb = new VoidedDocumentsLineBuilder();
        final Document document = b
                .init("RC-20150602-001", "2015-06-01", "2015-06-02", "6", "20563330709", "LABTECH SRL")
                .addLine(lb
                        .init("1", "01", "F001", "00000001", "ERROR EN RUC")
                        .compile()
                )
                .document("UTF-8");
        final DigitalSign ds = new DigitalSign();

        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(new FileInputStream("avinka.jks"), "3b1zl4t1n$FE".toCharArray());

        Key pk = ks.getKey("daniel crisostomo (sunat)", "3b1zl4t1n$AVINKA".toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate("daniel crisostomo (sunat)");

        ds.sign(document, pk, cert);
        String[] responses = ds.getResponses(document);
        System.out.println("resp[0]=" + responses[0]);
        System.out.println("resp[1]=" + responses[1]);

        byte[] data = ds.createRepresentation(document,
                "UTF-8"
        );

        try (FileOutputStream fos = new FileOutputStream("voideddocuments.xml")) {
            fos.write(data);
        } catch (IOException ex) {
            Logger.getLogger(VoidedDocumentsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
