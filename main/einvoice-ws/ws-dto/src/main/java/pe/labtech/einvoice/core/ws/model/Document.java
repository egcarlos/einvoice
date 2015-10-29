package pe.labtech.einvoice.core.ws.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Documento",
        propOrder = {
            "indicador",
            "tipoDocumentoEmisor",
            "numeroDocumentoEmisor",
            "razonSocialEmisor",
            "nombreComercialEmisor",
            "tipoDocumento",
            "serieNumero",
            "fechaEmision",
            "ubigeoEmisor",
            "direccionEmisor",
            "urbanizacion",
            "provinciaEmisor",
            "departamentoEmisor",
            "distritoEmisor",
            "paisEmisor",
            "correoEmisor",
            "tipoDocumentoAdquiriente",
            "numeroDocumentoAdquiriente",
            "razonSocialAdquiriente",
            "lugarDestino",
            "correoAdquiriente",
            "tipoMoneda",
            "tipoOperacionFactura",
            "totalValorVentaNetoOpGravadas",
            "totalValorVentaNetoOpNoGravada",
            "totalValorVentaNetoOpExoneradas",
            "totalValorVentaNetoOpGratuitas",
            "totalFondoInclusionSocial",
            "descuentosGlobales",
            "subTotal",
            "totalIgv",
            "totalIsc",
            "totalOtrosTributos",
            "totalOtrosCargos",
            "totalDescuentos",
            "totalVenta",
            "totalPercepcion",
            "baseImponiblePercepcion",
            "totalVentaConPercepcion",
            "tipoDocumentoReferenciaPrincipal",
            "numeroDocumentoReferenciaPrincipal",
            "tipoDocumentoReferenciaCorregido",
            "numeroDocumentoReferenciaCorregido",
            "motivoDocumento",
            "serieNumeroAfectado",
            "codigoSerieNumeroAfectado",
            "inHabilitado",
            "porcentajePercepcion",
            "totalRetencion",
            "porcentajeRetencion",
            "totalDetraccion",
            "porcentajeDetraccion",
            "valorReferencialDetraccion",
            "descripcionDetraccion",
            "totalBonificacion",
            "ubigeoDireccionPtoPartida",
            "direccionCompletaPtoPartida",
            "urbanizacionPtoPartida",
            "provinciaPtoPartida",
            "departamentoPtoPartida",
            "distritoPtoPartida",
            "paisPtoPartida",
            "ubigeoDireccionPtoLlegada",
            "direccionCompletaPtoLlegada",
            "urbanizacionPtoLlegada",
            "provinciaPtoLlegada",
            "departamentoPtoLlegada",
            "distritoPtoLlegada",
            "paisPtoLlegada",
            "marcaVehiculo",
            "placaVehiculo",
            "numeroConstanciaVehiculo",
            "numeroLicenciaConducir",
            "numeroRucTransportista",
            "numeroRucTransportistaCuenta",
            "razonSocialTransportista",
            "monto1",
            "monto2",
            "monto3",
            "monto4",
            "monto5",
            "totalDocumentoAnticipo",
            "tipoDocumentoAnticipo",
            "serieNumeroDocumentoAnticipo",
            "tipoReferencia_1",
            "numeroDocumentoReferencia_1",
            "tipoReferencia_2",
            "numeroDocumentoReferencia_2",
            "tipoReferencia_3",
            "numeroDocumentoReferencia_3",
            "tipoReferencia_4",
            "numeroDocumentoReferencia_4",
            "tipoReferencia_5",
            "numeroDocumentoReferencia_5",
            "tipoReferenciaAdicional_1",
            "numeroDocumentoReferenciaAdicional_1",
            "tipoReferenciaAdicional_2",
            "numeroDocumentoReferenciaAdicional_2",
            "tipoReferenciaAdicional_3",
            "numeroDocumentoReferenciaAdicional_3",
            "tipoReferenciaAdicional_4",
            "numeroDocumentoReferenciaAdicional_4",
            "tipoReferenciaAdicional_5",
            "numeroDocumentoReferenciaAdicional_5",
            "codigoLeyenda_1",
            "textoLeyenda_1",
            "textoAdicionalLeyenda_1",
            "codigoLeyenda_2",
            "textoLeyenda_2",
            "textoAdicionalLeyenda_2",
            "codigoLeyenda_3",
            "textoLeyenda_3",
            "textoAdicionalLeyenda_3",
            "codigoLeyenda_4",
            "textoLeyenda_4",
            "textoAdicionalLeyenda_4",
            "codigoLeyenda_5",
            "textoAdicionalLeyenda_5",
            "textoLeyenda_5",
            "codigoLeyenda_6",
            "textoLeyenda_6",
            "textoAdicionalLeyenda_6",
            "codigoLeyenda_7",
            "textoLeyenda_7",
            "textoAdicionalLeyenda_7",
            "codigoLeyenda_8",
            "textoLeyenda_8",
            "textoAdicionalLeyenda_8",
            "codigoLeyenda_9",
            "textoLeyenda_9",
            "textoAdicionalLeyenda_9",
            "codigoLeyenda_10",
            "textoLeyenda_10",
            "textoAdicionalLeyenda_10",
            "codigoLeyenda_11",
            "textoLeyenda_11",
            "textoAdicionalLeyenda_11",
            "codigoLeyenda_12",
            "textoLeyenda_12",
            "textoAdicionalLeyenda_12",
            "codigoLeyenda_13",
            "textoLeyenda_13",
            "textoAdicionalLeyenda_13",
            "codigoLeyenda_14",
            "textoLeyenda_14",
            "textoAdicionalLeyenda_14",
            "codigoLeyenda_15",
            "textoLeyenda_15",
            "textoAdicionalLeyenda_15",
            "codigoLeyenda_16",
            "textoLeyenda_16",
            "textoAdicionalLeyenda_16",
            "codigoLeyenda_17",
            "textoLeyenda_17",
            "textoAdicionalLeyenda_17",
            "codigoLeyenda_18",
            "textoLeyenda_18",
            "textoAdicionalLeyenda_18",
            "codigoLeyenda_19",
            "textoLeyenda_19",
            "textoAdicionalLeyenda_19",
            "codigoLeyenda_20",
            "textoLeyenda_20",
            "textoAdicionalLeyenda_20",
            "nombreAdjunto_1",
            "nombreAdjunto_2",
            "nombreAdjunto_3",
            "nombreAdjunto_4",
            "nombreAdjunto_5",
            "adjunto_1",
            "adjunto_2",
            "adjunto_3",
            "adjunto_4",
            "adjunto_5",
            "rutaAdjunto_1",
            "rutaAdjunto_2",
            "rutaAdjunto_3",
            "rutaAdjunto_4",
            "rutaAdjunto_5",
            "codigoAuxiliar100_1",
            "textoAuxiliar100_1",
            "codigoAuxiliar100_2",
            "textoAuxiliar100_2",
            "codigoAuxiliar100_3",
            "textoAuxiliar100_3",
            "codigoAuxiliar100_4",
            "textoAuxiliar100_4",
            "codigoAuxiliar100_5",
            "textoAuxiliar100_5",
            "codigoAuxiliar100_6",
            "textoAuxiliar100_6",
            "codigoAuxiliar100_7",
            "textoAuxiliar100_7",
            "codigoAuxiliar100_8",
            "textoAuxiliar100_8",
            "codigoAuxiliar100_9",
            "textoAuxiliar100_9",
            "codigoAuxiliar100_10",
            "textoAuxiliar100_10",
            "codigoAuxiliar40_1",
            "textoAuxiliar40_1",
            "codigoAuxiliar40_2",
            "textoAuxiliar40_2",
            "codigoAuxiliar40_3",
            "textoAuxiliar40_3",
            "codigoAuxiliar40_4",
            "textoAuxiliar40_4",
            "codigoAuxiliar40_5",
            "textoAuxiliar40_5",
            "codigoAuxiliar40_6",
            "textoAuxiliar40_6",
            "codigoAuxiliar40_7",
            "textoAuxiliar40_7",
            "codigoAuxiliar40_8",
            "textoAuxiliar40_8",
            "codigoAuxiliar40_9",
            "textoAuxiliar40_9",
            "codigoAuxiliar40_10",
            "textoAuxiliar40_10",
            "codigoAuxiliar40_11",
            "textoAuxiliar40_11",
            "codigoAuxiliar40_12",
            "textoAuxiliar40_12",
            "codigoAuxiliar40_13",
            "textoAuxiliar40_13",
            "codigoAuxiliar40_14",
            "textoAuxiliar40_14",
            "codigoAuxiliar40_15",
            "textoAuxiliar40_15",
            "codigoAuxiliar40_16",
            "textoAuxiliar40_16",
            "codigoAuxiliar40_17",
            "textoAuxiliar40_17",
            "codigoAuxiliar40_18",
            "textoAuxiliar40_18",
            "codigoAuxiliar40_19",
            "textoAuxiliar40_19",
            "codigoAuxiliar40_20",
            "textoAuxiliar40_20",
            "codigoAuxiliar250_1",
            "textoAuxiliar250_1",
            "codigoAuxiliar250_2",
            "textoAuxiliar250_2",
            "codigoAuxiliar250_3",
            "textoAuxiliar250_3",
            "codigoAuxiliar250_4",
            "textoAuxiliar250_4",
            "codigoAuxiliar250_5",
            "textoAuxiliar250_5",
            "codigoAuxiliar250_6",
            "textoAuxiliar250_6",
            "codigoAuxiliar250_7",
            "textoAuxiliar250_7",
            "codigoAuxiliar250_8",
            "textoAuxiliar250_8",
            "codigoAuxiliar250_9",
            "textoAuxiliar250_9",
            "codigoAuxiliar250_10",
            "textoAuxiliar250_10",
            "codigoAuxiliar250_11",
            "textoAuxiliar250_11",
            "codigoAuxiliar250_12",
            "textoAuxiliar250_12",
            "codigoAuxiliar250_13",
            "textoAuxiliar250_13",
            "codigoAuxiliar250_14",
            "textoAuxiliar250_14",
            "codigoAuxiliar250_15",
            "textoAuxiliar250_15",
            "codigoAuxiliar250_16",
            "textoAuxiliar250_16",
            "codigoAuxiliar250_17",
            "textoAuxiliar250_17",
            "codigoAuxiliar250_18",
            "textoAuxiliar250_18",
            "codigoAuxiliar250_19",
            "textoAuxiliar250_19",
            "codigoAuxiliar250_20",
            "textoAuxiliar250_20",
            "codigoAuxiliar250_21",
            "textoAuxiliar250_21",
            "codigoAuxiliar250_22",
            "textoAuxiliar250_22",
            "codigoAuxiliar250_23",
            "textoAuxiliar250_23",
            "codigoAuxiliar250_24",
            "textoAuxiliar250_24",
            "codigoAuxiliar250_25",
            "textoAuxiliar250_25",
            "codigoAuxiliar500_1",
            "textoAuxiliar500_1",
            "codigoAuxiliar500_2",
            "textoAuxiliar500_2",
            "codigoAuxiliar500_3",
            "textoAuxiliar500_3",
            "codigoAuxiliar500_4",
            "textoAuxiliar500_4",
            "codigoAuxiliar500_5",
            "textoAuxiliar500_5",
            "items",
            "advances",
            "referencias"
        })
public class Document {

    public static final String LINE_ID_HEADER = "C";
    public static final String LINE_ID_ITEM = "D";
    public static final String LINE_ID_REFERENCIA = "R";

