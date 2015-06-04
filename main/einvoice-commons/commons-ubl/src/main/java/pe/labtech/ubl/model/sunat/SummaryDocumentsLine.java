/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.sunat;

import java.util.LinkedList;
import java.util.List;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import static pe.labtech.ubl.model.Namespaces.CAC;
import static pe.labtech.ubl.model.Namespaces.CBC;
import static pe.labtech.ubl.model.Namespaces.SAC;
import pe.labtech.ubl.model.aggregate.AllowanceCharge;
import pe.labtech.ubl.model.aggregate.TaxTotal;
import pe.labtech.ubl.model.basic.Amount;

/**
 *
 * @author Carlos Echeverria
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "LineID",
    "DocumentTypeCode",
    "DocumentSerialID",
    "StartDocumentNumberID",
    "EndDocumentNumberID",
    "TotalAmount",
    "BillingPayment",
    "AllowanceCharge",
    "TaxTotal"
})
public class SummaryDocumentsLine {

    @XmlElement(namespace = CBC)
    private String LineID;
    @XmlElement(namespace = CBC)
    private String DocumentTypeCode;
    @XmlElement(namespace = SAC)
    private String DocumentSerialID;
    @XmlElement(namespace = SAC)
    private String StartDocumentNumberID;
    @XmlElement(namespace = SAC)
    private String EndDocumentNumberID;
    @XmlElement(namespace = SAC)
    private Amount TotalAmount;
    @XmlElement(namespace = SAC)
    private List<BillingPayment> BillingPayment;
    @XmlElement(namespace = CAC)
    private List<AllowanceCharge> AllowanceCharge;
    @XmlElement(namespace = CAC)
    private List<TaxTotal> TaxTotal;

    {
        BillingPayment = new LinkedList<>();
        AllowanceCharge = new LinkedList<>();
        TaxTotal = new LinkedList<>();
    }

    public SummaryDocumentsLine() {
    }

    public String getLineID() {
        return LineID;
    }

    public void setLineID(String LineID) {
        this.LineID = LineID;
    }

    public String getDocumentTypeCode() {
        return DocumentTypeCode;
    }

    public void setDocumentTypeCode(String DocumentTypeCode) {
        this.DocumentTypeCode = DocumentTypeCode;
    }

    public String getDocumentSerialID() {
        return DocumentSerialID;
    }

    public void setDocumentSerialID(String DocumentSerialID) {
        this.DocumentSerialID = DocumentSerialID;
    }

    public String getStartDocumentNumberID() {
        return StartDocumentNumberID;
    }

    public void setStartDocumentNumberID(String StartDocumentNumberID) {
        this.StartDocumentNumberID = StartDocumentNumberID;
    }

    public String getEndDocumentNumberID() {
        return EndDocumentNumberID;
    }

    public void setEndDocumentNumberID(String EndDocumentNumberID) {
        this.EndDocumentNumberID = EndDocumentNumberID;
    }

    public Amount getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Amount TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public List<BillingPayment> getBillingPayment() {
        return BillingPayment;
    }

    public void setBillingPayment(List<BillingPayment> BillingPayment) {
        this.BillingPayment = BillingPayment;
    }

    public List<AllowanceCharge> getAllowanceCharge() {
        return AllowanceCharge;
    }

    public void setAllowanceCharge(List<AllowanceCharge> AllowanceCharge) {
        this.AllowanceCharge = AllowanceCharge;
    }

    public List<TaxTotal> getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(List<TaxTotal> TaxTotal) {
        this.TaxTotal = TaxTotal;
    }

}
