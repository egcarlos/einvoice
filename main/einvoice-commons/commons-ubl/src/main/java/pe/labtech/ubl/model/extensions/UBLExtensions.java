/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model.extensions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import pe.labtech.ubl.model.Namespaces;

/**
* Clase UBLExtensions.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class UBLExtensions {

    @XmlElement(namespace = Namespaces.EXT)
    private List<UBLExtension> UBLExtension;

    public UBLExtensions() {
    }

    public UBLExtensions(UBLExtension... extensions) {
        this();
        this.UBLExtension = new LinkedList<>();
        this.UBLExtension.addAll(Arrays.asList(extensions));
    }

    public UBLExtensions(List<UBLExtension> UBLExtension) {
        this.UBLExtension = new LinkedList<>();
        if (UBLExtension != null) {
            this.UBLExtension.addAll(UBLExtension);
        }
    }

    public List<UBLExtension> getUBLExtension() {
        if (UBLExtension == null) {
            UBLExtension = new LinkedList<>();
        }
        return UBLExtension;
    }

    public void setUBLExtension(List<UBLExtension> UBLExtension) {
        this.UBLExtension = UBLExtension;
    }

}
