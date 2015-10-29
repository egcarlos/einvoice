/*
* Producto elaborado para Alignet S.A.C.
*
*/
package com.alignet.einvoide.tests;

import pe.labtech.einvoice.core.ws.helpers.Builder;
import pe.labtech.einvoice.core.ws.messages.response.CommonBody;
import pe.labtech.einvoice.core.ws.messages.response.DocumentInfo;
import pe.labtech.einvoice.core.ws.messages.response.Response;
import pe.labtech.einvoice.core.ws.messages.response.ResponseBody;
import pe.labtech.einvoice.core.ws.messages.response.ResponseMessage;
import pe.labtech.einvoice.core.ws.messages.response.SummaryResponse;
import pe.labtech.einvoice.core.ws.messages.response.XmlBody;
import pe.labtech.einvoice.core.ws.model.Document;
import java.util.Arrays;
import org.junit.Test;

/**
* Clase CreateCommandsTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/  
public class CreateCommandsTest {

    @Test
    public void publish() {
        String s = new Builder().buildPublish("20101363008", "01", "FB01-00000001", "FB01-00000002");
        System.out.println(s);
    }

    @Test
    public void declare() {
        String s = new Builder().buildDeclare("20101363008", "01", "FB01-00000001", "FB01-00000002");
        System.out.println(s);
    }

    @Test
    public void sign() {
        final Document document = new Document();
        
        document.setTipoDocumentoEmisor("6");
        document.setNumeroDocumentoEmisor("20101363008");
        document.setRazonSocialEmisor("");
        document.setNombreComercialEmisor("");
        document.setDireccionEmisor("");
        
        
        String s = new Builder().buildSign(
                "20101363008",
                "01",
                "PDF",
                true,
                false,
                false,
                "",
                Arrays.asList(
                        document)
        );
        System.out.println(s);
    }

    @Test
    public void response() {
        Response response = new Response(
                new ResponseBody(
                        new CommonBody(
                                new SummaryResponse(3, null, null, null),
                                "ERROR",
                                "Petición inválida",
                                Arrays.asList(
                                        new ResponseMessage("400", "Petición Inválida", "7026", "Estructura con errores", null),
                                        new ResponseMessage("400", "Petición Inválida", "7026", "Estructura con errores", null)
                                )
                        ),
                        new XmlBody(
                                null,
                                Arrays.asList(
                                        new DocumentInfo(
                                                "SIGNED",
                                                null,
                                                "01",
                                                "F101-00000001",
                                                "EM_01",
                                                null, null, null,
                                                "6", "20563330709", "La Viga S.A.",
                                                "2014-08-12", "2014-08-12", "2014-08-12",
                                                "EqSVL481yIsbFGaDRHmowYq+C+DXITQ3PdgvwkUThJmpLtpx1pc5iYApk97U8+XmCqAFdb+34BqWIy03XrpxkKo5s5R6sfR9KV5czC15SQJkTbd+hVlF1mrBVhIxwZC8BZR5osBW7Yq/JI3WiQX269nen2jR1c1o6KH+Vy5M7aQ=",
                                                "X1T4yAqdhbIDZ1M8dlxtPygiNBo=",
                                                "AC-03", "La Factura numero F101-00002011, ha sido aceptada",
                                                null,
                                                null,
                                                "http://test3.alignetsac.com/sfewsperu1/files/x%274F7372717353565334492B6C764C513262703434517745692B4A725834464B62732B416271676D616D634A536A7633666D49684758413D3D%27",
                                                null
                                        ),
                                        new DocumentInfo(
                                                "ERROR",
                                                null,
                                                "01",
                                                "F101-00000001",
                                                null,
                                                null, null, null,
                                                null, null, null,
                                                null, null, null,
                                                null,
                                                null,
                                                null, null,
                                                null,
                                                null,
                                                null,
                                                Arrays.asList(
                                                        new ResponseMessage("400", "Petición Inválida", "7026", "Estructura con errores", null),
                                                        new ResponseMessage("400", "Petición Inválida", "7026", "Estructura con errores", null)
                                                )
                                        )
                                )
                        )
                )
        );
        System.out.println(new Builder().marshall(response));
    }

}
