/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.hv.replicator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SPE_EINVOICEHEADER")
@NamedQueries({
    @NamedQuery(name = "Header.findAll", query = "SELECT h FROM Header h")})
public class Header implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HeaderPK headerPK;
    @Column(name = "CTIPDOCUMENTOEMISOR")
    private Character ctipdocumentoemisor;
    @Size(max = 11)
    @Column(name = "CDOCUMENTOEMISOR")
    private String cdocumentoemisor;
    @Size(max = 100)
    @Column(name = "CRSOCIALEMISOR")
    private String crsocialemisor;
    @Size(max = 100)
    @Column(name = "CNCOMERCIALEMISOR")
    private String cncomercialemisor;
    @Size(max = 2)
    @Column(name = "CTIPCOMPROBANTE")
    private String ctipcomprobante;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTE")
    private String ccomprobante;
    @Size(max = 10)
    @Column(name = "CFEMISION")
    private String cfemision;
    @Size(max = 6)
    @Column(name = "CUBIGEOEMISOR")
    private String cubigeoemisor;
    @Size(max = 100)
    @Column(name = "CDIRECCIONEMISOR")
    private String cdireccionemisor;
    @Size(max = 25)
    @Column(name = "CURBANIZACIONEMISOR")
    private String curbanizacionemisor;
    @Size(max = 30)
    @Column(name = "CDISTRITOEMISOR")
    private String cdistritoemisor;
    @Size(max = 30)
    @Column(name = "CPROVINCIAEMISOR")
    private String cprovinciaemisor;
    @Size(max = 30)
    @Column(name = "CDEPARTAMENTOEMISOR")
    private String cdepartamentoemisor;
    @Size(max = 2)
    @Column(name = "CCODPAISEMISOR")
    private String ccodpaisemisor;
    @Size(max = 100)
    @Column(name = "CCORREOEMISOR")
    private String ccorreoemisor;
    @Size(max = 2)
    @Column(name = "CTIPNCREDITODEBITO")
    private String ctipncreditodebito;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTEAFECTO")
    private String ccomprobanteafecto;
    @Column(name = "CTIPDOCUMENTOUSUARIO")
    private Character ctipdocumentousuario;
    @Size(max = 15)
    @Column(name = "CDOCUMENTOUSUARIO")
    private String cdocumentousuario;
    @Size(max = 100)
    @Column(name = "CRSOCIALUSUARIO")
    private String crsocialusuario;
    @Size(max = 100)
    @Column(name = "CDIRECCIONUSUARIO")
    private String cdireccionusuario;
    @Size(max = 100)
    @Column(name = "CCORREOUSUARIO")
    private String ccorreousuario;
    @Size(max = 3)
    @Column(name = "CMONEDA")
    private String cmoneda;
    @Size(max = 500)
    @Column(name = "CMOTNCREDITODEBITO")
    private String cmotncreditodebito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CVVENTAGRAVADA")
    private BigDecimal cvventagravada;
    @Column(name = "CVVENTANOGRAVADA")
    private BigDecimal cvventanogravada;
    @Column(name = "CVVENTAEXONERADA")
    private BigDecimal cvventaexonerada;
    @Column(name = "CVVENTAGRATUITA")
    private BigDecimal cvventagratuita;
    @Column(name = "CSUBTOTAL")
    private BigDecimal csubtotal;
    @Column(name = "CIGV")
    private BigDecimal cigv;
    @Column(name = "CISC")
    private BigDecimal cisc;
    @Column(name = "CTRIBUTO")
    private BigDecimal ctributo;
    @Column(name = "CCARGO")
    private BigDecimal ccargo;
    @Column(name = "CDESCUENTO")
    private BigDecimal cdescuento;
    @Column(name = "CDESCUENTOGLOBAL")
    private BigDecimal cdescuentoglobal;
    @Column(name = "CTOTAL")
    private BigDecimal ctotal;
    @Size(max = 3)
    @Column(name = "CMONEDANCREDITODEBITO")
    private String cmonedancreditodebito;
    @Size(max = 2)
    @Column(name = "CTIPNCREDITODEBITO1")
    private String ctipncreditodebito1;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTEAFECTO1")
    private String ccomprobanteafecto1;
    @Size(max = 2)
    @Column(name = "CTIPNCREDITODEBITO2")
    private String ctipncreditodebito2;
    @Size(max = 13)
    @Column(name = "CCOMPROBANTEAFECTO2")
    private String ccomprobanteafecto2;
    @Column(name = "CBASEPERCEPCION")
    private BigDecimal cbasepercepcion;
    @Column(name = "CTOTALPERCEPCION")
    private BigDecimal ctotalpercepcion;
    @Column(name = "CVENTAPERCEPCION")
    private BigDecimal cventapercepcion;
    @Column(name = "CPORCPERCEPCION")
    private BigDecimal cporcpercepcion;
    @Column(name = "CTOTALRETENCION")
    private BigDecimal ctotalretencion;
    @Column(name = "CPORCRETENCION")
    private BigDecimal cporcretencion;
    @Column(name = "CTOTALDETRACCION")
    private BigDecimal ctotaldetraccion;
    @Column(name = "CPORCDETRACCION")
    private BigDecimal cporcdetraccion;
    @Size(max = 100)
    @Column(name = "CDESCDETRACCION")
    private String cdescdetraccion;
    @Column(name = "CVALORDETRACCION")
    private BigDecimal cvalordetraccion;
    @Column(name = "CBONIFICACION")
    private BigDecimal cbonificacion;
    @Column(name = "CHABILITADO")
    private Character chabilitado;
    @Size(max = 200)
    @Column(name = "CLEY1")
    private String cley1;
    @Size(max = 200)
    @Column(name = "CLEY2")
    private String cley2;
    @Size(max = 200)
    @Column(name = "CLEY3")
    private String cley3;
    @Size(max = 200)
    @Column(name = "CLEY4")
    private String cley4;
    @Size(max = 40)
    @Column(name = "CAUX1")
    private String caux1;
    @Size(max = 40)
    @Column(name = "CAUX2")
    private String caux2;
    @Size(max = 40)
    @Column(name = "CAUX3")
    private String caux3;
    @Size(max = 40)
    @Column(name = "CAUX4")
    private String caux4;
    @Size(max = 40)
    @Column(name = "CAUX5")
    private String caux5;
    @Size(max = 40)
    @Column(name = "CAUX6")
    private String caux6;
    @Size(max = 40)
    @Column(name = "CAUX7")
    private String caux7;
    @Size(max = 40)
    @Column(name = "CAUX8")
    private String caux8;
    @Size(max = 40)
    @Column(name = "CAUX9")
    private String caux9;
    @Size(max = 40)
    @Column(name = "CAUX10")
    private String caux10;
    @Size(max = 40)
    @Column(name = "CAUX11")
    private String caux11;
    @Size(max = 40)
    @Column(name = "CAUX12")
    private String caux12;
    @Size(max = 40)
    @Column(name = "CAUX13")
    private String caux13;
    @Size(max = 40)
    @Column(name = "CAUX14")
    private String caux14;
    @Size(max = 40)
    @Column(name = "CAUX15")
    private String caux15;
    @Size(max = 40)
    @Column(name = "CAUX16")
    private String caux16;
    @Size(max = 40)
    @Column(name = "CAUX17")
    private String caux17;
    @Size(max = 40)
    @Column(name = "CAUX18")
    private String caux18;
    @Size(max = 40)
    @Column(name = "CAUX19")
    private String caux19;
    @Size(max = 40)
    @Column(name = "CAUX20")
    private String caux20;
    @Size(max = 100)
    @Column(name = "CAUX21")
    private String caux21;
    @Size(max = 100)
    @Column(name = "CAUX22")
    private String caux22;
    @Size(max = 100)
    @Column(name = "CAUX23")
    private String caux23;
    @Size(max = 100)
    @Column(name = "CAUX24")
    private String caux24;
    @Size(max = 100)
    @Column(name = "CAUX25")
    private String caux25;
    @Size(max = 100)
    @Column(name = "CAUX26")
    private String caux26;
    @Size(max = 100)
    @Column(name = "CAUX27")
    private String caux27;
    @Size(max = 100)
    @Column(name = "CAUX28")
    private String caux28;
    @Size(max = 100)
    @Column(name = "CAUX29")
    private String caux29;
    @Size(max = 100)
    @Column(name = "CAUX30")
    private String caux30;
    @Size(max = 100)
    @Column(name = "CAUX31")
    private String caux31;
    @Size(max = 100)
    @Column(name = "CAUX32")
    private String caux32;
    @Size(max = 100)
    @Column(name = "CAUX33")
    private String caux33;
    @Size(max = 100)
    @Column(name = "CAUX34")
    private String caux34;
    @Size(max = 100)
    @Column(name = "CAUX35")
    private String caux35;
    @Size(max = 250)
    @Column(name = "CAUX36")
    private String caux36;
    @Column(name = "CESTADO")
    private Character cestado;
    @Size(max = 100)
    @Column(name = "CNUMEROAUTORIZACION")
    private String cnumeroautorizacion;
    @Size(max = 10)
    @Column(name = "CFECHAAUTORIZACION")
    private String cfechaautorizacion;
    @Size(max = 8)
    @Column(name = "CHORAAUTORIZACION")
    private String choraautorizacion;
    @Size(max = 1000)
    @Column(name = "CRUTAXML")
    private String crutaxml;
    @Size(max = 1000)
    @Column(name = "CRUTAPDF")
    private String crutapdf;
    @Column(name = "LG_PROCESS_STATUS")
    private Character lgProcessStatus;
    @Size(max = 20)
    @Column(name = "LG_RECORD_STATUS")
    private String lgRecordStatus;
    @Size(max = 2000)
    @Column(name = "LG_FIRMA")
    private String lgFirma;
    @Size(max = 2000)
    @Column(name = "LG_FIRMA_HASH")
    private String lgFirmaHash;
    @Size(max = 2000)
    @Column(name = "LG_LOAD_MESSAGES")
    private String lgLoadMessages;
    @Size(max = 2000)
    @Column(name = "LG_SERVICE_RESPONSE")
    private String lgServiceResponse;

    public Header() {
    }

    public Header(HeaderPK headerPK) {
        this.headerPK = headerPK;
    }

    public HeaderPK getHeaderPK() {
        return headerPK;
    }

    public void setHeaderPK(HeaderPK headerPK) {
        this.headerPK = headerPK;
    }

    public Character getCtipdocumentoemisor() {
        return ctipdocumentoemisor;
    }

    public void setCtipdocumentoemisor(Character ctipdocumentoemisor) {
        this.ctipdocumentoemisor = ctipdocumentoemisor;
    }

    public String getCdocumentoemisor() {
        return cdocumentoemisor;
    }

    public void setCdocumentoemisor(String cdocumentoemisor) {
        this.cdocumentoemisor = cdocumentoemisor;
    }

    public String getCrsocialemisor() {
        return crsocialemisor;
    }

    public void setCrsocialemisor(String crsocialemisor) {
        this.crsocialemisor = crsocialemisor;
    }

    public String getCncomercialemisor() {
        return cncomercialemisor;
    }

    public void setCncomercialemisor(String cncomercialemisor) {
        this.cncomercialemisor = cncomercialemisor;
    }

    public String getCtipcomprobante() {
        return ctipcomprobante;
    }

    public void setCtipcomprobante(String ctipcomprobante) {
        this.ctipcomprobante = ctipcomprobante;
    }

    public String getCcomprobante() {
        return ccomprobante;
    }

    public void setCcomprobante(String ccomprobante) {
        this.ccomprobante = ccomprobante;
    }

    public String getCfemision() {
        return cfemision;
    }

    public void setCfemision(String cfemision) {
        this.cfemision = cfemision;
    }

    public String getCubigeoemisor() {
        return cubigeoemisor;
    }

    public void setCubigeoemisor(String cubigeoemisor) {
        this.cubigeoemisor = cubigeoemisor;
    }

    public String getCdireccionemisor() {
        return cdireccionemisor;
    }

    public void setCdireccionemisor(String cdireccionemisor) {
        this.cdireccionemisor = cdireccionemisor;
    }

    public String getCurbanizacionemisor() {
        return curbanizacionemisor;
    }

    public void setCurbanizacionemisor(String curbanizacionemisor) {
        this.curbanizacionemisor = curbanizacionemisor;
    }

    public String getCdistritoemisor() {
        return cdistritoemisor;
    }

    public void setCdistritoemisor(String cdistritoemisor) {
        this.cdistritoemisor = cdistritoemisor;
    }

    public String getCprovinciaemisor() {
        return cprovinciaemisor;
    }

    public void setCprovinciaemisor(String cprovinciaemisor) {
        this.cprovinciaemisor = cprovinciaemisor;
    }

    public String getCdepartamentoemisor() {
        return cdepartamentoemisor;
    }

    public void setCdepartamentoemisor(String cdepartamentoemisor) {
        this.cdepartamentoemisor = cdepartamentoemisor;
    }

    public String getCcodpaisemisor() {
        return ccodpaisemisor;
    }

    public void setCcodpaisemisor(String ccodpaisemisor) {
        this.ccodpaisemisor = ccodpaisemisor;
    }

    public String getCcorreoemisor() {
        return ccorreoemisor;
    }

    public void setCcorreoemisor(String ccorreoemisor) {
        this.ccorreoemisor = ccorreoemisor;
    }

    public String getCtipncreditodebito() {
        return ctipncreditodebito;
    }

    public void setCtipncreditodebito(String ctipncreditodebito) {
        this.ctipncreditodebito = ctipncreditodebito;
    }

    public String getCcomprobanteafecto() {
        return ccomprobanteafecto;
    }

    public void setCcomprobanteafecto(String ccomprobanteafecto) {
        this.ccomprobanteafecto = ccomprobanteafecto;
    }

    public Character getCtipdocumentousuario() {
        return ctipdocumentousuario;
    }

    public void setCtipdocumentousuario(Character ctipdocumentousuario) {
        this.ctipdocumentousuario = ctipdocumentousuario;
    }

    public String getCdocumentousuario() {
        return cdocumentousuario;
    }

    public void setCdocumentousuario(String cdocumentousuario) {
        this.cdocumentousuario = cdocumentousuario;
    }

    public String getCrsocialusuario() {
        return crsocialusuario;
    }

    public void setCrsocialusuario(String crsocialusuario) {
        this.crsocialusuario = crsocialusuario;
    }

    public String getCdireccionusuario() {
        return cdireccionusuario;
    }

    public void setCdireccionusuario(String cdireccionusuario) {
        this.cdireccionusuario = cdireccionusuario;
    }

    public String getCcorreousuario() {
        return ccorreousuario;
    }

    public void setCcorreousuario(String ccorreousuario) {
        this.ccorreousuario = ccorreousuario;
    }

    public String getCmoneda() {
        return cmoneda;
    }

    public void setCmoneda(String cmoneda) {
        this.cmoneda = cmoneda;
    }

    public String getCmotncreditodebito() {
        return cmotncreditodebito;
    }

    public void setCmotncreditodebito(String cmotncreditodebito) {
        this.cmotncreditodebito = cmotncreditodebito;
    }

    public BigDecimal getCvventagravada() {
        return cvventagravada;
    }

    public void setCvventagravada(BigDecimal cvventagravada) {
        this.cvventagravada = cvventagravada;
    }

    public BigDecimal getCvventanogravada() {
        return cvventanogravada;
    }

    public void setCvventanogravada(BigDecimal cvventanogravada) {
        this.cvventanogravada = cvventanogravada;
    }

    public BigDecimal getCvventaexonerada() {
        return cvventaexonerada;
    }

    public void setCvventaexonerada(BigDecimal cvventaexonerada) {
        this.cvventaexonerada = cvventaexonerada;
    }

    public BigDecimal getCvventagratuita() {
        return cvventagratuita;
    }

    public void setCvventagratuita(BigDecimal cvventagratuita) {
        this.cvventagratuita = cvventagratuita;
    }

    public BigDecimal getCsubtotal() {
        return csubtotal;
    }

    public void setCsubtotal(BigDecimal csubtotal) {
        this.csubtotal = csubtotal;
    }

    public BigDecimal getCigv() {
        return cigv;
    }

    public void setCigv(BigDecimal cigv) {
        this.cigv = cigv;
    }

    public BigDecimal getCisc() {
        return cisc;
    }

    public void setCisc(BigDecimal cisc) {
        this.cisc = cisc;
    }

    public BigDecimal getCtributo() {
        return ctributo;
    }

    public void setCtributo(BigDecimal ctributo) {
        this.ctributo = ctributo;
    }

    public BigDecimal getCcargo() {
        return ccargo;
    }

    public void setCcargo(BigDecimal ccargo) {
        this.ccargo = ccargo;
    }

    public BigDecimal getCdescuento() {
        return cdescuento;
    }

    public void setCdescuento(BigDecimal cdescuento) {
        this.cdescuento = cdescuento;
    }

    public BigDecimal getCdescuentoglobal() {
        return cdescuentoglobal;
    }

    public void setCdescuentoglobal(BigDecimal cdescuentoglobal) {
        this.cdescuentoglobal = cdescuentoglobal;
    }

    public BigDecimal getCtotal() {
        return ctotal;
    }

    public void setCtotal(BigDecimal ctotal) {
        this.ctotal = ctotal;
    }

    public String getCmonedancreditodebito() {
        return cmonedancreditodebito;
    }

    public void setCmonedancreditodebito(String cmonedancreditodebito) {
        this.cmonedancreditodebito = cmonedancreditodebito;
    }

    public String getCtipncreditodebito1() {
        return ctipncreditodebito1;
    }

    public void setCtipncreditodebito1(String ctipncreditodebito1) {
        this.ctipncreditodebito1 = ctipncreditodebito1;
    }

    public String getCcomprobanteafecto1() {
        return ccomprobanteafecto1;
    }

    public void setCcomprobanteafecto1(String ccomprobanteafecto1) {
        this.ccomprobanteafecto1 = ccomprobanteafecto1;
    }

    public String getCtipncreditodebito2() {
        return ctipncreditodebito2;
    }

    public void setCtipncreditodebito2(String ctipncreditodebito2) {
        this.ctipncreditodebito2 = ctipncreditodebito2;
    }

    public String getCcomprobanteafecto2() {
        return ccomprobanteafecto2;
    }

    public void setCcomprobanteafecto2(String ccomprobanteafecto2) {
        this.ccomprobanteafecto2 = ccomprobanteafecto2;
    }

    public BigDecimal getCbasepercepcion() {
        return cbasepercepcion;
    }

    public void setCbasepercepcion(BigDecimal cbasepercepcion) {
        this.cbasepercepcion = cbasepercepcion;
    }

    public BigDecimal getCtotalpercepcion() {
        return ctotalpercepcion;
    }

    public void setCtotalpercepcion(BigDecimal ctotalpercepcion) {
        this.ctotalpercepcion = ctotalpercepcion;
    }

    public BigDecimal getCventapercepcion() {
        return cventapercepcion;
    }

    public void setCventapercepcion(BigDecimal cventapercepcion) {
        this.cventapercepcion = cventapercepcion;
    }

    public BigDecimal getCporcpercepcion() {
        return cporcpercepcion;
    }

    public void setCporcpercepcion(BigDecimal cporcpercepcion) {
        this.cporcpercepcion = cporcpercepcion;
    }

    public BigDecimal getCtotalretencion() {
        return ctotalretencion;
    }

    public void setCtotalretencion(BigDecimal ctotalretencion) {
        this.ctotalretencion = ctotalretencion;
    }

    public BigDecimal getCporcretencion() {
        return cporcretencion;
    }

    public void setCporcretencion(BigDecimal cporcretencion) {
        this.cporcretencion = cporcretencion;
    }

    public BigDecimal getCtotaldetraccion() {
        return ctotaldetraccion;
    }

    public void setCtotaldetraccion(BigDecimal ctotaldetraccion) {
        this.ctotaldetraccion = ctotaldetraccion;
    }

    public BigDecimal getCporcdetraccion() {
        return cporcdetraccion;
    }

    public void setCporcdetraccion(BigDecimal cporcdetraccion) {
        this.cporcdetraccion = cporcdetraccion;
    }

    public String getCdescdetraccion() {
        return cdescdetraccion;
    }

    public void setCdescdetraccion(String cdescdetraccion) {
        this.cdescdetraccion = cdescdetraccion;
    }

    public BigDecimal getCvalordetraccion() {
        return cvalordetraccion;
    }

    public void setCvalordetraccion(BigDecimal cvalordetraccion) {
        this.cvalordetraccion = cvalordetraccion;
    }

    public BigDecimal getCbonificacion() {
        return cbonificacion;
    }

    public void setCbonificacion(BigDecimal cbonificacion) {
        this.cbonificacion = cbonificacion;
    }

    public Character getChabilitado() {
        return chabilitado;
    }

    public void setChabilitado(Character chabilitado) {
        this.chabilitado = chabilitado;
    }

    public String getCley1() {
        return cley1;
    }

    public void setCley1(String cley1) {
        this.cley1 = cley1;
    }

    public String getCley2() {
        return cley2;
    }

    public void setCley2(String cley2) {
        this.cley2 = cley2;
    }

    public String getCley3() {
        return cley3;
    }

    public void setCley3(String cley3) {
        this.cley3 = cley3;
    }

    public String getCley4() {
        return cley4;
    }

    public void setCley4(String cley4) {
        this.cley4 = cley4;
    }

    public String getCaux1() {
        return caux1;
    }

    public void setCaux1(String caux1) {
        this.caux1 = caux1;
    }

    public String getCaux2() {
        return caux2;
    }

    public void setCaux2(String caux2) {
        this.caux2 = caux2;
    }

    public String getCaux3() {
        return caux3;
    }

    public void setCaux3(String caux3) {
        this.caux3 = caux3;
    }

    public String getCaux4() {
        return caux4;
    }

    public void setCaux4(String caux4) {
        this.caux4 = caux4;
    }

    public String getCaux5() {
        return caux5;
    }

    public void setCaux5(String caux5) {
        this.caux5 = caux5;
    }

    public String getCaux6() {
        return caux6;
    }

    public void setCaux6(String caux6) {
        this.caux6 = caux6;
    }

    public String getCaux7() {
        return caux7;
    }

    public void setCaux7(String caux7) {
        this.caux7 = caux7;
    }

    public String getCaux8() {
        return caux8;
    }

    public void setCaux8(String caux8) {
        this.caux8 = caux8;
    }

    public String getCaux9() {
        return caux9;
    }

    public void setCaux9(String caux9) {
        this.caux9 = caux9;
    }

    public String getCaux10() {
        return caux10;
    }

    public void setCaux10(String caux10) {
        this.caux10 = caux10;
    }

    public String getCaux11() {
        return caux11;
    }

    public void setCaux11(String caux11) {
        this.caux11 = caux11;
    }

    public String getCaux12() {
        return caux12;
    }

    public void setCaux12(String caux12) {
        this.caux12 = caux12;
    }

    public String getCaux13() {
        return caux13;
    }

    public void setCaux13(String caux13) {
        this.caux13 = caux13;
    }

    public String getCaux14() {
        return caux14;
    }

    public void setCaux14(String caux14) {
        this.caux14 = caux14;
    }

    public String getCaux15() {
        return caux15;
    }

    public void setCaux15(String caux15) {
        this.caux15 = caux15;
    }

    public String getCaux16() {
        return caux16;
    }

    public void setCaux16(String caux16) {
        this.caux16 = caux16;
    }

    public String getCaux17() {
        return caux17;
    }

    public void setCaux17(String caux17) {
        this.caux17 = caux17;
    }

    public String getCaux18() {
        return caux18;
    }

    public void setCaux18(String caux18) {
        this.caux18 = caux18;
    }

    public String getCaux19() {
        return caux19;
    }

    public void setCaux19(String caux19) {
        this.caux19 = caux19;
    }

    public String getCaux20() {
        return caux20;
    }

    public void setCaux20(String caux20) {
        this.caux20 = caux20;
    }

    public String getCaux21() {
        return caux21;
    }

    public void setCaux21(String caux21) {
        this.caux21 = caux21;
    }

    public String getCaux22() {
        return caux22;
    }

    public void setCaux22(String caux22) {
        this.caux22 = caux22;
    }

    public String getCaux23() {
        return caux23;
    }

    public void setCaux23(String caux23) {
        this.caux23 = caux23;
    }

    public String getCaux24() {
        return caux24;
    }

    public void setCaux24(String caux24) {
        this.caux24 = caux24;
    }

    public String getCaux25() {
        return caux25;
    }

    public void setCaux25(String caux25) {
        this.caux25 = caux25;
    }

    public String getCaux26() {
        return caux26;
    }

    public void setCaux26(String caux26) {
        this.caux26 = caux26;
    }

    public String getCaux27() {
        return caux27;
    }

    public void setCaux27(String caux27) {
        this.caux27 = caux27;
    }

    public String getCaux28() {
        return caux28;
    }

    public void setCaux28(String caux28) {
        this.caux28 = caux28;
    }

    public String getCaux29() {
        return caux29;
    }

    public void setCaux29(String caux29) {
        this.caux29 = caux29;
    }

    public String getCaux30() {
        return caux30;
    }

    public void setCaux30(String caux30) {
        this.caux30 = caux30;
    }

    public String getCaux31() {
        return caux31;
    }

    public void setCaux31(String caux31) {
        this.caux31 = caux31;
    }

    public String getCaux32() {
        return caux32;
    }

    public void setCaux32(String caux32) {
        this.caux32 = caux32;
    }

    public String getCaux33() {
        return caux33;
    }

    public void setCaux33(String caux33) {
        this.caux33 = caux33;
    }

    public String getCaux34() {
        return caux34;
    }

    public void setCaux34(String caux34) {
        this.caux34 = caux34;
    }

    public String getCaux35() {
        return caux35;
    }

    public void setCaux35(String caux35) {
        this.caux35 = caux35;
    }

    public String getCaux36() {
        return caux36;
    }

    public void setCaux36(String caux36) {
        this.caux36 = caux36;
    }

    public Character getCestado() {
        return cestado;
    }

    public void setCestado(Character cestado) {
        this.cestado = cestado;
    }

    public String getCnumeroautorizacion() {
        return cnumeroautorizacion;
    }

    public void setCnumeroautorizacion(String cnumeroautorizacion) {
        this.cnumeroautorizacion = cnumeroautorizacion;
    }

    public String getCfechaautorizacion() {
        return cfechaautorizacion;
    }

    public void setCfechaautorizacion(String cfechaautorizacion) {
        this.cfechaautorizacion = cfechaautorizacion;
    }

    public String getChoraautorizacion() {
        return choraautorizacion;
    }

    public void setChoraautorizacion(String choraautorizacion) {
        this.choraautorizacion = choraautorizacion;
    }

    public String getCrutaxml() {
        return crutaxml;
    }

    public void setCrutaxml(String crutaxml) {
        this.crutaxml = crutaxml;
    }

    public String getCrutapdf() {
        return crutapdf;
    }

    public void setCrutapdf(String crutapdf) {
        this.crutapdf = crutapdf;
    }

    public Character getLgProcessStatus() {
        return lgProcessStatus;
    }

    public void setLgProcessStatus(Character lgProcessStatus) {
        this.lgProcessStatus = lgProcessStatus;
    }

    public String getLgRecordStatus() {
        return lgRecordStatus;
    }

    public void setLgRecordStatus(String lgRecordStatus) {
        this.lgRecordStatus = lgRecordStatus;
    }

    public String getLgFirma() {
        return lgFirma;
    }

    public void setLgFirma(String lgFirma) {
        this.lgFirma = lgFirma;
    }

    public String getLgFirmaHash() {
        return lgFirmaHash;
    }

    public void setLgFirmaHash(String lgFirmaHash) {
        this.lgFirmaHash = lgFirmaHash;
    }

    public String getLgLoadMessages() {
        return lgLoadMessages;
    }

    public void setLgLoadMessages(String lgLoadMessages) {
        this.lgLoadMessages = lgLoadMessages;
    }

    public String getLgServiceResponse() {
        return lgServiceResponse;
    }

    public void setLgServiceResponse(String lgServiceResponse) {
        this.lgServiceResponse = lgServiceResponse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (headerPK != null ? headerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Header)) {
            return false;
        }
        Header other = (Header) object;
        if ((this.headerPK == null && other.headerPK != null) || (this.headerPK != null && !this.headerPK.equals(other.headerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.labtech.einvoice.core.replicator.entity.Header[ headerPK=" + headerPK + " ]";
    }

}
