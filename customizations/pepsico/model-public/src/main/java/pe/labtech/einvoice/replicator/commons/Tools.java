/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.commons;

import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author carloseg
 */
public class Tools {

    public static Object getTargetId(String targetEntity, String clientId, String documentType, String documentNumber) {
        Object id;
        switch (targetEntity) {
            case "DocumentHeader":
                //TODO caso especial el query es por datos asociados
                id = new DocumentHeaderPK();
                break;
            default:
                id = null;
        }
        return id;
    }

    public static String getTargetEntity(String documentNumber) {
        if (isInvoice(documentNumber)) {
            return "DocumentHeader";
        } else if (isInvoiceSummary(documentNumber)) {
            return "SummaryHeader";
        } else if (isCancelSummary(documentNumber)) {
            return "CancelHeader";
        } else {
            return "ERROR_CONSTANT";
        }
    }

    public static boolean isCancelSummary(String documentNumber) {
        return documentNumber.startsWith("RA");
    }

    public static boolean isInvoiceSummary(String documentNumber) {
        return documentNumber.startsWith("RC");
    }

    public static boolean isInvoice(String documentNumber) {
        return documentNumber.startsWith("F") | documentNumber.startsWith("B");
    }

    /**
     * Campos usados para la replicación de datos de respuesta.
     *
     * @param name nombre del atributo en la base de datos
     * @return nombre del campo en la base de datos
     */
    public static String mapResponseName(String name) {
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
}
