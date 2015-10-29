/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import static pe.labtech.ubl.model.Namespaces.CBC;

/**
* Clase OrderReference.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(FIELD)
public class OrderReference {

    @XmlElement(namespace = CBC)
    private String ID;
    @XmlElement(namespace = CBC)
    private String SalesOrderID;
    @XmlElement(namespace = CBC)
    private String CopyIndicatorID;
    @XmlElement(namespace = CBC)
    private String UUID;
    @XmlElement(namespace = CBC)
    private String IssueDate;
    @XmlElement(namespace = CBC)
    private String IssueTime;
    @XmlElement(namespace = CBC)
    private String CustomerReference;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSalesOrderID() {
        return SalesOrderID;
    }

    public void setSalesOrderID(String SalesOrderID) {
        this.SalesOrderID = SalesOrderID;
    }

    public String getCopyIndicatorID() {
        return CopyIndicatorID;
    }

    public void setCopyIndicatorID(String CopyIndicatorID) {
        this.CopyIndicatorID = CopyIndicatorID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String IssueDate) {
        this.IssueDate = IssueDate;
    }

    public String getIssueTime() {
        return IssueTime;
    }

    public void setIssueTime(String IssueTime) {
        this.IssueTime = IssueTime;
    }

    public String getCustomerReference() {
        return CustomerReference;
    }

    public void setCustomerReference(String CustomerReference) {
        this.CustomerReference = CustomerReference;
    }

}
