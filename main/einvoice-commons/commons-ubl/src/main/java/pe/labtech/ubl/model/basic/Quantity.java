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
* Clase Quantity.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class Quantity {

    @XmlAttribute
    private String unitCode;

    @XmlValue
    private BigDecimal value;

    public Quantity() {
    }

    public Quantity(String unitCode, BigDecimal value) {
        this.unitCode = unitCode;
        this.value = value;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
