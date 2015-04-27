/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.offline;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import pe.labtech.ubl.model.InvoicePrefixMapper;

/**
 *
 * @author Carlos
 */
public class Commons {

    public static org.w3c.dom.Document toXmlDocument(Object o) {
        try {
            JAXBContext jbc = JAXBContext.newInstance(o.getClass());
            Marshaller m = jbc.createMarshaller();
            m.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new InvoicePrefixMapper());
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(MarshallerProperties.INDENT_STRING, "    ");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            m.marshal(o, bos);
            Logger.getLogger(Commons.class.getName()).log(Level.INFO, "UBL GENERADO\n{0}", new String(bos.toByteArray()));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document xmlInvoice = db.newDocument();
            m.marshal(o, xmlInvoice);

            return xmlInvoice;
        } catch (JAXBException | ParserConfigurationException ex) {
            Logger.getLogger(Commons.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
