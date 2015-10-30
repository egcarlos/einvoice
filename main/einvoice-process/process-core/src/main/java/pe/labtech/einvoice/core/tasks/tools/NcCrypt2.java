/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.core.tasks.tools;

import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
* Clase NcCrypt2.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class NcCrypt2 {

    private final Key key = new SecretKeySpec(DatatypeConverter.parseHexBinary("7C91DE760CCDFEEF3BA3F1951D2B5DF3B1486F2B0D2BFAC0"), "DESede");
    private final IvParameterSpec iv = new IvParameterSpec(new byte[]{-2, -36, -70, 33, -112, 67, -121, 101});
    private final ThreadLocal<Cipher> decoder = new ThreadLocal<Cipher>() {

        @Override
        protected Cipher initialValue() {
            try {
                Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, NcCrypt2.this.getKey(), NcCrypt2.this.getIv());
                return c;
            } catch (GeneralSecurityException ex) {
                throw new RuntimeException("Failed to initialize cipher for thread.", ex);
            }
        }

    };

    private Key getKey() {
        return key;
    }

    private IvParameterSpec getIv() {
        return iv;
    }

    /**
     * Decodifica una clave decodificada
     *
     * @param x_encoded clave codificada
     * @return clave decodificada
     */
    public String decode(String x_encoded) {
        try {
            byte[] encrypted = parseXEncoded(x_encoded);
            byte[] clear = decoder.get().doFinal(encrypted);
            return new String(clear);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException("Passphrase " + x_encoded + " was tampered", ex);
        }
    }

    private byte[] parseXEncoded(String encrypted) {
        byte[] binaryCiphered = DatatypeConverter.parseBase64Binary(unwrapSecond(unwrapFirst(encrypted)));
        return binaryCiphered;
    }

    private String unwrapSecond(String encrypted) {
        return new String(DatatypeConverter.parseHexBinary(encrypted)).trim();
    }

    private String unwrapFirst(String encrypted) {
        return encrypted.substring(2, encrypted.length() - 1);
    }

    private static final NcCrypt2 INSTANCE = new NcCrypt2();

    /**
     * Obtiene una referencia al objeto
     * 
     * @return referencia
     */
    public static NcCrypt2 getInstance() {
        return INSTANCE;
    }
}
