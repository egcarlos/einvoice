/*
* Producto elaborado para Alignet S.A.C.
*
 */
package pe.labtech.einvoice.commons2.ubl.model;

import org.eclipse.persistence.oxm.NamespacePrefixMapper;

/**
 * Clase CreditNotePrefixMapper.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 */
public class CreditNotePrefixMapper extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
        switch (uri) {
            case Namespaces.CREDIT:
                return "";
            case Namespaces.CAC:
                return "cac";
            case Namespaces.CBC:
                return "cbc";
            case Namespaces.DS:
                return "ds";
            case Namespaces.EXT:
                return "ext";
            case Namespaces.QDT:
                return "qdt";
            case Namespaces.SAC:
                return "sac";
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{
            Namespaces.CREDIT,
            Namespaces.CAC,
            Namespaces.CBC,
            Namespaces.DS,
            Namespaces.EXT,
            Namespaces.QDT,
            Namespaces.SAC
        };
    }

}
