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
* Clase PartyLegalEntity.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class PartyLegalEntity {

    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String RegistrationName;

    public PartyLegalEntity() {
    }

    public PartyLegalEntity(String RegistrationName) {
        this.RegistrationName = RegistrationName;
    }

    public String getRegistrationName() {
        return RegistrationName;
    }

    public void setRegistrationName(String RegistrationName) {
        this.RegistrationName = RegistrationName;
    }

}
