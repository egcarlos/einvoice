/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentData;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.DATA_LOADED;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;

/**
 *
 * @author Carlos
 */
@Stateless
public class DeclareOfflineTask implements DeclareOfflineTaskLocal {

    @EJB
    private DocumentLoaderLocal loader;

    @Inject
    private EBizGenericInvoker invoker;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @Override
    @Asynchronous
    public void handle(final Long t) {
        //marcar en trabajo
        Document d = loader.loadForWork(t, null);

        //obtener los datos del local ubl
        byte[] localubl = prv.seek(e -> e
                .createQuery(
                        "SELECT O.data FROM DocumentData O WHERE O.document.id = :id AND O.name = :name",
                        byte[].class
                )
                .setParameter("id", t)
                .setParameter("name", "localUBL")
                .getSingleResult()
        );

        //nombre del archivo generado
        String fileName = MessageFormat.format("{0}-{1}-{2}.xml", buildClientId(d),
                d.getDocumentType(),
                d.getDocumentNumber().startsWith("R") ? d.getDocumentNumber().substring(3) : d.getDocumentNumber()
        );

        Logger.getLogger(this.getClass().getName()).info(() -> "Nombre de archivo " + fileName);

        byte[] zippedUBL = zipBytes(localubl, fileName);

        prv.handle(e -> {
            final DocumentData data = new DocumentData();
            data.setDocument(d);
            data.setName(fileName + ".zip");
            data.setData(zippedUBL);
            data.setStatus(DATA_LOADED);
            data.setReplicate(Boolean.FALSE);
            e.persist(data);
        });

        if (zippedUBL == null) {
            return;
        }

        try {
            String command = "<ReplicateXmlCmd declare-sunat=\"1\" declare-direct-sunat=\"0\" publish=\"1\" output=\"PDF\">"
                    + "<parameter value=\"" + buildClientId(d) + "\" name=\"idEmisor\"/>"
                    + "</ReplicateXmlCmd>";
            loader.createEvent(d, "INFO", command);
            String response = invoker.replicateXml(fileName, zippedUBL, null);
            loader.createEvent(d, "INFO", response);
        } catch (WebServiceException ex) {
            loader.createEvent(d, "ERROR", exToString(ex, "Error raised while replicating xml"));
        }

        prv.handle(e -> e
                .createQuery(
                        "UPDATE Document O SET O.step = 'SIGN', O.status = 'SYNC' WHERE O.id = :id"
                )
                .setParameter("id", d.getId())
                .executeUpdate()
        );
    }

    private static Object buildClientId(Document d) {
        return d.getClientId().contains("-") ? d.getClientId().split("-")[1] : d.getClientId();
    }

    public static byte[] zipBytes(byte[] bytes, String entryName) {
        //generar el stream zipeado
        byte[] zippedUBL;
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream(10240);
                ZipOutputStream output = new ZipOutputStream(bos);
                ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
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

    public static String exToString(Exception ex, String... headers) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            Arrays.stream(headers).forEach(s -> pw.println(s));
            pw.println();
            pw.println(ex.getMessage());
            pw.println();
            pw.println("Stack Trace");
            ex.printStackTrace(pw);
            pw.flush();
            pw.close();
            return sw.toString();
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }
}
