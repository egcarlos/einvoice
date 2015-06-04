/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.replicate;

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
import pe.labtech.einvoice.core.tasks.tools.ServiceCommons;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;

/**
 *
 * @author Carlos
 */
@Stateless
public class ReplicateXmlTask implements ReplicateXmlTaskLocal {

    @Inject
    private EBizGenericInvoker invoker;

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    @Override
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

        String command = "<ReplicateXmlCmd declare-sunat=\"1\" declare-direct-sunat=\"0\" publish=\"1\" output=\"PDF\">"
                + "<parametros/>"
                + "<parameter value=\"" + buildClientId(document) + "\" name=\"idEmisor\"/>"
                + "</ReplicateXmlCmd>";

        ServiceCommons.replicateXml(prv, loader, invoker, document, command, zippedUBL);
    }

    private static Object buildClientId(Document d) {
        return d.getClientId().contains("-") ? d.getClientId().split("-")[1] : d.getClientId();
    }

    //TODO extraer la logica de los ID a otra clase.
    //TODO homologar las tablas de datos para aceptar un solo modo de operación
    private String buildEntryName(Document document) {
        if (document.getDocumentType().startsWith("R")) {
            //caso de resumenes
            return document.getClientId() + "-" + document.getDocumentNumber();
        }
        //otros documentos
        return MessageFormat.format(
                "{0}-{1}-{2}",
                buildClientId(document),
                document.getDocumentType(),
                document.getDocumentNumber()
        );
    }
}
