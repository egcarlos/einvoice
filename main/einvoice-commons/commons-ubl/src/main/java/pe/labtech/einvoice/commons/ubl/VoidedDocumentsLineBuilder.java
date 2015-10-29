/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ubl;

import pe.labtech.ubl.model.sunat.VoidedDocumentsLine;

/**
* Clase VoidedDocumentsLineBuilder.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class VoidedDocumentsLineBuilder implements Builder<VoidedDocumentsLine> {

    private VoidedDocumentsLine item;

    public VoidedDocumentsLineBuilder init(
            String lineNumber,
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
        this.item.setVoidReasonDescription(reason);
        return this;
    }

    @Override
    public VoidedDocumentsLine compile() {
        return item;
    }

}
