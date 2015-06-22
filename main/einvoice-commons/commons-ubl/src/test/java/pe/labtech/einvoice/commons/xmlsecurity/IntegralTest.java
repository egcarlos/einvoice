/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import pe.labtech.einvoice.commons.ubl.InvoiceBuilder;
import pe.labtech.einvoice.commons.ubl.InvoiceLineBuilder;
import pe.labtech.ubl.model.Invoice;

/**
 *
 * @author carloseg
 */
public class IntegralTest {

    @Test
    public void jbcontext() throws JAXBException, ParserConfigurationException {
        final String moneda = "PEN";
        final String tipoDocumento = "01";
        final String number = "F000-00000000";
        final String date = "yyyy-mm-dd";
        final String tipoIdEmisor = "6";
        final String idEmisor = "66666666666";
        final String razonSocialEmisor = "razon social del emisor";
        final String tipoIdCliente = "1";
        final String idCliente = "11111111";
        final String razonSocialCliente = "razon social del cliente";

        final InvoiceBuilder invoiceBuilder = new InvoiceBuilder()
                .init(tipoDocumento, number, date, moneda, tipoIdEmisor, idEmisor, razonSocialEmisor, tipoIdCliente, idCliente, razonSocialCliente)
                .addAmount("1001", BigDecimal.valueOf(10000, 2))
                .addAmount("1002", BigDecimal.valueOf(0, 2))
                .addAmount("1003", BigDecimal.valueOf(0, 2))
                .addAmount("2005", BigDecimal.valueOf(0, 2))
                .addNote("1000", "CIENTO DIECIOCHO Y 00/100 NUEVOS SOLES")
                .addCustomNote("9999", "COMENTARIO")
                .addTax("1000", "IGV", "VAT", BigDecimal.valueOf(1800, 2))
                //                .addTotalCharge(()null)
                .addTotalPayable(BigDecimal.valueOf(11800, 2))
                .addLine(
                        new InvoiceLineBuilder()
                        .init("1", BigDecimal.ONE, "NIU", "IH-107", "articulo 1", "PEN", BigDecimal.valueOf(10000, 2), "01", BigDecimal.valueOf(11800, 2), BigDecimal.valueOf(10000, 2))
                        .addTax("1000", "IGV", "VAT", BigDecimal.valueOf(1800, 2), "10", null)
                        .compile()
                )
                .addOrderReference("12345678");

//        m.marshal(invoice, System.out);
        Document document = invoiceBuilder.document("UTF-8");

        DigitalSign ds = new DigitalSign();

        try (FileOutputStream fos = new FileOutputStream("out.xml")) {
            fos.write(ds.createRepresentation(document, "UTF-8"));
            fos.flush();
        } catch (IOException ex) {
            Logger.getLogger(IntegralTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Invoice i = invoiceBuilder.compile();
        JAXBContext ctx = JAXBContext.newInstance(Invoice.class);

        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        m.marshal(i, System.out);

        System.out.println();
        System.out.println();
        System.out.println();

        document.setXmlStandalone(true);

        String text = ds.createTextRepresentation(document, "UTF-8");
        System.out.println(text);
    }

}
