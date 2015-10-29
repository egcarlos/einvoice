/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.xmlsecurity;

import java.math.BigDecimal;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.Document;
import pe.labtech.einvoice.commons.ubl.DebitNoteBuilder;
import pe.labtech.einvoice.commons.ubl.DebitNoteLineBuilder;
import pe.labtech.einvoice.commons.ubl.DocumentMorpher;

/**
* Clase DebitTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class DebitTest {

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

        final DebitNoteBuilder invoiceBuilder = new DebitNoteBuilder()
                .init(number, date, moneda, tipoIdEmisor, idEmisor, razonSocialEmisor, tipoIdCliente, idCliente, razonSocialCliente)
                .addAmount("1001", BigDecimal.valueOf(10000, 2))
                .addAmount("1002", BigDecimal.valueOf(0, 2))
                .addAmount("1003", BigDecimal.valueOf(0, 2))
                .addAmount("2005", BigDecimal.valueOf(0, 2))
                .addNote("1000", "CIENTO DIECIOCHO Y 00/100 NUEVOS SOLES")
                .addCustomNote("9999", "COMENTARIO")
                .addTax("1000", "IGV", "VAT", BigDecimal.valueOf(1800, 2))
                .addTotalPayable(BigDecimal.valueOf(11800, 2))
                .addDiscrepancyResponse("F000-00000000", "01", "ANULACION")
                .addBillingReference("F000-00000000", "01")
                .addLine(
                        new DebitNoteLineBuilder()
                        .init("1", BigDecimal.ONE, "NIU", "IH-107", "articulo 1", "PEN", BigDecimal.valueOf(10000, 2), "01", BigDecimal.valueOf(11800, 2), BigDecimal.valueOf(10000, 2))
                        .addTax("1000", "IGV", "VAT", BigDecimal.valueOf(1800, 2), "10", null)
                        .compile()
                );

//        m.marshal(invoice, System.out);
        Document document = invoiceBuilder.document("UTF-8");
        DocumentMorpher.morph("08", document);

        DigitalSign ds = new DigitalSign();
        String text = ds.createTextRepresentation(document, "UTF-8");
        System.out.println(text);

    }

}
