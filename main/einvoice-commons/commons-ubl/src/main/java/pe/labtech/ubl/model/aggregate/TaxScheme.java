/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.aggregate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase TaxScheme.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class TaxScheme {

    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String Name;

    @XmlElement(namespace = Namespaces.CBC)
    private String TaxTypeCode;

    public TaxScheme() {
    }

    public TaxScheme(String ID, String Name, String TaxTypeCode) {
        this.ID = ID;
        this.Name = Name;
        this.TaxTypeCode = TaxTypeCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTaxTypeCode() {
        return TaxTypeCode;
    }

    public void setTaxTypeCode(String TaxTypeCode) {
        this.TaxTypeCode = TaxTypeCode;
    }

}
