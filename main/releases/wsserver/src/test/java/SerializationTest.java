/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pe.labtech.einvoice.core.entity.Document;
import pe.labtech.einvoice.core.entity.DocumentAttribute;
import pe.labtech.einvoice.core.entity.DocumentAuxiliar;
import pe.labtech.einvoice.core.entity.DocumentLegend;
import pe.labtech.einvoice.core.entity.Item;
import pe.labtech.einvoice.core.entity.ItemAttribute;

/**
 *
 * @author Carlos
 */
public class SerializationTest {

    public SerializationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //DOCUMENT
    @Test
    public void hello() throws JAXBException {
        JAXBContext j = JAXBContext.newInstance(Document.class);
        Document d = new Document();
        d.setAttributes(new LinkedList<DocumentAttribute>());
        d.setAuxiliars(new LinkedList<DocumentAuxiliar>());
        d.setLegends(new LinkedList<DocumentLegend>());
        d.setItems(new LinkedList<Item>());

        final Marshaller m = j.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        d.getAttributes().add(new DocumentAttribute("tipoDocumentoEmisor", "6"));
        d.getAttributes().add(new DocumentAttribute("numeroDocumentoEmisor", "20111111111"));
        d.getAttributes().add(new DocumentAttribute("tipoDocumento", "01"));
        d.getAttributes().add(new DocumentAttribute("serieNumero", "F001-00000001"));
        d.getAttributes().add(new DocumentAttribute("razonSocialEmisor", "EMISOR"));
        d.getAttributes().add(new DocumentAttribute("fechaEmision", "2015-01-21"));
        d.getAttributes().add(new DocumentAttribute("correoEmisor", "emisor@foo.bar"));
        d.getAttributes().add(new DocumentAttribute("tipoDocumentoAdquiriente", "1"));
        d.getAttributes().add(new DocumentAttribute("numeroDocumentoAdquiriente", "20222222222"));
        d.getAttributes().add(new DocumentAttribute("razonSocialAdquiriente", "ADQUIRIENTE"));
        d.getAttributes().add(new DocumentAttribute("correoAdquiriente", "adquiriente@foo.bar"));
        d.getAttributes().add(new DocumentAttribute("tipoMoneda", "PEN"));
        d.getAttributes().add(new DocumentAttribute("totalValorVentaNetoOpGravadas", "100.00"));
        d.getAttributes().add(new DocumentAttribute("totalValorVentaNetoOpNoGravada", "0.00"));
        d.getAttributes().add(new DocumentAttribute("totalValorVentaNetoOpExoneradas", "0.00"));
        d.getAttributes().add(new DocumentAttribute("totalIgv", "18.00"));
        d.getAttributes().add(new DocumentAttribute("totalVenta", "118.00"));

        d.getLegends().add(new DocumentLegend("1000", "CIENTO DIECIOCHO Y 00/100 NUEVOS SOLES"));
        d.getAuxiliars().add(new DocumentAuxiliar("2005", "40", "0.00"));

        Item i = new Item();
        i.setAttributes(new LinkedList<ItemAttribute>());
        i.getAttributes().add(new ItemAttribute("codigoProducto", "000001"));
        i.getAttributes().add(new ItemAttribute("descripcion", "Articulo"));
        i.getAttributes().add(new ItemAttribute("cantidad", "1.00"));
        i.getAttributes().add(new ItemAttribute("unidadMedida", "NIU"));
        i.getAttributes().add(new ItemAttribute("importeUnitarioSinImpuesto", "100.00"));
        i.getAttributes().add(new ItemAttribute("importeUnitarioConImpuesto", "118.00"));
        i.getAttributes().add(new ItemAttribute("codigoImporteUnitarioConImpues", "01"));
        i.getAttributes().add(new ItemAttribute("importeTotalSinImpuesto", "100.00"));
        i.getAttributes().add(new ItemAttribute("codigoRazonExoneracion", "10"));
        i.getAttributes().add(new ItemAttribute("importeIgv", "18.00"));

        d.getItems().add(i);

        m.marshal(d, System.out);
    }
}
