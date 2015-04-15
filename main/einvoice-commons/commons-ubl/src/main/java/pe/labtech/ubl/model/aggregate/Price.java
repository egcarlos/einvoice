/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.ubl.model.aggregate;

import java.math.BigDecimal;
import pe.labtech.ubl.model.basic.Amount;

/**
 *
 * @author carloseg
 */
public class Price {

    private Amount PriceAmount;

    public Price() {
    }

    public Price(String currencyID, BigDecimal value) {
        this.PriceAmount = new Amount(currencyID, value);
    }

    public Amount getPriceAmount() {
        return PriceAmount;
    }

    public void setPriceAmount(Amount PriceAmount) {
        this.PriceAmount = PriceAmount;
    }

}
