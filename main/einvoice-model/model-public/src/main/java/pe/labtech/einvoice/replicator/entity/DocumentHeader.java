/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import pe.labtech.einvoice.replicator.commons.BLResponse;
import pe.labtech.einvoice.replicator.commons.BLResponseImpl;
import pe.labtech.einvoice.replicator.commons.Header;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_EINVOICEHEADER")
@NamedQueries({
    @NamedQuery(
            name = "DocumentHeader.findIdWithState",
            query = "SELECT o.id FROM DocumentHeader o where o.bl_estadoRegistro = :status"
    ),
    @NamedQuery(
            name = "DocumentHeader.safeUpdateStatus",
            query = "UPDATE DocumentHeader o SET o.bl_estadoRegistro = :newstatus WHERE o.bl_estadoRegistro = :oldstatus AND o.id = :id"
    )
})
@XmlRootElement
public class DocumentHeader extends BLResponseImpl implements Serializable, BLResponse, Header<DocumentDetail> {

    //LLAVE PRIMARIA
    @EmbeddedId
    private DocumentHeaderPK id;

    //CAMPOS DE DATOS
    @NotNull
    @Size(min = 1, max = 100)
    @Basic(optional = false)
    @Column(name = "razonSocialEmisor", length = 100, nullable = false)
    private String razonSocialEmisor;
    @Size(max = 100)
    @Column(name = "nombreComercialEmisor", length = 100)
    private String nombreComercialEmisor;
    @NotNull
    @Size(min = 1, max = 10)
    @Basic(optional = false)
    @Column(name = "fechaEmision", length = 10, nullable = false)
    private String fechaEmision;
    @Size(max = 6)
    @Column(name = "ubigeoEmisor", length = 6)
    private String ubigeoEmisor;
    @Size(max = 100)
    @Column(name = "direccionEmisor", length = 100)
    private String direccionEmisor;
    @Size(max = 25)
    @Column(name = "urbanizacion", length = 25)
    private String urbanizacion;
    @Size(max = 30)
    @Column(name = "provinciaEmisor", length = 30)
    private String provinciaEmisor;
    @Size(max = 30)
    @Column(name = "departamentoEmisor", length = 30)
    private String departamentoEmisor;
    @Size(max = 30)
    @Column(name = "distritoEmisor", length = 30)
    private String distritoEmisor;
    @Size(max = 2)
    @Column(name = "paisEmisor", length = 2)
    private String paisEmisor;
    @NotNull
    @Size(min = 1, max = 100)
    @Basic(optional = false)
    @Column(name = "correoEmisor", length = 100, nullable = false)
    private String correoEmisor;
    @NotNull
    @Size(min = 1, max = 1)
    @Basic(optional = false)
    @Column(name = "tipoDocumentoAdquiriente", length = 1, nullable = false)
    private String tipoDocumentoAdquiriente;
    @Size(max = 2)
    @Column(name = "codigoSerieNumeroAfectado", length = 2)
    private String codigoSerieNumeroAfectado;
    @Size(max = 13)
    @Column(name = "serieNumeroAfectado", length = 13)
    private String serieNumeroAfectado;
    @NotNull
    @Size(min = 1, max = 15)
    @Basic(optional = false)
    @Column(name = "numeroDocumentoAdquiriente", length = 15, nullable = false)
    private String numeroDocumentoAdquiriente;
    @NotNull
    @Size(min = 1, max = 100)
    @Basic(optional = false)
    @Column(name = "razonSocialAdquiriente", length = 100, nullable = false)
    private String razonSocialAdquiriente;
    @Size(max = 100)
    @Column(name = "lugarDestino", length = 100)
    private String lugarDestino;
    @NotNull
    @Size(min = 1, max = 100)
    @Basic(optional = false)
    @Column(name = "correoAdquiriente", length = 100, nullable = false)
    private String correoAdquiriente;
    @Size(max = 500)
    @Column(name = "motivoDocumento", length = 500)
    private String motivoDocumento;
    @NotNull
    @Size(min = 1, max = 3)
    @Basic(optional = false)
    @Column(name = "tipoMoneda", length = 3, nullable = false)
    private String tipoMoneda;
    @NotNull
    @Size(min = 1, max = 15)
    @Basic(optional = false)
    @Column(name = "totalValorVentaNetoOpGravadas", length = 15, nullable = false)
    private String totalValorVentaNetoOpGravadas;
    @NotNull
    @Size(min = 1, max = 15)
    @Basic(optional = false)
    @Column(name = "totalValorVentaNetoOpNoGravada", length = 15, nullable = false)
    private String totalValorVentaNetoOpNoGravada;
    @NotNull
    @Size(min = 1, max = 15)
    @Basic(optional = false)
    @Column(name = "totalValorVentaNetoOpExonerada", length = 15, nullable = false)
    private String totalValorVentaNetoOpExoneradas;
    @Size(max = 18)
    @Column(name = "totalValorVentaNetoOpGratuitas", length = 18)
    private String totalValorVentaNetoOpGratuitas;
    @Size(max = 15)
    @Column(name = "subTotal", length = 15)
    private String subTotal;
    @Size(max = 15)
    @Column(name = "totalIgv", length = 15)
    private String totalIgv;
    @Size(max = 15)
    @Column(name = "totalIsc", length = 15)
    private String totalIsc;
    @Size(max = 15)
    @Column(name = "totalOtrosTributos", length = 15)
    private String totalOtrosTributos;
    @Size(max = 15)
    @Column(name = "totalOtrosCargos", length = 15)
    private String totalOtrosCargos;
    @Size(max = 15)
    @Column(name = "totalDescuentos", length = 15)
    private String totalDescuentos;
    @Size(max = 15)
    @Column(name = "descuentosGlobales", length = 15)
    private String descuentosGlobales;
    @NotNull
    @Size(min = 1, max = 15)
    @Basic(optional = false)
    @Column(name = "totalVenta", length = 15, nullable = false)
    private String totalVenta;
    @Size(max = 2)
    @Column(name = "tipoDocumentoReferenciaPrincip", length = 2)
    private String tipoDocumentoReferenciaPrincipal;
    @Size(max = 13)
    @Column(name = "numeroDocumentoReferenciaPrinc", length = 13)
    private String numeroDocumentoReferenciaPrincipal;
    @Size(max = 2)
    @Column(name = "tipoDocumentoReferenciaCorregi", length = 2)
    private String tipoDocumentoReferenciaCorregido;
    @Size(max = 13)
    @Column(name = "numeroDocumentoReferenciaCorre", length = 13)
    private String numeroDocumentoReferenciaCorregido;
    @Size(max = 15)
    @Column(name = "baseImponiblePercepcion", length = 15)
    private String baseImponiblePercepcion;
    @Size(max = 15)
    @Column(name = "totalPercepcion", length = 15)
    private String totalPercepcion;
    @Size(max = 15)
    @Column(name = "totalVentaConPercepcion", length = 15)
    private String totalVentaConPercepcion;
    @Size(max = 15)
    @Column(name = "porcentajePercepcion", length = 15)
    private String porcentajePercepcion;
    @Size(max = 15)
    @Column(name = "totalRetencion", length = 15)
    private String totalRetencion;
    @Size(max = 15)
    @Column(name = "porcentajeRetencion", length = 15)
    private String porcentajeRetencion;
    @Size(max = 15)
    @Column(name = "totalDetraccion", length = 15)
    private String totalDetraccion;
    @Size(max = 15)
    @Column(name = "porcentajeDetraccion", length = 15)
    private String porcentajeDetraccion;
    @Size(max = 100)
    @Column(name = "descripcionDetraccion", length = 100)
    private String descripcionDetraccion;
    @Size(max = 15)
    @Column(name = "valorReferencialDetraccion", length = 15)
    private String valorReferencialDetraccion;
    @Size(max = 15)
    @Column(name = "totalBonificacion", length = 15)
    private String totalBonificacion;
    @Size(max = 1)
    @Column(name = "inHabilitado", length = 1)
    private String inHabilitado;
    @Size(max = 2)
    @Column(name = "tipoReferencia_1", length = 2)
    private String tipoReferencia_1;
    @Size(max = 30)
    @Column(name = "numeroDocumentoReferencia_1", length = 30)
    private String numeroDocumentoReferencia_1;
    @Size(max = 2)
    @Column(name = "tipoReferencia_2", length = 2)
    private String tipoReferencia_2;
    @Size(max = 30)
    @Column(name = "numeroDocumentoReferencia_2", length = 30)
    private String numeroDocumentoReferencia_2;
    @Size(max = 2)
    @Column(name = "tipoReferencia_3", length = 2)
    private String tipoReferencia_3;
    @Size(max = 30)
    @Column(name = "numeroDocumentoReferencia_3", length = 30)
    private String numeroDocumentoReferencia_3;
    @Size(max = 2)
    @Column(name = "tipoReferencia_4", length = 2)
    private String tipoReferencia_4;
    @Size(max = 30)
    @Column(name = "numeroDocumentoReferencia_4", length = 30)
    private String numeroDocumentoReferencia_4;
    @Size(max = 2)
    @Column(name = "tipoReferencia_5", length = 2)
    private String tipoReferencia_5;
    @Size(max = 30)
    @Column(name = "numeroDocumentoReferencia_5", length = 30)
    private String numeroDocumentoReferencia_5;
    @Size(max = 2)
    @Column(name = "tipoReferenciaAdicional_1", length = 2)
    private String tipoReferenciaAdicional_1;
    @Size(max = 30)
    @Column(name = "numeroDocumentoRefeAdicional_1", length = 30)
    private String numeroDocumentoReferenciaAdicional_1;
    @Size(max = 2)
    @Column(name = "tipoReferenciaAdicional_2", length = 2)
    private String tipoReferenciaAdicional_2;
    @Size(max = 30)
    @Column(name = "numeroDocumentoRefeAdicional_2", length = 30)
    private String numeroDocumentoReferenciaAdicional_2;
    @Size(max = 2)
    @Column(name = "tipoReferenciaAdicional_3", length = 2)
    private String tipoReferenciaAdicional_3;
    @Size(max = 30)
    @Column(name = "numeroDocumentoRefeAdicional_3", length = 30)
    private String numeroDocumentoReferenciaAdicional_3;
    @Size(max = 2)
    @Column(name = "tipoReferenciaAdicional_4", length = 2)
    private String tipoReferenciaAdicional_4;
    @Size(max = 30)
    @Column(name = "numeroDocumentoRefeAdicional_4", length = 30)
    private String numeroDocumentoReferenciaAdicional_4;
    @Size(max = 2)
    @Column(name = "tipoReferenciaAdicional_5", length = 2)
    private String tipoReferenciaAdicional_5;
    @Size(max = 30)
    @Column(name = "numeroDocumentoRefeAdicional_5", length = 30)
    private String numeroDocumentoReferenciaAdicional_5;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_1", length = 4)
    private String codigoLeyenda_1;
    @Size(max = 200)
    @Column(name = "textoLeyenda_1", length = 200)
    private String textoLeyenda_1;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_1", length = 200)
    private String textoAdicionalLeyenda_1;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_2", length = 4)
    private String codigoLeyenda_2;
    @Size(max = 200)
    @Column(name = "textoLeyenda_2", length = 200)
    private String textoLeyenda_2;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_2", length = 200)
    private String textoAdicionalLeyenda_2;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_3", length = 4)
    private String codigoLeyenda_3;
    @Size(max = 200)
    @Column(name = "textoLeyenda_3", length = 200)
    private String textoLeyenda_3;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_3", length = 200)
    private String textoAdicionalLeyenda_3;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_4", length = 4)
    private String codigoLeyenda_4;
    @Size(max = 200)
    @Column(name = "textoLeyenda_4", length = 200)
    private String textoLeyenda_4;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_4", length = 200)
    private String textoAdicionalLeyenda_4;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_5", length = 4)
    private String codigoLeyenda_5;
    @Size(max = 200)
    @Column(name = "textoLeyenda_5", length = 200)
    private String textoLeyenda_5;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_5", length = 200)
    private String textoAdicionalLeyenda_5;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_6", length = 4)
    private String codigoLeyenda_6;
    @Size(max = 200)
    @Column(name = "textoLeyenda_6", length = 200)
    private String textoLeyenda_6;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_6", length = 200)
    private String textoAdicionalLeyenda_6;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_7", length = 4)
    private String codigoLeyenda_7;
    @Size(max = 200)
    @Column(name = "textoLeyenda_7", length = 200)
    private String textoLeyenda_7;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_7", length = 200)
    private String textoAdicionalLeyenda_7;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_8", length = 4)
    private String codigoLeyenda_8;
    @Size(max = 200)
    @Column(name = "textoLeyenda_8", length = 200)
    private String textoLeyenda_8;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_8", length = 200)
    private String textoAdicionalLeyenda_8;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_9", length = 4)
    private String codigoLeyenda_9;
    @Size(max = 200)
    @Column(name = "textoLeyenda_9", length = 200)
    private String textoLeyenda_9;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_9", length = 200)
    private String textoAdicionalLeyenda_9;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_10", length = 4)
    private String codigoLeyenda_10;
    @Size(max = 200)
    @Column(name = "textoLeyenda_10", length = 200)
    private String textoLeyenda_10;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_10", length = 200)
    private String textoAdicionalLeyenda_10;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_11", length = 4)
    private String codigoLeyenda_11;
    @Size(max = 200)
    @Column(name = "textoLeyenda_11", length = 200)
    private String textoLeyenda_11;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_11", length = 200)
    private String textoAdicionalLeyenda_11;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_12", length = 4)
    private String codigoLeyenda_12;
    @Size(max = 200)
    @Column(name = "textoLeyenda_12", length = 200)
    private String textoLeyenda_12;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_12", length = 200)
    private String textoAdicionalLeyenda_12;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_13", length = 4)
    private String codigoLeyenda_13;
    @Size(max = 200)
    @Column(name = "textoLeyenda_13", length = 200)
    private String textoLeyenda_13;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_13", length = 200)
    private String textoAdicionalLeyenda_13;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_14", length = 4)
    private String codigoLeyenda_14;
    @Size(max = 200)
    @Column(name = "textoLeyenda_14", length = 200)
    private String textoLeyenda_14;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_14", length = 200)
    private String textoAdicionalLeyenda_14;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_15", length = 4)
    private String codigoLeyenda_15;
    @Size(max = 200)
    @Column(name = "textoLeyenda_15", length = 200)
    private String textoLeyenda_15;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_15", length = 200)
    private String textoAdicionalLeyenda_15;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_16", length = 4)
    private String codigoLeyenda_16;
    @Size(max = 200)
    @Column(name = "textoLeyenda_16", length = 200)
    private String textoLeyenda_16;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_16", length = 200)
    private String textoAdicionalLeyenda_16;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_17", length = 4)
    private String codigoLeyenda_17;
    @Size(max = 200)
    @Column(name = "textoLeyenda_17", length = 200)
    private String textoLeyenda_17;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_17", length = 200)
    private String textoAdicionalLeyenda_17;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_18", length = 4)
    private String codigoLeyenda_18;
    @Size(max = 200)
    @Column(name = "textoLeyenda_18", length = 200)
    private String textoLeyenda_18;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_18", length = 200)
    private String textoAdicionalLeyenda_18;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_19", length = 4)
    private String codigoLeyenda_19;
    @Size(max = 200)
    @Column(name = "textoLeyenda_19", length = 200)
    private String textoLeyenda_19;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_19", length = 200)
    private String textoAdicionalLeyenda_19;
    @Size(max = 4)
    @Column(name = "codigoLeyenda_20", length = 4)
    private String codigoLeyenda_20;
    @Size(max = 200)
    @Column(name = "textoLeyenda_20", length = 200)
    private String textoLeyenda_20;
    @Size(max = 200)
    @Column(name = "textoAdicionalLeyenda_20", length = 200)
    private String textoAdicionalLeyenda_20;
    @Size(max = 6)
    @Column(name = "ubigeoDireccionPtoPartida", length = 6)
    private String ubigeoDireccionPtoPartida;
    @Size(max = 100)
    @Column(name = "direccionCompletaPtoPartida", length = 100)
    private String direccionCompletaPtoPartida;
    @Size(max = 25)
    @Column(name = "urbanizacionPtoPartida", length = 25)
    private String urbanizacionPtoPartida;
    @Size(max = 30)
    @Column(name = "provinciaPtoPartida", length = 30)
    private String provinciaPtoPartida;
    @Size(max = 30)
    @Column(name = "departamentoPtoPartida", length = 30)
    private String departamentoPtoPartida;
    @Size(max = 30)
    @Column(name = "distritoPtoPartida", length = 30)
    private String distritoPtoPartida;
    @Size(max = 2)
    @Column(name = "paisPtoPartida", length = 2)
    private String paisPtoPartida;
    @Size(max = 6)
    @Column(name = "ubigeoDireccionPtoLlegada", length = 6)
    private String ubigeoDireccionPtoLlegada;
    @Size(max = 100)
    @Column(name = "direccionCompletaPtoLlegada", length = 100)
    private String direccionCompletaPtoLlegada;
    @Size(max = 25)
    @Column(name = "urbanizacionPtoLlegada", length = 25)
    private String urbanizacionPtoLlegada;
    @Size(max = 30)
    @Column(name = "provinciaPtoLlegada", length = 30)
    private String provinciaPtoLlegada;
    @Size(max = 30)
    @Column(name = "departamentoPtoLlegada", length = 30)
    private String departamentoPtoLlegada;
    @Size(max = 30)
    @Column(name = "distritoPtoLlegada", length = 30)
    private String distritoPtoLlegada;
    @Size(max = 2)
    @Column(name = "paisPtoLlegada", length = 2)
    private String paisPtoLlegada;
    @Size(max = 50)
    @Column(name = "marcaVehiculo", length = 50)
    private String marcaVehiculo;
    @Size(max = 10)
    @Column(name = "placaVehiculo", length = 10)
    private String placaVehiculo;
    @Size(max = 100)
    @Column(name = "numeroConstanciaVehiculo", length = 100)
    private String numeroConstanciaVehiculo;
    @Size(max = 30)
    @Column(name = "numeroLicenciaConducir", length = 30)
    private String numeroLicenciaConducir;
    @Size(max = 11)
    @Column(name = "numeroRucTransportista", length = 11)
    private String numeroRucTransportista;
    @Size(max = 2)
    @Column(name = "numeroRucTransportistaCuenta", length = 2)
    private String numeroRucTransportistaCuenta;
    @Size(max = 100)
    @Column(name = "razonSocialTransportista", length = 100)
    private String razonSocialTransportista;
    @Size(max = 15)
    @Column(name = "totalFondoInclusionSocial", length = 15)
    private String totalFondoInclusionSocial;
    @Size(max = 2)
    @Column(name = "tipoOperacionFactura", length = 2)
    private String tipoOperacionFactura;
    @Size(max = 18)
    @Column(name = "totalDocumentoAnticipo", length = 18)
    private String totalDocumentoAnticipo;
    @Size(max = 2)
    @Column(name = "tipoDocumentoAnticipo", length = 2)
    private String tipoDocumentoAnticipo;
    @Size(max = 20)
    @Column(name = "serieNumeroDocumentoAnticipo", length = 20)
    private String serieNumeroDocumentoAnticipo;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_1", length = 4)
    private String codigoAuxiliar100_1;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_1", length = 100)
    private String textoAuxiliar100_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_2", length = 4)
    private String codigoAuxiliar100_2;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_2", length = 100)
    private String textoAuxiliar100_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_3", length = 4)
    private String codigoAuxiliar100_3;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_3", length = 100)
    private String textoAuxiliar100_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_4", length = 4)
    private String codigoAuxiliar100_4;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_4", length = 100)
    private String textoAuxiliar100_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_5", length = 4)
    private String codigoAuxiliar100_5;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_5", length = 100)
    private String textoAuxiliar100_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_6", length = 4)
    private String codigoAuxiliar100_6;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_6", length = 100)
    private String textoAuxiliar100_6;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_7", length = 4)
    private String codigoAuxiliar100_7;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_7", length = 100)
    private String textoAuxiliar100_7;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_8", length = 4)
    private String codigoAuxiliar100_8;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_8", length = 100)
    private String textoAuxiliar100_8;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_9", length = 4)
    private String codigoAuxiliar100_9;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_9", length = 100)
    private String textoAuxiliar100_9;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar100_10", length = 4)
    private String codigoAuxiliar100_10;
    @Size(max = 100)
    @Column(name = "textoAuxiliar100_10", length = 100)
    private String textoAuxiliar100_10;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_1", length = 4)
    private String codigoAuxiliar40_1;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_1", length = 40)
    private String textoAuxiliar40_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_2", length = 4)
    private String codigoAuxiliar40_2;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_2", length = 40)
    private String textoAuxiliar40_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_3", length = 4)
    private String codigoAuxiliar40_3;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_3", length = 40)
    private String textoAuxiliar40_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_4", length = 4)
    private String codigoAuxiliar40_4;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_4", length = 40)
    private String textoAuxiliar40_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_5", length = 4)
    private String codigoAuxiliar40_5;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_5", length = 40)
    private String textoAuxiliar40_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_6", length = 4)
    private String codigoAuxiliar40_6;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_6", length = 40)
    private String textoAuxiliar40_6;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_7", length = 4)
    private String codigoAuxiliar40_7;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_7", length = 40)
    private String textoAuxiliar40_7;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_8", length = 4)
    private String codigoAuxiliar40_8;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_8", length = 40)
    private String textoAuxiliar40_8;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_9", length = 4)
    private String codigoAuxiliar40_9;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_9", length = 40)
    private String textoAuxiliar40_9;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_10", length = 4)
    private String codigoAuxiliar40_10;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_10", length = 40)
    private String textoAuxiliar40_10;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_11", length = 4)
    private String codigoAuxiliar40_11;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_11", length = 40)
    private String textoAuxiliar40_11;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_12", length = 4)
    private String codigoAuxiliar40_12;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_12", length = 40)
    private String textoAuxiliar40_12;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_13", length = 4)
    private String codigoAuxiliar40_13;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_13", length = 40)
    private String textoAuxiliar40_13;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_14", length = 4)
    private String codigoAuxiliar40_14;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_14", length = 40)
    private String textoAuxiliar40_14;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_15", length = 4)
    private String codigoAuxiliar40_15;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_15", length = 40)
    private String textoAuxiliar40_15;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_16", length = 4)
    private String codigoAuxiliar40_16;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_16", length = 40)
    private String textoAuxiliar40_16;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_17", length = 4)
    private String codigoAuxiliar40_17;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_17", length = 40)
    private String textoAuxiliar40_17;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_18", length = 4)
    private String codigoAuxiliar40_18;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_18", length = 40)
    private String textoAuxiliar40_18;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_19", length = 4)
    private String codigoAuxiliar40_19;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_19", length = 40)
    private String textoAuxiliar40_19;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar40_20", length = 4)
    private String codigoAuxiliar40_20;
    @Size(max = 40)
    @Column(name = "textoAuxiliar40_20", length = 40)
    private String textoAuxiliar40_20;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_1", length = 4)
    private String codigoAuxiliar500_1;
    @Size(max = 500)
    @Column(name = "textoAuxiliar500_1", length = 500)
    private String textoAuxiliar500_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_2", length = 4)
    private String codigoAuxiliar500_2;
    @Size(max = 500)
    @Column(name = "textoAuxiliar500_2", length = 500)
    private String textoAuxiliar500_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_3", length = 4)
    private String codigoAuxiliar500_3;
    @Size(max = 500)
    @Column(name = "textoAuxiliar500_3", length = 500)
    private String textoAuxiliar500_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_4", length = 4)
    private String codigoAuxiliar500_4;
    @Size(max = 500)
    @Column(name = "textoAuxiliar500_4", length = 500)
    private String textoAuxiliar500_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar500_5", length = 4)
    private String codigoAuxiliar500_5;
    @Size(max = 500)
    @Column(name = "textoAuxiliar500_5", length = 500)
    private String textoAuxiliar500_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_1", length = 4)
    private String codigoAuxiliar250_1;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_1", length = 250)
    private String textoAuxiliar250_1;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_2", length = 4)
    private String codigoAuxiliar250_2;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_2", length = 250)
    private String textoAuxiliar250_2;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_3", length = 4)
    private String codigoAuxiliar250_3;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_3", length = 250)
    private String textoAuxiliar250_3;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_4", length = 4)
    private String codigoAuxiliar250_4;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_4", length = 250)
    private String textoAuxiliar250_4;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_5", length = 4)
    private String codigoAuxiliar250_5;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_5", length = 250)
    private String textoAuxiliar250_5;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_6", length = 4)
    private String codigoAuxiliar250_6;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_6", length = 250)
    private String textoAuxiliar250_6;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_7", length = 4)
    private String codigoAuxiliar250_7;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_7", length = 250)
    private String textoAuxiliar250_7;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_8", length = 4)
    private String codigoAuxiliar250_8;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_8", length = 250)
    private String textoAuxiliar250_8;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_9", length = 4)
    private String codigoAuxiliar250_9;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_9", length = 250)
    private String textoAuxiliar250_9;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_10", length = 4)
    private String codigoAuxiliar250_10;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_10", length = 250)
    private String textoAuxiliar250_10;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_11", length = 4)
    private String codigoAuxiliar250_11;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_11", length = 250)
    private String textoAuxiliar250_11;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_12", length = 4)
    private String codigoAuxiliar250_12;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_12", length = 250)
    private String textoAuxiliar250_12;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_13", length = 4)
    private String codigoAuxiliar250_13;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_13", length = 250)
    private String textoAuxiliar250_13;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_14", length = 4)
    private String codigoAuxiliar250_14;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_14", length = 250)
    private String textoAuxiliar250_14;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_15", length = 4)
    private String codigoAuxiliar250_15;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_15", length = 250)
    private String textoAuxiliar250_15;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_16", length = 4)
    private String codigoAuxiliar250_16;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_16", length = 250)
    private String textoAuxiliar250_16;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_17", length = 4)
    private String codigoAuxiliar250_17;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_17", length = 250)
    private String textoAuxiliar250_17;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_18", length = 4)
    private String codigoAuxiliar250_18;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_18", length = 250)
    private String textoAuxiliar250_18;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_19", length = 4)
    private String codigoAuxiliar250_19;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_19", length = 250)
    private String textoAuxiliar250_19;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_20", length = 4)
    private String codigoAuxiliar250_20;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_20", length = 250)
    private String textoAuxiliar250_20;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_21", length = 4)
    private String codigoAuxiliar250_21;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_21", length = 250)
    private String textoAuxiliar250_21;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_22", length = 4)
    private String codigoAuxiliar250_22;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_22", length = 250)
    private String textoAuxiliar250_22;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_23", length = 4)
    private String codigoAuxiliar250_23;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_23", length = 250)
    private String textoAuxiliar250_23;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_24", length = 4)
    private String codigoAuxiliar250_24;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_24", length = 250)
    private String textoAuxiliar250_24;
    @Size(max = 4)
    @Column(name = "codigoAuxiliar250_25", length = 4)
    private String codigoAuxiliar250_25;
    @Size(max = 250)
    @Column(name = "textoAuxiliar250_25", length = 250)
    private String textoAuxiliar250_25;

