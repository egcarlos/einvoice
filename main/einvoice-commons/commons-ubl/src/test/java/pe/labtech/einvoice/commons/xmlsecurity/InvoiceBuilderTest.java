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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalInformationType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import org.junit.Test;
import pe.labtech.einvoice.commons.ubl.InvoiceBuilder;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalInformation;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalAmount;
import sunat.names.specification.ubl.peru.schema.xsd.AdditionalProperty;

/**
 *
 * @author Carlos
 */
public class InvoiceBuilderTest {

    @Test
    public void test() throws ParserConfigurationException, JAXBException, XMLStreamException {

        InvoiceBuilder ib = new InvoiceBuilder()
                .initialize()
                .setCurrency("PEN")
                .addAmountTotal("1001", "100.00")
                .addAmountTotal("1002", "0.00")
                .addAmountTotal("1003", "0.00")
                .addAmountTotal("2005", "0.00")
                .addAmountProperty("1000", "CIENTO DIECIOCHO Y 00/100 NUEVOS SOLES");

        //root ubl document holder
        final InvoiceType it = new InvoiceType();
        //ubl extension mandatory section
        final UBLExtensionsType ublExtensions = new UBLExtensionsType();
        it.setUBLExtensions(ublExtensions);

        //ubl extension for sunat data
        final UBLExtensionType sunatExtension = new UBLExtensionType("SUNAT");
        ublExtensions.getUBLExtension().add(sunatExtension);
        final ExtensionContentType sunatContent = new ExtensionContentType();
        sunatExtension.setExtensionContent(sunatContent);
        final AdditionalInformation ais = new AdditionalInformation();
        //this values came from catalogs
        ais.getAdditionalMonetaryTotals().add(new AdditionalAmount("1001", "PEN", new BigDecimal("100.00")));
        ais.getAdditionalMonetaryTotals().add(new AdditionalAmount("1002", "PEN", new BigDecimal("0.00")));
        ais.getAdditionalMonetaryTotals().add(new AdditionalAmount("1003", "PEN", new BigDecimal("0.00")));
        ais.getAdditionalMonetaryTotals().add(new AdditionalAmount("2005", "PEN", new BigDecimal("0.00")));
        ais.getAdditionalProperties().add(new AdditionalProperty("1000", "CIENTO DIECIOCHO Y 00/100 NUEVOS SOLES"));
        sunatContent.setAdditionalInformationSunat(ais);

        //UBL EXTENSION FOR EBIZ ID... is allways empty
        //TODO determine wich fields maps to EBIZ interior
        final UBLExtensionType ebizExtension = new UBLExtensionType("EBIZ");
        it.getUBLExtensions().getUBLExtension().add(ebizExtension);
        ebizExtension.setExtensionContent(new ExtensionContentType());
        ebizExtension.getExtensionContent().setAdditionalInformationCBC(new AdditionalInformationType());

        final UBLExtensionType signExtension = new UBLExtensionType();
        signExtension.setExtensionContent(new ExtensionContentType());
        it.getUBLExtensions().getUBLExtension().add(signExtension);

        JAXBContext context = JAXBContext.newInstance(InvoiceType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(ib.getInvoice(), System.out);
    }

}
