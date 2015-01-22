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
@Table(name = "SPE_EINVOICEDETAIL")
@NamedQueries({
    @NamedQuery(name = "Detail.findAll", query = "SELECT d FROM Detail d")})
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetailPK detailPK;
    @Size(max = 1700)
    @Column(name = "DDESPRODUCTO")
    private String ddesproducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DCANPRODUCTO")
    private BigDecimal dcanproducto;
    @Size(max = 3)
    @Column(name = "DUNIPRODUCTO")
    private String duniproducto;
    @Column(name = "DVVENTAUNITARIO")
    private BigDecimal dvventaunitario;
    @Column(name = "DPVENTAUNITARIO")
    private BigDecimal dpventaunitario;
    @Size(max = 2)
    @Column(name = "DCODPVENTAUNITARIO")
    private String dcodpventaunitario;
    @Column(name = "DIREFERENCIAUNITARIO")
    private BigDecimal direferenciaunitario;
    @Size(max = 2)
    @Column(name = "DCODIREFERENCIAUNITARIO")
    private String dcodireferenciaunitario;
    @Column(name = "DVVENTA")
    private BigDecimal dvventa;
    @Column(name = "DCARGO")
    private BigDecimal dcargo;
    @Column(name = "DDESCUENTO")
    private BigDecimal ddescuento;
    @Size(max = 2)
    @Column(name = "DTIPIGV")
    private String dtipigv;
    @Column(name = "DIGV")
    private BigDecimal digv;
    @Size(max = 2)
    @Column(name = "DTIPISC")
    private String dtipisc;
    @Column(name = "DISC")
    private BigDecimal disc;
    @Size(max = 100)
    @Column(name = "DAUX1")
    private String daux1;
    @Size(max = 100)
    @Column(name = "DAUX2")
    private String daux2;
    @Size(max = 100)
    @Column(name = "DAUX3")
    private String daux3;
    @Size(max = 100)
    @Column(name = "DAUX4")
    private String daux4;
    @Size(max = 100)
    @Column(name = "DAUX5")
    private String daux5;
    @Size(max = 100)
    @Column(name = "DAUX6")
    private String daux6;
    @Size(max = 100)
    @Column(name = "DAUX7")
    private String daux7;
    @Size(max = 100)
    @Column(name = "DAUX8")
    private String daux8;
    @Size(max = 100)
    @Column(name = "DAUX9")
    private String daux9;
    @Size(max = 100)
    @Column(name = "DAUX10")
    private String daux10;
    @Size(max = 100)
    @Column(name = "DAUX11")
    private String daux11;
    @Size(max = 100)
    @Column(name = "DAUX12")
    private String daux12;
    @Size(max = 100)
    @Column(name = "DAUX13")
    private String daux13;
    @Size(max = 100)
    @Column(name = "DAUX14")
    private String daux14;

    public Detail() {
    }

    public Detail(DetailPK detailPK) {
        this.detailPK = detailPK;
    }

    public DetailPK getDetailPK() {
        return detailPK;
    }

    public void setDetailPK(DetailPK detailPK) {
        this.detailPK = detailPK;
    }

    public String getDdesproducto() {
        return ddesproducto;
    }

    public void setDdesproducto(String ddesproducto) {
        this.ddesproducto = ddesproducto;
    }

    public BigDecimal getDcanproducto() {
        return dcanproducto;
    }

    public void setDcanproducto(BigDecimal dcanproducto) {
        this.dcanproducto = dcanproducto;
    }

    public String getDuniproducto() {
        return duniproducto;
    }

    public void setDuniproducto(String duniproducto) {
        this.duniproducto = duniproducto;
    }

    public BigDecimal getDvventaunitario() {
        return dvventaunitario;
    }

    public void setDvventaunitario(BigDecimal dvventaunitario) {
        this.dvventaunitario = dvventaunitario;
    }

    public BigDecimal getDpventaunitario() {
        return dpventaunitario;
    }

    public void setDpventaunitario(BigDecimal dpventaunitario) {
        this.dpventaunitario = dpventaunitario;
    }

    public String getDcodpventaunitario() {
        return dcodpventaunitario;
    }

    public void setDcodpventaunitario(String dcodpventaunitario) {
        this.dcodpventaunitario = dcodpventaunitario;
    }

    public BigDecimal getDireferenciaunitario() {
        return direferenciaunitario;
    }

    public void setDireferenciaunitario(BigDecimal direferenciaunitario) {
        this.direferenciaunitario = direferenciaunitario;
    }

    public String getDcodireferenciaunitario() {
        return dcodireferenciaunitario;
    }

    public void setDcodireferenciaunitario(String dcodireferenciaunitario) {
        this.dcodireferenciaunitario = dcodireferenciaunitario;
    }

    public BigDecimal getDvventa() {
        return dvventa;
    }

    public void setDvventa(BigDecimal dvventa) {
        this.dvventa = dvventa;
    }

    public BigDecimal getDcargo() {
        return dcargo;
    }

    public void setDcargo(BigDecimal dcargo) {
        this.dcargo = dcargo;
    }

    public BigDecimal getDdescuento() {
        return ddescuento;
    }

    public void setDdescuento(BigDecimal ddescuento) {
        this.ddescuento = ddescuento;
    }

    public String getDtipigv() {
        return dtipigv;
    }

    public void setDtipigv(String dtipigv) {
        this.dtipigv = dtipigv;
    }

    public BigDecimal getDigv() {
        return digv;
    }

    public void setDigv(BigDecimal digv) {
        this.digv = digv;
    }

    public String getDtipisc() {
        return dtipisc;
    }

    public void setDtipisc(String dtipisc) {
        this.dtipisc = dtipisc;
    }

    public BigDecimal getDisc() {
        return disc;
    }

    public void setDisc(BigDecimal disc) {
        this.disc = disc;
    }

    public String getDaux1() {
        return daux1;
    }

    public void setDaux1(String daux1) {
        this.daux1 = daux1;
    }

    public String getDaux2() {
        return daux2;
    }

    public void setDaux2(String daux2) {
        this.daux2 = daux2;
    }

    public String getDaux3() {
        return daux3;
    }

    public void setDaux3(String daux3) {
        this.daux3 = daux3;
    }

    public String getDaux4() {
        return daux4;
    }

    public void setDaux4(String daux4) {
        this.daux4 = daux4;
    }

    public String getDaux5() {
        return daux5;
    }

    public void setDaux5(String daux5) {
        this.daux5 = daux5;
    }

    public String getDaux6() {
        return daux6;
    }

    public void setDaux6(String daux6) {
        this.daux6 = daux6;
    }

    public String getDaux7() {
        return daux7;
    }

    public void setDaux7(String daux7) {
        this.daux7 = daux7;
    }

    public String getDaux8() {
        return daux8;
    }

    public void setDaux8(String daux8) {
        this.daux8 = daux8;
    }

    public String getDaux9() {
        return daux9;
    }

    public void setDaux9(String daux9) {
        this.daux9 = daux9;
    }

    public String getDaux10() {
        return daux10;
    }

    public void setDaux10(String daux10) {
        this.daux10 = daux10;
    }

    public String getDaux11() {
        return daux11;
    }

    public void setDaux11(String daux11) {
        this.daux11 = daux11;
    }

    public String getDaux12() {
        return daux12;
    }

    public void setDaux12(String daux12) {
        this.daux12 = daux12;
    }

    public String getDaux13() {
        return daux13;
    }

    public void setDaux13(String daux13) {
        this.daux13 = daux13;
    }

    public String getDaux14() {
        return daux14;
    }

    public void setDaux14(String daux14) {
        this.daux14 = daux14;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailPK != null ? detailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detail)) {
            return false;
        }
        Detail other = (Detail) object;
        if ((this.detailPK == null && other.detailPK != null) || (this.detailPK != null && !this.detailPK.equals(other.detailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.labtech.einvoice.core.replicator.entity.Detail[ detailPK=" + detailPK + " ]";
    }

}
