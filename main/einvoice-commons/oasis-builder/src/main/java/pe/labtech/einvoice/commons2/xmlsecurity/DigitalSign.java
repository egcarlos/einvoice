/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.xmlsecurity;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.transform.OutputKeys;
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
import pe.labtech.einvoice.commons2.ubl.model.Namespaces;

/**
 *
 * @author Carlos
 */
public class DigitalSign {

    static {
        System.setProperty("org.apache.xml.security.ignoreLineBreaks", "true");
        org.apache.xml.security.Init.init();
        
    }

    /**
     * Signs the document using the specifications from sunat.
     *
     * @param document
     * @param key
     * @param certificate
     */
    public void sign(Document document, Key key, X509Certificate certificate) {
        new PortalSign().sign(document, (PrivateKey) key, certificate);

//        Node targetNode = locateTargetNode(document);
//        applysign(key, targetNode, certificate);
        //removed since it seems to interfere with signature validation
        //this.fixFormat(document);
    }

    /**
     * Serialize to a text representation of the document.
     *
     * @param document
     * @return
     */
    public String createTextRepresentation(Document document) {
        byte[] representation = createRepresentation(document, Charset.defaultCharset().name());
        return new String(representation);
    }

    public String createTextRepresentation(Document document, String charsetName) {
        try {
            byte[] representation = createRepresentation(document, charsetName);
            return new String(representation, charsetName);
        } catch (UnsupportedEncodingException ex) {
            throw new SignatureException("Invalid charset " + charsetName, ex);
        }
    }

    public byte[] createRepresentation(Document document, String charsetName) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(10240);
            TransformerFactory tf = TransformerFactory.newInstance();
//            tf.setAttribute(OutputKeys.ENCODING, charsetName);
            Transformer t = tf.newTransformer();
            t.setParameter(OutputKeys.ENCODING, charsetName);
            t.transform(new DOMSource(document), new StreamResult(bos));
            return bos.toByteArray();
        } catch (TransformerException ex) {
            throw new SignatureException("Unable to serialize XML", ex);
        }
    }

    /**
     * Returns hash and signature.
     *
     * @param document
     * @return
     */
    public String[] getResponses(Document document) {
        try {
            XPath xpath = buildXpath(document);
            String prefix = xpath
                    .getNamespaceContext()
                    .getPrefix("http://www.w3.org/2000/09/xmldsig#");
            Node dvn = (Node) xpath.evaluate("//" + prefix + ":DigestValue", document, XPathConstants.NODE);

            Node svn = (Node) xpath.evaluate("//" + prefix + ":SignatureValue", document, XPathConstants.NODE);
            return new String[]{dvn.getTextContent(), svn.getTextContent()};
        } catch (XPathExpressionException ex) {
            Logger.getLogger(DigitalSign.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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
                    .getPrefix(Namespaces.DS);

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