    public DocumentHeader() {
    }

    public DocumentHeader(DocumentHeaderPK id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentHeader other = (DocumentHeader) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public DocumentHeaderPK getId() {
        return id;
    }

    public void setId(DocumentHeaderPK id) {
        this.id = id;
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

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
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

    public String getTipoDocumentoAdquiriente() {
        return tipoDocumentoAdquiriente;
    }

    public void setTipoDocumentoAdquiriente(String tipoDocumentoAdquiriente) {
        this.tipoDocumentoAdquiriente = tipoDocumentoAdquiriente;
    }

    public String getCodigoSerieNumeroAfectado() {
        return codigoSerieNumeroAfectado;
    }

    public void setCodigoSerieNumeroAfectado(String codigoSerieNumeroAfectado) {
        this.codigoSerieNumeroAfectado = codigoSerieNumeroAfectado;
    }

    public String getSerieNumeroAfectado() {
        return serieNumeroAfectado;
    }

    public void setSerieNumeroAfectado(String serieNumeroAfectado) {
        this.serieNumeroAfectado = serieNumeroAfectado;
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

    public String getMotivoDocumento() {
        return motivoDocumento;
    }

    public void setMotivoDocumento(String motivoDocumento) {
        this.motivoDocumento = motivoDocumento;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getTotalValorVentaNetoOpGravadas() {
        return totalValorVentaNetoOpGravadas;
    }

    public void setTotalValorVentaNetoOpGravadas(String totalValorVentaNetoOpGravadas) {
        this.totalValorVentaNetoOpGravadas = totalValorVentaNetoOpGravadas;
    }

    public String getTotalValorVentaNetoOpNoGravada() {
        return totalValorVentaNetoOpNoGravada;
    }

    public void setTotalValorVentaNetoOpNoGravada(String totalValorVentaNetoOpNoGravada) {
        this.totalValorVentaNetoOpNoGravada = totalValorVentaNetoOpNoGravada;
    }

    public String getTotalValorVentaNetoOpExoneradas() {
        return totalValorVentaNetoOpExoneradas;
    }

    public void setTotalValorVentaNetoOpExoneradas(String totalValorVentaNetoOpExoneradas) {
        this.totalValorVentaNetoOpExoneradas = totalValorVentaNetoOpExoneradas;
    }

    public String getTotalValorVentaNetoOpGratuitas() {
        return totalValorVentaNetoOpGratuitas;
    }

    public void setTotalValorVentaNetoOpGratuitas(String totalValorVentaNetoOpGratuitas) {
        this.totalValorVentaNetoOpGratuitas = totalValorVentaNetoOpGratuitas;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotalIgv() {
        return totalIgv;
    }

    public void setTotalIgv(String totalIgv) {
        this.totalIgv = totalIgv;
    }

    public String getTotalIsc() {
        return totalIsc;
    }

    public void setTotalIsc(String totalIsc) {
        this.totalIsc = totalIsc;
    }

    public String getTotalOtrosTributos() {
        return totalOtrosTributos;
    }

    public void setTotalOtrosTributos(String totalOtrosTributos) {
        this.totalOtrosTributos = totalOtrosTributos;
    }

    public String getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(String totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public String getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(String totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public String getDescuentosGlobales() {
        return descuentosGlobales;
    }

    public void setDescuentosGlobales(String descuentosGlobales) {
        this.descuentosGlobales = descuentosGlobales;
    }

    public String getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(String totalVenta) {
        this.totalVenta = totalVenta;
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

    public String getBaseImponiblePercepcion() {
        return baseImponiblePercepcion;
    }

    public void setBaseImponiblePercepcion(String baseImponiblePercepcion) {
        this.baseImponiblePercepcion = baseImponiblePercepcion;
    }

    public String getTotalPercepcion() {
        return totalPercepcion;
    }

    public void setTotalPercepcion(String totalPercepcion) {
        this.totalPercepcion = totalPercepcion;
    }

    public String getTotalVentaConPercepcion() {
        return totalVentaConPercepcion;
    }

    public void setTotalVentaConPercepcion(String totalVentaConPercepcion) {
        this.totalVentaConPercepcion = totalVentaConPercepcion;
    }

    public String getPorcentajePercepcion() {
        return porcentajePercepcion;
    }

    public void setPorcentajePercepcion(String porcentajePercepcion) {
        this.porcentajePercepcion = porcentajePercepcion;
    }

    public String getTotalRetencion() {
        return totalRetencion;
    }

    public void setTotalRetencion(String totalRetencion) {
        this.totalRetencion = totalRetencion;
    }

    public String getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(String porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public String getTotalDetraccion() {
        return totalDetraccion;
    }

    public void setTotalDetraccion(String totalDetraccion) {
        this.totalDetraccion = totalDetraccion;
    }

    public String getPorcentajeDetraccion() {
        return porcentajeDetraccion;
    }

    public void setPorcentajeDetraccion(String porcentajeDetraccion) {
        this.porcentajeDetraccion = porcentajeDetraccion;
    }

    public String getDescripcionDetraccion() {
        return descripcionDetraccion;
    }

    public void setDescripcionDetraccion(String descripcionDetraccion) {
        this.descripcionDetraccion = descripcionDetraccion;
    }

    public String getValorReferencialDetraccion() {
        return valorReferencialDetraccion;
    }

    public void setValorReferencialDetraccion(String valorReferencialDetraccion) {
        this.valorReferencialDetraccion = valorReferencialDetraccion;
    }

    public String getTotalBonificacion() {
        return totalBonificacion;
    }

    public void setTotalBonificacion(String totalBonificacion) {
        this.totalBonificacion = totalBonificacion;
    }

    public String getInHabilitado() {
        return inHabilitado;
    }

    public void setInHabilitado(String inHabilitado) {
        this.inHabilitado = inHabilitado;
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

    public String getTotalFondoInclusionSocial() {
        return totalFondoInclusionSocial;
    }

    public void setTotalFondoInclusionSocial(String totalFondoInclusionSocial) {
        this.totalFondoInclusionSocial = totalFondoInclusionSocial;
    }

    public String getTipoOperacionFactura() {
        return tipoOperacionFactura;
    }

    public void setTipoOperacionFactura(String tipoOperacionFactura) {
        this.tipoOperacionFactura = tipoOperacionFactura;
    }

    public String getTotalDocumentoAnticipo() {
        return totalDocumentoAnticipo;
    }

    public void setTotalDocumentoAnticipo(String totalDocumentoAnticipo) {
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

    //CAMPO EXCLUSIVO DEL XML
    @Transient
    private List<DocumentDetail> item;

    @Override
    public List<DocumentDetail> getItem() {
        if (item == null) {
            item = new LinkedList<>();
        }
        return item;
    }

    @Override
    public void setItem(List<DocumentDetail> item) {
        this.item = item;
    }

}
