/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "summary",
    "status",
    "statusDescription",
    "messages"
})
public class CommonBody {

    private SummaryResponse summary;
    private String status;
    private String statusDescription;
    private List<ResponseMessage> messages;

    public CommonBody() {
    }

    public CommonBody(SummaryResponse summary, String status, String statusDescription, List<ResponseMessage> messages) {
        this.summary = summary;
        this.status = status;
        this.statusDescription = statusDescription;
        this.messages = messages;
    }

    @XmlElement(name = "summaryResult")
    public SummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(SummaryResponse summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name = "descriptionStatus")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @XmlElement(nillable = true)
    public List<ResponseMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ResponseMessage> messages) {
        this.messages = messages;
    }

}
