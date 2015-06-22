/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Carlos Echeverria
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = {
    "ID",
    "DebitedQuantity",
    "LineExtensionAmount",
    "PricingReference",
    "TaxTotal",
    "Item",
    "Price"
})
public class DebitNoteLine {

    /**
     * Secuencia de la linea
     */
    @XmlElement(namespace = CBC)
    private String ID;

    /**
     * Cantidad de articulos vendidos
     */
    @XmlElement(namespace = CBC)
    private Quantity DebitedQuantity;

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

    public DebitNoteLine() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Quantity getDebitedQuantity() {
        return DebitedQuantity;
    }

    public void setDebitedQuantity(Quantity DebitedQuantity) {
        this.DebitedQuantity = DebitedQuantity;
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
