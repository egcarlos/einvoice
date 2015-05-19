/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.response;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "total",
    "totalError",
    "totalQueued",
    "totalSign"
})
public class SummaryResponse {

    private Integer total;
    private Integer totalError;
    private Integer totalQueued;
    private Integer totalSign;

    public SummaryResponse() {
    }

    public SummaryResponse(Integer total, Integer totalError, Integer totalQueued, Integer totalSign) {
        this.total = total;
        this.totalError = totalError;
        this.totalQueued = totalQueued;
        this.totalSign = totalSign;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalError() {
        return totalError;
    }

    public void setTotalError(Integer totalError) {
        this.totalError = totalError;
    }

    public Integer getTotalQueued() {
        return totalQueued;
    }

    public void setTotalQueued(Integer totalQueued) {
        this.totalQueued = totalQueued;
    }

    public Integer getTotalSign() {
        return totalSign;
    }

    public void setTotalSign(Integer totalSign) {
        this.totalSign = totalSign;
    }

}
