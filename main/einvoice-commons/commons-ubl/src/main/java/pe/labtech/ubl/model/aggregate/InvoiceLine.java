/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

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

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    @XmlElement(namespace = Namespaces.CBC)
    private Quantity InvoicedQuantity;

    @XmlElement(namespace = Namespaces.CBC)
    private Amount LineExtensionAmount;
    
    

}
