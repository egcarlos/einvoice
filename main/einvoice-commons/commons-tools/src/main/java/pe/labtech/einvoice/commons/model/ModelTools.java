/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.model;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
* Clase ModelTools.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class ModelTools {

    public static Object getTargetId(
            String documentNumber
    ) {
        InvoiceType targetEntity = InvoiceType.getType(documentNumber);
        try {
            return Class.forName(targetEntity.idClassName).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            return null;
        }
    }

    public static void initId(
            Object id,
            String clientId,
            String documentType,
            String documentNumber
    ) {
        InvoiceType targetEntity = InvoiceType.getType(documentNumber);
        try {
            PropertyUtils.setProperty(id, "tipoDocumentoEmisor", clientId.split("-")[0]);
            PropertyUtils.setProperty(id, "numeroDocumentoEmisor", clientId.split("-")[1]);
            if (targetEntity == InvoiceType.Document) {
                PropertyUtils.setProperty(id, "tipoDocumento", documentType);
                PropertyUtils.setProperty(id, "serieNumero", documentNumber);
            } else {
                PropertyUtils.setProperty(id, "resumenId", documentNumber);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(ModelTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Maps names of binary fields.
     *
     * @param name name in internal model
     * @return name in external model
     */
    public static String mapDataName(String name) {
        switch (name) {
            case "pdfFileUrl":
                return "bl_pdf";
            case "xmlFileSignUrl":
                return "bl_xmlubl";
            case "xmlFileSunatUrl":
                return "bl_cdr";
            case "xmlText":
                return "bl_xml";
            case "signXml":
                return "bl_xml";
        }
        return null;
    }

    /**
     * Maps names of response fields.
     *
     * @param name name in internal model
     * @return name in external model
     */
    public static String mapResponseName(String name) {
        switch (name) {
            case "hashCode":
                return "bl_hashFirma";
            case "integratedStatus":
                return "bl_estadoProceso";
            case "recordStatus":
                return "bl_estadoRegistro";
            case "pdfFileUrl":
                return "bl_urlpdf";
            case "signatureValue":
                return "bl_firma";
            case "xmlFileSignUrl":
                return "bl_urlxmlubl";
            case "xmlFileSunatUrl":
                return "bl_urlcdr";
            case "sunatMessage":
                return "bl_mensajeSunat";
            case "messages":
                return "bl_mensaje";
        }
        return null;
    }

}
