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
public class Number2Adapter extends XmlAdapter<String, BigDecimal> {

    @Override
    public BigDecimal unmarshal(String result) {
        int p = result.indexOf(".");
        return new BigDecimal(result.substring(0, p + 3));
    }

    @Override
    public String marshal(BigDecimal v) {
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        NumberFormat nf = new DecimalFormat("###########0.00#", unusualSymbols);
        String result = nf.format(v);
        int p = result.indexOf(".");
        return result.substring(0, p + 3);
    }
}
