/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
* Clase ZipTools.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class ZipTools {

    /**
     * Default buffer size for read write operations set at 10KB.
     */
    public static final int DEFAULT_BUFFER_SIZE = 10240;

    /**
     * Creates a zipped byte array with a single compressed entry from data.
     *
     * @param entryName name for the data
     * @param data input data
     * @return
     */
    public static byte[] compress(String entryName, byte[] data) {
        //generar el stream zipeado
        byte[] zippedUBL;
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream(10240);
                ZipOutputStream output = new ZipOutputStream(bos);
                ByteArrayInputStream input = new ByteArrayInputStream(data)) {
            output.putNextEntry(new ZipEntry(entryName));

            byte[] buffer = new byte[10240];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.closeEntry();
            output.finish();
            output.close();
            zippedUBL = bos.toByteArray();
        } catch (IOException ex) {
            //mark as error
            zippedUBL = null;
        }
        return zippedUBL;
    }

    /**
     * Creates a zip stream with a single compressed entry. out and in are not
     * closed after method call.
     *
     * @param entryName name for the data
     * @param in input data
     * @param out output data
     */
    public static void compress(String entryName, InputStream in, OutputStream out) {
        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            zos.putNextEntry(new ZipEntry(entryName));
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) >= 0) {
                out.write(buffer, 0, bytesRead);
            }
            zos.closeEntry();
            zos.finish();
        } catch (IOException ex) {
            Logger.getLogger(ZipTools.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
