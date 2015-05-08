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
            case Namespaces.CBC:
                return "cbc";
            case Namespaces.CAC:
                return "cac";
            case Namespaces.EXT:
                return "ext";
            case Namespaces.SAC:
                return "sac";
            case Namespaces.NS10:
                return "ns10";
            case Namespaces.NS11:
                return "ns11";
            case Namespaces.NS6:
                return "ns6";
            case Namespaces.NS7:
                return "ns7";
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
            Namespaces.DEFAULT,
            Namespaces.CBC,
            Namespaces.CAC,
            Namespaces.EXT,
            Namespaces.SAC,
            Namespaces.NS10,
            Namespaces.NS11,
            Namespaces.NS6,
            Namespaces.NS7,
            Namespaces.QDT,
            Namespaces.DS
        };
    }

}
