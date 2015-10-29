package pe.labtech.einvoice.core.ws.messages.response;

import javax.xml.bind.annotation.XmlElement;

public class ResponseBody {

    private CommonBody common;
    private XmlBody xml;

    public ResponseBody() {
    }

    public ResponseBody(CommonBody common, XmlBody xml) {
        this.common = common;
        this.xml = xml;
    }

    @XmlElement(name = "commonResponse")
    public CommonBody getCommon() {
        return common;
    }

    public void setCommon(CommonBody common) {
        this.common = common;
    }

    @XmlElement(name = "xmlResult")
    public XmlBody getXml() {
        return xml;
    }

    public void setXml(XmlBody xml) {
        this.xml = xml;
    }

}
