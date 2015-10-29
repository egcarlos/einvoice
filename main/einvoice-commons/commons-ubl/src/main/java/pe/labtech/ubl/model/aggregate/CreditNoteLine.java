/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import java.util.LinkedList;
import java.util.List;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import pe.labtech.ubl.model.Namespaces;
import static pe.labtech.ubl.model.Namespaces.CAC;
import static pe.labtech.ubl.model.Namespaces.CBC;
import pe.labtech.ubl.model.basic.Amount;
import pe.labtech.ubl.model.basic.Quantity;

/**
* Clase CreditNoteLine.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "ID",
    "CreditedQuantity",
    "LineExtensionAmount",
    "PricingReference",
    "TaxTotal",
    "Item",
    "Price"
})
public class CreditNoteLine {

    /**
     * Secuencia de la linea
     */
    @XmlElement(namespace = CBC)
    private String ID;

    /**
     * Cantidad de articulos vendidos
     */
    @XmlElement(namespace = CBC)
    private Quantity CreditedQuantity;

    /**
     * Total de la linea sin impuestos.
     */
    @XmlElement(namespace = CBC)
    private Amount LineExtensionAmount;

    /**
     * Monto unitario con inpuesto
     */
    @XmlElement(namespace = CAC)
    private PricingReference PricingReference;

    /**
     * Impuestos de la linea.
     */
    @XmlElement(namespace = CAC)
    private List<TaxTotal> TaxTotal;

    /**
     * Identificacion del item. Codigo de producto y descripcion;
     */
    @XmlElement(namespace = Namespaces.CAC)
    private Item Item;

    /**
     * Precio unitario sin impuesto.
     */
    @XmlElement(namespace = Namespaces.CAC)
    private Price Price;

    {
        this.TaxTotal = new LinkedList<>();
    }

    public CreditNoteLine() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Quantity getCreditedQuantity() {
        return CreditedQuantity;
    }

    public void setCreditedQuantity(Quantity CreditedQuantity) {
        this.CreditedQuantity = CreditedQuantity;
    }

    public Amount getLineExtensionAmount() {
        return LineExtensionAmount;
    }

    public void setLineExtensionAmount(Amount LineExtensionAmount) {
        this.LineExtensionAmount = LineExtensionAmount;
    }

    public PricingReference getPricingReference() {
        return PricingReference;
    }

    public void setPricingReference(PricingReference PricingReference) {
        this.PricingReference = PricingReference;
    }

    public List<TaxTotal> getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(List<TaxTotal> TaxTotal) {
        this.TaxTotal = TaxTotal;
    }

    public Item getItem() {
        return Item;
    }

    public void setItem(Item Item) {
        this.Item = Item;
    }

    public Price getPrice() {
        return Price;
    }

    public void setPrice(Price Price) {
        this.Price = Price;
    }

}
