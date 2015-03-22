/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Carlos
 */
public class DigitalSign {

    /**
     * Signs the document using the specifications from sunat.
     *
     * @param document
     * @param key
     * @param certificate
     */
    public void sign(Document document, Key key, X509Certificate certificate) {
        Node targetNode = locateTargetNode(document);
        applysign(key, targetNode, certificate);
        this.fixFormat(document);

    }

    /**
     * Serialize to a text representation of the document.
     *
     * @param document
     * @return
     */
    public String createTextRepresentation(Document document) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.transform(new DOMSource(document), new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException ex) {
            throw new SignatureException("Unable to serialize XML", ex);
        }
    }

    private void applysign(Key key, Node targetNode, X509Certificate certificate) throws SignatureException {
        try {
            DOMSignContext dsc = new DOMSignContext(key, targetNode);
            XMLSignature signature = buildSignature(certificate);
            signature.sign(dsc);
        } catch (MarshalException | XMLSignatureException ex) {
            throw new SignatureException("Unable to sign document", ex);
        }
    }

    private XMLSignature buildSignature(X509Certificate certificate) {
        try {
            XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
            KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
            XMLSignature signature = signatureFactory.newXMLSignature(
                    signatureFactory.newSignedInfo(
                            signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                            signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                            Arrays.asList(
                                    signatureFactory.newReference(
                                            "",
                                            signatureFactory.newDigestMethod(DigestMethod.SHA1, null),
                                            Arrays.asList(signatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                                            null,
                                            null
                                    )
                            )
                    ),
                    keyInfoFactory.newKeyInfo(
                            Arrays.asList(
                                    keyInfoFactory.newX509Data(Arrays.asList(certificate)),
                                    keyInfoFactory.newKeyValue(certificate.getPublicKey())
                            )
                    ),
                    null,
                    "signatureKG",
                    null
            );
            return signature;
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | KeyException ex) {
            throw new SignatureException("Unable to create the signature entity", ex);
        }
    }

    private Node locateTargetNode(Document document) {
        try {
            XPath xpath = buildXpath(document);
            String prefix = xpath
                    .getNamespaceContext()
                    .getPrefix("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
            Node targetNode = (Node) xpath.evaluate(
                    "//" + prefix + ":UBLExtension[last()]/" + prefix + ":ExtensionContent",
                    document,
                    XPathConstants.NODE
            );
            return targetNode;
        } catch (XPathExpressionException ex) {
            throw new SignatureException("Unable to locate the target node for placinc signature", ex);
        }
    }

    private void fixFormat(Document document) {
        try {
            XPath xpath = buildXpath(document);
            String prefix = xpath
                    .getNamespaceContext()
                    .getPrefix("http://www.w3.org/2000/09/xmldsig#");

            //add prefix to signature elements
            {
                NodeList nodes = (NodeList) xpath.evaluate("//" + prefix + ":*", document, XPathConstants.NODESET);
                for (int i = 0; i < nodes.getLength(); i++) {
                    nodes.item(i).setPrefix(prefix);
                }
            }
            //remove the anonymous namespace from signature node since it's in the header
            {
                Node node = (Node) xpath.evaluate("//" + prefix + ":Signature", document, XPathConstants.NODE);
                node.getAttributes().removeNamedItem("xmlns");
            }
            //remove empty spaces from the certificate representation
            {
                Node node = (Node) xpath.evaluate("//" + prefix + ":X509Certificate", document, XPathConstants.NODE);
                NodeList nodes = node.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node item = nodes.item(i);
                    String nodeValue = item
                            .getNodeValue()
                            .replace("\n", "")
                            .replace("\r", "");
                    item.setNodeValue(nodeValue);
                }
            }
        } catch (XPathExpressionException ex) {
            throw new SignatureException("Unable to normalize signature elements", ex);
        }
    }

    private XPath buildXpath(Document document) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new UniversalNamespaceCache(document));
        return xpath;
    }

}
