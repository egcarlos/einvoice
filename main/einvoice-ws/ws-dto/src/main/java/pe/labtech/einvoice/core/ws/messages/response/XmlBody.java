package pe.labtech.einvoice.core.ws.messages.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {
    "result",
    "documents"
})
public class XmlBody {

    private String result;
    private List<DocumentInfo> documents;

    public XmlBody() {
    }

    public XmlBody(String result, List<DocumentInfo> documents) {
        this.result = result;
        this.documents = documents;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @XmlElement(name = "document")
    public List<DocumentInfo> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentInfo> documents) {
        this.documents = documents;
    }

}
