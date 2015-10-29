/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ubl;

import java.util.Arrays;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pe.labtech.einvoice.commons.xmlsecurity.SignatureException;
import pe.labtech.einvoice.commons.xmlsecurity.UniversalNamespaceCache;
import static pe.labtech.ubl.model.Namespaces.CAC;
import static pe.labtech.ubl.model.Namespaces.CBC;
import static pe.labtech.ubl.model.Namespaces.CREDIT;
import static pe.labtech.ubl.model.Namespaces.DEBIT;

/**
* Clase DocumentMorpher.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class DocumentMorpher {

    private static final String CREDIT_NOTE = "07";
    private static final String DEBIT_NOTE = "08";

    public static void morph(String documentType, Document document) {
        switch (documentType) {
            case CREDIT_NOTE:
                morph(
                        document,
                        a(CREDIT, "CreditNote"),
                        new String[][]{
                            a(CAC, "InvoiceLine", "CreditNoteLine"),
                            a(CBC, "InvoicedQuantity", "CreditedQuantity")
                        },
                        new String[][]{
                            a(CBC, "InvoiceTypeCode"),
                            a(CAC, "AdditionalItemProperty")
                        }
                );
                break;
            case DEBIT_NOTE:
                morph(
                        document,
                        a(DEBIT, "DebitNote"),
                        new String[][]{
                            a(CAC, "InvoiceLine", "DebitNoteLine"),
                            a(CBC, "InvoicedQuantity", "DebitedQuantity"),
                            a(CAC, "LegalMonetaryTotal", "RequestedMonetaryTotal")
                        },
                        new String[][]{
                            a(CBC, "InvoiceTypeCode"),
                            a(CAC, "AdditionalItemProperty")
                        }
                );
                break;
        }
    }

    public static void morph(Document document, String[] root, String[][] renames, String[][] removes) {
        document.renameNode(document.getDocumentElement(), root[0], root[1]);
        renameTags(document, renames);
        removeTags(document, removes);
    }

    public static void renameTags(Document document, String[]... tags) {
        Arrays.stream(tags).forEach(d -> {
            DocumentMorpher.remaneTag(document, d[0], d[1], d[2]);
            DocumentMorpher.adjustPrefix(document, d[0], d[2]);
        });
    }

    public static void remaneTag(Document document, String namespace, String sourceTag, String targetTag) {
        NodeList lines = document.getElementsByTagNameNS(namespace, sourceTag);
        for (int index = 0; index < lines.getLength(); index++) {
            Node n = lines.item(index);
            document.renameNode(n, namespace, targetTag);
        }
    }

    public static void adjustPrefix(Document document, String namespace, String element) {
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

    public static void removeTags(Document document, String[]... tags) {
        Arrays.stream(tags).forEach(tag -> removeTag(document, tag[0], tag[1]));
    }

    public static void removeTag(Document document, String namespace, String tag) {
        NodeList type = document.getElementsByTagNameNS(namespace, tag);
        for (int index = 0; index < type.getLength(); index++) {
            Node n = type.item(index);
            n.getParentNode().removeChild(n);
        }
    }

    private static XPath buildXpath(Document document) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new UniversalNamespaceCache(document));
        return xpath;
    }

    private static <T> T[] a(T... items) {
        return items;
    }

}
