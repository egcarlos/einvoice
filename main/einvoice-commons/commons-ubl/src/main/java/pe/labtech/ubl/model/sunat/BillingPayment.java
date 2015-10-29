/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.sunat;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import static pe.labtech.ubl.model.Namespaces.CBC;
import pe.labtech.ubl.model.basic.Amount;

/**
* Clase BillingPayment.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "PaidAmount",
    "InstructionID"
})
public class BillingPayment {

    @XmlElement(namespace = CBC)
    private Amount PaidAmount;
    @XmlElement(namespace = CBC)
    private String InstructionID;

    public BillingPayment() {
    }

    public BillingPayment(Amount PaidAmount, String InstructionID) {
        this.PaidAmount = PaidAmount;
        this.InstructionID = InstructionID;
    }

    public Amount getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(Amount PaidAmount) {
        this.PaidAmount = PaidAmount;
    }

    public String getInstructionID() {
        return InstructionID;
    }

    public void setInstructionID(String InstructionID) {
        this.InstructionID = InstructionID;
    }

}
