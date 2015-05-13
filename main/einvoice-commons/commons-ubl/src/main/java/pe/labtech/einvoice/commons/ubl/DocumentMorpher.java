/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pe.labtech.einvoice.commons.xmlsecurity.SignatureException;
import pe.labtech.einvoice.commons.xmlsecurity.UniversalNamespaceCache;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author Carlos
 */
public class DocumentMorpher {

    private static final String CREDIT_NOTE = "07";
    private static final String DEBIT_NOTE = "08";

    public static void morph(String documentType, Document document) {
        switch (documentType) {
            case CREDIT_NOTE:
                morphToCreditNote(document);
                break;
            case DEBIT_NOTE:
                morphToDebitNote(document);
                break;
        }
    }

    public static void morphToCreditNote(Document document) {
        //cambiar el root element
        Element root = document.getDocumentElement();
        document.renameNode(root, Namespaces.NS6, "CreditNote");
        {
            NodeList lines = document.getElementsByTagNameNS(Namespaces.CAC, "InvoiceLine");
            for (int index = 0; index < lines.getLength(); index++) {
                Node n = lines.item(index);
                document.renameNode(n, Namespaces.CAC, "CreditNoteLine");
            }
        }
        {
            NodeList quantities = document.getElementsByTagNameNS(Namespaces.CBC, "InvoicedQuantity");
            for (int index = 0; index < quantities.getLength(); index++) {
                Node n = quantities.item(index);
                document.renameNode(n, Namespaces.CBC, "CreditedQuantity");
            }
        }
        adjustPrefix(document, "CreditedQuantity", Namespaces.CBC);
        adjustPrefix(document, "CreditNoteLine", Namespaces.CAC);
        {
            NodeList type = document.getElementsByTagNameNS(Namespaces.CBC, "InvoiceTypeCode");
            for (int index = 0; index < type.getLength(); index++) {
                Node n = type.item(index);
                n.getParentNode().removeChild(n);
            }
        }
    }

    public static void morphToDebitNote(Document document) {
        //cambiar el root element
        Element root = document.getDocumentElement();
        document.renameNode(root, Namespaces.NS7, "DebitNote");
        {
            NodeList lines = document.getElementsByTagNameNS(Namespaces.CAC, "InvoiceLine");
            for (int index = 0; index < lines.getLength(); index++) {
                Node n = lines.item(index);
                document.renameNode(n, Namespaces.CAC, "DebitNoteLine");

            }
        }
        {
            NodeList quantities = document.getElementsByTagNameNS(Namespaces.CBC, "InvoicedQuantity");
            for (int index = 0; index < quantities.getLength(); index++) {
                Node n = quantities.item(index);
                document.renameNode(n, Namespaces.CBC, "DebitedQuantity");
            }
        }
        adjustPrefix(document, "DebitedQuantity", Namespaces.CBC);
        adjustPrefix(document, "DebitNoteLine", Namespaces.CAC);
        {
            NodeList type = document.getElementsByTagNameNS(Namespaces.CBC, "InvoiceTypeCode");
            for (int index = 0; index < type.getLength(); index++) {
                Node n = type.item(index);
                n.getParentNode().removeChild(n);
            }
        }
    }

    public static void adjustPrefix(Document document, String element, String namespace) {
        try {
            XPath xpath = buildXpath(document);
            String prefix = xpath
                    .getNamespaceContext()
                    .getPrefix(namespace);

            //add prefix to signature elements
            {
                NodeList nodes = (NodeList) xpath.evaluate("//" + prefix + ":" + element, document, XPathConstants.NODESET);
                for (int i = 0; i < nodes.getLength(); i++) {
                    nodes.item(i).setPrefix(prefix);
                }
            }
            //remove the anonymous namespace from node since it's in the header
            {
                NodeList nodes = (NodeList) xpath.evaluate("//" + prefix + ":" + element, document, XPathConstants.NODESET);
                for (int i = 0; i < nodes.getLength(); i++) {
                    if (nodes.item(i).getAttributes().getNamedItem("xmlns") != null) {
                        nodes.item(i).getAttributes().removeNamedItem("xmlns");
                    }
                }
            }
        } catch (XPathExpressionException ex) {
            throw new SignatureException("Unable to normalize signature elements", ex);
        }
    }

    private static XPath buildXpath(Document document) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new UniversalNamespaceCache(document));
        return xpath;
    }
}
