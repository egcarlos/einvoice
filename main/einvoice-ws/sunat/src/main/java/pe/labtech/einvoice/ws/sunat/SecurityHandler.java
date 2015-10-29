/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.ws.sunat;

import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author Carlos Echeverria
 */
public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String WSSE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";

    private String user;
    private String password;

    public SecurityHandler() {
    }

    public SecurityHandler(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
        if ((Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            try {
                final SOAPHeader header = messageContext.getMessage().getSOAPHeader();
                final SOAPHeaderElement security = header.addHeaderElement(new QName(WSSE_NS, "Security", "wsse"));
                security.addNamespaceDeclaration("wsu", WSU_NS);
                final SOAPElement nameToken = security.addChildElement("UsernameToken", "wsse", WSSE_NS);
                nameToken.addAttribute(new QName(WSU_NS, "Id", "wsu"), "UsernameToken-1");
                final SOAPElement user = nameToken.addChildElement("Username", "wsse", WSSE_NS);
                user.addTextNode(this.user);
                final SOAPElement pass = nameToken.addChildElement("Password", "wsse", WSSE_NS);
                pass.addTextNode(this.password);
                pass.addAttribute(new QName("Type"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
            } catch (SOAPException ex) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
