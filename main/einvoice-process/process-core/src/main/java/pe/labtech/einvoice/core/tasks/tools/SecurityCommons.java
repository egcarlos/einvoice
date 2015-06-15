/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
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
            String unlocked = unlock(sv.getProtection());
            return ks.getKey(sv.getAlias(), unlocked.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(OfflineInvoice.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String unlock(String protection) {
        if (protection.startsWith("y'")) {
            return unobfuscateY(protection);
        } else if (protection.startsWith("x'")) {
            return unobfuscateX(protection);
        } else {
            return protection;
        }
    }

    private static String unobfuscateY(String password) {
        try {
            byte[] bytes = DatatypeConverter.parseHexBinary(
                    password.substring(
                            2,
                            password.length() - 1
                    )
            );
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    new SecretKeySpec(
                            new byte[]{
                                (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                                (byte) 0, (byte) 0, (byte) 0, (byte) 0
                            },
                            "DES"
                    )
            );
            bytes = cipher.doFinal(bytes);
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String unobfuscateX(String password) {
        return NcCrypt2.getInstance().decode(password);
    }
}
