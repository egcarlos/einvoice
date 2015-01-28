/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Raúl
 */
public class NumberAdapter extends XmlAdapter<String, BigDecimal> {

    @Override
    public BigDecimal unmarshal(String v) throws Exception {
        return new BigDecimal(v);
    }

    @Override
    public String marshal(BigDecimal v) throws Exception {
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        NumberFormat nf = new DecimalFormat("###########0.00############", unusualSymbols);

        return nf.format(v);
    }

}
