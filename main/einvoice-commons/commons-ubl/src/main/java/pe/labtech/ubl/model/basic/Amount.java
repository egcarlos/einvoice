/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.basic;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
* Clase Amount.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class Amount {

    @XmlAttribute
    private String currencyID;

    @XmlValue
    private BigDecimal value;

    public Amount() {
    }

    public Amount(String currencyID, BigDecimal value) {
        this.currencyID = currencyID;
        this.value = value;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
