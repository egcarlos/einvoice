/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.ws.helpers;

import pe.labtech.einvoice.core.ws.messages.request.CommandParameter;
import pe.labtech.einvoice.core.ws.messages.request.DeclareDocument;
import pe.labtech.einvoice.core.ws.messages.request.PublishDocument;
import pe.labtech.einvoice.core.ws.messages.request.QueryDocument;
import pe.labtech.einvoice.core.ws.messages.request.SignDocument;
import pe.labtech.einvoice.core.ws.messages.request.SignSummary;
import pe.labtech.einvoice.core.ws.model.Document;
import pe.labtech.einvoice.core.ws.model.Summary;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Clase Builder.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
public class Builder {

    /**
     * Construye un mensaje XML para realizar consultas al servidor.
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento a consultar
     * @param documentNumber serie y numero del documento a consultar
     * @return XML de consulta al servidor
     */
    public String buildQuery(String clientId, String documentType, String documentNumber) {
        return buildQuery(clientId, documentType, documentNumber, documentNumber);
    }

    /**
     * Construye un mensaje XML para realizar consultas al servidor. Se debe
     * considerar que el presente metodo no realiza validaciones sobre los datos
     * y debe verificarse como mínimo los formatos antes de realizar la
     * consulta.
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento a consultar
     * @param firstDocument primer documento en el formato F###-########
     * @param lastDocument ultimo documento en el formato F###-########, la
     * @return XML de consulta al servidor
     */
    public String buildQuery(String clientId, String documentType, String firstDocument, String lastDocument) {
        String serial = extractGroup(firstDocument);
        String firstNumber = extractNumber(firstDocument);
        String lastNumber = extractNumber(lastDocument);
        QueryDocument query = new QueryDocument(
                "PDF",
                Arrays.asList(
                        new CommandParameter("idEmisor", clientId),
                        new CommandParameter("tipoDocumento", documentType),
                        new CommandParameter("serieGrupoDocumento", serial),
                        new CommandParameter("numeroCorrelativoInicio", firstNumber),
                        new CommandParameter("numeroCorrelativoFin", lastNumber)
                ));
        return marshall(query);
    }

    /**
     * Construye un requerimiento de firma de un resúmen de comprobantes ya sea
     * de declaración o de baja.
     *
     * El comportamiento por defecto de los resúmens es de declaración directa a
     * sunat. Esto se verá reflejado en los flags del mensaje.
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento a consultar
     * @param output tipo de salida de la consulta
     * @param summary objeto representando al resumen de documento
     * @return XML de consulta al servidor
     */
    public String buildSignSummary(String clientId, String documentType, String output, Summary summary) {
        SignSummary signSummary = new SignSummary("1", "1", output,
                Arrays.asList(
                        new CommandParameter("idEmisor", clientId),
                        new CommandParameter("tipoDocumento", documentType)
                ),
                Arrays.asList(
                        summary
                ));
        return marshall(signSummary);
    }

    /**
     * Construye un requerimiento de firma de documento (boleta, factura, nota
     * de crédito o nota de débito).
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento
     * @param output tipo de salida del documento
     * @param publish bandera de publicacion
     * @param declare bandera de declaracion
     * @param directDeclare bandera de declaración directa
     * @param status estado a asignar al adocumento después del registro
     * @param documents lista de documentos para firmar
     * @return
     */
    public String buildSign(String clientId, String documentType, String output,
            boolean publish, boolean declare, boolean directDeclare, String status, List<Document> documents) {
        SignDocument signDocument = new SignDocument(
                output,
                publish ? "1" : "0",
                declare ? "1" : "0",
                directDeclare ? "1" : "0",
                Arrays.asList(
                        new CommandParameter("idEmisor", clientId),
                        new CommandParameter("tipoDocumento", documentType),
                        new CommandParameter("estadoDocumento", status)
                ),
                documents
        );
        return this.marshall(signDocument);
    }

    /**
     * Construye un requerimiento de firma de documento (boleta, factura, nota
     * de crédito o nota de débito).
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento
     * @param output tipo de salida del documento
     * @param publish bandera de publicacion
     * @param declare bandera de declaracion
     * @param directDeclare bandera de declaración directa
     * @param status estado a asignar al adocumento después del registro
     * @param documents arreglo de documentos para firmar
     * @return
     */
    public String buildSign(String clientId, String documentType, String output,
            boolean publish, boolean declare, boolean directDeclare, String status, Document... documents) {
        return this.buildSign(
                clientId,
                documentType,
                output,
                publish,
                declare,
                directDeclare,
                status,
                Arrays.asList(documents)
        );
    }

    /**
     * Construye un mensaje XML para dar la indicación de publicación en el
     * portal de un documento.
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento
     * @param documentNumber numero de documento
     * @return
     */
    public String buildPublish(String clientId, String documentType, String documentNumber) {
        return this.buildPublish(clientId, documentType, documentNumber, false);
    }

    /**
     * Construye un mensaje XML para dar la indicación de publicación en el
     * portal de un documento.
     *
     * @param clientId RUC del emisor
     * @param documentType tipo de documento
     * @param documentNumber numero de documento
     * @param declare bandera que indica si el documento se debe declarar en
     * l&iacute;nea
     * @return
     */
    public String buildPublish(String clientId, String documentType, String documentNumber, boolean declare) {
        return this.buildPublish(clientId, documentType, documentNumber, documentNumber, declare);
    }

