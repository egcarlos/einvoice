/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
* Clase NumberAdapter.
*
* @author Labtech S.R.L. (info@labtech.pe)
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
