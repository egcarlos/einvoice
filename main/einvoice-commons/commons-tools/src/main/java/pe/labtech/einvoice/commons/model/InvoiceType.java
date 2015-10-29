/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.model;

/**
* Clase InvoiceType.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public enum InvoiceType {

    Document(
            "pe.labtech.einvoice.replicator.entity.DocumentHeader",
            "pe.labtech.einvoice.replicator.entity.DocumentHeaderPK",
            "DocumentHeader",
            "^[FB].*$"
    ),
    Summary(
            "pe.labtech.einvoice.replicator.entity.SummaryHeader",
            "pe.labtech.einvoice.replicator.entity.SummaryHeaderPK",
            "SummaryHeader",
            "^RA.*$"
    ),
    Cancel(
            "pe.labtech.einvoice.replicator.entity.CancelHeader",
            "pe.labtech.einvoice.replicator.entity.CancelHeaderPK",
            "CancelHeader",
            "^RC.*$"
    );

    public final String className;
    public final String idClassName;
    public final String entity;
    public final String regex;

    private InvoiceType(String className, String idClassName, String entity, String regex) {
        this.className = className;
        this.idClassName = idClassName;
        this.entity = entity;
        this.regex = regex;
    }

    public boolean isThisType(String documentNumber) {
        return documentNumber == null ? false : documentNumber.trim().matches(regex);
    }

    public static final InvoiceType getType(String documentNumber) {
        if (Document.isThisType(documentNumber)) {
            return Document;
        } else if (Summary.isThisType(documentNumber)) {
            return Summary;
        } else if (Cancel.isThisType(documentNumber)) {
            return Cancel;
        } else {
            return null;
        }
    }

}
