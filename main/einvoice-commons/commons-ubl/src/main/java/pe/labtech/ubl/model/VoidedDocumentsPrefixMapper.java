/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model;

import org.eclipse.persistence.oxm.NamespacePrefixMapper;

/**
* Clase VoidedDocumentsPrefixMapper.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class VoidedDocumentsPrefixMapper extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
        switch (uri) {
            case Namespaces.VD:
                return "";
            case Namespaces.CBC:
                return "cbc";
            case Namespaces.CAC:
                return "cac";
            case Namespaces.EXT:
                return "ext";
            case Namespaces.SAC:
                return "sac";
            case Namespaces.QDT:
                return "qdt";
            case Namespaces.DS:
                return "ds";
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{
            Namespaces.VD,
            Namespaces.CBC,
            Namespaces.CAC,
            Namespaces.EXT,
            Namespaces.SAC,
            Namespaces.QDT,
            Namespaces.DS
        };
    }

}
