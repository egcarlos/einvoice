package pe.labtech.einvoice.core.tasks.tools;

import COM.rsa.jsafe.JSAFE_SecretKey;
import COM.rsa.jsafe.JSAFE_SymmetricCipher;
import javax.xml.bind.DatatypeConverter;


/**
 * User: aglwkrs Date: 14/01/12
 */
public class NcCrypt {

    public static String encriptarPassword(String strpassword) {
        String strkey = "7C91DE760CCDFEEF".toLowerCase();
        String cadena = NcCrypt.encrypt(strpassword, strkey).trim();

        String inicio = "x\'";
        String hexa = inicio;
        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            String hex = Integer.toHexString(caracter).toUpperCase();
            hexa += hex;
        }
        for (int i = 0; hexa.length() / 2 <= 32; i++) {
            hexa += "20";
        }
        hexa += "\'";

        return hexa;
    }

    public static String desencriptarPassword(String strpassword) {
        String strkey = "7C91DE760CCDFEEF".toLowerCase();
        if (strpassword == null || strpassword.equals("")) {
            return "";
        }
        //borro la x'
        strpassword = strpassword.substring(2);
        strpassword = strpassword.substring(0, strpassword.length() - 1);

        byte[] b = NcCrypt.hex2byte(strpassword);

        String strcadena = "";
        for (int i = 0; i < b.length; i++) {
            char c = (char) b[i];
            strcadena += c;
        }

        String ascii = NcCrypt.decrypt(strcadena.trim(), strkey).trim();

        return ascii;
    }

    public static byte[] createKey(byte abyte0[]) {
        byte abyte1[] = new byte[24];
        nc_decode_key(abyte0, abyte1);
        byte abyte2[] = new byte[16];
        gen2(abyte2);
        byte abyte3[] = new byte[16];
        nc_decode_key(abyte2, abyte3);
        memcpy(abyte1, 8, abyte3, 0, 8);
        memset(abyte2, 0, 8);
        byte abyte4[] = new byte[16];
        gen3(abyte4);
        byte abyte5[] = new byte[16];
        nc_decode_key(abyte4, abyte5);
        memcpy(abyte1, 16, abyte5, 0, 8);
        memset(abyte4, 0, 8);
        return abyte1;
    }

    public static synchronized String decrypt(String s, String s1) {
        JSAFE_SecretKey jsafe_secretkey = null;
        JSAFE_SymmetricCipher jsafe_symmetriccipher = null;
        byte abyte0[] = null;
        byte abyte1[] = null;
        byte abyte2[] = {
            -2, -36, -70, 33, -112, 67, -121, 101
        };
        byte abyte3[] = new byte[16];
        if (s1 != null) {
            abyte3 = stringToBytes(s1);
        } else {
            gen1(abyte3);
        }
        try {
            //BASE64Decoder base64decoder = new BASE64Decoder();
            //abyte0 = base64decoder.decodeBuffer(s);
            abyte0 = DatatypeConverter.parseBase64Binary(s);
        } catch (Exception exception) {
            System.err.println("Exception caught while base64 decoding." + exception);
        }
        try {
            jsafe_symmetriccipher = JSAFE_SymmetricCipher.getInstance("3DES_EDE/CBC/PKCS5Padding", "Java");
            jsafe_secretkey = jsafe_symmetriccipher.getBlankKey();
            byte abyte4[] = createKey(abyte3);
            jsafe_secretkey.setSecretKeyData(abyte4, 0, abyte4.length);
            jsafe_symmetriccipher.setIV(abyte2, 0, abyte2.length);
        } catch (Exception exception1) {
            System.err.println("%3DES-F-CIPHERINIT; Exception caught while initializing the cipher object.");
            System.err.println("; " + exception1.toString());
            return null;
        }
        try {
            abyte1 = new byte[abyte0.length];
            jsafe_symmetriccipher.decryptInit(jsafe_secretkey);
            int i = jsafe_symmetriccipher.decryptUpdate(abyte0, 0, abyte0.length, abyte1, 0);
            int k = jsafe_symmetriccipher.decryptFinal(abyte1, i);
        } catch (Exception exception2) {
            System.err.println("%3DES-F-DCRYPT; Exception caught while decrypting");
            System.err.println("; " + exception2.toString());
            return null;
        }
        jsafe_secretkey.clearSensitiveData();
        jsafe_symmetriccipher.clearSensitiveData();
        for (int j = 0; j < abyte0.length; j++) {
            abyte0[j] = 0;
        }

        String s2 = new String(abyte1);
        for (int l = 0; l < abyte1.length; l++) {
            abyte1[l] = 0;
        }

        return s2.trim();
    }

    public static synchronized String encrypt(String s, String s1) {
        JSAFE_SecretKey jsafe_secretkey = null;
        JSAFE_SymmetricCipher jsafe_symmetriccipher = null;
        byte abyte0[] = null;
        byte abyte1[] = null;
        byte abyte2[] = {
            -2, -36, -70, 33, -112, 67, -121, 101
        };
        byte abyte3[] = new byte[16];
        if (s1 != null) {
            abyte3 = stringToBytes(s1);
        } else {
            gen1(abyte3);
        }
        abyte0 = stringToBytes(s);
        try {
            jsafe_symmetriccipher = JSAFE_SymmetricCipher.getInstance("3DES_EDE/CBC/PKCS5Padding", "Java");
            jsafe_secretkey = jsafe_symmetriccipher.getBlankKey();
            byte abyte4[] = createKey(abyte3);
            jsafe_secretkey.setSecretKeyData(abyte4, 0, abyte4.length);
            jsafe_symmetriccipher.setIV(abyte2, 0, abyte2.length);
        } catch (Exception exception) {
            System.err.println("%3DES-F-CIPHERINIT; Exception caught while initializing the cipher object.");
            System.err.println("; " + exception.toString());
            return null;
        }
        try {
            jsafe_symmetriccipher.encryptInit(jsafe_secretkey);
            abyte1 = new byte[jsafe_symmetriccipher.getOutputBufferSize(abyte0.length)];
            int i = jsafe_symmetriccipher.encryptUpdate(abyte0, 0, abyte0.length, abyte1, 0);
            int j = jsafe_symmetriccipher.encryptFinal(abyte1, i);
        } catch (Exception exception1) {
            System.err.println("%3DES-F-NCRYPT; Exception caught while encrypting.");
            System.err.println("; " + exception1.toString());
            return null;
        }
        String s2 = null;
        try {
//            BASE64Encoder base64encoder = new BASE64Encoder();
//            s2 = base64encoder.encodeBuffer(abyte1);
            s2 = DatatypeConverter.printBase64Binary(abyte1);
        } catch (Exception exception2) {
            System.err.println("Exception caught while base64 decoding." + exception2);
        }
        jsafe_secretkey.clearSensitiveData();
        jsafe_symmetriccipher.clearSensitiveData();
        for (int k = 0; k < abyte0.length; k++) {
            abyte0[k] = 0;
        }

        for (int l = 0; l < abyte1.length; l++) {
            abyte1[l] = 0;
        }

        return s2;
    }

    static void gen1(byte abyte0[]) {
        abyte0[0] = code[8];
        abyte0[1] = code[13];
        abyte0[2] = code[7];
        abyte0[3] = code[2];
        abyte0[4] = code[15];
        abyte0[5] = code[6];
        abyte0[6] = code[1];
        abyte0[7] = code[11];
        abyte0[8] = code[0];
        abyte0[9] = code[3];
        abyte0[10] = code[9];
        abyte0[11] = code[15];
        abyte0[12] = code[1];
        abyte0[13] = code[10];
        abyte0[14] = code[10];
        abyte0[15] = code[12];
    }

    static void gen2(byte abyte0[]) {
        abyte0[0] = code[3];
        abyte0[1] = code[11];
        abyte0[2] = code[10];
        abyte0[3] = code[3];
        abyte0[4] = code[15];
        abyte0[5] = code[1];
        abyte0[6] = code[9];
        abyte0[7] = code[5];
        abyte0[8] = code[1];
        abyte0[9] = code[13];
        abyte0[10] = code[2];
        abyte0[11] = code[11];
        abyte0[12] = code[5];
        abyte0[13] = code[13];
        abyte0[14] = code[15];
        abyte0[15] = code[3];
    }

    static void gen3(byte abyte0[]) {
        abyte0[0] = code[11];
        abyte0[1] = code[1];
        abyte0[2] = code[4];
        abyte0[3] = code[8];
        abyte0[4] = code[6];
        abyte0[5] = code[15];
        abyte0[6] = code[2];
        abyte0[7] = code[11];
        abyte0[8] = code[0];
        abyte0[9] = code[13];
        abyte0[10] = code[2];
        abyte0[11] = code[11];
        abyte0[12] = code[15];
        abyte0[13] = code[10];
        abyte0[14] = code[12];
        abyte0[15] = code[0];
    }

    public static byte[] hex2byte(String cadena) {
        int j = 0;
        int size = 0;
        String sHex = null;

        size = cadena.length() / 2;
        byte[] respuesta = new byte[size];

        for (int i = 0; i <= (cadena.length() - 2); i = i + 2) {
            sHex = cadena.substring(i, i + 2);
            respuesta[j] = (byte) (Integer.parseInt(sHex, 16));
            j++;
        }

        return respuesta;
    }

    static void memcpy(byte abyte0[], int i, byte abyte1[], int j, int k) {
        for (int l = 0; l < k; l++) {
            abyte0[l + i] = abyte1[l + j];
        }

    }

    static void memset(byte abyte0[], int i, int j) {
        for (int k = 0; k < j; k++) {
            abyte0[k] = (byte) i;
        }

    }

    static int nc_decode_key(byte abyte0[], byte abyte1[]) {
        int k = 0;
        for (int j = 0; j < CODE_LENGTH;) {
            int i;
            for (i = 0; code[i] != abyte0[j] && i < CODE_LENGTH; i++);

            if (i < CODE_LENGTH) {
                abyte1[k] = (byte) (i << 4);
            } else {
                return 0;
            }
            i = 0;
            for (j++; code[i] != abyte0[j] && i < CODE_LENGTH; i++);
            if (i < CODE_LENGTH) {
                abyte1[k] |= (byte) i;
            } else {
                return 0;
            }
            j++;
            k++;
        }

        return 1;
    }

    public static byte[] stringToBytes(String s) {
        int i = s.length();
        byte abyte0[] = new byte[i];
        try {
            for (int j = 0; j < i; j++) {
                abyte0[j] = (byte) s.charAt(j);
            }

        } catch (NumberFormatException _ex) {
            System.out.println("stringToBytes() - Could not parse string into bytes!");
            System.exit(2);
        }
        return abyte0;
    }

    public NcCrypt() {
    }

    private static String charToHex(char c) {
        char c1 = c;
        return new String(hexDigit[c1 / 16] + "" + hexDigit[c1 % 16]);
    }

    public static final String IBM_COPYRIGHT = "Licensed Materials - Property of IBM\n\n5697-D24\n\n(c) Copyright IBM Corp. 1996 (1998). All Rights Reserved.\n\nUS Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.\n";
    static final int DES_KEY_STRING_LEN = 16;
    static final int DES_KEY_SIZE = 8;
    static final int TRIPLE_DES_KEY_SIZE = 24;
    static int CODE_LENGTH = 16;
    static byte code[] = {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
        97, 98, 99, 100, 101, 102
    };
    private static char hexDigit[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'
    };

}
