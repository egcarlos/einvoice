/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.extensions;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
 *
 * @author carloseg
 */
public class UBLExtensions {

    @XmlElement(namespace = Namespaces.EXT)
    private List<UBLExtension> UBLExtension;

    public List<UBLExtension> getUBLExtension() {
        return UBLExtension;
    }

    public void setUBLExtension(List<UBLExtension> UBLExtension) {
        this.UBLExtension = UBLExtension;
    }

}
