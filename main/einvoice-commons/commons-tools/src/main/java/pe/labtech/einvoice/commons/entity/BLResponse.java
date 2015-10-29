/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.entity;

/**
* Clase BLResponse.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public interface BLResponse {

    /**
     * Firma del documento.
     *
     * @return
     */
    String getBl_firma();

    /**
     * Firma del documento.
     *
     * @param bl_firma
     */
    void setBl_firma(String bl_firma);

    /**
     * Hash de la firma.
     *
     * @return
     */
    String getBl_hashFirma();

    /**
     * Hash de la firma.
     *
     * @param bl_hashFirma
     */
    void setBl_hashFirma(String bl_hashFirma);

    /**
     * El campo estado del proceso es la construcción de tres valores de estado
     * en formato de sepración por "/". La información es
     * (estado)/(estadoDocumento)/(estadoSunat).
     *
     * @return
     */
    String getBl_estadoProceso();

    /**
     * Estado del proceso.
     *
     * @param bl_estadoProceso
     */
    void setBl_estadoProceso(String bl_estadoProceso);

    /**
     * El estado del registro es uno de los siguientes: N nuevo, A agregado para
     * proceso, L leido, P procesado, E con error.
     *
     * @return
     */
    String getBl_estadoRegistro();

    /**
     * Estado del registro.
     *
     * @param bl_estadoRegistro
     */
    void setBl_estadoRegistro(String bl_estadoRegistro);

    /**
     * Mensaje de la plataforma.
     *
     * @return
     */
    String getBl_mensaje();

    /**
     * Mensaje de la paltaforma.
     *
     * @param bl_mensaje
     */
    void setBl_mensaje(String bl_mensaje);

    /**
     * Mensaje de sunat.
     *
     * @return
     */
    String getBl_mensajeSunat();

    /**
     * Mensaje de sunat.
     *
     * @param bl_mensajeSunat
     */
    void setBl_mensajeSunat(String bl_mensajeSunat);

    /**
     * PDF de formato de impresión.
     *
     * @return
     */
    byte[] getBl_pdf();

    /**
     * PDF de formato de impresión.
     *
     * @param bl_pdf
     */
    void setBl_pdf(byte[] bl_pdf);

    /**
     * URL del PDF de formato de impresión.
     *
     * @return
     */
    String getBl_urlpdf();

    /**
     * URL del PDF de formato de impresión.
     *
     * @param bl_urlpdf
     */
    void setBl_urlpdf(String bl_urlpdf);

    byte[] getBl_xmlubl();

    void setBl_xmlubl(byte[] bl_xmlubl);

    String getBl_urlxmlubl();

    void setBl_urlxmlubl(String bl_urlxmlubl);

    byte[] getBl_cdr();

    void setBl_cdr(byte[] bl_cdr);

    String getBl_urlcdr();

    void setBl_urlcdr(String bl_urlcdr);

    byte[] getBl_xml();

    void setBl_xml(byte[] bl_xml);

    /**
     * Limpia los campos de la respuesta y establece el valor inicial del estado
     * registro en "N"
     *
     * @param content
     */
    public static void configureForCreate(BLResponse content) {
        content.setBl_estadoRegistro("N");
        content.setBl_cdr(null);
        content.setBl_estadoProceso(null);
        content.setBl_estadoRegistro(null);
        content.setBl_firma(null);
        content.setBl_hashFirma(null);
        content.setBl_mensaje(null);
        content.setBl_mensajeSunat(null);
        content.setBl_pdf(null);
        content.setBl_urlcdr(null);
        content.setBl_urlpdf(null);
        content.setBl_urlxmlubl(null);
        content.setBl_xml(null);
        content.setBl_xmlubl(null);

    }
}
