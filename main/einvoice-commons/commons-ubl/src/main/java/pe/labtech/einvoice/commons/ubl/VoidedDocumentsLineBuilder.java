/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ubl;

import pe.labtech.ubl.model.sunat.VoidedDocumentsLine;

/**
 *
 * @author Carlos Echeverria
 */
public class VoidedDocumentsLineBuilder implements Builder<VoidedDocumentsLine> {

    private VoidedDocumentsLine item;

    public VoidedDocumentsLineBuilder init(
            Long lineNumber,
            String documentType,
            String documentSerial,
            String number,
            String reason
    ) {
        this.item = new VoidedDocumentsLine();
        this.item.setLineID(lineNumber);
        this.item.setDocumentTypeCode(documentType);
        this.item.setDocumentSerialID(documentSerial);
        this.item.setDocumentNumberID(number);
        this.item.setVoidedReasonDescription(reason);
        return this;
    }

    @Override
    public VoidedDocumentsLine compile() {
        return item;
    }

}
