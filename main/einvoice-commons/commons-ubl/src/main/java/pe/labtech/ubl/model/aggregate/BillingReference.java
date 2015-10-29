/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase BillingReference.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class BillingReference {

    @XmlElement(namespace = Namespaces.CAC)
    private List<InvoiceDocumentReference> InvoiceDocumentReference;

    public BillingReference() {
    }

    public List<InvoiceDocumentReference> getInvoiceDocumentReference() {
        if (InvoiceDocumentReference == null) {
            InvoiceDocumentReference = new LinkedList<>();
        }
        return InvoiceDocumentReference;
    }

    public void setInvoiceDocumentReference(List<InvoiceDocumentReference> InvoiceDocumentReference) {
        this.InvoiceDocumentReference = InvoiceDocumentReference;
    }

}
