/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

/**
* Clase BLResponseImpl.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@MappedSuperclass
public abstract class BLResponseImpl implements BLResponse {

    @XmlTransient
    @Column(name = "bl_firma", length = 4000)
    private String bl_firma;
    @XmlTransient
    @Column(name = "bl_hashFirma", length = 4000)
    private String bl_hashFirma;
    @XmlTransient
    @Column(name = "bl_estadoProceso", length = 40)
    private String bl_estadoProceso;
    @XmlTransient
    @Column(name = "bl_estadoRegistro", length = 40)
    private String bl_estadoRegistro;
    @XmlTransient
    @Column(name = "bl_mensaje", length = 4000)
    private String bl_mensaje;
    @XmlTransient
    @Column(name = "bl_mensajeSunat", length = 4000)
    private String bl_mensajeSunat;
    @XmlTransient
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_pdf")
    private byte[] bl_pdf;
    @XmlTransient
    @Column(name = "bl_urlpdf", length = 4000)
    private String bl_urlpdf;
    @XmlTransient
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_xmlubl", length = 4000)
    private byte[] bl_xmlubl;
    @XmlTransient
    @Column(name = "bl_urlxmlubl", length = 4000)
    private String bl_urlxmlubl;
    @XmlTransient
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_cdr")
    private byte[] bl_cdr;
    @XmlTransient
    @Column(name = "bl_urlcdr", length = 4000)
    private String bl_urlcdr;
    @XmlTransient
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "bl_xml")
    private byte[] bl_xml;

    @Override
    public String getBl_firma() {
        return bl_firma;
    }

    @Override
    public void setBl_firma(String bl_firma) {
        this.bl_firma = bl_firma;
    }

    @Override
    public String getBl_hashFirma() {
        return bl_hashFirma;
    }

    @Override
    public void setBl_hashFirma(String bl_hashFirma) {
        this.bl_hashFirma = bl_hashFirma;
    }

    @Override
    public String getBl_estadoProceso() {
        return bl_estadoProceso;
    }

    @Override
    public void setBl_estadoProceso(String bl_estadoProceso) {
        this.bl_estadoProceso = bl_estadoProceso;
    }

    @Override
    public String getBl_estadoRegistro() {
        return bl_estadoRegistro;
    }

    @Override
    public void setBl_estadoRegistro(String bl_estadoRegistro) {
        this.bl_estadoRegistro = bl_estadoRegistro;
    }

    @Override
    public String getBl_mensaje() {
        return bl_mensaje;
    }

    @Override
    public void setBl_mensaje(String bl_mensaje) {
        this.bl_mensaje = bl_mensaje;
    }

    @Override
    public String getBl_mensajeSunat() {
        return bl_mensajeSunat;
    }

    @Override
    public void setBl_mensajeSunat(String bl_mensajeSunat) {
        this.bl_mensajeSunat = bl_mensajeSunat;
    }

    @Override
    public byte[] getBl_pdf() {
        return bl_pdf;
    }

    @Override
    public void setBl_pdf(byte[] bl_pdf) {
        this.bl_pdf = bl_pdf;
    }

    @Override
    public String getBl_urlpdf() {
        return bl_urlpdf;
    }

    @Override
    public void setBl_urlpdf(String bl_urlpdf) {
        this.bl_urlpdf = bl_urlpdf;
    }

    @Override
    public byte[] getBl_xmlubl() {
        return bl_xmlubl;
    }

    @Override
    public void setBl_xmlubl(byte[] bl_xmlubl) {
        this.bl_xmlubl = bl_xmlubl;
    }

    @Override
    public String getBl_urlxmlubl() {
        return bl_urlxmlubl;
    }

    @Override
    public void setBl_urlxmlubl(String bl_urlxmlubl) {
        this.bl_urlxmlubl = bl_urlxmlubl;
    }

    @Override
    public byte[] getBl_cdr() {
        return bl_cdr;
    }

    @Override
    public void setBl_cdr(byte[] bl_cdr) {
        this.bl_cdr = bl_cdr;
    }

    @Override
    public String getBl_urlcdr() {
        return bl_urlcdr;
    }

    @Override
    public void setBl_urlcdr(String bl_urlcdr) {
        this.bl_urlcdr = bl_urlcdr;
    }

    @Override
    public byte[] getBl_xml() {
        return bl_xml;
    }

    @Override
    public void setBl_xml(byte[] bl_xml) {
        this.bl_xml = bl_xml;
    }
}
