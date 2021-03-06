/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws.messages.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
* Clase Response.
*
* @author Labtech S.R.L. (info@labtech.pe)
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
