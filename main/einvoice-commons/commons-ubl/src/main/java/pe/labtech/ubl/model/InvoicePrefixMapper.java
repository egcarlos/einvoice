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
            case Namespaces.DS:
                return "ds";
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{Namespaces.DS};
    }

}
