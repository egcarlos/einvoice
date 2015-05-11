/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.offline;

import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.labtech.einvoice.commons.xmlsecurity.DigitalSign;
import pe.labtech.einvoice.core.entity.SecurityValues;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;

/**
 *
 * @author Carlos
 */
public class Commons {

    public static final DigitalSign DIGISIGN = new DigitalSign();

    public static DocumentInfo synthDocumentInfo(org.w3c.dom.Document xml) {
        String[] responses = DIGISIGN.getResponses(xml);
        DocumentInfo di = new DocumentInfo();
        di.setHashCode(responses[0]);
        di.setSignatureValue(responses[1]);
        di.setStatus("SIGNED");
        return di;
    }

    public static X509Certificate extractCertificate(KeyStore ks, SecurityValues sv) {
        try {
            return (X509Certificate) ks.getCertificate(sv.getAlias());
        } catch (KeyStoreException ex) {
            Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Key extractKey(KeyStore ks, SecurityValues sv) {
        try {
            return ks.getKey(sv.getAlias(), sv.getProtection().toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static BigDecimal buildNumber(String amount) {
        if (amount == null) {
            return null;
        }
        return new BigDecimal(amount);
    }

}
