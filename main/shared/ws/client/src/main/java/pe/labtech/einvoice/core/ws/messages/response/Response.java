/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws.messages.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = "ebizResponse")
public class Response {

    private ResponseBody responseBody;

    public Response() {
    }

    public Response(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    @XmlElement(name = "genericInvokeResponse")
    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

}
