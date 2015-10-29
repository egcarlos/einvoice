/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;
import pe.labtech.ubl.model.basic.Amount;

/**
* Clase AlternativeConditionPrice.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class AlternativeConditionPrice {

    @XmlElement(namespace = Namespaces.CBC)
    private Amount PriceAmount;

    @XmlElement(namespace = Namespaces.CBC)
    private String PriceTypeCode;

    public AlternativeConditionPrice() {
    }

    public AlternativeConditionPrice(String PriceTypeCode, String currencyID, BigDecimal price) {
        this.PriceTypeCode = PriceTypeCode;
        this.PriceAmount = new Amount(currencyID, price);
    }

    public Amount getPriceAmount() {
        return PriceAmount;
    }

    public void setPriceAmount(Amount PriceAmount) {
        this.PriceAmount = PriceAmount;
    }

    public String getPriceTypeCode() {
        return PriceTypeCode;
    }

    public void setPriceTypeCode(String PriceTypeCode) {
        this.PriceTypeCode = PriceTypeCode;
    }

}
