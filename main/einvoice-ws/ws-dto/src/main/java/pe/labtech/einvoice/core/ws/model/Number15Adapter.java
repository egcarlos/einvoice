package pe.labtech.einvoice.core.ws.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Number15Adapter extends XmlAdapter<String, BigDecimal> {

    @Override
    public BigDecimal unmarshal(String result) throws Exception {
        int p = result.indexOf(".");
        return new BigDecimal(result.substring(0, p + 3));
    }

    @Override
    public String marshal(BigDecimal v) throws Exception {
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        NumberFormat nf = new DecimalFormat("##############0.00#", unusualSymbols);
        String result = nf.format(v);
        int p = result.indexOf(".");
        return result.substring(0, p + 3);
    }
}
