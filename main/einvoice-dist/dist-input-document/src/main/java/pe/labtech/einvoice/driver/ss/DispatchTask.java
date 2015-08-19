/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.driver.ss;

import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import pe.labtech.einvoice.commons.jndi.JNDI;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeaderPK;

/**
 *
 * @author Carlos Echeverria
 */
public class DispatchTask implements Runnable {

    private static final Logger logger = Logger.getLogger(DispatchTask.class.getSimpleName());
    private static final Client client = ClientBuilder.newClient();

    private final DocumentHeaderPK id;
    private final String source;
    private final DocumentHeader h;

    public DispatchTask(DocumentHeaderPK id, String source, DocumentHeader h) {
        this.id = id;
        this.source = source;
        this.h = h;
    }

    @Override
    public void run() {
        //obtiene destino del cliente 
        String target = JNDI.getInstance().lookup("java:global/einvoice/config/target");
        client
                .target(target)
                .path(id.getTipoDocumentoEmisor())
                .path(id.getNumeroDocumentoEmisor())
                .path(id.getTipoDocumento())
                .path(id.getSerieNumero())
                .queryParam("source", source)
                .request()
                .buildPut(Entity.xml(h))
                .submit();
    }

}
