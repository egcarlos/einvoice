/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.response;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "statusCode",
    "statusDescription",
    "detailCode",
    "detailDescription",
    "detailList"
})
public class ResponseMessage {

    private String statusCode;
    private String statusDescription;
    private String detailCode;
    private String detailDescription;
    private String detailList;

    public ResponseMessage() {
    }

    public ResponseMessage(String statusCode, String statusDescription, String detailCode, String detailDescription, String detailList) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.detailCode = detailCode;
        this.detailDescription = detailDescription;
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        BiFunction nvl = (v, n) -> v == null ? n : v;
        return MessageFormat.format(
                "{0}.{2}-> {1}: {3} ({4})",
                nvl.apply(statusCode, "-"),
                nvl.apply(statusDescription, "-"),
                nvl.apply(detailCode, "N/D"),
                nvl.apply(detailDescription, "N/D"),
                nvl.apply(detailList, "-")
        );
    }

    @XmlElement(name = "codeStatus")
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @XmlElement(name = "descriptionStatus")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @XmlElement(name = "codeDetail")
    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    @XmlElement(name = "descriptionDetail")
    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    @XmlElement(name = "listDetail")
    public String getDetailList() {
        return detailList;
    }

    public void setDetailList(String detailList) {
        this.detailList = detailList;
    }

}
