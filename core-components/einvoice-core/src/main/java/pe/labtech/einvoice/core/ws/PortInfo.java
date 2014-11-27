/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.ws;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Carlos
 */
@ApplicationScoped
public class PortInfo {

    private static final Logger logger = Logger.getLogger("PortInfo");

    private String endpoint;
    private String user;
    private String password;
    private String connectionTimeout;
    private String receiveTimeout;

    //TODO move to a factory method
    @PostConstruct
    public void init() {
        final File file = new File("portinfo.json");
        final String filepath = file.getAbsolutePath();
        logger.log(Level.INFO, () -> "loading port info from: " + filepath);

        //valores por defecto
        this.endpoint = "http://test3.alignetsac.com/sfewsperu/ws/invoker";
        this.user = "avinka";
        this.password = "ebiz";

        try {
            PortInfo pi = new Gson().fromJson(new FileReader(file), this.getClass());
            this.endpoint = pi.endpoint;
            this.password = pi.password;
            this.user = pi.user;
            this.connectionTimeout = pi.connectionTimeout;
            this.receiveTimeout = pi.receiveTimeout;
            logger.info("Port values captured from file");
        } catch (IOException ex) {
            logger.log(Level.INFO, null, () -> "Invalid file, keeping default values. " + ex.getMessage());
        }

    }

    /**
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getReceiveTimeout() {
        return receiveTimeout;
    }

    public void setReceiveTimeout(String receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

}