    @XmlElement(required = true, defaultValue = LINE_ID_HEADER)
    private String indicador;
    @XmlElement(required = true)
    private String tipoDocumentoEmisor;
    @XmlElement(required = true)
    private String numeroDocumentoEmisor;
    @XmlElement(required = true)
    private String razonSocialEmisor;
    private String nombreComercialEmisor;
    @XmlElement(required = true)
    private String tipoDocumento;
    @XmlElement(required = true)
    private String serieNumero;
    @XmlElement(required = true)
//    @XmlSchemaType(name = "date")
    private Date fechaEmision;
    private String ubigeoEmisor;
    private String direccionEmisor;
    private String urbanizacion;
    private String provinciaEmisor;
    private String departamentoEmisor;
    private String distritoEmisor;
    private String paisEmisor;
    private String correoEmisor;
    private String inHabilitado;
    private String rutaAdjunto_1;
    private String rutaAdjunto_2;
    private String rutaAdjunto_3;
    private String rutaAdjunto_4;
    private String rutaAdjunto_5;
    private String nombreAdjunto_1;
    private String nombreAdjunto_2;
    private String nombreAdjunto_3;
    private String nombreAdjunto_4;
    private String nombreAdjunto_5;
    private String adjunto_1;
    private String adjunto_2;
    private String adjunto_3;
    private String adjunto_4;
    private String adjunto_5;
    @XmlElement(required = true)
    private String tipoDocumentoAdquiriente;
    @XmlElement(required = true)
    private String numeroDocumentoAdquiriente;
    @XmlElement(required = true)
    private String razonSocialAdquiriente;
    private String lugarDestino;
    private String correoAdquiriente;
    @XmlElement(required = true)
    private String tipoMoneda;
    private String tipoOperacionFactura;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalDocumentoAnticipo;
    private String tipoDocumentoAnticipo;
    private String serieNumeroDocumentoAnticipo;
    @XmlJavaTypeAdapter(Number15Adapter.class)
    private BigDecimal totalFondoInclusionSocial;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalValorVentaNetoOpGravadas;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalValorVentaNetoOpNoGravada;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalValorVentaNetoOpExoneradas;
    @XmlJavaTypeAdapter(Number15Adapter.class)
    private BigDecimal totalValorVentaNetoOpGratuitas;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal descuentosGlobales;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal baseImponiblePercepcion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal subTotal;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalIgv;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalIsc;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalOtrosTributos;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalOtrosCargos;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalDescuentos;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalVenta;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalPercepcion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalVentaConPercepcion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal monto1;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal monto2;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal monto3;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal monto4;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal monto5;
    private String tipoDocumentoReferenciaPrincipal;
    private String numeroDocumentoReferenciaPrincipal;
    private String tipoDocumentoReferenciaCorregido;
    private String numeroDocumentoReferenciaCorregido;
    private String motivoDocumento;
    private String serieNumeroAfectado;
    private String codigoSerieNumeroAfectado;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal porcentajePercepcion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalRetencion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal porcentajeRetencion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalDetraccion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal valorReferencialDetraccion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal porcentajeDetraccion;
    private String descripcionDetraccion;
    @XmlJavaTypeAdapter(Number2Adapter.class)
    private BigDecimal totalBonificacion;
    private String ubigeoDireccionPtoPartida;
    private String direccionCompletaPtoPartida;
    private String urbanizacionPtoPartida;
    private String provinciaPtoPartida;
    private String departamentoPtoPartida;
    private String distritoPtoPartida;
    private String paisPtoPartida;
    private String ubigeoDireccionPtoLlegada;
    private String direccionCompletaPtoLlegada;
    private String urbanizacionPtoLlegada;
    private String provinciaPtoLlegada;
    private String departamentoPtoLlegada;
    private String distritoPtoLlegada;
    private String paisPtoLlegada;
    private String marcaVehiculo;
    private String placaVehiculo;
    private String numeroConstanciaVehiculo;
    private String numeroLicenciaConducir;
    private String numeroRucTransportista;
    private String numeroRucTransportistaCuenta;
    private String razonSocialTransportista;
    @XmlElement(name = "tipoReferencia_1")
    private String tipoReferencia_1;
    @XmlElement(name = "numeroDocumentoReferencia_1")
    private String numeroDocumentoReferencia_1;
    @XmlElement(name = "tipoReferencia_2")
    private String tipoReferencia_2;
    @XmlElement(name = "numeroDocumentoReferencia_2")
    private String numeroDocumentoReferencia_2;
    @XmlElement(name = "tipoReferencia_3")
    private String tipoReferencia_3;
    @XmlElement(name = "numeroDocumentoReferencia_3")
    private String numeroDocumentoReferencia_3;
    @XmlElement(name = "tipoReferencia_4")
    private String tipoReferencia_4;
    @XmlElement(name = "numeroDocumentoReferencia_4")
    private String numeroDocumentoReferencia_4;
    @XmlElement(name = "tipoReferencia_5")
    private String tipoReferencia_5;
    @XmlElement(name = "numeroDocumentoReferencia_5")
    private String numeroDocumentoReferencia_5;
    @XmlElement(name = "tipoReferenciaAdicional_1")
    private String tipoReferenciaAdicional_1;
    @XmlElement(name = "numeroDocumentoReferenciaAdicional_1")
    private String numeroDocumentoReferenciaAdicional_1;
    @XmlElement(name = "tipoReferenciaAdicional_2")
    private String tipoReferenciaAdicional_2;
    @XmlElement(name = "numeroDocumentoReferenciaAdicional_2")
    private String numeroDocumentoReferenciaAdicional_2;
    @XmlElement(name = "tipoReferenciaAdicional_3")
    private String tipoReferenciaAdicional_3;
    @XmlElement(name = "numeroDocumentoReferenciaAdicional_3")
    private String numeroDocumentoReferenciaAdicional_3;
    @XmlElement(name = "tipoReferenciaAdicional_4")
    private String tipoReferenciaAdicional_4;
    @XmlElement(name = "numeroDocumentoReferenciaAdicional_4")
    private String numeroDocumentoReferenciaAdicional_4;
    @XmlElement(name = "tipoReferenciaAdicional_5")
    private String tipoReferenciaAdicional_5;
    @XmlElement(name = "numeroDocumentoReferenciaAdicional_5")
    private String numeroDocumentoReferenciaAdicional_5;
    @XmlElement(name = "codigoLeyenda_1")
    private String codigoLeyenda_1;
    @XmlElement(name = "textoLeyenda_1")
    private String textoLeyenda_1;
    @XmlElement(name = "textoAdicionalLeyenda_1")
    private String textoAdicionalLeyenda_1;
    @XmlElement(name = "codigoLeyenda_2")
    private String codigoLeyenda_2;
    @XmlElement(name = "textoLeyenda_2")
    private String textoLeyenda_2;
    @XmlElement(name = "textoAdicionalLeyenda_2")
    private String textoAdicionalLeyenda_2;
    @XmlElement(name = "codigoLeyenda_3")
    private String codigoLeyenda_3;
    @XmlElement(name = "textoLeyenda_3")
    private String textoLeyenda_3;
    @XmlElement(name = "textoAdicionalLeyenda_3")
    private String textoAdicionalLeyenda_3;
    @XmlElement(name = "codigoLeyenda_4")
    private String codigoLeyenda_4;
    @XmlElement(name = "textoLeyenda_4")
    private String textoLeyenda_4;
    @XmlElement(name = "textoAdicionalLeyenda_4")
    private String textoAdicionalLeyenda_4;
    @XmlElement(name = "codigoLeyenda_5")
    private String codigoLeyenda_5;
    @XmlElement(name = "textoLeyenda_5")
    private String textoLeyenda_5;
    @XmlElement(name = "textoAdicionalLeyenda_5")
    private String textoAdicionalLeyenda_5;
    @XmlElement(name = "codigoLeyenda_6")
    private String codigoLeyenda_6;
    @XmlElement(name = "textoLeyenda_6")
    private String textoLeyenda_6;
    @XmlElement(name = "textoAdicionalLeyenda_6")
    private String textoAdicionalLeyenda_6;
    @XmlElement(name = "codigoLeyenda_7")
    private String codigoLeyenda_7;
    @XmlElement(name = "textoLeyenda_7")
    private String textoLeyenda_7;
    @XmlElement(name = "textoAdicionalLeyenda_7")
    private String textoAdicionalLeyenda_7;
    @XmlElement(name = "codigoLeyenda_8")
    private String codigoLeyenda_8;
    @XmlElement(name = "textoLeyenda_8")
    private String textoLeyenda_8;
    @XmlElement(name = "textoAdicionalLeyenda_8")
    private String textoAdicionalLeyenda_8;
    @XmlElement(name = "codigoLeyenda_9")
    private String codigoLeyenda_9;
    @XmlElement(name = "textoLeyenda_9")
    private String textoLeyenda_9;
    @XmlElement(name = "textoAdicionalLeyenda_9")
    private String textoAdicionalLeyenda_9;
    @XmlElement(name = "codigoLeyenda_10")
    private String codigoLeyenda_10;
    @XmlElement(name = "textoLeyenda_10")
    private String textoLeyenda_10;
    @XmlElement(name = "textoAdicionalLeyenda_10")
    private String textoAdicionalLeyenda_10;
    @XmlElement(name = "codigoLeyenda_11")
    private String codigoLeyenda_11;
    @XmlElement(name = "textoLeyenda_11")
    private String textoLeyenda_11;
    @XmlElement(name = "textoAdicionalLeyenda_11")
    private String textoAdicionalLeyenda_11;
    @XmlElement(name = "codigoLeyenda_12")
    private String codigoLeyenda_12;
    @XmlElement(name = "textoLeyenda_12")
    private String textoLeyenda_12;
    @XmlElement(name = "textoAdicionalLeyenda_12")
    private String textoAdicionalLeyenda_12;
    @XmlElement(name = "codigoLeyenda_13")
    private String codigoLeyenda_13;
    @XmlElement(name = "textoLeyenda_13")
    private String textoLeyenda_13;
    @XmlElement(name = "textoAdicionalLeyenda_13")
    private String textoAdicionalLeyenda_13;
    @XmlElement(name = "codigoLeyenda_14")
    private String codigoLeyenda_14;
    @XmlElement(name = "textoLeyenda_14")
    private String textoLeyenda_14;
    @XmlElement(name = "textoAdicionalLeyenda_14")
    private String textoAdicionalLeyenda_14;
    @XmlElement(name = "codigoLeyenda_15")
    private String codigoLeyenda_15;
    @XmlElement(name = "textoLeyenda_15")
    private String textoLeyenda_15;
    @XmlElement(name = "textoAdicionalLeyenda_15")
    private String textoAdicionalLeyenda_15;
    @XmlElement(name = "codigoLeyenda_16")
    private String codigoLeyenda_16;
    @XmlElement(name = "textoLeyenda_16")
    private String textoLeyenda_16;
    @XmlElement(name = "textoAdicionalLeyenda_16")
    private String textoAdicionalLeyenda_16;
    @XmlElement(name = "codigoLeyenda_17")
    private String codigoLeyenda_17;
    @XmlElement(name = "textoLeyenda_17")
    private String textoLeyenda_17;
    @XmlElement(name = "textoAdicionalLeyenda_17")
    private String textoAdicionalLeyenda_17;
    @XmlElement(name = "codigoLeyenda_18")
    private String codigoLeyenda_18;
    @XmlElement(name = "textoLeyenda_18")
    private String textoLeyenda_18;
    @XmlElement(name = "textoAdicionalLeyenda_18")
    private String textoAdicionalLeyenda_18;
    @XmlElement(name = "codigoLeyenda_19")
    private String codigoLeyenda_19;
    @XmlElement(name = "textoLeyenda_19")
    private String textoLeyenda_19;
    @XmlElement(name = "textoAdicionalLeyenda_19")
    private String textoAdicionalLeyenda_19;
    @XmlElement(name = "codigoLeyenda_20")
    private String codigoLeyenda_20;
    @XmlElement(name = "textoLeyenda_20")
    private String textoLeyenda_20;
    @XmlElement(name = "textoAdicionalLeyenda_20")
    private String textoAdicionalLeyenda_20;
    @XmlElement(name = "item")
    private List<DocumentItem> items;
    @XmlElement(name = "anticipo")
    private List<DocumentAdvance> advances;
    @XmlElement(name = "referencia")
    private List<DocumentReference> referencias;
    @XmlElement(name = "codigoAuxiliar100_1")
    private String codigoAuxiliar100_1;
    @XmlElement(name = "textoAuxiliar100_1")
    private String textoAuxiliar100_1;
    @XmlElement(name = "codigoAuxiliar100_2")
    private String codigoAuxiliar100_2;
    @XmlElement(name = "textoAuxiliar100_2")
    private String textoAuxiliar100_2;
    @XmlElement(name = "codigoAuxiliar100_3")
    private String codigoAuxiliar100_3;
    @XmlElement(name = "textoAuxiliar100_3")
    private String textoAuxiliar100_3;
    @XmlElement(name = "codigoAuxiliar100_4")
    private String codigoAuxiliar100_4;
    @XmlElement(name = "textoAuxiliar100_4")
    private String textoAuxiliar100_4;
    @XmlElement(name = "codigoAuxiliar100_5")
    private String codigoAuxiliar100_5;
    @XmlElement(name = "textoAuxiliar100_5")
    private String textoAuxiliar100_5;
    @XmlElement(name = "codigoAuxiliar100_6")
    private String codigoAuxiliar100_6;
    @XmlElement(name = "textoAuxiliar100_6")
    private String textoAuxiliar100_6;
    @XmlElement(name = "codigoAuxiliar100_7")
    private String codigoAuxiliar100_7;
    @XmlElement(name = "textoAuxiliar100_7")
    private String textoAuxiliar100_7;
    @XmlElement(name = "codigoAuxiliar100_8")
    private String codigoAuxiliar100_8;
    @XmlElement(name = "textoAuxiliar100_8")
    private String textoAuxiliar100_8;
    @XmlElement(name = "codigoAuxiliar100_9")
    private String codigoAuxiliar100_9;
    @XmlElement(name = "textoAuxiliar100_9")
    private String textoAuxiliar100_9;
    @XmlElement(name = "codigoAuxiliar100_10")
    private String codigoAuxiliar100_10;
    @XmlElement(name = "textoAuxiliar100_10")
    private String textoAuxiliar100_10;
    @XmlElement(name = "codigoAuxiliar40_1")
    private String codigoAuxiliar40_1;
    @XmlElement(name = "textoAuxiliar40_1")
    private String textoAuxiliar40_1;
    @XmlElement(name = "codigoAuxiliar40_2")
    private String codigoAuxiliar40_2;
    @XmlElement(name = "textoAuxiliar40_2")
    private String textoAuxiliar40_2;
    @XmlElement(name = "codigoAuxiliar40_3")
    private String codigoAuxiliar40_3;
    @XmlElement(name = "textoAuxiliar40_3")
    private String textoAuxiliar40_3;
    @XmlElement(name = "codigoAuxiliar40_4")
    private String codigoAuxiliar40_4;
    @XmlElement(name = "textoAuxiliar40_4")
    private String textoAuxiliar40_4;
    @XmlElement(name = "codigoAuxiliar40_5")
    private String codigoAuxiliar40_5;
    @XmlElement(name = "textoAuxiliar40_5")
    private String textoAuxiliar40_5;
    @XmlElement(name = "codigoAuxiliar40_6")
    private String codigoAuxiliar40_6;
    @XmlElement(name = "textoAuxiliar40_6")
    private String textoAuxiliar40_6;
    @XmlElement(name = "codigoAuxiliar40_7")
    private String codigoAuxiliar40_7;
    @XmlElement(name = "textoAuxiliar40_7")
    private String textoAuxiliar40_7;
    @XmlElement(name = "codigoAuxiliar40_8")
    private String codigoAuxiliar40_8;
    @XmlElement(name = "textoAuxiliar40_8")
    private String textoAuxiliar40_8;
    @XmlElement(name = "codigoAuxiliar40_9")
    private String codigoAuxiliar40_9;
    @XmlElement(name = "textoAuxiliar40_9")
    private String textoAuxiliar40_9;
    @XmlElement(name = "codigoAuxiliar40_10")
    private String codigoAuxiliar40_10;
    @XmlElement(name = "textoAuxiliar40_10")
    private String textoAuxiliar40_10;
    @XmlElement(name = "codigoAuxiliar40_11")
    private String codigoAuxiliar40_11;
    @XmlElement(name = "textoAuxiliar40_11")
    private String textoAuxiliar40_11;
    @XmlElement(name = "codigoAuxiliar40_12")
    private String codigoAuxiliar40_12;
    @XmlElement(name = "textoAuxiliar40_12")
    private String textoAuxiliar40_12;
    @XmlElement(name = "codigoAuxiliar40_13")
    private String codigoAuxiliar40_13;
    @XmlElement(name = "textoAuxiliar40_13")
    private String textoAuxiliar40_13;
    @XmlElement(name = "codigoAuxiliar40_14")
    private String codigoAuxiliar40_14;
    @XmlElement(name = "textoAuxiliar40_14")
    private String textoAuxiliar40_14;
    @XmlElement(name = "codigoAuxiliar40_15")
    private String codigoAuxiliar40_15;
    @XmlElement(name = "textoAuxiliar40_15")
    private String textoAuxiliar40_15;
    @XmlElement(name = "codigoAuxiliar40_16")
    private String codigoAuxiliar40_16;
    @XmlElement(name = "textoAuxiliar40_16")
    private String textoAuxiliar40_16;
    @XmlElement(name = "codigoAuxiliar40_17")
    private String codigoAuxiliar40_17;
    @XmlElement(name = "textoAuxiliar40_17")
    private String textoAuxiliar40_17;
    @XmlElement(name = "codigoAuxiliar40_18")
    private String codigoAuxiliar40_18;
    @XmlElement(name = "textoAuxiliar40_18")
    private String textoAuxiliar40_18;
    @XmlElement(name = "codigoAuxiliar40_19")
    private String codigoAuxiliar40_19;
    @XmlElement(name = "textoAuxiliar40_19")
    private String textoAuxiliar40_19;
    @XmlElement(name = "codigoAuxiliar40_20")
    private String codigoAuxiliar40_20;
    @XmlElement(name = "textoAuxiliar40_20")
    private String textoAuxiliar40_20;
    @XmlElement(name = "codigoAuxiliar500_1")
    private String codigoAuxiliar500_1;
    @XmlElement(name = "textoAuxiliar500_1")
    private String textoAuxiliar500_1;
    @XmlElement(name = "codigoAuxiliar500_2")
    private String codigoAuxiliar500_2;
    @XmlElement(name = "textoAuxiliar500_2")
    private String textoAuxiliar500_2;
    @XmlElement(name = "codigoAuxiliar500_3")
    private String codigoAuxiliar500_3;
    @XmlElement(name = "textoAuxiliar500_3")
    private String textoAuxiliar500_3;
    @XmlElement(name = "codigoAuxiliar500_4")
    private String codigoAuxiliar500_4;
    @XmlElement(name = "textoAuxiliar500_4")
    private String textoAuxiliar500_4;
    @XmlElement(name = "codigoAuxiliar500_5")
    private String codigoAuxiliar500_5;
    @XmlElement(name = "textoAuxiliar500_5")
    private String textoAuxiliar500_5;
    @XmlElement(name = "codigoAuxiliar250_1")
    private String codigoAuxiliar250_1;
    @XmlElement(name = "textoAuxiliar250_1")
    private String textoAuxiliar250_1;
    @XmlElement(name = "codigoAuxiliar250_2")
    private String codigoAuxiliar250_2;
    @XmlElement(name = "textoAuxiliar250_2")
    private String textoAuxiliar250_2;
    @XmlElement(name = "codigoAuxiliar250_3")
    private String codigoAuxiliar250_3;
    @XmlElement(name = "textoAuxiliar250_3")
    private String textoAuxiliar250_3;
    @XmlElement(name = "codigoAuxiliar250_4")
    private String codigoAuxiliar250_4;
    @XmlElement(name = "textoAuxiliar250_4")
    private String textoAuxiliar250_4;
    @XmlElement(name = "codigoAuxiliar250_5")
    private String codigoAuxiliar250_5;
    @XmlElement(name = "textoAuxiliar250_5")
    private String textoAuxiliar250_5;
    @XmlElement(name = "codigoAuxiliar250_6")
    private String codigoAuxiliar250_6;
    @XmlElement(name = "textoAuxiliar250_6")
    private String textoAuxiliar250_6;
    @XmlElement(name = "codigoAuxiliar250_7")
    private String codigoAuxiliar250_7;
    @XmlElement(name = "textoAuxiliar250_7")
    private String textoAuxiliar250_7;
    @XmlElement(name = "codigoAuxiliar250_8")
    private String codigoAuxiliar250_8;
    @XmlElement(name = "textoAuxiliar250_8")
    private String textoAuxiliar250_8;
    @XmlElement(name = "codigoAuxiliar250_9")
    private String codigoAuxiliar250_9;
    @XmlElement(name = "textoAuxiliar250_9")
    private String textoAuxiliar250_9;
    @XmlElement(name = "codigoAuxiliar250_10")
    private String codigoAuxiliar250_10;
    @XmlElement(name = "textoAuxiliar250_10")
    private String textoAuxiliar250_10;
    @XmlElement(name = "codigoAuxiliar250_11")
    private String codigoAuxiliar250_11;
    @XmlElement(name = "textoAuxiliar250_11")
    private String textoAuxiliar250_11;
    @XmlElement(name = "codigoAuxiliar250_12")
    private String codigoAuxiliar250_12;
    @XmlElement(name = "textoAuxiliar250_12")
    private String textoAuxiliar250_12;
    @XmlElement(name = "codigoAuxiliar250_13")
    private String codigoAuxiliar250_13;
    @XmlElement(name = "textoAuxiliar250_13")
    private String textoAuxiliar250_13;
    @XmlElement(name = "codigoAuxiliar250_14")
    private String codigoAuxiliar250_14;
    @XmlElement(name = "textoAuxiliar250_14")
    private String textoAuxiliar250_14;
    @XmlElement(name = "codigoAuxiliar250_15")
    private String codigoAuxiliar250_15;
    @XmlElement(name = "textoAuxiliar250_15")
    private String textoAuxiliar250_15;
    @XmlElement(name = "codigoAuxiliar250_16")
    private String codigoAuxiliar250_16;
    @XmlElement(name = "textoAuxiliar250_16")
    private String textoAuxiliar250_16;
    @XmlElement(name = "codigoAuxiliar250_17")
    private String codigoAuxiliar250_17;
    @XmlElement(name = "textoAuxiliar250_17")
    private String textoAuxiliar250_17;
    @XmlElement(name = "codigoAuxiliar250_18")
    private String codigoAuxiliar250_18;
    @XmlElement(name = "textoAuxiliar250_18")
    private String textoAuxiliar250_18;
    @XmlElement(name = "codigoAuxiliar250_19")
    private String codigoAuxiliar250_19;
    @XmlElement(name = "textoAuxiliar250_19")
    private String textoAuxiliar250_19;
    @XmlElement(name = "codigoAuxiliar250_20")
    private String codigoAuxiliar250_20;
    @XmlElement(name = "textoAuxiliar250_20")
    private String textoAuxiliar250_20;
    @XmlElement(name = "codigoAuxiliar250_21")
    private String codigoAuxiliar250_21;
    @XmlElement(name = "textoAuxiliar250_21")
    private String textoAuxiliar250_21;
    @XmlElement(name = "codigoAuxiliar250_22")
    private String codigoAuxiliar250_22;
    @XmlElement(name = "textoAuxiliar250_22")
    private String textoAuxiliar250_22;
    @XmlElement(name = "codigoAuxiliar250_23")
    private String codigoAuxiliar250_23;
    @XmlElement(name = "textoAuxiliar250_23")
    private String textoAuxiliar250_23;
    @XmlElement(name = "codigoAuxiliar250_24")
    private String codigoAuxiliar250_24;
    @XmlElement(name = "textoAuxiliar250_24")
    private String textoAuxiliar250_24;
    @XmlElement(name = "codigoAuxiliar250_25")
    private String codigoAuxiliar250_25;
    @XmlElement(name = "textoAuxiliar250_25")
    private String textoAuxiliar250_25;

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getTipoDocumentoEmisor() {
        return tipoDocumentoEmisor;
    }

