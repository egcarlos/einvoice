/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.basic.Amount;
import pe.labtech.ubl.model.basic.Quantity;

/**
 *
 * @author carloseg
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceLine {

    /**
     * Secuencia de la linea
     */
    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    /**
     * Cantidad de articulos vendidos
     */
    @XmlElement(namespace = Namespaces.CBC)
    private Quantity InvoicedQuantity;

    /**
     * Total de la linea sin impuestos.
     */
    @XmlElement(namespace = Namespaces.CBC)
    private Amount LineExtensionAmount;

    /**
     * Monto unitario con inpuesto
     */
    @XmlElement(namespace = Namespaces.CAC)
    private PricingReference PricingReference;

    /**
     * Impuestos de la linea.
     */
    @XmlElement(namespace = Namespaces.CAC)
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
    private Price PriceAmount;

    public InvoiceLine() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Quantity getInvoicedQuantity() {
        return InvoicedQuantity;
    }

    public void setInvoicedQuantity(Quantity InvoicedQuantity) {
        this.InvoicedQuantity = InvoicedQuantity;
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
        if (TaxTotal == null) {
            TaxTotal = new LinkedList<>();
        }
        return TaxTotal;
    }

    public void setTaxTotal(List<TaxTotal> taxTotal) {
        this.TaxTotal = taxTotal;
    }

    public Item getItem() {
        return Item;
    }

    public void setItem(Item Item) {
        this.Item = Item;
    }

    public Price getPriceAmount() {
        return PriceAmount;
    }

    public void setPriceAmount(Price PriceAmount) {
        this.PriceAmount = PriceAmount;
    }

}
