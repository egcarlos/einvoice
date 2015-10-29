package pe.labtech.einvoice.commons2.xmlsecurity;

import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import org.apache.xml.security.exceptions.XMLSecurityException;

/**
 * User: RAC Date: 06/03/12
 */
public class PortalSign {

    @PostConstruct
    public void init() {
        org.apache.xml.security.Init.init();
    }

    public void sign(Document doc, PrivateKey privateKey, X509Certificate cert) {
        try {
            XMLSignature xmlSignature = new XMLSignature(doc, "", XMLSignature.ALGO_ID_SIGNATURE_RSA);
            xmlSignature.getElement().setAttribute("Id", "signatureKG");

            Element elemento = doc.getDocumentElement();
            NodeList lista1 = elemento.getChildNodes();
            Node segundoNodo = lista1.item(0);

            Element ublExtensionsType = doc.createElement("ext:UBLExtensions");
            Element ublExtensionType = doc.createElement("ext:UBLExtension");
            Element extensionContentType = doc.createElement("ext:ExtensionContent");
            extensionContentType.appendChild(xmlSignature.getElement());
            ublExtensionType.appendChild(extensionContentType);
            ublExtensionsType.appendChild(ublExtensionType);
            if (segundoNodo != null && segundoNodo.getNodeName().startsWith("ext:UBLExtensions")) {
                segundoNodo.appendChild(ublExtensionType);
            } else {
                //este caso nunca ocurre
                elemento.insertBefore(ublExtensionsType, elemento.getFirstChild());
            }

            Transforms transforms = new Transforms(doc);
            transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);

            xmlSignature.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

            xmlSignature.addKeyInfo(cert);
            xmlSignature.addKeyInfo(cert.getPublicKey());
            xmlSignature.sign(privateKey);

        } catch (RuntimeException ex) {
            throw ex;
        } catch (XMLSecurityException ex) {
            throw new RuntimeException(ex);
        }

    }

}
