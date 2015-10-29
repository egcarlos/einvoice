/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ubl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.w3c.dom.Document;
import pe.labtech.ubl.model.SummaryDocuments;
import pe.labtech.ubl.model.SummaryDocumentsPrefixMapper;
import pe.labtech.ubl.model.aggregate.AccountingParty;
import pe.labtech.ubl.model.aggregate.Signature;
import pe.labtech.ubl.model.extensions.ExtensionContent;
import pe.labtech.ubl.model.extensions.UBLExtension;
import pe.labtech.ubl.model.extensions.UBLExtensions;
import pe.labtech.ubl.model.sunat.SummaryDocumentsLine;

/**
* Clase SummaryDocumentsBuilder.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class SummaryDocumentsBuilder implements Builder<SummaryDocuments> {

    private static final JAXBContext JAXB_CONTEXT;
    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

    private SummaryDocuments item;

    static {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(SummaryDocuments.class);
        } catch (JAXBException ex) {
            context = null;
        }
        JAXB_CONTEXT = context;
        DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);

    }

    @Override
    public SummaryDocuments compile() {
        return item;
    }

    /**
     *
     * @param summaryId id del resumen siempre en formato RC-YYYYMMDD-NNN
     * @param referenceDate fecha de emisión de los documentos declarados
     * @param issueDate fecha de generación del resumen (coincide con la fecha
     * del summaryId)
     * @param issuerIdType tipo de documento emisor (siempre 01)
     * @param issuerId numero de documento emisor (RUC)
     * @param issuerName razon social del emisor
     * @return
     */
    public SummaryDocumentsBuilder init(
            final String summaryId,
            final String referenceDate,
            final String issueDate,
            final String issuerIdType,
            final String issuerId,
            final String issuerName
    ) {
        //crear un nuevo summary
        this.item = new SummaryDocuments();

        //crear el espacio para almacenar la firma
        this.item.setUBLExtensions(new UBLExtensions(new UBLExtension(new ExtensionContent())));
        this.item.setUBLVersionID("2.0");
        this.item.setCustomizationID("1.0");
        this.item.setID(summaryId);
        this.item.setReferenceDate(referenceDate);
        this.item.setIssueDate(issueDate);
        this.item.setSignature(new Signature("IDSignKG", issuerId, issuerName, "#signatureKG"));
        this.item.setAccountingSupplierParty(new AccountingParty(issuerIdType, issuerId, issuerName));

        return this;
    }

    public SummaryDocumentsBuilder addLine(SummaryDocumentsLine line) {
        if (line != null) {
            this.item.getSummaryDocumentsLine().add(line);
        }
        return this;
    }

    /**
     * Final operation used in order to get the XML representation of the UBL
     * document.
     *
     * @param charsetName
     * @return
     */
    public Document document(String charsetName) {
        try {
            Marshaller marshaller = getMarshaller(charsetName);
            DocumentBuilder db = getDocumentBuilder();
            Document document = db.newDocument();
            marshaller.marshal(this.compile(), document);
            return document;
        } catch (JAXBException ex) {
            throw new InvoiceBuilderException(ex);
        }
    }

    public JAXBContext getContext() {
        return JAXB_CONTEXT;
    }

    //TODO migrate to pooled implementation to enhance memmory usage
    public Marshaller getMarshaller(String charsetName) {
        try {
            Marshaller marshaller = getContext().createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charsetName);
            marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new SummaryDocumentsPrefixMapper());
            return marshaller;
        } catch (JAXBException ex) {
            throw new InvoiceBuilderException(ex);
        }
    }

    public DocumentBuilderFactory getDocumentBuilderFactory() {
        return DOCUMENT_BUILDER_FACTORY;
    }

    //this works for xml... should be implemented as shared memmory
    //TODO migrate to pooled implementation to enhance memmory usage
    public DocumentBuilder getDocumentBuilder() {
        try {
            return getDocumentBuilderFactory().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new InvoiceBuilderException(ex);
        }
    }

}
