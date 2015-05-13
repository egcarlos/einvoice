/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.labtech.einvoice.core.entity.SecurityValues;
import pe.labtech.einvoice.core.tasks.sign.OfflineInvoice;

/**
 *
 * @author Carlos
 */
public class SecurityCommons {

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
}
