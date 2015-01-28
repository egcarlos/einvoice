/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alignet.einvoide.tests;

import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.request.CommandParameter;
import pe.labtech.einvoice.core.ws.messages.request.SignSummary;
import pe.labtech.einvoice.core.ws.model.Summary;
import pe.labtech.einvoice.core.ws.model.SummaryItem;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.testng.annotations.Test;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvoker;
import pe.labtech.einvoice.core.ws.generated.EBizGenericInvokerImplService;

/**
 *
 * @author Carlos
 */
public class ServiceTest {

    public ServiceTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu/ws/invoker?wsdl");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");

        String publish = new Builder().buildDeclare("20101363008", "03", "BB01-00000001");
        System.out.println("request:\n" + publish);

        String response = port.invoke(publish);

        System.out.println("response:\n" + response);
    }

    @Test
    public void hello1() throws ParseException {
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu1/ws/invoker?wsdl");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");

        Builder b = new Builder();

        Summary s = new Summary();
        s.setResumenId("RC-20140921-002");
        s.setTipoDocumentoEmisor("6");
        s.setNumeroDocumentoEmisor("20101363008");
        s.setFechaEmisionComprobante(new SimpleDateFormat("yyyyMMdd").parse("20140918"));
        s.setFechaGeneracionResumen(new SimpleDateFormat("yyyyMMdd").parse("20140921"));
        s.setRazonSocialEmisor("Scania del Perú S.A.");
        s.setCorreoEmisor("william.laportilla@scania.com");
        s.setResumenTipo("RC");

        s.setItems(Arrays.asList(
                buildSummaryItem(1l, "03", "BB11", "00001101", "00001105", "USD", "211.72", "38.11")
        ));

        SignSummary sc = new SignSummary(
                "1",
                "",
                "",
                Arrays.asList(
                        new CommandParameter("idEmisor", "20101363008"),
                        new CommandParameter("tipoDocumento", "RC")
                ),
                Arrays.asList(s)
        );

        //String response = port.invoke(publish);
        String request = b.marshall(sc);

        System.out.println("request:\n" + request);
        String response = port.invoke(request);

        System.out.println("response:\n" + response);

    }

    private SummaryItem buildSummaryItem(final long row, final String documentType, final String documentSeries, final String documentNumber, String documentNumber2, final String currency, final String amount, final String iva) {
        SummaryItem si1 = new SummaryItem();
        si1.setNumeroFila(row);
        si1.setTipoDocumento(documentType);
        si1.setSerieGrupoDocumento(documentSeries);
        si1.setNumeroCorrelativoInicio(documentNumber);
        si1.setNumeroCorrelativoFin(documentNumber);
        si1.setTipoMoneda(currency);

        si1.setTotalValorVentaOpGravadasConIgv(BigDecimal.ZERO);
        si1.setTotalValorVentaOpExoneradasIgv(BigDecimal.ZERO);
        si1.setTotalValorVentaOpExoneradasConIgv(BigDecimal.ZERO);
        si1.setTotalValorVentaOpInafectasIgv(BigDecimal.ZERO);
        si1.setTotalVenta(BigDecimal.ZERO);
        si1.setTotalOtrosTributos(BigDecimal.ZERO);
        si1.setTotalOtrosCargos(BigDecimal.ZERO);
        si1.setTotalIgv(BigDecimal.ZERO);
        si1.setTotalIsc(BigDecimal.ZERO);

        si1.setTotalValorVentaOpGravadasConIgv(new BigDecimal(amount));
        si1.setTotalIgv(new BigDecimal(iva));

        si1.setTotalVenta(
                si1.getTotalIgv().add(si1.getTotalValorVentaOpGravadasConIgv())
        );
        return si1;
    }

    private SummaryItem buildSummaryItem(final long row, final String documentType, final String documentSeries, final String documentNumber, final String currency, final String amount) {
        SummaryItem si1 = new SummaryItem();
        si1.setNumeroFila(row);
        si1.setTipoDocumento(documentType);
        si1.setSerieGrupoDocumento(documentSeries);
        si1.setNumeroCorrelativoInicio(documentNumber);
        si1.setNumeroCorrelativoFin(documentNumber);
        si1.setTipoMoneda(currency);

        return si1;
    }

    @Test
    public void RA() throws ParseException {
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu1/ws/invoker?wsdl");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");

        Builder b = new Builder();

        Summary summary = new Summary();
        summary.setIndicador("C");
        summary.setTipoDocumentoEmisor("6");
        summary.setNumeroDocumentoEmisor("20101363008");
        summary.setResumenId("RA-20140921-001");
        summary.setFechaEmisionComprobante(new SimpleDateFormat("yyyyMMdd").parse("20140919"));
        summary.setFechaGeneracionResumen(new SimpleDateFormat("yyyyMMdd").parse("20140921"));
        summary.setRazonSocialEmisor("Scania del Perú S.A.");
        summary.setCorreoEmisor("william.laportilla@scania.com");
        summary.setResumenTipo("RA");

        summary.setItems(Arrays.asList(
                buildSummaryItem(1l, "01", "FF11", "00000202", "Cancelacion"),
                buildSummaryItem(2l, "01", "FF12", "00000302", "Cancelacion"),
                buildSummaryItem(3l, "01", "FF13", "00000401", "Cancelacion"),
                buildSummaryItem(4l, "01", "FF14", "00000502", "Cancelacion"),
                buildSummaryItem(5l, "01", "FF50", "00000600", "Cancelacion")
        ));

        SignSummary sc = new SignSummary(
                "1",
                "1",
                "",
                Arrays.asList(
                        new CommandParameter("idEmisor", "20101363008"),
                        new CommandParameter("tipoDocumento", "RA")
                ),
                Arrays.asList(summary)
        );
        final String request = b.marshall(sc);

        System.out.println(request);

        String response = port.invoke(request);
        System.out.println(response);

    }

    private SummaryItem buildSummaryItem(final long row, final String documentType, final String documentSeries, final String documentNumber, final String motive) {
        SummaryItem item = new SummaryItem();
        item.setIndicador("D");
        item.setNumeroFila(row);
        item.setTipoDocumento(documentType);
        item.setSerieDocumentoBaja(documentSeries);
        item.setNumeroDocumentoBaja(documentNumber);
        item.setMotivoBaja(motive);
        return item;
    }

    @Test
    public void query() {
        String command = "<ConsultCmd output=\"PDF\">\n"
                + "    <parameter value=\"20101363008\" name=\"idEmisor\"/>\n"
                + "    <parameter value=\"BB01\" name=\"serieGrupoDocumento\"/>\n"
                + "    <parameter value=\"00000001\" name=\"numeroCorrelativoInicio\"/>\n"
                + "    <parameter value=\"00000001\" name=\"numeroCorrelativoFin\"/>\n"
                + "    <parameter value=\"03\" name=\"tipoDocumento\"/>\n"
                + "</ConsultCmd>";
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu/ws/invoker?wsdl");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");

        String response = port.invoke(command);

        System.out.println("response:\n" + response);
    }

    @Test
    public void sign() {
        String command = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<SignOnLineCmd declare-sunat=\"0\" declare-direct-sunat=\"0\" publish=\"1\" output=\"PDF\">"
                + "    <parameter value=\"20502433629\" name=\"idEmisor\"/>"
                + "    <parameter value=\"01\" name=\"tipoDocumento\"/>"
                + "    <parameter value=\"\" name=\"estadoDocumento\"/>"
                + "    <documento>"
                + "        <indicador>C</indicador>"
                + "        <tipoDocumentoEmisor>6</tipoDocumentoEmisor>"
                + "        <numeroDocumentoEmisor>20502433629</numeroDocumentoEmisor>"
                + "        <razonSocialEmisor>PEPSICO ALIMENTOS PERU S.R.L.</razonSocialEmisor>"
                + "        <nombreComercialEmisor>PEPSICO ALIMENTOS PERU S.R.L.</nombreComercialEmisor>"
                + "        <tipoDocumento>01</tipoDocumento>"
                + "        <serieNumero>F001-00000003</serieNumero>"
                + "        <fechaEmision>2014-11-04T00:00:00-05:00</fechaEmision>"
                + "        <ubigeoEmisor>150137</ubigeoEmisor>"
                + "        <direccionEmisor>AV. FRANCISCO BOLOGNESI 550</direccionEmisor>"
                + "        <urbanizacion>LOS FICUS</urbanizacion>"
                + "        <provinciaEmisor>LIMA</provinciaEmisor>"
                + "        <departamentoEmisor>LIMA</departamentoEmisor>"
                + "        <distritoEmisor>SANTA ANITA</distritoEmisor>"
                + "        <paisEmisor>PE</paisEmisor>"
                + "        <correoEmisor>fe.peru@pepsico.com</correoEmisor>"
                + "        <tipoDocumentoAdquiriente>6</tipoDocumentoAdquiriente>"
                + "        <numeroDocumentoAdquiriente>20565293495</numeroDocumentoAdquiriente>"
                + "        <razonSocialAdquiriente>DISTRIBUIDORA MARGARITA MR EIRL</razonSocialAdquiriente>"
                + "        <lugarDestino>MZ B LT 15 AV EL PORVENIR SAN JUAN LURIGANCHO</lugarDestino>"
                + "        <correoAdquiriente>dmperu@hotmail.com</correoAdquiriente>"
                + "        <tipoMoneda>PEN</tipoMoneda>"
                + "        <totalValorVentaNetoOpGravadas>621.27</totalValorVentaNetoOpGravadas>"
                + "        <totalValorVentaNetoOpNoGravada>0.00</totalValorVentaNetoOpNoGravada>"
                + "        <totalValorVentaNetoOpExoneradas>0.00</totalValorVentaNetoOpExoneradas>"
                + "        <totalValorVentaNetoOpGratuitas>0.00</totalValorVentaNetoOpGratuitas>"
                + "        <descuentosGlobales>0.00</descuentosGlobales>"
                + "        <subTotal>559.14</subTotal>"
                + "        <totalIgv>100.65</totalIgv>"
                + "        <totalIsc>0.00</totalIsc>"
                + "        <totalOtrosTributos>0.00</totalOtrosTributos>"
                + "        <totalOtrosCargos>0.00</totalOtrosCargos>"
                + "        <totalDescuentos>62.13</totalDescuentos>"
                + "        <totalVenta>659.79</totalVenta>"
                + "        <totalPercepcion>13.20</totalPercepcion>"
                + "        <baseImponiblePercepcion>659.79</baseImponiblePercepcion>"
                + "        <totalVentaConPercepcion>672.99</totalVentaConPercepcion>"
                + "        <inHabilitado>1</inHabilitado>"
                + "        <porcentajePercepcion>0.02</porcentajePercepcion>"
                + "        <totalRetencion>0.00</totalRetencion>"
                + "        <porcentajeRetencion>0.00</porcentajeRetencion>"
                + "        <totalDetraccion>0.00</totalDetraccion>"
                + "        <porcentajeDetraccion>0.00</porcentajeDetraccion>"
                + "        <valorReferencialDetraccion>0.00</valorReferencialDetraccion>"
                + "        <descripcionDetraccion>0.00</descripcionDetraccion>"
                + "        <totalBonificacion>0.00</totalBonificacion>"
                + "        <item>"
                + "            <numeroOrdenItem>1</numeroOrdenItem>"
                + "            <codigoProducto>8039</codigoProducto>"
                + "            <descripcion>NATUCHIPS CAMOTE X40GRX63</descripcion>"
                + "            <cantidad>567.00</cantidad>"
                + "            <unidadMedida>NIU</unidadMedida>"
                + "            <importeUnitarioSinImpuesto>0.71</importeUnitarioSinImpuesto>"
                + "            <importeUnitarioConImpuesto>0.83</importeUnitarioConImpuesto>"
                + "            <codigoImporteUnitarioConImpuesto>01</codigoImporteUnitarioConImpuesto>"
                + "            <importeTotalSinImpuesto>402.57</importeTotalSinImpuesto>"
                + "            <importeDescuento>40.26</importeDescuento>"
                + "            <importeCargo>0.00</importeCargo>"
                + "            <codigoRazonExoneracion>10</codigoRazonExoneracion>"
                + "            <importeIgv>65.22</importeIgv>"
                + "            <codigoAuxiliar100_1>9000</codigoAuxiliar100_1>"
                + "            <textoAuxiliar100_1>362.31</textoAuxiliar100_1>"
                + "            <codigoAuxiliar100_2>9001</codigoAuxiliar100_2>"
                + "            <textoAuxiliar100_2>0.10</textoAuxiliar100_2>"
                + "            <codigoAuxiliar100_3>9147</codigoAuxiliar100_3>"
                + "            <textoAuxiliar100_3>9/0</textoAuxiliar100_3>"
                + "            <codigoAuxiliar100_4>9148</codigoAuxiliar100_4>"
                + "            <textoAuxiliar100_4>0.02</textoAuxiliar100_4>"
                + "        </item>"
                + "        <item>"
                + "            <numeroOrdenItem>2</numeroOrdenItem>"
                + "            <codigoProducto>8040</codigoProducto>"
                + "            <descripcion>NATUCHIPS CAMOTE X90GRX27</descripcion>"
                + "            <cantidad>270.00</cantidad>"
                + "            <unidadMedida>NIU</unidadMedida>"
                + "            <importeUnitarioSinImpuesto>0.81</importeUnitarioSinImpuesto>"
                + "            <importeUnitarioConImpuesto>0.95</importeUnitarioConImpuesto>"
                + "            <codigoImporteUnitarioConImpuesto>01</codigoImporteUnitarioConImpuesto>"
                + "            <importeTotalSinImpuesto>218.70</importeTotalSinImpuesto>"
                + "            <importeDescuento>21.87</importeDescuento>"
                + "            <importeCargo>0.00</importeCargo>"
                + "            <codigoRazonExoneracion>10</codigoRazonExoneracion>"
                + "            <importeIgv>35.43</importeIgv>"
                + "            <codigoAuxiliar100_1>9000</codigoAuxiliar100_1>"
                + "            <textoAuxiliar100_1>196.83</textoAuxiliar100_1>"
                + "            <codigoAuxiliar100_2>9001</codigoAuxiliar100_2>"
                + "            <textoAuxiliar100_2>0.10</textoAuxiliar100_2>"
                + "            <codigoAuxiliar100_3>9147</codigoAuxiliar100_3>"
                + "            <textoAuxiliar100_3>10/0</textoAuxiliar100_3>"
                + "            <codigoAuxiliar100_4>9148</codigoAuxiliar100_4>"
                + "            <textoAuxiliar100_4>0.02</textoAuxiliar100_4>"
                + "        </item>"
                + "    </documento>"
                + "</SignOnLineCmd>"
                + "";
        EBizGenericInvokerImplService service = new EBizGenericInvokerImplService();
        EBizGenericInvoker port = service.getEBizGenericInvokerImplPort();

        Map<String, Object> rc = ((BindingProvider) port).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://test3.alignetsac.com/sfewsperu/ws/invoker?wsdl");
        rc.put(BindingProvider.USERNAME_PROPERTY, "avinka");
        rc.put(BindingProvider.PASSWORD_PROPERTY, "ebiz");

        String response = port.invoke(command);

        System.out.println("response:\n" + response);
    }

}
