/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.ubl.model;

import org.eclipse.persistence.oxm.NamespacePrefixMapper;
import static pe.labtech.ubl.model.Namespaces.CAC;
import static pe.labtech.ubl.model.Namespaces.CBC;
import static pe.labtech.ubl.model.Namespaces.CREDIT;
import static pe.labtech.ubl.model.Namespaces.DS;
import static pe.labtech.ubl.model.Namespaces.EXT;
import static pe.labtech.ubl.model.Namespaces.QDT;
import static pe.labtech.ubl.model.Namespaces.SAC;

/**
* Clase CreditNotePrefixMapper.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class CreditNotePrefixMapper extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
        switch (uri) {
            case CREDIT:
                return "";
            case CAC:
                return "cac";
            case CBC:
                return "cbc";
            case DS:
                return "ds";
            case EXT:
                return "ext";
            case QDT:
                return "qdt";
            case SAC:
                return "sac";
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{
            CREDIT,
            CAC,
            CBC,
            DS,
            EXT,
            QDT,
            SAC
        };
    }

}
