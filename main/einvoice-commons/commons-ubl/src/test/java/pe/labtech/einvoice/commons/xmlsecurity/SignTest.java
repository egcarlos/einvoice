/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.text.MessageFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Carlos
 */
public class SignTest {

    public SignTest() {
    }

    @Test
    public void signTest() throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        File f = new File("BIZLINKS7825819230563186661.XML");
        DigitalSign ds = new DigitalSign();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(f);

        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(new FileInputStream("avinka.jks"), "3b1zl4t1n$FE".toCharArray());

        Key pk = ks.getKey("daniel crisostomo (sunat)", "3b1zl4t1n$AVINKA".toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate("daniel crisostomo (sunat)");

        ds.sign(d, pk, cert);
        String[] responses = ds.getResponses(d);
        System.out.println(MessageFormat.format("hash:{0}\nsign:{1}", responses[0], responses[1]));
    }

    @Test
    public void signTest1() throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        File f = new File("20501827623-01-FLT2-00000030.xml");
        DigitalSign ds = new DigitalSign();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(f);

        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(new FileInputStream("avinka.jks"), "3b1zl4t1n$FE".toCharArray());

        Key pk = ks.getKey("daniel crisostomo (sunat)", "3b1zl4t1n$AVINKA".toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate("daniel crisostomo (sunat)");

        ds.sign(d, pk, cert);
        String[] responses = ds.getResponses(d);
        System.out.println(MessageFormat.format("hash:{0}\nsign:{1}", responses[0], responses[1]));
    }

}
