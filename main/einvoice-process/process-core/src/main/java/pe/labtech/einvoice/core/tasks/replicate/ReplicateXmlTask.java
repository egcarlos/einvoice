/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.replicate;

import java.text.MessageFormat;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.tools.DatabaseCommons;
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

        StringBuilder command = new StringBuilder();

        //Fragmento de código para agregar la optimización de envío de
        //documentos en ambiente de homologación.
        //TODO parametriar para activación permanente
        String dds = "0";
        //if (isSunatTest(document)) {
        //    dds = "1";
        //}

        command
                .append("<ReplicateXmlCmd ")
                .append("\n        ").append("declare-sunat=\"1\" ")
                .append("\n        ").append("\n\tdeclare-direct-sunat=\"").append(dds).append("\" ")
                .append("\n        ").append("\n\tpublish=\"1\" ")
                .append("\n        ").append("\n\toutput=\"PDF\"")
                .append(">")
                .append("\n    ").append("<parametros/>")
                .append("\n    ").append("<parameter value=\"").append(buildClientId(document)).append("\" name=\"idEmisor\"/>");

        if (document.getDocumentType().startsWith("R")) {
            String correoEmisor = DatabaseCommons.getAttributeValue(prv, document, "correoEmisor");
            command.append("<parameter value=\"").append(correoEmisor).append("\" name=\"correoEmisor\"/>");
        }

        command.append("</ReplicateXmlCmd>");

        ServiceCommons.replicateXml(prv, loader, invoker, document, command.toString(), zippedUBL);
    }

    private boolean isSunatTest(Document document) {
        String prefix = document.getDocumentNumber().substring(0, 4);
        switch (prefix) {
            case "BB11":
            case "BB12":
            case "BB13":
            case "BB14":
            case "BB50":
            case "FF11":
            case "FF12":
            case "FF13":
            case "FF14":
            case "FF50":
                return true;
        }
        return false;
    }

    private static Object buildClientId(Document d) {
        return d.getClientId().contains("-") ? d.getClientId().split("-")[1] : d.getClientId();
    }

    //TODO extraer la logica de los ID a otra clase.
    //TODO homologar las tablas de datos para aceptar un solo modo de operación
    private String buildEntryName(Document document) {
        if (document.getDocumentType().startsWith("R")) {
            //caso de resumenes
            return buildClientId(document) + "-" + document.getDocumentNumber();
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
