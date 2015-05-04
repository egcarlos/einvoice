/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.model;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 *
 * @author carloseg
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
