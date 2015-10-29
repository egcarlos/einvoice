/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model;

import java.util.LinkedList;
import java.util.List;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import static pe.labtech.ubl.model.Namespaces.CAC;
import static pe.labtech.ubl.model.Namespaces.CBC;
import static pe.labtech.ubl.model.Namespaces.EXT;
import static pe.labtech.ubl.model.Namespaces.SAC;
import static pe.labtech.ubl.model.Namespaces.SD;
import pe.labtech.ubl.model.aggregate.AccountingParty;
import pe.labtech.ubl.model.aggregate.Signature;
import pe.labtech.ubl.model.extensions.UBLExtensions;
import pe.labtech.ubl.model.sunat.SummaryDocumentsLine;

/**
* Clase SummaryDocuments.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlRootElement(name = "SummaryDocuments", namespace = SD)
@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "UBLExtensions",
    "UBLVersionID",
    "CustomizationID",
    "ID",
    "ReferenceDate",
    "IssueDate",
    "Signature",
    "AccountingSupplierParty",
    "SummaryDocumentsLine"
})
public class SummaryDocuments {

    @XmlElement(namespace = EXT)
    private UBLExtensions UBLExtensions;

    //Siempre 2.0
    @XmlElement(namespace = CBC)
    private String UBLVersionID;

    //Siempre 1.0
    @XmlElement(namespace = CBC)
    private String CustomizationID;

    //mapea a serie numero
    @XmlElement(namespace = CBC)
    private String ID;

    //mapea a fecha de emision en GMT-5
    @XmlElement(namespace = CBC)
    private String ReferenceDate;

    //mapea a fecha de emision en GMT-5
    @XmlElement(namespace = CBC)
    private String IssueDate;

    //datos de la firma digital
    @XmlElement(namespace = CAC)
    private Signature Signature;

    //datos el emisor
    @XmlElement(namespace = CAC)
    private AccountingParty AccountingSupplierParty;

    @XmlElement(namespace = SAC)
    private List<SummaryDocumentsLine> SummaryDocumentsLine;

    {
        this.SummaryDocumentsLine = new LinkedList<>();
    }

    public SummaryDocuments() {
    }

    public UBLExtensions getUBLExtensions() {
        return UBLExtensions;
    }

    public void setUBLExtensions(UBLExtensions UBLExtensions) {
        this.UBLExtensions = UBLExtensions;
    }

    public String getUBLVersionID() {
        return UBLVersionID;
    }

    public void setUBLVersionID(String UBLVersionID) {
        this.UBLVersionID = UBLVersionID;
    }

    public String getCustomizationID() {
        return CustomizationID;
    }

    public void setCustomizationID(String CustomizationID) {
        this.CustomizationID = CustomizationID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReferenceDate() {
        return ReferenceDate;
    }

    public void setReferenceDate(String ReferenceDate) {
        this.ReferenceDate = ReferenceDate;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String IssueDate) {
        this.IssueDate = IssueDate;
    }

    public Signature getSignature() {
        return Signature;
    }

    public void setSignature(Signature Signature) {
        this.Signature = Signature;
    }

    public AccountingParty getAccountingSupplierParty() {
        return AccountingSupplierParty;
    }

    public void setAccountingSupplierParty(AccountingParty AccountingSupplierParty) {
        this.AccountingSupplierParty = AccountingSupplierParty;
    }

    public List<SummaryDocumentsLine> getSummaryDocumentsLine() {
        return SummaryDocumentsLine;
    }

    public void setSummaryDocumentsLine(List<SummaryDocumentsLine> SummaryDocumentsLine) {
        this.SummaryDocumentsLine = SummaryDocumentsLine;
    }

}
