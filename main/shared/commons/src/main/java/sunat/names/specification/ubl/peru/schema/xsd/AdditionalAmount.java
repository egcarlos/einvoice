/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sunat.names.specification.ubl.peru.schema.xsd;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import un.unece.uncefact.codelist.specification._54217._2001.CurrencyCodeContentType;

/**
 *
 * @author Carlos
 */
public class AdditionalAmount {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private IDType id;
    @XmlElement(name = "PayableAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private PayableAmountType amount;

    public AdditionalAmount() {
    }

    public AdditionalAmount(String id, String currency, BigDecimal value) {
        this.id = new IDType();
        this.id.setValue(id);

        this.amount = new PayableAmountType();
        this.amount.setCurrencyID(CurrencyCodeContentType.valueOf(currency));
        this.amount.setValue(value);
    }

}
