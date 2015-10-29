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
* Clase Address.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

    /**
     * UBIGEO.
     */
    @XmlElement(namespace = Namespaces.CBC)
    private String ID;

    /**
     * Direccion.
     */
    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String StreetName;

    /**
     * Urbanizacion/Zona.
     */
    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String CitySubdivisionName;

    /**
     * Departamento.
     */
    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String CityName;

    /**
     * Provincia.
     */
    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String CountrySubentity;

    /**
     * Distrito.
     */
    @XmlElement(namespace = Namespaces.CBC)
    @XmlCDATA
    private String District;

    /**
     * Pais (codigo).
     */
    @XmlElement(namespace = Namespaces.CAC)
    private Country Country;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getCitySubdivisionName() {
        return CitySubdivisionName;
    }

    public void setCitySubdivisionName(String CitySubdivisionName) {
        this.CitySubdivisionName = CitySubdivisionName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getCountrySubentity() {
        return CountrySubentity;
    }

    public void setCountrySubentity(String CountrySubentity) {
        this.CountrySubentity = CountrySubentity;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public Country getCountry() {
        return Country;
    }

    public void setCountry(Country Country) {
        this.Country = Country;
    }

}
