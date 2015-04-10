/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.xmlsecurity;

import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.Test;
import pe.labtech.einvoice.commons.ubl.InvoiceBuilder;
import pe.labtech.ubl.model.Invoice;
import pe.labtech.ubl.model.InvoicePrefixMapper;

/**
 *
 * @author carloseg
 */
public class IntegralTest {

    @Test
    public void jbcontext() throws JAXBException {
        JAXBContext jbc = JAXBContext.newInstance(Invoice.class);
        Marshaller m = jbc.createMarshaller();
        m.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new InvoicePrefixMapper());
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(MarshallerProperties.INDENT_STRING, "    ");

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

        Invoice invoice = new InvoiceBuilder()
                .init(tipoDocumento, number, date, moneda, tipoIdEmisor, idEmisor, razonSocialEmisor, tipoIdCliente, idCliente, razonSocialCliente)
                .addAmount("1001", BigDecimal.valueOf(10000, 2))
                .addAmount("1002", BigDecimal.valueOf(0, 2))
                .addAmount("1003", BigDecimal.valueOf(0, 2))
                .addAmount("2005", BigDecimal.valueOf(0, 2))
                .addNote("1000", "Son ciento dieciocho y 00/100 Nuevos Soles")
                .compile();

        

        m.marshal(invoice, System.out);
    }

}
