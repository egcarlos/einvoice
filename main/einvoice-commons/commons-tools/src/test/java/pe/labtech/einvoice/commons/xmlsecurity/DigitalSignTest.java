/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
* Clase DigitalSignTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class DigitalSignTest {

    public DigitalSignTest() {
    }

    @Test
    public void testSign() throws SAXException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, ParserConfigurationException {

        File fXmlFile = new File("ubl.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fXmlFile);

        //cargar la llave y el certificado
        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(
                new FileInputStream("avinka.jks"),
                "3b1zl4t1n$FE".toCharArray()
        );
        String alias = "daniel crisostomo (sunat)";
        Key key = ks.getKey(alias, "3b1zl4t1n$AVINKA".toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

//        DigitalSign ds = new DigitalSign();
//        ds.sign(document, key, cert);
//        System.out.println(ds.createTextRepresentation(document));
    }

}