    /**
     * Construye un mensaje XML para dar la indicación de publicación en el
     * portal de un documento. Se debe considerar que el presente metodo no
     * realiza validaciones sobre los datos y debe verificarse como mínimo los
     * formatos antes de realizar la consulta.
     *
     * @param clientId
     * @param documentType
     * @param firstDocument primer documento en el formato F###-########
     * @param lastDocument ultimo documento en el formato F###-########, la
     * @return
     */
    public String buildPublish(String clientId, String documentType, String firstDocument, String lastDocument) {
        return this.buildPublish(clientId, documentType, firstDocument, lastDocument, false);
    }

    /**
     * Construye un mensaje XML para dar la indicación de publicación de un
     * documento. Se debe considerar que el presente metodo no realiza
     * validaciones sobre los datos y debe verificarse como mínimo los formatos
     * antes de realizar la consulta.
     *
     * @param clientId RUC del emisor del documento
     * @param documentType tipo de documento
     * @param firstDocument primer documento en el formato F###-########
     * @param lastDocument ultimo documento en el formato F###-########, la
     * serie del documento debe ser igual que el primero documento
     * @param declare bandera que indica si el documento se debe declarar en
     * l&iacute;nea
     * @return
     */
    public String buildPublish(String clientId, String documentType, String firstDocument, String lastDocument, boolean declare) {
        String serial = extractGroup(firstDocument);
        String firstNumber = extractNumber(firstDocument);
        String lastNumber = extractNumber(lastDocument);
        PublishDocument publish = new PublishDocument(declare ? "1" : "0", Arrays.asList(
                new CommandParameter("idEmisor", clientId),
                new CommandParameter("tipoDocumento", documentType),
                new CommandParameter("serieGrupoDocumento", serial),
                new CommandParameter("numeroCorrelativoInicio", firstNumber),
                new CommandParameter("numeroCorrelativoFin", lastNumber),
                new CommandParameter("correlativoExcluir", ""),
                new CommandParameter("estadoDocumento", "")
        ));
        return this.marshall(publish);
    }

    /**
     * Construye un mensaje XML para dar la indicación de declaración de un
     * documento. Se debe considerar que el presente metodo no realiza
     * validaciones sobre los datos y debe verificarse como mínimo los formatos
     * antes de realizar la consulta.
     *
     * @param clientId RUC del emisor del documento
     * @param documentType tipo de documento
     * @param documentNumber numero del documento en el formato F###-########
     * @return
     */
    public String buildDeclare(String clientId, String documentType, String documentNumber) {
        return this.buildDeclare(clientId, documentType, documentNumber, documentNumber);
    }

    /**
     * Construye un mensaje XML para dar la indicación de declaración de un
     * documento. Se debe considerar que el presente metodo no realiza
     * validaciones sobre los datos y debe verificarse como mínimo los formatos
     * antes de realizar la consulta.
     *
     * @param clientId RUC del emisor del documento
     * @param documentType tipo de documento
     * @param firstDocument primer documento en el formato F###-########
     * @param lastDocument ultimo documento en el formato F###-########, la
     * serie del documento debe ser igual que el primero documento
     * @return
     */
    public String buildDeclare(String clientId, String documentType, String firstDocument, String lastDocument) {
        String serial = extractGroup(firstDocument);
        String firstNumber = extractNumber(firstDocument);
        String lastNumber = extractNumber(lastDocument);

        DeclareDocument declare = new DeclareDocument(Arrays.asList(
                new CommandParameter("idEmisor", clientId),
                new CommandParameter("tipoDocumento", documentType),
                new CommandParameter("serieGrupoDocumento", serial),
                new CommandParameter("numeroCorrelativoInicio", firstNumber),
                new CommandParameter("numeroCorrelativoFin", lastNumber),
                new CommandParameter("correlativoExcluir", "")
        ));

        return this.marshall(declare);
    }

    private String extractNumber(String firstDocument) {
        int i = firstDocument.lastIndexOf("-");
        return firstDocument.substring(i + 1);
    }

    private String extractGroup(String firstDocument) {
        int i = firstDocument.lastIndexOf("-");
        return firstDocument.substring(0, i);
    }

    /**
     * Genera el texto XML que representa a un objeto.
     *
     * @param <T> tipo genérico de la entidad
     * @param o objeto a serializar
     * @return texto xml que representa la entidad
     * @throws BuilderException cuando ocurre un error en el procesamiento
     */
    public <T> String marshall(T o) {
        try {
            //TODO fix memmory consumption of creating sucesive contexts for JAXB elements
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            m.setProperty("jaxb.formatted.output", true);
            m.marshal(o, bos);
            String s = new String(bos.toByteArray(), Charset.forName("UTF-8"));
            return s;
        } catch (JAXBException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw new BuilderException("Unable to marshall " + o + ". Possible caus is... " + ex.getMessage(), ex);
        }
    }

    /**
     * Genera el objeto representado por un texto XML.
     *
     * @param <T> tipo genérico de la entidad
     * @param clazz clase de la entidad a interpretar
     * @param text texto XML a interpretar
     * @return entidad leída desde el texto XML
     * @throws BuilderException cuando ocurre un error en el procesamiento
     */
    public <T> T unmarshall(Class<T> clazz, String text) throws BuilderException {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            T t = (T) u.unmarshal(new StringReader(text));
            return t;
        } catch (JAXBException ex) {
            throw new BuilderException("Unable to unmarshall " + text + ". Possible caus is... " + ex.getMessage(), ex);
        }

    }
}
