/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.console.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;

/**
 *
 * @author Carlos
 */
@Stateless
public class SecurityBean implements SecurityBeanLocal {

    @EJB
    PrivateDatabaseManagerLocal prv;

    @Override
    public boolean updateSecurityPassword(String id, String password) {
        String pw = obfuscate(password);
        return prv.seek(e -> e
                .createQuery("UPDATE SecurityValues O SET O.protection = :pw WHERE O.userId = :id")
                .setParameter("id", id)
                .setParameter("pw", pw)
                .executeUpdate() == 1
        );
    }

    @Override
    public boolean updateStorePassword(String id, String password) {
        String pw = obfuscate(password);
        return prv.seek(e -> e
                .createQuery("UPDATE KeystoreReference O SET O.protection = :pw WHERE O.id = :id")
                .setParameter("id", id)
                .setParameter("pw", pw)
                .executeUpdate() == 1
        );
    }

    private String obfuscate(String password) {
        try {
            byte[] bytes = password.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(
                            new byte[]{
                                (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                                (byte) 0, (byte) 0, (byte) 0, (byte) 0
                            },
                            "DES"
                    )
            );
            bytes = cipher.doFinal(bytes);
            return "y'" + DatatypeConverter.printHexBinary(bytes) + "'";
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
