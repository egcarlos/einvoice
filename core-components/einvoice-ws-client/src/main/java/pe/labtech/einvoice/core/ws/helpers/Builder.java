/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Carlos
 */
public class Builder {

    public String buildQuery(String clientId, String documentType, String documentNumber) {
        return buildQuery(clientId, documentType, documentNumber, documentNumber);
    }

    public String buildQuery(String clientId, String documentType, String firstDocument, String lastDocument) {
        String serial = firstDocument.split("-")[0];
        String firstNumber = firstDocument.split("-")[1];
        String lastNumber = lastDocument.split("-")[1];
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
     *
     * @param clientId
     * @param documentType
     * @param documentNumber
     * @return
     */
    public String buildPublish(String clientId, String documentType, String documentNumber) {
        return this.buildPublish(clientId, documentType, documentNumber, false);
    }

    /**
     *
     * @param clientId
     * @param documentType
     * @param documentNumber
     * @param declare bandera que indica si el documento se debe declarar en
     * l&iacute;nea
     * @return
     */
    public String buildPublish(String clientId, String documentType, String documentNumber, boolean declare) {
        return this.buildPublish(clientId, documentType, documentNumber, documentNumber, declare);
    }

    /**
     *
     * @param clientId
     * @param documentType
     * @param firstDocument
     * @param lastDocument
     * @return
     */
    public String buildPublish(String clientId, String documentType, String firstDocument, String lastDocument) {
        return this.buildPublish(clientId, documentType, firstDocument, lastDocument, false);
    }

    /**
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
        String serial = firstDocument.split("-")[0];
        String firstNumber = firstDocument.split("-")[1];
        String lastNumber = lastDocument.split("-")[1];
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
     *
     * @param clientId RUC del emisor del documento
     * @param documentType tipo de documento
     * @param firstDocument primer documento en el formato F###-########
     * @param lastDocument ultimo documento en el formato F###-########, la
     * serie del documento debe ser igual que el primero documento
     * @return
     */
    public String buildDeclare(String clientId, String documentType, String firstDocument, String lastDocument) {
        String serial = firstDocument.split("-")[0];
        String firstNumber = firstDocument.split("-")[1];
        String lastNumber = lastDocument.split("-")[1];

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

    /**
     *
     * @param <T>
     * @param o
     * @return
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
            throw new RuntimeException("Unable to marshall " + o + ". Possible caus is... " + ex.getMessage(), ex);
        }
    }

    public <T> T unmarshall(Class<T> clazz, String text) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            T t = (T) u.unmarshal(new StringReader(text));
            return t;
        } catch (JAXBException ex) {
            throw new RuntimeException("Unable to unmarshall " + text + ". Possible caus is... " + ex.getMessage(), ex);
        }

    }
}
