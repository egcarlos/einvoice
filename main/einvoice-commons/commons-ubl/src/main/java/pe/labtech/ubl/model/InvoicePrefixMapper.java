/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model;

import org.eclipse.persistence.oxm.NamespacePrefixMapper;

/**
 *
 * @author carloseg
 */
public class InvoicePrefixMapper extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
        switch (uri) {
            case Namespaces.DEFAULT:
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
            Namespaces.DEFAULT,
            Namespaces.CAC,
            Namespaces.CBC,
            Namespaces.DS,
            Namespaces.EXT,
            Namespaces.QDT,
            Namespaces.SAC
        };
    }

}
