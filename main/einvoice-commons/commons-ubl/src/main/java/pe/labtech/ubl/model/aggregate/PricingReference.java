/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase PricingReference.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class PricingReference {

    @XmlElement(namespace = Namespaces.CAC)
    private List<AlternativeConditionPrice> AlternativeConditionPrice;

    public PricingReference() {
    }

    public List<AlternativeConditionPrice> getAlternativeConditionPrice() {
        if (this.AlternativeConditionPrice == null) {
            this.AlternativeConditionPrice = new LinkedList<>();
        }
        return AlternativeConditionPrice;
    }

    public void setAlternativeConditionPrice(List<AlternativeConditionPrice> AlternativeConditionPrice) {
        this.AlternativeConditionPrice = AlternativeConditionPrice;
    }

}
