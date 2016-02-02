/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.tasks.replicate;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.model.DocumentLoaderLocal;
import pe.labtech.einvoice.core.model.PrivateDatabaseManagerLocal;
import pe.labtech.einvoice.core.tasks.tools.DatabaseCommons;
import pe.labtech.einvoice.core.tasks.tools.ServiceCommons;
import pe.labtech.einvoice.core.tasks.tools.Tools;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;

/**
 * Clase ReplicateXmlTask.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Stateless
public class ReplicateXmlTask implements ReplicateXmlTaskLocal {

    @Inject
    private EBizGenericInvoker invoker;

    @EJB
    private DocumentLoaderLocal loader;

    @EJB
    private PrivateDatabaseManagerLocal prv;

    /**
     * Replica un documento.
     *
     * @param t identificador del documento.
     */
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

        //armado de la secuencia de comandos
        String options = Tools.toMap(String.class, String.class,
                "declare-sunat", "1",
                "declare-direct-sunat", "0",
                "publish", "1",
                "output", "PDF"
        ).entrySet().stream()
                .map(e -> MessageFormat.format("{0}=\"{1}\"", e.getKey(), e.getValue()))
                .reduce("", (a, b) -> a + " " + b);

        //armado de la secuencia de parametros
        Map<String, String> parametersMap = new LinkedHashMap<>();
        //closure para procesar los atributos asociados
        BiConsumer<String, String> parameterConsumer = (dbName, xmlName) -> {
            String value = DatabaseCommons.getAttributeValue(prv, document, dbName);
            if (value != null) {
                parametersMap.put(xmlName, value);
            }
        };

        parametersMap.put("idEmisor", buildClientId(document));
        parameterConsumer.accept("correoEmisor", "correoEmisor");
        parameterConsumer.accept("correoAdquiriente", "correoAdquiriente");

        String parameters = parametersMap.entrySet().stream()
                .map(e -> MessageFormat.format("<parameter value=\"{1}\" name=\"{0}\" />", e.getKey(), e.getValue()))
                .reduce("", (a, b) -> a + b);

        command
                .append("<ReplicateXmlCmd ").append(options).append(">")
                .append("<parametros/>")
                .append(parameters)
                .append("</ReplicateXmlCmd>");

        ServiceCommons.replicateXml(prv, loader, invoker, document, command.toString(), zippedUBL);
    }

    /**
     * Usado solo en builds de homologación para establecer con
     * declare-direct-sunat = 1.
     *
     * @param document
     * @return
     * @deprecated
     */
    @Deprecated
    public boolean isSunatTest(Document document) {
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

    /**
     * Obtiene el ruc del emisor.
     *
     * @param d documento
     * @return ruc del emisor
     */
    public static String buildClientId(Document d) {
        return d.getClientId().contains("-") ? d.getClientId().split("-")[1] : d.getClientId();
    }

    /**
     * Crea el identificador de documento en formato sunat.
     *
     * @param document documento
     * @return identificador formato suant
     */
    public String buildEntryName(Document document) {
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
