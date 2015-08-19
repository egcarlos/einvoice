/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.driver.ss;

import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Carlos Echeverria
 */
@Singleton
@LocalBean
@Path("load")
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class LoadDocumentResource {

    @Resource(name = "java:global/einvoice/executor/load")
    private ExecutorService executor;

    /**
     * Creates a new instance of LoadDocumentResource
     */
    public LoadDocumentResource() {
    }

    /**
     * Retrieves representation of an instance of
     * pe.labtech.einvoice.driver.ss.LoadDocumentResource
     *
     * @param batch
     * @param source
     * @param target
     * @return an instance of java.lang.String
     */
    @Path("documents")
    @GET
    @Produces("application/xml")
    public String getXml(
            @QueryParam("batch") @DefaultValue("1000") int batch,
            @QueryParam("source") @DefaultValue("A") String source,
            @QueryParam("target") @DefaultValue("L") String target
    ) {
        Logger.getAnonymousLogger().info("Start replication process");
        executor.execute(new LoadDocumentTask(batch, source, target));
        return "<done />";
    }

}
