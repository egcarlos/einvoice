/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;

/**
 *
 * @author Carlos
 */
@Stateless
public class DeclareOfflineTask implements DeclareOfflineTaskLocal {

    @Inject
    private EBizGenericInvoker invoker;

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @Override
    @Asynchronous
    public void handle(final Long t) {
        //marcar en trabajo
        Document document = prv.seek(e -> e.find(Document.class, t));

        //armar el nombre del entrada
        String name = buildEntryName(document);

        //obtener los datos del local ubl
        byte[] zippedUBL = prv.seek(e -> e
                .createQuery(
                        "SELECT O.data FROM DocumentData O WHERE O.document.id = :id AND O.name = (:name || '.zip')",
                        byte[].class
                )
                .setParameter("id", t)
                .setParameter("name", name)
                .getSingleResult()
        );

        try {
            String command = "<ReplicateXmlCmd declare-sunat=\"1\" declare-direct-sunat=\"0\" publish=\"1\" output=\"PDF\">"
                    + "<parametros/>"
                    + "<parameter value=\"" + buildClientId(document) + "\" name=\"idEmisor\"/>"
                    + "</ReplicateXmlCmd>";
            loader.createEvent(document, "INFO", command);
            String response = invoker.replicateXml(command, zippedUBL, null);
            loader.createEvent(document, "INFO", response);
        } catch (WebServiceException ex) {
            loader.createEvent(document, "ERROR", exToString(ex, "Error raised while replicating xml"));
        }

        prv.handle(e -> e
                .createQuery(
                        "UPDATE Document O SET O.step = 'SIGN', O.status = 'SYNC' WHERE O.id = :id"
                )
                .setParameter("id", document.getId())
                .executeUpdate()
        );
    }

    private static Object buildClientId(Document d) {
        return d.getClientId().contains("-") ? d.getClientId().split("-")[1] : d.getClientId();
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

    private String buildEntryName(Document document) {
        return MessageFormat.format(
                "{0}-{1}-{2}",
                buildClientId(document),
                document.getDocumentType(),
                document.getDocumentNumber().startsWith("R") ? document.getDocumentNumber().substring(3) : document.getDocumentNumber()
        );
    }
}
