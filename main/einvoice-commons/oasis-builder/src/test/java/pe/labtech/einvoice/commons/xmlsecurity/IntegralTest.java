/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import pe.labtech.einvoice.commons2.xmlsecurity.DigitalSign;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import pe.labtech.einvoice.commons2.ubl.builder.InvoiceBuilder;
import pe.labtech.einvoice.commons2.ubl.builder.InvoiceLineBuilder;

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
        final String date = "2015-09-23";
        final String tipoIdEmisor = "6";
        final String idEmisor = "66666666666";
        final String razonSocialEmisor = "razon social del emisor";
        final String tipoIdCliente = "1";
        final String idCliente = "11111111";
        final String razonSocialCliente = "razon social del cliente";

        final InvoiceBuilder invoiceBuilder = new InvoiceBuilder()
                .init()
                .addCurrency(moneda)
                .addIdentification(tipoDocumento, number, date)
                .addSupplierInformation(tipoIdEmisor, idEmisor, razonSocialEmisor)
                .addCustomerInformation(tipoIdCliente, idCliente, razonSocialCliente)
                .addSignatureElement(idEmisor, razonSocialEmisor)
                .addAmount("1001", "100.00")
                .addAmount("1002", "0.00")
                .addAmount("1003", "0.00")
                .addAmount("2005", "0.00")
                .addNote("1000", "CIENTO DIECIOCHO Y 00/100 NUEVOS SOLES")
                .addCustomNote("9999", "COMENTARIO")
                .addTax("1000", "IGV", "VAT", BigDecimal.valueOf(1800, 2))
                .addPrepaidPayment(tipoIdEmisor, idEmisor, "01", "FA00-00000001", "100.00")
                .addPrepaidPayment(tipoIdEmisor, idEmisor, "01", "FA00-00000001", "50.00")
                .fixPrepaidAmount()
                .addTotalPayable(BigDecimal.valueOf(11800, 2))
                .setIssuerName("el nombre comercial del emisor")
                .setIssuerAddress("123456", "mi calle 123", "mi urbanizacion", "mi distrito", "mi provincia", "mi departamento", "PE")
                .setAcquirerName("el nombre comercial del cliente")
                .setAcquirerAddress(null, "la calle destino", null, null, null, null, null)
                .addLine(
                        new InvoiceLineBuilder()
                        .init("1", BigDecimal.ONE, "NIU", "IH-107", "articulo 1", "PEN", BigDecimal.valueOf(10000, 2), "01", BigDecimal.valueOf(11800, 2), BigDecimal.valueOf(10000, 2))
                        .addTax("1000", "IGV", "VAT", BigDecimal.valueOf(1800, 2), "10", null)
                        .addTax("2000", "ISC", "EXC", BigDecimal.valueOf(400, 2), null, "02")
                        .addAllowanceCharge(true, BigDecimal.valueOf(0, 2))
                        .addAllowanceCharge(false, BigDecimal.valueOf(1000, 2))
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

        InvoiceType i = invoiceBuilder.compile();
        JAXBContext ctx = JAXBContext.newInstance(InvoiceType.class);

        ObjectFactory iof = new ObjectFactory();
        JAXBElement<InvoiceType> ioe = iof.createInvoice(i);

        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        m.marshal(ioe, System.out);

        System.out.println();
        System.out.println();
        System.out.println();

        document.setXmlStandalone(true);

        String text = ds.createTextRepresentation(document, "UTF-8");
        System.out.println(text);
    }

}
