/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.ws;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
* Clase PortInfo.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
@ApplicationScoped
public class PortInfo {

    private static final String FILE_NAME = "portinfo.json";
    private static final Logger logger = Logger.getLogger(PortInfo.class.getName());

    private String endpoint;
    private String user;
    private String password;
    private String connectionTimeout;
    private String receiveTimeout;

    //TODO move to a factory method
    @PostConstruct
    public void init() {
        //since is the first load set the default values first
        setDefaults();
        //and then try to load from disk
        load();
    }

    public void persist() {
        final File file = new File(FILE_NAME);
        final String filepath = file.getAbsolutePath();
        logger.log(Level.INFO, () -> "saving port info to: " + filepath);
        try (FileWriter fw = new FileWriter(file)) {
            new Gson().toJson(this, fw);
            fw.flush();
        } catch (IOException ex) {
            logger.log(Level.INFO, null, () -> "Can't persist values to " + filepath + ", check logs." + ex.getMessage());
        }
    }

    public void load() {
        final File file = new File(FILE_NAME);
        final String filepath = file.getAbsolutePath();
        logger.log(Level.INFO, () -> "loading port info from: " + filepath);

        //if file does not exit keep current values
        if (!file.exists()) {
            logger.log(Level.INFO, null, () -> "File " + filepath + " is missing, default values loaded.");
            return;
        }

        try (FileReader fr = new FileReader(file)) {
            PortInfo pi = new Gson().fromJson(fr, this.getClass());
            this.endpoint = pi.endpoint;
            this.password = pi.password;
            this.user = pi.user;
            this.connectionTimeout = pi.connectionTimeout;
            this.receiveTimeout = pi.receiveTimeout;
            logger.info("Port values captured from file");
        } catch (IOException ex) {
            logger.log(Level.INFO, null, () -> "File " + filepath + " is invalid, keeping current values." + ex.getMessage());
        }

    }

    private void setDefaults() {
        //valores por defecto
        this.endpoint = "http://test3.alignetsac.com/sfewsperu/ws/invoker";
        this.user = "avinka";
        this.password = "ebiz";
        //fixes missing default values
        this.connectionTimeout = "600000";
        this.receiveTimeout = "600000";
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
