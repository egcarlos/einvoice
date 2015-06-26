/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replication.invoice.mappings;

import java.util.Map;

/**
 *
 * @author Carlos Echeverria
 */
public class Mapping {

    private Map<String, AuxiliarMapping> header;
    private Map<String, AuxiliarMapping> detail;

    public Map<String, AuxiliarMapping> getHeader() {
        return header;
    }

    public void setHeader(Map<String, AuxiliarMapping> header) {
        this.header = header;
    }

    public Map<String, AuxiliarMapping> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, AuxiliarMapping> detail) {
        this.detail = detail;
    }

}