    public void setTipoDocumentoEmisor(String tipoDocumentoEmisor) {
        this.tipoDocumentoEmisor = tipoDocumentoEmisor;
    }

    public String getNumeroDocumentoEmisor() {
        return numeroDocumentoEmisor;
    }

    public void setNumeroDocumentoEmisor(String numeroDocumentoEmisor) {
        this.numeroDocumentoEmisor = numeroDocumentoEmisor;
    }

    public String getRazonSocialEmisor() {
        return razonSocialEmisor;
    }

    public void setRazonSocialEmisor(String razonSocialEmisor) {
        this.razonSocialEmisor = razonSocialEmisor;
    }

    public String getNombreComercialEmisor() {
        return nombreComercialEmisor;
    }

    public void setNombreComercialEmisor(String nombreComercialEmisor) {
        this.nombreComercialEmisor = nombreComercialEmisor;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getUbigeoEmisor() {
        return ubigeoEmisor;
    }

    public void setUbigeoEmisor(String ubigeoEmisor) {
        this.ubigeoEmisor = ubigeoEmisor;
    }

    public String getDireccionEmisor() {
        return direccionEmisor;
    }

    public void setDireccionEmisor(String direccionEmisor) {
        this.direccionEmisor = direccionEmisor;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getProvinciaEmisor() {
        return provinciaEmisor;
    }

    public void setProvinciaEmisor(String provinciaEmisor) {
        this.provinciaEmisor = provinciaEmisor;
    }

    public String getDepartamentoEmisor() {
        return departamentoEmisor;
    }

    public void setDepartamentoEmisor(String departamentoEmisor) {
        this.departamentoEmisor = departamentoEmisor;
    }

    public String getDistritoEmisor() {
        return distritoEmisor;
    }

    public void setDistritoEmisor(String distritoEmisor) {
        this.distritoEmisor = distritoEmisor;
    }

    public String getPaisEmisor() {
        return paisEmisor;
    }

    public void setPaisEmisor(String paisEmisor) {
        this.paisEmisor = paisEmisor;
    }

    public String getCorreoEmisor() {
        return correoEmisor;
    }

    public void setCorreoEmisor(String correoEmisor) {
        this.correoEmisor = correoEmisor;
    }

    public String getInHabilitado() {
        return inHabilitado;
    }

    public void setInHabilitado(String inHabilitado) {
        this.inHabilitado = inHabilitado;
    }

    public String getRutaAdjunto_1() {
        return rutaAdjunto_1;
    }

    public void setRutaAdjunto_1(String rutaAdjunto_1) {
        this.rutaAdjunto_1 = rutaAdjunto_1;
    }

    public String getRutaAdjunto_2() {
        return rutaAdjunto_2;
    }

    public void setRutaAdjunto_2(String rutaAdjunto_2) {
        this.rutaAdjunto_2 = rutaAdjunto_2;
    }

    public String getRutaAdjunto_3() {
        return rutaAdjunto_3;
    }

    public void setRutaAdjunto_3(String rutaAdjunto_3) {
        this.rutaAdjunto_3 = rutaAdjunto_3;
    }

    public String getRutaAdjunto_4() {
        return rutaAdjunto_4;
    }

    public void setRutaAdjunto_4(String rutaAdjunto_4) {
        this.rutaAdjunto_4 = rutaAdjunto_4;
    }

    public String getRutaAdjunto_5() {
        return rutaAdjunto_5;
    }

    public void setRutaAdjunto_5(String rutaAdjunto_5) {
        this.rutaAdjunto_5 = rutaAdjunto_5;
    }

    public String getNombreAdjunto_1() {
        return nombreAdjunto_1;
    }

    public void setNombreAdjunto_1(String nombreAdjunto_1) {
        this.nombreAdjunto_1 = nombreAdjunto_1;
    }

    public String getNombreAdjunto_2() {
        return nombreAdjunto_2;
    }

    public void setNombreAdjunto_2(String nombreAdjunto_2) {
        this.nombreAdjunto_2 = nombreAdjunto_2;
    }

    public String getNombreAdjunto_3() {
        return nombreAdjunto_3;
    }

    public void setNombreAdjunto_3(String nombreAdjunto_3) {
        this.nombreAdjunto_3 = nombreAdjunto_3;
    }

    public String getNombreAdjunto_4() {
        return nombreAdjunto_4;
    }

    public void setNombreAdjunto_4(String nombreAdjunto_4) {
        this.nombreAdjunto_4 = nombreAdjunto_4;
    }

    public String getNombreAdjunto_5() {
        return nombreAdjunto_5;
    }

    public void setNombreAdjunto_5(String nombreAdjunto_5) {
        this.nombreAdjunto_5 = nombreAdjunto_5;
    }

    public String getAdjunto_1() {
        return adjunto_1;
    }

    public void setAdjunto_1(String adjunto_1) {
        this.adjunto_1 = adjunto_1;
    }

    public String getAdjunto_2() {
        return adjunto_2;
    }

    public void setAdjunto_2(String adjunto_2) {
        this.adjunto_2 = adjunto_2;
    }

    public String getAdjunto_3() {
        return adjunto_3;
    }

    public void setAdjunto_3(String adjunto_3) {
        this.adjunto_3 = adjunto_3;
    }

    public String getAdjunto_4() {
        return adjunto_4;
    }

    public void setAdjunto_4(String adjunto_4) {
        this.adjunto_4 = adjunto_4;
    }

    public String getAdjunto_5() {
        return adjunto_5;
    }

    public void setAdjunto_5(String adjunto_5) {
        this.adjunto_5 = adjunto_5;
    }

    public String getTipoDocumentoAdquiriente() {
        return tipoDocumentoAdquiriente;
    }

    public void setTipoDocumentoAdquiriente(String tipoDocumentoAdquiriente) {
        this.tipoDocumentoAdquiriente = tipoDocumentoAdquiriente;
    }

    public String getNumeroDocumentoAdquiriente() {
        return numeroDocumentoAdquiriente;
    }

    public void setNumeroDocumentoAdquiriente(String numeroDocumentoAdquiriente) {
        this.numeroDocumentoAdquiriente = numeroDocumentoAdquiriente;
    }

    public String getRazonSocialAdquiriente() {
        return razonSocialAdquiriente;
    }

    public void setRazonSocialAdquiriente(String razonSocialAdquiriente) {
        this.razonSocialAdquiriente = razonSocialAdquiriente;
    }

    public String getLugarDestino() {
        return lugarDestino;
    }

    public void setLugarDestino(String lugarDestino) {
        this.lugarDestino = lugarDestino;
    }

    public String getCorreoAdquiriente() {
        return correoAdquiriente;
    }

    public void setCorreoAdquiriente(String correoAdquiriente) {
        this.correoAdquiriente = correoAdquiriente;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getTipoOperacionFactura() {
        return tipoOperacionFactura;
    }

    public void setTipoOperacionFactura(String tipoOperacionFactura) {
        this.tipoOperacionFactura = tipoOperacionFactura;
    }

    public BigDecimal getTotalDocumentoAnticipo() {
        return totalDocumentoAnticipo;
    }

    public void setTotalDocumentoAnticipo(BigDecimal totalDocumentoAnticipo) {
        this.totalDocumentoAnticipo = totalDocumentoAnticipo;
    }

    public String getTipoDocumentoAnticipo() {
        return tipoDocumentoAnticipo;
    }

    public void setTipoDocumentoAnticipo(String tipoDocumentoAnticipo) {
        this.tipoDocumentoAnticipo = tipoDocumentoAnticipo;
    }

    public String getSerieNumeroDocumentoAnticipo() {
        return serieNumeroDocumentoAnticipo;
    }

    public void setSerieNumeroDocumentoAnticipo(String serieNumeroDocumentoAnticipo) {
        this.serieNumeroDocumentoAnticipo = serieNumeroDocumentoAnticipo;
    }

    public BigDecimal getTotalFondoInclusionSocial() {
        return totalFondoInclusionSocial;
    }

    public void setTotalFondoInclusionSocial(BigDecimal totalFondoInclusionSocial) {
        this.totalFondoInclusionSocial = totalFondoInclusionSocial;
    }

    public BigDecimal getTotalValorVentaNetoOpGravadas() {
        return totalValorVentaNetoOpGravadas;
    }

    public void setTotalValorVentaNetoOpGravadas(BigDecimal totalValorVentaNetoOpGravadas) {
        this.totalValorVentaNetoOpGravadas = totalValorVentaNetoOpGravadas;
    }

    public BigDecimal getTotalValorVentaNetoOpNoGravada() {
        return totalValorVentaNetoOpNoGravada;
    }

    public void setTotalValorVentaNetoOpNoGravada(BigDecimal totalValorVentaNetoOpNoGravada) {
        this.totalValorVentaNetoOpNoGravada = totalValorVentaNetoOpNoGravada;
    }

    public BigDecimal getTotalValorVentaNetoOpExoneradas() {
        return totalValorVentaNetoOpExoneradas;
    }

    public void setTotalValorVentaNetoOpExoneradas(BigDecimal totalValorVentaNetoOpExoneradas) {
        this.totalValorVentaNetoOpExoneradas = totalValorVentaNetoOpExoneradas;
    }

    public BigDecimal getTotalValorVentaNetoOpGratuitas() {
        return totalValorVentaNetoOpGratuitas;
    }

    public void setTotalValorVentaNetoOpGratuitas(BigDecimal totalValorVentaNetoOpGratuitas) {
        this.totalValorVentaNetoOpGratuitas = totalValorVentaNetoOpGratuitas;
    }

    public BigDecimal getDescuentosGlobales() {
        return descuentosGlobales;
    }

    public void setDescuentosGlobales(BigDecimal descuentosGlobales) {
        this.descuentosGlobales = descuentosGlobales;
    }

    public BigDecimal getBaseImponiblePercepcion() {
        return baseImponiblePercepcion;
    }

    public void setBaseImponiblePercepcion(BigDecimal baseImponiblePercepcion) {
        this.baseImponiblePercepcion = baseImponiblePercepcion;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalIgv() {
        return totalIgv;
    }

    public void setTotalIgv(BigDecimal totalIgv) {
        this.totalIgv = totalIgv;
    }

    public BigDecimal getTotalIsc() {
        return totalIsc;
    }

    public void setTotalIsc(BigDecimal totalIsc) {
        this.totalIsc = totalIsc;
    }

    public BigDecimal getTotalOtrosTributos() {
        return totalOtrosTributos;
    }

    public void setTotalOtrosTributos(BigDecimal totalOtrosTributos) {
        this.totalOtrosTributos = totalOtrosTributos;
    }

    public BigDecimal getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(BigDecimal totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getTotalPercepcion() {
        return totalPercepcion;
    }

    public void setTotalPercepcion(BigDecimal totalPercepcion) {
        this.totalPercepcion = totalPercepcion;
    }

    public BigDecimal getTotalVentaConPercepcion() {
        return totalVentaConPercepcion;
    }

    public void setTotalVentaConPercepcion(BigDecimal totalVentaConPercepcion) {
        this.totalVentaConPercepcion = totalVentaConPercepcion;
    }

    public BigDecimal getMonto1() {
        return monto1;
    }

    public void setMonto1(BigDecimal monto1) {
        this.monto1 = monto1;
    }

    public BigDecimal getMonto2() {
        return monto2;
    }

    public void setMonto2(BigDecimal monto2) {
        this.monto2 = monto2;
    }

    public BigDecimal getMonto3() {
        return monto3;
    }

    public void setMonto3(BigDecimal monto3) {
        this.monto3 = monto3;
    }

    public BigDecimal getMonto4() {
        return monto4;
    }

    public void setMonto4(BigDecimal monto4) {
        this.monto4 = monto4;
    }

    public BigDecimal getMonto5() {
        return monto5;
    }

    public void setMonto5(BigDecimal monto5) {
        this.monto5 = monto5;
    }

    public String getTipoDocumentoReferenciaPrincipal() {
        return tipoDocumentoReferenciaPrincipal;
    }

    public void setTipoDocumentoReferenciaPrincipal(String tipoDocumentoReferenciaPrincipal) {
        this.tipoDocumentoReferenciaPrincipal = tipoDocumentoReferenciaPrincipal;
    }

    public String getNumeroDocumentoReferenciaPrincipal() {
        return numeroDocumentoReferenciaPrincipal;
    }

    public void setNumeroDocumentoReferenciaPrincipal(String numeroDocumentoReferenciaPrincipal) {
        this.numeroDocumentoReferenciaPrincipal = numeroDocumentoReferenciaPrincipal;
    }

    public String getTipoDocumentoReferenciaCorregido() {
        return tipoDocumentoReferenciaCorregido;
    }

    public void setTipoDocumentoReferenciaCorregido(String tipoDocumentoReferenciaCorregido) {
        this.tipoDocumentoReferenciaCorregido = tipoDocumentoReferenciaCorregido;
    }

    public String getNumeroDocumentoReferenciaCorregido() {
        return numeroDocumentoReferenciaCorregido;
    }

    public void setNumeroDocumentoReferenciaCorregido(String numeroDocumentoReferenciaCorregido) {
        this.numeroDocumentoReferenciaCorregido = numeroDocumentoReferenciaCorregido;
    }

    public String getMotivoDocumento() {
        return motivoDocumento;
    }

    public void setMotivoDocumento(String motivoDocumento) {
        this.motivoDocumento = motivoDocumento;
    }

    public String getSerieNumeroAfectado() {
        return serieNumeroAfectado;
    }

    public void setSerieNumeroAfectado(String serieNumeroAfectado) {
        this.serieNumeroAfectado = serieNumeroAfectado;
    }

    public String getCodigoSerieNumeroAfectado() {
        return codigoSerieNumeroAfectado;
    }

    public void setCodigoSerieNumeroAfectado(String codigoSerieNumeroAfectado) {
        this.codigoSerieNumeroAfectado = codigoSerieNumeroAfectado;
    }

    public BigDecimal getPorcentajePercepcion() {
        return porcentajePercepcion;
    }

    public void setPorcentajePercepcion(BigDecimal porcentajePercepcion) {
        this.porcentajePercepcion = porcentajePercepcion;
    }

    public BigDecimal getTotalRetencion() {
        return totalRetencion;
    }

    public void setTotalRetencion(BigDecimal totalRetencion) {
        this.totalRetencion = totalRetencion;
    }

    public BigDecimal getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public BigDecimal getTotalDetraccion() {
        return totalDetraccion;
    }

    public void setTotalDetraccion(BigDecimal totalDetraccion) {
        this.totalDetraccion = totalDetraccion;
    }

    public BigDecimal getValorReferencialDetraccion() {
        return valorReferencialDetraccion;
    }

    public void setValorReferencialDetraccion(BigDecimal valorReferencialDetraccion) {
        this.valorReferencialDetraccion = valorReferencialDetraccion;
    }

    public BigDecimal getPorcentajeDetraccion() {
        return porcentajeDetraccion;
    }

    public void setPorcentajeDetraccion(BigDecimal porcentajeDetraccion) {
        this.porcentajeDetraccion = porcentajeDetraccion;
    }

    public String getDescripcionDetraccion() {
        return descripcionDetraccion;
    }

    public void setDescripcionDetraccion(String descripcionDetraccion) {
        this.descripcionDetraccion = descripcionDetraccion;
    }

    public BigDecimal getTotalBonificacion() {
        return totalBonificacion;
    }

    public void setTotalBonificacion(BigDecimal totalBonificacion) {
        this.totalBonificacion = totalBonificacion;
    }

    public String getUbigeoDireccionPtoPartida() {
        return ubigeoDireccionPtoPartida;
    }

    public void setUbigeoDireccionPtoPartida(String ubigeoDireccionPtoPartida) {
        this.ubigeoDireccionPtoPartida = ubigeoDireccionPtoPartida;
    }

    public String getDireccionCompletaPtoPartida() {
        return direccionCompletaPtoPartida;
    }

    public void setDireccionCompletaPtoPartida(String direccionCompletaPtoPartida) {
        this.direccionCompletaPtoPartida = direccionCompletaPtoPartida;
    }

    public String getUrbanizacionPtoPartida() {
        return urbanizacionPtoPartida;
    }

    public void setUrbanizacionPtoPartida(String urbanizacionPtoPartida) {
        this.urbanizacionPtoPartida = urbanizacionPtoPartida;
    }

    public String getProvinciaPtoPartida() {
        return provinciaPtoPartida;
    }

    public void setProvinciaPtoPartida(String provinciaPtoPartida) {
        this.provinciaPtoPartida = provinciaPtoPartida;
    }

    public String getDepartamentoPtoPartida() {
        return departamentoPtoPartida;
    }

    public void setDepartamentoPtoPartida(String departamentoPtoPartida) {
        this.departamentoPtoPartida = departamentoPtoPartida;
    }

    public String getDistritoPtoPartida() {
        return distritoPtoPartida;
    }

    public void setDistritoPtoPartida(String distritoPtoPartida) {
        this.distritoPtoPartida = distritoPtoPartida;
    }

    public String getPaisPtoPartida() {
        return paisPtoPartida;
    }

    public void setPaisPtoPartida(String paisPtoPartida) {
        this.paisPtoPartida = paisPtoPartida;
    }

    public String getUbigeoDireccionPtoLlegada() {
        return ubigeoDireccionPtoLlegada;
    }

    public void setUbigeoDireccionPtoLlegada(String ubigeoDireccionPtoLlegada) {
        this.ubigeoDireccionPtoLlegada = ubigeoDireccionPtoLlegada;
    }

    public String getDireccionCompletaPtoLlegada() {
        return direccionCompletaPtoLlegada;
    }

    public void setDireccionCompletaPtoLlegada(String direccionCompletaPtoLlegada) {
        this.direccionCompletaPtoLlegada = direccionCompletaPtoLlegada;
    }

    public String getUrbanizacionPtoLlegada() {
        return urbanizacionPtoLlegada;
    }

    public void setUrbanizacionPtoLlegada(String urbanizacionPtoLlegada) {
        this.urbanizacionPtoLlegada = urbanizacionPtoLlegada;
    }

    public String getProvinciaPtoLlegada() {
        return provinciaPtoLlegada;
    }

    public void setProvinciaPtoLlegada(String provinciaPtoLlegada) {
        this.provinciaPtoLlegada = provinciaPtoLlegada;
    }

    public String getDepartamentoPtoLlegada() {
        return departamentoPtoLlegada;
    }

    public void setDepartamentoPtoLlegada(String departamentoPtoLlegada) {
        this.departamentoPtoLlegada = departamentoPtoLlegada;
    }

    public String getDistritoPtoLlegada() {
        return distritoPtoLlegada;
    }

    public void setDistritoPtoLlegada(String distritoPtoLlegada) {
        this.distritoPtoLlegada = distritoPtoLlegada;
    }

    public String getPaisPtoLlegada() {
        return paisPtoLlegada;
    }

    public void setPaisPtoLlegada(String paisPtoLlegada) {
        this.paisPtoLlegada = paisPtoLlegada;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getNumeroConstanciaVehiculo() {
        return numeroConstanciaVehiculo;
    }

    public void setNumeroConstanciaVehiculo(String numeroConstanciaVehiculo) {
        this.numeroConstanciaVehiculo = numeroConstanciaVehiculo;
    }

    public String getNumeroLicenciaConducir() {
        return numeroLicenciaConducir;
    }

    public void setNumeroLicenciaConducir(String numeroLicenciaConducir) {
        this.numeroLicenciaConducir = numeroLicenciaConducir;
    }

    public String getNumeroRucTransportista() {
        return numeroRucTransportista;
    }

    public void setNumeroRucTransportista(String numeroRucTransportista) {
        this.numeroRucTransportista = numeroRucTransportista;
    }

    public String getNumeroRucTransportistaCuenta() {
        return numeroRucTransportistaCuenta;
    }

    public void setNumeroRucTransportistaCuenta(String numeroRucTransportistaCuenta) {
        this.numeroRucTransportistaCuenta = numeroRucTransportistaCuenta;
    }

    public String getRazonSocialTransportista() {
        return razonSocialTransportista;
    }

    public void setRazonSocialTransportista(String razonSocialTransportista) {
        this.razonSocialTransportista = razonSocialTransportista;
    }

    public String getTipoReferencia_1() {
        return tipoReferencia_1;
    }

    public void setTipoReferencia_1(String tipoReferencia_1) {
        this.tipoReferencia_1 = tipoReferencia_1;
    }

    public String getNumeroDocumentoReferencia_1() {
        return numeroDocumentoReferencia_1;
    }

    public void setNumeroDocumentoReferencia_1(String numeroDocumentoReferencia_1) {
        this.numeroDocumentoReferencia_1 = numeroDocumentoReferencia_1;
    }

    public String getTipoReferencia_2() {
        return tipoReferencia_2;
    }

    public void setTipoReferencia_2(String tipoReferencia_2) {
        this.tipoReferencia_2 = tipoReferencia_2;
    }

    public String getNumeroDocumentoReferencia_2() {
        return numeroDocumentoReferencia_2;
    }

    public void setNumeroDocumentoReferencia_2(String numeroDocumentoReferencia_2) {
        this.numeroDocumentoReferencia_2 = numeroDocumentoReferencia_2;
    }

    public String getTipoReferencia_3() {
        return tipoReferencia_3;
    }

    public void setTipoReferencia_3(String tipoReferencia_3) {
        this.tipoReferencia_3 = tipoReferencia_3;
    }

    public String getNumeroDocumentoReferencia_3() {
        return numeroDocumentoReferencia_3;
    }

    public void setNumeroDocumentoReferencia_3(String numeroDocumentoReferencia_3) {
        this.numeroDocumentoReferencia_3 = numeroDocumentoReferencia_3;
    }

    public String getTipoReferencia_4() {
        return tipoReferencia_4;
    }

    public void setTipoReferencia_4(String tipoReferencia_4) {
        this.tipoReferencia_4 = tipoReferencia_4;
    }

    public String getNumeroDocumentoReferencia_4() {
        return numeroDocumentoReferencia_4;
    }

    public void setNumeroDocumentoReferencia_4(String numeroDocumentoReferencia_4) {
        this.numeroDocumentoReferencia_4 = numeroDocumentoReferencia_4;
    }

    public String getTipoReferencia_5() {
        return tipoReferencia_5;
    }

    public void setTipoReferencia_5(String tipoReferencia_5) {
        this.tipoReferencia_5 = tipoReferencia_5;
    }

    public String getNumeroDocumentoReferencia_5() {
        return numeroDocumentoReferencia_5;
    }

    public void setNumeroDocumentoReferencia_5(String numeroDocumentoReferencia_5) {
        this.numeroDocumentoReferencia_5 = numeroDocumentoReferencia_5;
    }

    public String getTipoReferenciaAdicional_1() {
        return tipoReferenciaAdicional_1;
    }

    public void setTipoReferenciaAdicional_1(String tipoReferenciaAdicional_1) {
        this.tipoReferenciaAdicional_1 = tipoReferenciaAdicional_1;
    }

    public String getNumeroDocumentoReferenciaAdicional_1() {
        return numeroDocumentoReferenciaAdicional_1;
    }

    public void setNumeroDocumentoReferenciaAdicional_1(String numeroDocumentoReferenciaAdicional_1) {
        this.numeroDocumentoReferenciaAdicional_1 = numeroDocumentoReferenciaAdicional_1;
    }

    public String getTipoReferenciaAdicional_2() {
        return tipoReferenciaAdicional_2;
    }

    public void setTipoReferenciaAdicional_2(String tipoReferenciaAdicional_2) {
        this.tipoReferenciaAdicional_2 = tipoReferenciaAdicional_2;
    }

    public String getNumeroDocumentoReferenciaAdicional_2() {
        return numeroDocumentoReferenciaAdicional_2;
    }

    public void setNumeroDocumentoReferenciaAdicional_2(String numeroDocumentoReferenciaAdicional_2) {
        this.numeroDocumentoReferenciaAdicional_2 = numeroDocumentoReferenciaAdicional_2;
    }

    public String getTipoReferenciaAdicional_3() {
        return tipoReferenciaAdicional_3;
    }

    public void setTipoReferenciaAdicional_3(String tipoReferenciaAdicional_3) {
        this.tipoReferenciaAdicional_3 = tipoReferenciaAdicional_3;
    }

    public String getNumeroDocumentoReferenciaAdicional_3() {
        return numeroDocumentoReferenciaAdicional_3;
    }

    public void setNumeroDocumentoReferenciaAdicional_3(String numeroDocumentoReferenciaAdicional_3) {
        this.numeroDocumentoReferenciaAdicional_3 = numeroDocumentoReferenciaAdicional_3;
    }

    public String getTipoReferenciaAdicional_4() {
        return tipoReferenciaAdicional_4;
    }

    public void setTipoReferenciaAdicional_4(String tipoReferenciaAdicional_4) {
        this.tipoReferenciaAdicional_4 = tipoReferenciaAdicional_4;
    }

    public String getNumeroDocumentoReferenciaAdicional_4() {
        return numeroDocumentoReferenciaAdicional_4;
    }

    public void setNumeroDocumentoReferenciaAdicional_4(String numeroDocumentoReferenciaAdicional_4) {
        this.numeroDocumentoReferenciaAdicional_4 = numeroDocumentoReferenciaAdicional_4;
    }

    public String getTipoReferenciaAdicional_5() {
        return tipoReferenciaAdicional_5;
    }

    public void setTipoReferenciaAdicional_5(String tipoReferenciaAdicional_5) {
        this.tipoReferenciaAdicional_5 = tipoReferenciaAdicional_5;
    }

    public String getNumeroDocumentoReferenciaAdicional_5() {
        return numeroDocumentoReferenciaAdicional_5;
    }

    public void setNumeroDocumentoReferenciaAdicional_5(String numeroDocumentoReferenciaAdicional_5) {
        this.numeroDocumentoReferenciaAdicional_5 = numeroDocumentoReferenciaAdicional_5;
    }

    public String getCodigoLeyenda_1() {
        return codigoLeyenda_1;
    }

    public void setCodigoLeyenda_1(String codigoLeyenda_1) {
        this.codigoLeyenda_1 = codigoLeyenda_1;
    }

    public String getTextoLeyenda_1() {
        return textoLeyenda_1;
    }

    public void setTextoLeyenda_1(String textoLeyenda_1) {
        this.textoLeyenda_1 = textoLeyenda_1;
    }

    public String getTextoAdicionalLeyenda_1() {
        return textoAdicionalLeyenda_1;
    }

    public void setTextoAdicionalLeyenda_1(String textoAdicionalLeyenda_1) {
        this.textoAdicionalLeyenda_1 = textoAdicionalLeyenda_1;
    }

    public String getCodigoLeyenda_2() {
        return codigoLeyenda_2;
    }

    public void setCodigoLeyenda_2(String codigoLeyenda_2) {
        this.codigoLeyenda_2 = codigoLeyenda_2;
    }

    public String getTextoLeyenda_2() {
        return textoLeyenda_2;
    }

    public void setTextoLeyenda_2(String textoLeyenda_2) {
        this.textoLeyenda_2 = textoLeyenda_2;
    }

    public String getTextoAdicionalLeyenda_2() {
        return textoAdicionalLeyenda_2;
    }

    public void setTextoAdicionalLeyenda_2(String textoAdicionalLeyenda_2) {
        this.textoAdicionalLeyenda_2 = textoAdicionalLeyenda_2;
    }

    public String getCodigoLeyenda_3() {
        return codigoLeyenda_3;
    }

    public void setCodigoLeyenda_3(String codigoLeyenda_3) {
        this.codigoLeyenda_3 = codigoLeyenda_3;
    }

    public String getTextoLeyenda_3() {
        return textoLeyenda_3;
    }

    public void setTextoLeyenda_3(String textoLeyenda_3) {
        this.textoLeyenda_3 = textoLeyenda_3;
    }

    public String getTextoAdicionalLeyenda_3() {
        return textoAdicionalLeyenda_3;
    }

    public void setTextoAdicionalLeyenda_3(String textoAdicionalLeyenda_3) {
        this.textoAdicionalLeyenda_3 = textoAdicionalLeyenda_3;
    }

    public String getCodigoLeyenda_4() {
        return codigoLeyenda_4;
    }

    public void setCodigoLeyenda_4(String codigoLeyenda_4) {
        this.codigoLeyenda_4 = codigoLeyenda_4;
    }

    public String getTextoLeyenda_4() {
        return textoLeyenda_4;
    }

    public void setTextoLeyenda_4(String textoLeyenda_4) {
        this.textoLeyenda_4 = textoLeyenda_4;
    }

    public String getTextoAdicionalLeyenda_4() {
        return textoAdicionalLeyenda_4;
    }

    public void setTextoAdicionalLeyenda_4(String textoAdicionalLeyenda_4) {
        this.textoAdicionalLeyenda_4 = textoAdicionalLeyenda_4;
    }

    public String getCodigoLeyenda_5() {
        return codigoLeyenda_5;
    }

    public void setCodigoLeyenda_5(String codigoLeyenda_5) {
        this.codigoLeyenda_5 = codigoLeyenda_5;
    }

    public String getTextoLeyenda_5() {
        return textoLeyenda_5;
    }

    public void setTextoLeyenda_5(String textoLeyenda_5) {
        this.textoLeyenda_5 = textoLeyenda_5;
    }

    public String getTextoAdicionalLeyenda_5() {
        return textoAdicionalLeyenda_5;
    }

    public void setTextoAdicionalLeyenda_5(String textoAdicionalLeyenda_5) {
        this.textoAdicionalLeyenda_5 = textoAdicionalLeyenda_5;
    }

    public String getCodigoLeyenda_6() {
        return codigoLeyenda_6;
    }

    public void setCodigoLeyenda_6(String codigoLeyenda_6) {
        this.codigoLeyenda_6 = codigoLeyenda_6;
    }

    public String getTextoLeyenda_6() {
        return textoLeyenda_6;
    }

    public void setTextoLeyenda_6(String textoLeyenda_6) {
        this.textoLeyenda_6 = textoLeyenda_6;
    }

    public String getTextoAdicionalLeyenda_6() {
        return textoAdicionalLeyenda_6;
    }

    public void setTextoAdicionalLeyenda_6(String textoAdicionalLeyenda_6) {
        this.textoAdicionalLeyenda_6 = textoAdicionalLeyenda_6;
    }

    public String getCodigoLeyenda_7() {
        return codigoLeyenda_7;
    }

    public void setCodigoLeyenda_7(String codigoLeyenda_7) {
        this.codigoLeyenda_7 = codigoLeyenda_7;
    }

    public String getTextoLeyenda_7() {
        return textoLeyenda_7;
    }

    public void setTextoLeyenda_7(String textoLeyenda_7) {
        this.textoLeyenda_7 = textoLeyenda_7;
    }

    public String getTextoAdicionalLeyenda_7() {
        return textoAdicionalLeyenda_7;
    }

    public void setTextoAdicionalLeyenda_7(String textoAdicionalLeyenda_7) {
        this.textoAdicionalLeyenda_7 = textoAdicionalLeyenda_7;
    }

    public String getCodigoLeyenda_8() {
        return codigoLeyenda_8;
    }

    public void setCodigoLeyenda_8(String codigoLeyenda_8) {
        this.codigoLeyenda_8 = codigoLeyenda_8;
    }

    public String getTextoLeyenda_8() {
        return textoLeyenda_8;
    }

    public void setTextoLeyenda_8(String textoLeyenda_8) {
        this.textoLeyenda_8 = textoLeyenda_8;
    }

    public String getTextoAdicionalLeyenda_8() {
        return textoAdicionalLeyenda_8;
    }

    public void setTextoAdicionalLeyenda_8(String textoAdicionalLeyenda_8) {
        this.textoAdicionalLeyenda_8 = textoAdicionalLeyenda_8;
    }

    public String getCodigoLeyenda_9() {
        return codigoLeyenda_9;
    }

    public void setCodigoLeyenda_9(String codigoLeyenda_9) {
        this.codigoLeyenda_9 = codigoLeyenda_9;
    }

    public String getTextoLeyenda_9() {
        return textoLeyenda_9;
    }

    public void setTextoLeyenda_9(String textoLeyenda_9) {
        this.textoLeyenda_9 = textoLeyenda_9;
    }

    public String getTextoAdicionalLeyenda_9() {
        return textoAdicionalLeyenda_9;
    }

    public void setTextoAdicionalLeyenda_9(String textoAdicionalLeyenda_9) {
        this.textoAdicionalLeyenda_9 = textoAdicionalLeyenda_9;
    }

    public String getCodigoLeyenda_10() {
        return codigoLeyenda_10;
    }

    public void setCodigoLeyenda_10(String codigoLeyenda_10) {
        this.codigoLeyenda_10 = codigoLeyenda_10;
    }

    public String getTextoLeyenda_10() {
        return textoLeyenda_10;
    }

    public void setTextoLeyenda_10(String textoLeyenda_10) {
        this.textoLeyenda_10 = textoLeyenda_10;
    }

    public String getTextoAdicionalLeyenda_10() {
        return textoAdicionalLeyenda_10;
    }

    public void setTextoAdicionalLeyenda_10(String textoAdicionalLeyenda_10) {
        this.textoAdicionalLeyenda_10 = textoAdicionalLeyenda_10;
    }

    public String getCodigoLeyenda_11() {
        return codigoLeyenda_11;
    }

    public void setCodigoLeyenda_11(String codigoLeyenda_11) {
        this.codigoLeyenda_11 = codigoLeyenda_11;
    }

    public String getTextoLeyenda_11() {
        return textoLeyenda_11;
    }

    public void setTextoLeyenda_11(String textoLeyenda_11) {
        this.textoLeyenda_11 = textoLeyenda_11;
    }

    public String getTextoAdicionalLeyenda_11() {
        return textoAdicionalLeyenda_11;
    }

    public void setTextoAdicionalLeyenda_11(String textoAdicionalLeyenda_11) {
        this.textoAdicionalLeyenda_11 = textoAdicionalLeyenda_11;
    }

    public String getCodigoLeyenda_12() {
        return codigoLeyenda_12;
    }

    public void setCodigoLeyenda_12(String codigoLeyenda_12) {
        this.codigoLeyenda_12 = codigoLeyenda_12;
    }

    public String getTextoLeyenda_12() {
        return textoLeyenda_12;
    }

    public void setTextoLeyenda_12(String textoLeyenda_12) {
        this.textoLeyenda_12 = textoLeyenda_12;
    }

    public String getTextoAdicionalLeyenda_12() {
        return textoAdicionalLeyenda_12;
    }

    public void setTextoAdicionalLeyenda_12(String textoAdicionalLeyenda_12) {
        this.textoAdicionalLeyenda_12 = textoAdicionalLeyenda_12;
    }

    public String getCodigoLeyenda_13() {
        return codigoLeyenda_13;
    }

    public void setCodigoLeyenda_13(String codigoLeyenda_13) {
        this.codigoLeyenda_13 = codigoLeyenda_13;
    }

    public String getTextoLeyenda_13() {
        return textoLeyenda_13;
    }

    public void setTextoLeyenda_13(String textoLeyenda_13) {
        this.textoLeyenda_13 = textoLeyenda_13;
    }

    public String getTextoAdicionalLeyenda_13() {
        return textoAdicionalLeyenda_13;
    }

    public void setTextoAdicionalLeyenda_13(String textoAdicionalLeyenda_13) {
        this.textoAdicionalLeyenda_13 = textoAdicionalLeyenda_13;
    }

    public String getCodigoLeyenda_14() {
        return codigoLeyenda_14;
    }

    public void setCodigoLeyenda_14(String codigoLeyenda_14) {
        this.codigoLeyenda_14 = codigoLeyenda_14;
    }

    public String getTextoLeyenda_14() {
        return textoLeyenda_14;
    }

    public void setTextoLeyenda_14(String textoLeyenda_14) {
        this.textoLeyenda_14 = textoLeyenda_14;
    }

    public String getTextoAdicionalLeyenda_14() {
        return textoAdicionalLeyenda_14;
    }

    public void setTextoAdicionalLeyenda_14(String textoAdicionalLeyenda_14) {
        this.textoAdicionalLeyenda_14 = textoAdicionalLeyenda_14;
    }

    public String getCodigoLeyenda_15() {
        return codigoLeyenda_15;
    }

    public void setCodigoLeyenda_15(String codigoLeyenda_15) {
        this.codigoLeyenda_15 = codigoLeyenda_15;
    }

    public String getTextoLeyenda_15() {
        return textoLeyenda_15;
    }

    public void setTextoLeyenda_15(String textoLeyenda_15) {
        this.textoLeyenda_15 = textoLeyenda_15;
    }

    public String getTextoAdicionalLeyenda_15() {
        return textoAdicionalLeyenda_15;
    }

    public void setTextoAdicionalLeyenda_15(String textoAdicionalLeyenda_15) {
        this.textoAdicionalLeyenda_15 = textoAdicionalLeyenda_15;
    }

    public String getCodigoLeyenda_16() {
        return codigoLeyenda_16;
    }

    public void setCodigoLeyenda_16(String codigoLeyenda_16) {
        this.codigoLeyenda_16 = codigoLeyenda_16;
    }

    public String getTextoLeyenda_16() {
        return textoLeyenda_16;
    }

    public void setTextoLeyenda_16(String textoLeyenda_16) {
        this.textoLeyenda_16 = textoLeyenda_16;
    }

    public String getTextoAdicionalLeyenda_16() {
        return textoAdicionalLeyenda_16;
    }

    public void setTextoAdicionalLeyenda_16(String textoAdicionalLeyenda_16) {
        this.textoAdicionalLeyenda_16 = textoAdicionalLeyenda_16;
    }

    public String getCodigoLeyenda_17() {
        return codigoLeyenda_17;
    }

    public void setCodigoLeyenda_17(String codigoLeyenda_17) {
        this.codigoLeyenda_17 = codigoLeyenda_17;
    }

    public String getTextoLeyenda_17() {
        return textoLeyenda_17;
    }

    public void setTextoLeyenda_17(String textoLeyenda_17) {
        this.textoLeyenda_17 = textoLeyenda_17;
    }

    public String getTextoAdicionalLeyenda_17() {
        return textoAdicionalLeyenda_17;
    }

    public void setTextoAdicionalLeyenda_17(String textoAdicionalLeyenda_17) {
        this.textoAdicionalLeyenda_17 = textoAdicionalLeyenda_17;
    }

    public String getCodigoLeyenda_18() {
        return codigoLeyenda_18;
    }

    public void setCodigoLeyenda_18(String codigoLeyenda_18) {
        this.codigoLeyenda_18 = codigoLeyenda_18;
    }

    public String getTextoLeyenda_18() {
        return textoLeyenda_18;
    }

    public void setTextoLeyenda_18(String textoLeyenda_18) {
        this.textoLeyenda_18 = textoLeyenda_18;
    }

    public String getTextoAdicionalLeyenda_18() {
        return textoAdicionalLeyenda_18;
    }

    public void setTextoAdicionalLeyenda_18(String textoAdicionalLeyenda_18) {
        this.textoAdicionalLeyenda_18 = textoAdicionalLeyenda_18;
    }

    public String getCodigoLeyenda_19() {
        return codigoLeyenda_19;
    }

    public void setCodigoLeyenda_19(String codigoLeyenda_19) {
        this.codigoLeyenda_19 = codigoLeyenda_19;
    }

    public String getTextoLeyenda_19() {
        return textoLeyenda_19;
    }

    public void setTextoLeyenda_19(String textoLeyenda_19) {
        this.textoLeyenda_19 = textoLeyenda_19;
    }

    public String getTextoAdicionalLeyenda_19() {
        return textoAdicionalLeyenda_19;
    }

    public void setTextoAdicionalLeyenda_19(String textoAdicionalLeyenda_19) {
        this.textoAdicionalLeyenda_19 = textoAdicionalLeyenda_19;
    }

    public String getCodigoLeyenda_20() {
        return codigoLeyenda_20;
    }

    public void setCodigoLeyenda_20(String codigoLeyenda_20) {
        this.codigoLeyenda_20 = codigoLeyenda_20;
    }

    public String getTextoLeyenda_20() {
        return textoLeyenda_20;
    }

    public void setTextoLeyenda_20(String textoLeyenda_20) {
        this.textoLeyenda_20 = textoLeyenda_20;
    }

    public String getTextoAdicionalLeyenda_20() {
        return textoAdicionalLeyenda_20;
    }

    public void setTextoAdicionalLeyenda_20(String textoAdicionalLeyenda_20) {
        this.textoAdicionalLeyenda_20 = textoAdicionalLeyenda_20;
    }

    public List<DocumentItem> getItems() {
        if (items == null) {
            items = new LinkedList<>();
        }
        return items;
    }

    public void setItems(List<DocumentItem> items) {
        this.items = items;
    }

    public List<DocumentAdvance> getAdvances() {
        if (advances == null) {
            advances = new LinkedList<>();
        }
        return advances;
    }

    public void setAdvances(List<DocumentAdvance> advances) {
        this.advances = advances;
    }

    public List<DocumentReference> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<DocumentReference> referencias) {
        this.referencias = referencias;
    }

    public String getCodigoAuxiliar100_1() {
        return codigoAuxiliar100_1;
    }

    public void setCodigoAuxiliar100_1(String codigoAuxiliar100_1) {
        this.codigoAuxiliar100_1 = codigoAuxiliar100_1;
    }

    public String getTextoAuxiliar100_1() {
        return textoAuxiliar100_1;
    }

    public void setTextoAuxiliar100_1(String textoAuxiliar100_1) {
        this.textoAuxiliar100_1 = textoAuxiliar100_1;
    }

    public String getCodigoAuxiliar100_2() {
        return codigoAuxiliar100_2;
    }

    public void setCodigoAuxiliar100_2(String codigoAuxiliar100_2) {
        this.codigoAuxiliar100_2 = codigoAuxiliar100_2;
    }

    public String getTextoAuxiliar100_2() {
        return textoAuxiliar100_2;
    }

    public void setTextoAuxiliar100_2(String textoAuxiliar100_2) {
        this.textoAuxiliar100_2 = textoAuxiliar100_2;
    }

    public String getCodigoAuxiliar100_3() {
        return codigoAuxiliar100_3;
    }

    public void setCodigoAuxiliar100_3(String codigoAuxiliar100_3) {
        this.codigoAuxiliar100_3 = codigoAuxiliar100_3;
    }

    public String getTextoAuxiliar100_3() {
        return textoAuxiliar100_3;
    }

    public void setTextoAuxiliar100_3(String textoAuxiliar100_3) {
        this.textoAuxiliar100_3 = textoAuxiliar100_3;
    }

    public String getCodigoAuxiliar100_4() {
        return codigoAuxiliar100_4;
    }

    public void setCodigoAuxiliar100_4(String codigoAuxiliar100_4) {
        this.codigoAuxiliar100_4 = codigoAuxiliar100_4;
    }

    public String getTextoAuxiliar100_4() {
        return textoAuxiliar100_4;
    }

    public void setTextoAuxiliar100_4(String textoAuxiliar100_4) {
        this.textoAuxiliar100_4 = textoAuxiliar100_4;
    }

    public String getCodigoAuxiliar100_5() {
        return codigoAuxiliar100_5;
    }

    public void setCodigoAuxiliar100_5(String codigoAuxiliar100_5) {
        this.codigoAuxiliar100_5 = codigoAuxiliar100_5;
    }

    public String getTextoAuxiliar100_5() {
        return textoAuxiliar100_5;
    }

    public void setTextoAuxiliar100_5(String textoAuxiliar100_5) {
        this.textoAuxiliar100_5 = textoAuxiliar100_5;
    }

    public String getCodigoAuxiliar100_6() {
        return codigoAuxiliar100_6;
    }

    public void setCodigoAuxiliar100_6(String codigoAuxiliar100_6) {
        this.codigoAuxiliar100_6 = codigoAuxiliar100_6;
    }

    public String getTextoAuxiliar100_6() {
        return textoAuxiliar100_6;
    }

    public void setTextoAuxiliar100_6(String textoAuxiliar100_6) {
        this.textoAuxiliar100_6 = textoAuxiliar100_6;
    }

    public String getCodigoAuxiliar100_7() {
        return codigoAuxiliar100_7;
    }

    public void setCodigoAuxiliar100_7(String codigoAuxiliar100_7) {
        this.codigoAuxiliar100_7 = codigoAuxiliar100_7;
    }

    public String getTextoAuxiliar100_7() {
        return textoAuxiliar100_7;
    }

    public void setTextoAuxiliar100_7(String textoAuxiliar100_7) {
        this.textoAuxiliar100_7 = textoAuxiliar100_7;
    }

    public String getCodigoAuxiliar100_8() {
        return codigoAuxiliar100_8;
    }

    public void setCodigoAuxiliar100_8(String codigoAuxiliar100_8) {
        this.codigoAuxiliar100_8 = codigoAuxiliar100_8;
    }

    public String getTextoAuxiliar100_8() {
        return textoAuxiliar100_8;
    }

    public void setTextoAuxiliar100_8(String textoAuxiliar100_8) {
        this.textoAuxiliar100_8 = textoAuxiliar100_8;
    }

    public String getCodigoAuxiliar100_9() {
        return codigoAuxiliar100_9;
    }

    public void setCodigoAuxiliar100_9(String codigoAuxiliar100_9) {
        this.codigoAuxiliar100_9 = codigoAuxiliar100_9;
    }

    public String getTextoAuxiliar100_9() {
        return textoAuxiliar100_9;
    }

    public void setTextoAuxiliar100_9(String textoAuxiliar100_9) {
        this.textoAuxiliar100_9 = textoAuxiliar100_9;
    }

    public String getCodigoAuxiliar100_10() {
        return codigoAuxiliar100_10;
    }

    public void setCodigoAuxiliar100_10(String codigoAuxiliar100_10) {
        this.codigoAuxiliar100_10 = codigoAuxiliar100_10;
    }

    public String getTextoAuxiliar100_10() {
        return textoAuxiliar100_10;
    }

    public void setTextoAuxiliar100_10(String textoAuxiliar100_10) {
        this.textoAuxiliar100_10 = textoAuxiliar100_10;
    }

    public String getCodigoAuxiliar40_1() {
        return codigoAuxiliar40_1;
    }

    public void setCodigoAuxiliar40_1(String codigoAuxiliar40_1) {
        this.codigoAuxiliar40_1 = codigoAuxiliar40_1;
    }

    public String getTextoAuxiliar40_1() {
        return textoAuxiliar40_1;
    }

    public void setTextoAuxiliar40_1(String textoAuxiliar40_1) {
        this.textoAuxiliar40_1 = textoAuxiliar40_1;
    }

    public String getCodigoAuxiliar40_2() {
        return codigoAuxiliar40_2;
    }

    public void setCodigoAuxiliar40_2(String codigoAuxiliar40_2) {
        this.codigoAuxiliar40_2 = codigoAuxiliar40_2;
    }

    public String getTextoAuxiliar40_2() {
        return textoAuxiliar40_2;
    }

    public void setTextoAuxiliar40_2(String textoAuxiliar40_2) {
        this.textoAuxiliar40_2 = textoAuxiliar40_2;
    }

    public String getCodigoAuxiliar40_3() {
        return codigoAuxiliar40_3;
    }

    public void setCodigoAuxiliar40_3(String codigoAuxiliar40_3) {
        this.codigoAuxiliar40_3 = codigoAuxiliar40_3;
    }

    public String getTextoAuxiliar40_3() {
        return textoAuxiliar40_3;
    }

    public void setTextoAuxiliar40_3(String textoAuxiliar40_3) {
        this.textoAuxiliar40_3 = textoAuxiliar40_3;
    }

    public String getCodigoAuxiliar40_4() {
        return codigoAuxiliar40_4;
    }

    public void setCodigoAuxiliar40_4(String codigoAuxiliar40_4) {
        this.codigoAuxiliar40_4 = codigoAuxiliar40_4;
    }

    public String getTextoAuxiliar40_4() {
        return textoAuxiliar40_4;
    }

    public void setTextoAuxiliar40_4(String textoAuxiliar40_4) {
        this.textoAuxiliar40_4 = textoAuxiliar40_4;
    }

    public String getCodigoAuxiliar40_5() {
        return codigoAuxiliar40_5;
    }

    public void setCodigoAuxiliar40_5(String codigoAuxiliar40_5) {
        this.codigoAuxiliar40_5 = codigoAuxiliar40_5;
    }

    public String getTextoAuxiliar40_5() {
        return textoAuxiliar40_5;
    }

    public void setTextoAuxiliar40_5(String textoAuxiliar40_5) {
        this.textoAuxiliar40_5 = textoAuxiliar40_5;
    }

    public String getCodigoAuxiliar40_6() {
        return codigoAuxiliar40_6;
    }

    public void setCodigoAuxiliar40_6(String codigoAuxiliar40_6) {
        this.codigoAuxiliar40_6 = codigoAuxiliar40_6;
    }

    public String getTextoAuxiliar40_6() {
        return textoAuxiliar40_6;
    }

    public void setTextoAuxiliar40_6(String textoAuxiliar40_6) {
        this.textoAuxiliar40_6 = textoAuxiliar40_6;
    }

    public String getCodigoAuxiliar40_7() {
        return codigoAuxiliar40_7;
    }

    public void setCodigoAuxiliar40_7(String codigoAuxiliar40_7) {
        this.codigoAuxiliar40_7 = codigoAuxiliar40_7;
    }

    public String getTextoAuxiliar40_7() {
        return textoAuxiliar40_7;
    }

    public void setTextoAuxiliar40_7(String textoAuxiliar40_7) {
        this.textoAuxiliar40_7 = textoAuxiliar40_7;
    }

    public String getCodigoAuxiliar40_8() {
        return codigoAuxiliar40_8;
    }

    public void setCodigoAuxiliar40_8(String codigoAuxiliar40_8) {
        this.codigoAuxiliar40_8 = codigoAuxiliar40_8;
    }

    public String getTextoAuxiliar40_8() {
        return textoAuxiliar40_8;
    }

    public void setTextoAuxiliar40_8(String textoAuxiliar40_8) {
        this.textoAuxiliar40_8 = textoAuxiliar40_8;
    }

    public String getCodigoAuxiliar40_9() {
        return codigoAuxiliar40_9;
    }

    public void setCodigoAuxiliar40_9(String codigoAuxiliar40_9) {
        this.codigoAuxiliar40_9 = codigoAuxiliar40_9;
    }

    public String getTextoAuxiliar40_9() {
        return textoAuxiliar40_9;
    }

    public void setTextoAuxiliar40_9(String textoAuxiliar40_9) {
        this.textoAuxiliar40_9 = textoAuxiliar40_9;
    }

    public String getCodigoAuxiliar40_10() {
        return codigoAuxiliar40_10;
    }

    public void setCodigoAuxiliar40_10(String codigoAuxiliar40_10) {
        this.codigoAuxiliar40_10 = codigoAuxiliar40_10;
    }

    public String getTextoAuxiliar40_10() {
        return textoAuxiliar40_10;
    }

    public void setTextoAuxiliar40_10(String textoAuxiliar40_10) {
        this.textoAuxiliar40_10 = textoAuxiliar40_10;
    }

    public String getCodigoAuxiliar40_11() {
        return codigoAuxiliar40_11;
    }

    public void setCodigoAuxiliar40_11(String codigoAuxiliar40_11) {
        this.codigoAuxiliar40_11 = codigoAuxiliar40_11;
    }

    public String getTextoAuxiliar40_11() {
        return textoAuxiliar40_11;
    }

    public void setTextoAuxiliar40_11(String textoAuxiliar40_11) {
        this.textoAuxiliar40_11 = textoAuxiliar40_11;
    }

    public String getCodigoAuxiliar40_12() {
        return codigoAuxiliar40_12;
    }

    public void setCodigoAuxiliar40_12(String codigoAuxiliar40_12) {
        this.codigoAuxiliar40_12 = codigoAuxiliar40_12;
    }

    public String getTextoAuxiliar40_12() {
        return textoAuxiliar40_12;
    }

    public void setTextoAuxiliar40_12(String textoAuxiliar40_12) {
        this.textoAuxiliar40_12 = textoAuxiliar40_12;
    }

    public String getCodigoAuxiliar40_13() {
        return codigoAuxiliar40_13;
    }

    public void setCodigoAuxiliar40_13(String codigoAuxiliar40_13) {
        this.codigoAuxiliar40_13 = codigoAuxiliar40_13;
    }

    public String getTextoAuxiliar40_13() {
        return textoAuxiliar40_13;
    }

    public void setTextoAuxiliar40_13(String textoAuxiliar40_13) {
        this.textoAuxiliar40_13 = textoAuxiliar40_13;
    }

    public String getCodigoAuxiliar40_14() {
        return codigoAuxiliar40_14;
    }

    public void setCodigoAuxiliar40_14(String codigoAuxiliar40_14) {
        this.codigoAuxiliar40_14 = codigoAuxiliar40_14;
    }

    public String getTextoAuxiliar40_14() {
        return textoAuxiliar40_14;
    }

    public void setTextoAuxiliar40_14(String textoAuxiliar40_14) {
        this.textoAuxiliar40_14 = textoAuxiliar40_14;
    }

    public String getCodigoAuxiliar40_15() {
        return codigoAuxiliar40_15;
    }

    public void setCodigoAuxiliar40_15(String codigoAuxiliar40_15) {
        this.codigoAuxiliar40_15 = codigoAuxiliar40_15;
    }

    public String getTextoAuxiliar40_15() {
        return textoAuxiliar40_15;
    }

    public void setTextoAuxiliar40_15(String textoAuxiliar40_15) {
        this.textoAuxiliar40_15 = textoAuxiliar40_15;
    }

    public String getCodigoAuxiliar40_16() {
        return codigoAuxiliar40_16;
    }

    public void setCodigoAuxiliar40_16(String codigoAuxiliar40_16) {
        this.codigoAuxiliar40_16 = codigoAuxiliar40_16;
    }

    public String getTextoAuxiliar40_16() {
        return textoAuxiliar40_16;
    }

    public void setTextoAuxiliar40_16(String textoAuxiliar40_16) {
        this.textoAuxiliar40_16 = textoAuxiliar40_16;
    }

    public String getCodigoAuxiliar40_17() {
        return codigoAuxiliar40_17;
    }

    public void setCodigoAuxiliar40_17(String codigoAuxiliar40_17) {
        this.codigoAuxiliar40_17 = codigoAuxiliar40_17;
    }

    public String getTextoAuxiliar40_17() {
        return textoAuxiliar40_17;
    }

    public void setTextoAuxiliar40_17(String textoAuxiliar40_17) {
        this.textoAuxiliar40_17 = textoAuxiliar40_17;
    }

    public String getCodigoAuxiliar40_18() {
        return codigoAuxiliar40_18;
    }

    public void setCodigoAuxiliar40_18(String codigoAuxiliar40_18) {
        this.codigoAuxiliar40_18 = codigoAuxiliar40_18;
    }

    public String getTextoAuxiliar40_18() {
        return textoAuxiliar40_18;
    }

    public void setTextoAuxiliar40_18(String textoAuxiliar40_18) {
        this.textoAuxiliar40_18 = textoAuxiliar40_18;
    }

    public String getCodigoAuxiliar40_19() {
        return codigoAuxiliar40_19;
    }

    public void setCodigoAuxiliar40_19(String codigoAuxiliar40_19) {
        this.codigoAuxiliar40_19 = codigoAuxiliar40_19;
    }

    public String getTextoAuxiliar40_19() {
        return textoAuxiliar40_19;
    }

    public void setTextoAuxiliar40_19(String textoAuxiliar40_19) {
        this.textoAuxiliar40_19 = textoAuxiliar40_19;
    }

    public String getCodigoAuxiliar40_20() {
        return codigoAuxiliar40_20;
    }

    public void setCodigoAuxiliar40_20(String codigoAuxiliar40_20) {
        this.codigoAuxiliar40_20 = codigoAuxiliar40_20;
    }

    public String getTextoAuxiliar40_20() {
        return textoAuxiliar40_20;
    }

    public void setTextoAuxiliar40_20(String textoAuxiliar40_20) {
        this.textoAuxiliar40_20 = textoAuxiliar40_20;
    }

    public String getCodigoAuxiliar500_1() {
        return codigoAuxiliar500_1;
    }

    public void setCodigoAuxiliar500_1(String codigoAuxiliar500_1) {
        this.codigoAuxiliar500_1 = codigoAuxiliar500_1;
    }

    public String getTextoAuxiliar500_1() {
        return textoAuxiliar500_1;
    }

    public void setTextoAuxiliar500_1(String textoAuxiliar500_1) {
        this.textoAuxiliar500_1 = textoAuxiliar500_1;
    }

    public String getCodigoAuxiliar500_2() {
        return codigoAuxiliar500_2;
    }

    public void setCodigoAuxiliar500_2(String codigoAuxiliar500_2) {
        this.codigoAuxiliar500_2 = codigoAuxiliar500_2;
    }

    public String getTextoAuxiliar500_2() {
        return textoAuxiliar500_2;
    }

    public void setTextoAuxiliar500_2(String textoAuxiliar500_2) {
        this.textoAuxiliar500_2 = textoAuxiliar500_2;
    }

    public String getCodigoAuxiliar500_3() {
        return codigoAuxiliar500_3;
    }

    public void setCodigoAuxiliar500_3(String codigoAuxiliar500_3) {
        this.codigoAuxiliar500_3 = codigoAuxiliar500_3;
    }

    public String getTextoAuxiliar500_3() {
        return textoAuxiliar500_3;
    }

    public void setTextoAuxiliar500_3(String textoAuxiliar500_3) {
        this.textoAuxiliar500_3 = textoAuxiliar500_3;
    }

    public String getCodigoAuxiliar500_4() {
        return codigoAuxiliar500_4;
    }

    public void setCodigoAuxiliar500_4(String codigoAuxiliar500_4) {
        this.codigoAuxiliar500_4 = codigoAuxiliar500_4;
    }

    public String getTextoAuxiliar500_4() {
        return textoAuxiliar500_4;
    }

    public void setTextoAuxiliar500_4(String textoAuxiliar500_4) {
        this.textoAuxiliar500_4 = textoAuxiliar500_4;
    }

    public String getCodigoAuxiliar500_5() {
        return codigoAuxiliar500_5;
    }

    public void setCodigoAuxiliar500_5(String codigoAuxiliar500_5) {
        this.codigoAuxiliar500_5 = codigoAuxiliar500_5;
    }

    public String getTextoAuxiliar500_5() {
        return textoAuxiliar500_5;
    }

    public void setTextoAuxiliar500_5(String textoAuxiliar500_5) {
        this.textoAuxiliar500_5 = textoAuxiliar500_5;
    }

    public String getCodigoAuxiliar250_1() {
        return codigoAuxiliar250_1;
    }

    public void setCodigoAuxiliar250_1(String codigoAuxiliar250_1) {
        this.codigoAuxiliar250_1 = codigoAuxiliar250_1;
    }

    public String getTextoAuxiliar250_1() {
        return textoAuxiliar250_1;
    }

    public void setTextoAuxiliar250_1(String textoAuxiliar250_1) {
        this.textoAuxiliar250_1 = textoAuxiliar250_1;
    }

    public String getCodigoAuxiliar250_2() {
        return codigoAuxiliar250_2;
    }

    public void setCodigoAuxiliar250_2(String codigoAuxiliar250_2) {
        this.codigoAuxiliar250_2 = codigoAuxiliar250_2;
    }

    public String getTextoAuxiliar250_2() {
        return textoAuxiliar250_2;
    }

    public void setTextoAuxiliar250_2(String textoAuxiliar250_2) {
        this.textoAuxiliar250_2 = textoAuxiliar250_2;
    }

    public String getCodigoAuxiliar250_3() {
        return codigoAuxiliar250_3;
    }

    public void setCodigoAuxiliar250_3(String codigoAuxiliar250_3) {
        this.codigoAuxiliar250_3 = codigoAuxiliar250_3;
    }

    public String getTextoAuxiliar250_3() {
        return textoAuxiliar250_3;
    }

    public void setTextoAuxiliar250_3(String textoAuxiliar250_3) {
        this.textoAuxiliar250_3 = textoAuxiliar250_3;
    }

    public String getCodigoAuxiliar250_4() {
        return codigoAuxiliar250_4;
    }

    public void setCodigoAuxiliar250_4(String codigoAuxiliar250_4) {
        this.codigoAuxiliar250_4 = codigoAuxiliar250_4;
    }

    public String getTextoAuxiliar250_4() {
        return textoAuxiliar250_4;
    }

    public void setTextoAuxiliar250_4(String textoAuxiliar250_4) {
        this.textoAuxiliar250_4 = textoAuxiliar250_4;
    }

    public String getCodigoAuxiliar250_5() {
        return codigoAuxiliar250_5;
    }

    public void setCodigoAuxiliar250_5(String codigoAuxiliar250_5) {
        this.codigoAuxiliar250_5 = codigoAuxiliar250_5;
    }

    public String getTextoAuxiliar250_5() {
        return textoAuxiliar250_5;
    }

    public void setTextoAuxiliar250_5(String textoAuxiliar250_5) {
        this.textoAuxiliar250_5 = textoAuxiliar250_5;
    }

    public String getCodigoAuxiliar250_6() {
        return codigoAuxiliar250_6;
    }

    public void setCodigoAuxiliar250_6(String codigoAuxiliar250_6) {
        this.codigoAuxiliar250_6 = codigoAuxiliar250_6;
    }

    public String getTextoAuxiliar250_6() {
        return textoAuxiliar250_6;
    }

    public void setTextoAuxiliar250_6(String textoAuxiliar250_6) {
        this.textoAuxiliar250_6 = textoAuxiliar250_6;
    }

    public String getCodigoAuxiliar250_7() {
        return codigoAuxiliar250_7;
    }

    public void setCodigoAuxiliar250_7(String codigoAuxiliar250_7) {
        this.codigoAuxiliar250_7 = codigoAuxiliar250_7;
    }

    public String getTextoAuxiliar250_7() {
        return textoAuxiliar250_7;
    }

    public void setTextoAuxiliar250_7(String textoAuxiliar250_7) {
        this.textoAuxiliar250_7 = textoAuxiliar250_7;
    }

    public String getCodigoAuxiliar250_8() {
        return codigoAuxiliar250_8;
    }

    public void setCodigoAuxiliar250_8(String codigoAuxiliar250_8) {
        this.codigoAuxiliar250_8 = codigoAuxiliar250_8;
    }

    public String getTextoAuxiliar250_8() {
        return textoAuxiliar250_8;
    }

    public void setTextoAuxiliar250_8(String textoAuxiliar250_8) {
        this.textoAuxiliar250_8 = textoAuxiliar250_8;
    }

    public String getCodigoAuxiliar250_9() {
        return codigoAuxiliar250_9;
    }

    public void setCodigoAuxiliar250_9(String codigoAuxiliar250_9) {
        this.codigoAuxiliar250_9 = codigoAuxiliar250_9;
    }

    public String getTextoAuxiliar250_9() {
        return textoAuxiliar250_9;
    }

    public void setTextoAuxiliar250_9(String textoAuxiliar250_9) {
        this.textoAuxiliar250_9 = textoAuxiliar250_9;
    }

    public String getCodigoAuxiliar250_10() {
        return codigoAuxiliar250_10;
    }

    public void setCodigoAuxiliar250_10(String codigoAuxiliar250_10) {
        this.codigoAuxiliar250_10 = codigoAuxiliar250_10;
    }

    public String getTextoAuxiliar250_10() {
        return textoAuxiliar250_10;
    }

    public void setTextoAuxiliar250_10(String textoAuxiliar250_10) {
        this.textoAuxiliar250_10 = textoAuxiliar250_10;
    }

    public String getCodigoAuxiliar250_11() {
        return codigoAuxiliar250_11;
    }

    public void setCodigoAuxiliar250_11(String codigoAuxiliar250_11) {
        this.codigoAuxiliar250_11 = codigoAuxiliar250_11;
    }

    public String getTextoAuxiliar250_11() {
        return textoAuxiliar250_11;
    }

    public void setTextoAuxiliar250_11(String textoAuxiliar250_11) {
        this.textoAuxiliar250_11 = textoAuxiliar250_11;
    }

    public String getCodigoAuxiliar250_12() {
        return codigoAuxiliar250_12;
    }

    public void setCodigoAuxiliar250_12(String codigoAuxiliar250_12) {
        this.codigoAuxiliar250_12 = codigoAuxiliar250_12;
    }

    public String getTextoAuxiliar250_12() {
        return textoAuxiliar250_12;
    }

    public void setTextoAuxiliar250_12(String textoAuxiliar250_12) {
        this.textoAuxiliar250_12 = textoAuxiliar250_12;
    }

    public String getCodigoAuxiliar250_13() {
        return codigoAuxiliar250_13;
    }

    public void setCodigoAuxiliar250_13(String codigoAuxiliar250_13) {
        this.codigoAuxiliar250_13 = codigoAuxiliar250_13;
    }

    public String getTextoAuxiliar250_13() {
        return textoAuxiliar250_13;
    }

    public void setTextoAuxiliar250_13(String textoAuxiliar250_13) {
        this.textoAuxiliar250_13 = textoAuxiliar250_13;
    }

    public String getCodigoAuxiliar250_14() {
        return codigoAuxiliar250_14;
    }

    public void setCodigoAuxiliar250_14(String codigoAuxiliar250_14) {
        this.codigoAuxiliar250_14 = codigoAuxiliar250_14;
    }

    public String getTextoAuxiliar250_14() {
        return textoAuxiliar250_14;
    }

    public void setTextoAuxiliar250_14(String textoAuxiliar250_14) {
        this.textoAuxiliar250_14 = textoAuxiliar250_14;
    }

    public String getCodigoAuxiliar250_15() {
        return codigoAuxiliar250_15;
    }

    public void setCodigoAuxiliar250_15(String codigoAuxiliar250_15) {
        this.codigoAuxiliar250_15 = codigoAuxiliar250_15;
    }

    public String getTextoAuxiliar250_15() {
        return textoAuxiliar250_15;
    }

    public void setTextoAuxiliar250_15(String textoAuxiliar250_15) {
        this.textoAuxiliar250_15 = textoAuxiliar250_15;
    }

    public String getCodigoAuxiliar250_16() {
        return codigoAuxiliar250_16;
    }

    public void setCodigoAuxiliar250_16(String codigoAuxiliar250_16) {
        this.codigoAuxiliar250_16 = codigoAuxiliar250_16;
    }

    public String getTextoAuxiliar250_16() {
        return textoAuxiliar250_16;
    }

    public void setTextoAuxiliar250_16(String textoAuxiliar250_16) {
        this.textoAuxiliar250_16 = textoAuxiliar250_16;
    }

    public String getCodigoAuxiliar250_17() {
        return codigoAuxiliar250_17;
    }

    public void setCodigoAuxiliar250_17(String codigoAuxiliar250_17) {
        this.codigoAuxiliar250_17 = codigoAuxiliar250_17;
    }

    public String getTextoAuxiliar250_17() {
        return textoAuxiliar250_17;
    }

    public void setTextoAuxiliar250_17(String textoAuxiliar250_17) {
        this.textoAuxiliar250_17 = textoAuxiliar250_17;
    }

    public String getCodigoAuxiliar250_18() {
        return codigoAuxiliar250_18;
    }

    public void setCodigoAuxiliar250_18(String codigoAuxiliar250_18) {
        this.codigoAuxiliar250_18 = codigoAuxiliar250_18;
    }

    public String getTextoAuxiliar250_18() {
        return textoAuxiliar250_18;
    }

    public void setTextoAuxiliar250_18(String textoAuxiliar250_18) {
        this.textoAuxiliar250_18 = textoAuxiliar250_18;
    }

    public String getCodigoAuxiliar250_19() {
        return codigoAuxiliar250_19;
    }

    public void setCodigoAuxiliar250_19(String codigoAuxiliar250_19) {
        this.codigoAuxiliar250_19 = codigoAuxiliar250_19;
    }

    public String getTextoAuxiliar250_19() {
        return textoAuxiliar250_19;
    }

    public void setTextoAuxiliar250_19(String textoAuxiliar250_19) {
        this.textoAuxiliar250_19 = textoAuxiliar250_19;
    }

    public String getCodigoAuxiliar250_20() {
        return codigoAuxiliar250_20;
    }

    public void setCodigoAuxiliar250_20(String codigoAuxiliar250_20) {
        this.codigoAuxiliar250_20 = codigoAuxiliar250_20;
    }

    public String getTextoAuxiliar250_20() {
        return textoAuxiliar250_20;
    }

    public void setTextoAuxiliar250_20(String textoAuxiliar250_20) {
        this.textoAuxiliar250_20 = textoAuxiliar250_20;
    }

    public String getCodigoAuxiliar250_21() {
        return codigoAuxiliar250_21;
    }

    public void setCodigoAuxiliar250_21(String codigoAuxiliar250_21) {
        this.codigoAuxiliar250_21 = codigoAuxiliar250_21;
    }

    public String getTextoAuxiliar250_21() {
        return textoAuxiliar250_21;
    }

    public void setTextoAuxiliar250_21(String textoAuxiliar250_21) {
        this.textoAuxiliar250_21 = textoAuxiliar250_21;
    }

    public String getCodigoAuxiliar250_22() {
        return codigoAuxiliar250_22;
    }

    public void setCodigoAuxiliar250_22(String codigoAuxiliar250_22) {
        this.codigoAuxiliar250_22 = codigoAuxiliar250_22;
    }

    public String getTextoAuxiliar250_22() {
        return textoAuxiliar250_22;
    }

    public void setTextoAuxiliar250_22(String textoAuxiliar250_22) {
        this.textoAuxiliar250_22 = textoAuxiliar250_22;
    }

    public String getCodigoAuxiliar250_23() {
        return codigoAuxiliar250_23;
    }

    public void setCodigoAuxiliar250_23(String codigoAuxiliar250_23) {
        this.codigoAuxiliar250_23 = codigoAuxiliar250_23;
    }

    public String getTextoAuxiliar250_23() {
        return textoAuxiliar250_23;
    }

    public void setTextoAuxiliar250_23(String textoAuxiliar250_23) {
        this.textoAuxiliar250_23 = textoAuxiliar250_23;
    }

    public String getCodigoAuxiliar250_24() {
        return codigoAuxiliar250_24;
    }

    public void setCodigoAuxiliar250_24(String codigoAuxiliar250_24) {
        this.codigoAuxiliar250_24 = codigoAuxiliar250_24;
    }

    public String getTextoAuxiliar250_24() {
        return textoAuxiliar250_24;
    }

    public void setTextoAuxiliar250_24(String textoAuxiliar250_24) {
        this.textoAuxiliar250_24 = textoAuxiliar250_24;
    }

    public String getCodigoAuxiliar250_25() {
        return codigoAuxiliar250_25;
    }

    public void setCodigoAuxiliar250_25(String codigoAuxiliar250_25) {
        this.codigoAuxiliar250_25 = codigoAuxiliar250_25;
    }

    public String getTextoAuxiliar250_25() {
        return textoAuxiliar250_25;
    }

    public void setTextoAuxiliar250_25(String textoAuxiliar250_25) {
        this.textoAuxiliar250_25 = textoAuxiliar250_25;
    }

}
