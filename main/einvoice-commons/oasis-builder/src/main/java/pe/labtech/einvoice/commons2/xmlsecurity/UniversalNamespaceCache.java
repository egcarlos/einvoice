/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.xmlsecurity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Carlos
 */
public class UniversalNamespaceCache implements NamespaceContext {

    private static final Logger logger = Logger.getLogger(UniversalNamespaceCache.class.getName());
    private static final String DEFAULT_NS = "##DEFAULT";
    private final Map<String, String> prefix2Uri = new HashMap<>();
    private final Map<String, String> uri2Prefix = new HashMap<>();

    /**
     * This constructor parses the document and stores all namespaces it can
     * find. If toplevelOnly is true, only namespaces in the root are used.
     *
     * @param document source document
     */
    public UniversalNamespaceCache(Document document) {
        examineNode(document.getDocumentElement());
    }

    /**
     * A single node is read, the namespace attributes are extracted and stored.
     *
     * @param node to examine
     * @param attributesOnly, if true no recursion happens
     */
    private void examineNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            storeAttribute((Attr) attribute);
        }
    }

    /**
     * This method looks at an attribute and stores it, if it is a namespace
     * attribute.
     *
     * @param attribute to examine
     */
    private void storeAttribute(Attr attribute) {
        // examine the attributes in namespace xmlns
        if (attribute.getNodeName().equals(XMLConstants.XMLNS_ATTRIBUTE)) {
            putInCache(DEFAULT_NS, attribute.getNodeValue());
        } else if (attribute.getPrefix().equals(XMLConstants.XMLNS_ATTRIBUTE)) {
            putInCache(attribute.getLocalName(), attribute.getNodeValue());
        }
    }

    private void putInCache(String prefix, String uri) {
        logger.fine(() -> "" + prefix + " -> " + uri);
        prefix2Uri.put(prefix, uri);
        uri2Prefix.put(uri, prefix);
    }

    /**
     * This method is called by XPath. It returns the default namespace, if the
     * prefix is null or "".
     *
     * @param prefix to search for
     * @return uri
     */
    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null || prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
            return prefix2Uri.get(DEFAULT_NS);
        } else {
            return prefix2Uri.get(prefix);
        }
    }

    /**
     * This method is not needed in this context, but can be implemented in a
     * similar way.
     *
     * @param namespaceURI
     * @return
     */
    @Override
    public String getPrefix(String namespaceURI) {
        return uri2Prefix.get(namespaceURI);
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        // Not implemented
        return null;
    }
}
