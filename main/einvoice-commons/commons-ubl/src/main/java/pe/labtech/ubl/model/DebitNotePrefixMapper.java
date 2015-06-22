/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model;

import org.eclipse.persistence.oxm.NamespacePrefixMapper;
import static pe.labtech.ubl.model.Namespaces.CAC;
import static pe.labtech.ubl.model.Namespaces.CBC;
import static pe.labtech.ubl.model.Namespaces.DEBIT;
import static pe.labtech.ubl.model.Namespaces.DS;
import static pe.labtech.ubl.model.Namespaces.EXT;
import static pe.labtech.ubl.model.Namespaces.QDT;
import static pe.labtech.ubl.model.Namespaces.SAC;

/**
 *
 * @author carloseg
 */
public class DebitNotePrefixMapper extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
        switch (uri) {
            case DEBIT:
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
            DEBIT,
            CAC,
            CBC,
            DS,
            EXT,
            QDT,
            SAC
        };
    }

}
