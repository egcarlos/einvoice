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
import static pe.labtech.ubl.model.Namespaces.CREDIT;
import static pe.labtech.ubl.model.Namespaces.EXT;
import pe.labtech.ubl.model.aggregate.AccountingParty;
import pe.labtech.ubl.model.aggregate.BillingReference;
import pe.labtech.ubl.model.aggregate.CreditNoteLine;
import pe.labtech.ubl.model.aggregate.DiscrepancyResponse;
import pe.labtech.ubl.model.aggregate.DocumentReference;
import pe.labtech.ubl.model.aggregate.LegalMonetaryTotal;
import pe.labtech.ubl.model.aggregate.Signature;
import pe.labtech.ubl.model.aggregate.TaxTotal;
import pe.labtech.ubl.model.extensions.UBLExtensions;

/**
* Clase CreditNote.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlRootElement(name = "CreditNote", namespace = CREDIT)
@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "UBLExtensions",
    "UBLVersionID",
    "CustomizationID",
    "ID",
    "IssueDate",
    "DocumentCurrencyCode",
    "DiscrepancyResponse",
    "BillingReference",
    "DespatchDocumentReference",
    "AdditionalDocumentReference",
    "Signature",
    "AccountingSupplierParty",
    "AccountingCustomerParty",
    "TaxTotal",
    "LegalMonetaryTotal",
    "CreditNoteLine"
})
public class CreditNote {

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
    private String IssueDate;

    //mapea a moneda documento
    @XmlElement(namespace = CBC)
    private String DocumentCurrencyCode;

    //campos adicionales para la nota de crédito
    @XmlElement(namespace = CAC)
    private List<DiscrepancyResponse> DiscrepancyResponse;

    @XmlElement(namespace = CAC)
    private BillingReference BillingReference;

    //guías de remisión
    @XmlElement(namespace = CAC)
    private List<DocumentReference> DespatchDocumentReference;

    //otros documentos asociados
    @XmlElement(namespace = CAC)
    private List<DocumentReference> AdditionalDocumentReference;

    //datos de la firma digital
    @XmlElement(namespace = CAC)
    private Signature Signature;

    //datos el emisor
    @XmlElement(namespace = CAC)
    private AccountingParty AccountingSupplierParty;

    //datos del receptor
    @XmlElement(namespace = CAC)
    private AccountingParty AccountingCustomerParty;

    //impuestos
    @XmlElement(namespace = CAC)
    private List<TaxTotal> TaxTotal;

    @XmlElement(namespace = CAC)
    private LegalMonetaryTotal LegalMonetaryTotal;

    @XmlElement(namespace = CAC)
    private List<CreditNoteLine> CreditNoteLine;

    {
        this.AdditionalDocumentReference = new LinkedList<>();
        this.CreditNoteLine = new LinkedList<>();
        this.DespatchDocumentReference = new LinkedList<>();
        this.DiscrepancyResponse = new LinkedList<>();
        this.TaxTotal = new LinkedList<>();
    }

    public CreditNote() {
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

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String IssueDate) {
        this.IssueDate = IssueDate;
    }

    public String getDocumentCurrencyCode() {
        return DocumentCurrencyCode;
    }

    public void setDocumentCurrencyCode(String DocumentCurrencyCode) {
        this.DocumentCurrencyCode = DocumentCurrencyCode;
    }

    public List<DiscrepancyResponse> getDiscrepancyResponse() {
        return DiscrepancyResponse;
    }

    public void setDiscrepancyResponse(List<DiscrepancyResponse> DiscrepancyResponse) {
        this.DiscrepancyResponse = DiscrepancyResponse;
    }

    public BillingReference getBillingReference() {
        return BillingReference;
    }

    public void setBillingReference(BillingReference BillingReference) {
        this.BillingReference = BillingReference;
    }

    public List<DocumentReference> getDespatchDocumentReference() {
        return DespatchDocumentReference;
    }

    public void setDespatchDocumentReference(List<DocumentReference> DespatchDocumentReference) {
        this.DespatchDocumentReference = DespatchDocumentReference;
    }

    public List<DocumentReference> getAdditionalDocumentReference() {
        return AdditionalDocumentReference;
    }

    public void setAdditionalDocumentReference(List<DocumentReference> AdditionalDocumentReference) {
        this.AdditionalDocumentReference = AdditionalDocumentReference;
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

    public AccountingParty getAccountingCustomerParty() {
        return AccountingCustomerParty;
    }

    public void setAccountingCustomerParty(AccountingParty AccountingCustomerParty) {
        this.AccountingCustomerParty = AccountingCustomerParty;
    }

    public List<TaxTotal> getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(List<TaxTotal> TaxTotal) {
        this.TaxTotal = TaxTotal;
    }

    public LegalMonetaryTotal getLegalMonetaryTotal() {
        return LegalMonetaryTotal;
    }

    public void setLegalMonetaryTotal(LegalMonetaryTotal LegalMonetaryTotal) {
        this.LegalMonetaryTotal = LegalMonetaryTotal;
    }

    public List<CreditNoteLine> getCreditNoteLine() {
        return CreditNoteLine;
    }

    public void setCreditNoteLine(List<CreditNoteLine> CreditNoteLine) {
        this.CreditNoteLine = CreditNoteLine;
    }

}
