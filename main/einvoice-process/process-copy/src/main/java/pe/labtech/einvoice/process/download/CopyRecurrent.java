/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.process.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import pe.labtech.einvoice.commons.recurrent.AbstractRecurrentTask;
import pe.labtech.einvoice.core.entity.DocumentData;
import pe.labtech.einvoice.core.model.DocumentDataLoaderLocal;
import static pe.labtech.einvoice.core.model.DocumentDataLoaderLocal.*;

/**
 * Clase CopyRecurrent.
 *
 * @author Labtech S.R.L. (info@labtech.pe)
 *
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class CopyRecurrent extends AbstractRecurrentTask<DocumentData> {

    @EJB
    DocumentDataLoaderLocal loader;

    public CopyRecurrent() {

    }

    /**
     * Inicializador.
     */
    @Override
    @PostConstruct
    public void init() {
        super.init();
        this.findTasks = () -> loader.find(DATA_LOADED);
        this.tryLock = t -> loader.changeStatus(t, DATA_LOADED, DATA_COPYING);
        this.getId = t -> t.getDocument().getClientId() + "-" + t.getDocument().getDocumentType() + "-" + t.getDocument().getDocumentNumber() + "[copy:" + t.getName() + "]";
        this.consumer = t -> {
            try {
                File dir = buildDirectory(t);
                String fileName = getFileName(t);
                File file = getFile(dir, fileName);
                byte[] data = loader.readData(t);
                writeData(file, data);
                loader.changeStatus(t, DATA_COPYING, DATA_COPIED);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        };
    }

    private File buildDirectory(DocumentData t) {
        File dir = new File("../downloads/" + t.getDocument().getClientId() + "/" + t.getDocument().getDocumentType() + "-" + t.getDocument().getDocumentNumber());
        if (!dir.exists()) {
            dir.mkdirs();
            dir.mkdirs();
        }
        return dir;
    }

    private String getFileName(DocumentData t) throws IOException {
        if (t.getSource() == null) {
            return buildFileName(t);
        }
        URL url = new URL(t.getSource());
        String fileName = urlFileName(url);
        if (fileName == null) {
            fileName = buildFileName(t);
        }
        return fileName;
    }

    private File getFile(File dir, String fileName) {
        File file = new File(dir, fileName);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    private String urlFileName(URL url) throws IOException {
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        try {
            String disposition = uc.getHeaderField("Content-Disposition");
            if (disposition != null) {
                //get the file name
                disposition = disposition.trim();
                String n = disposition.split("=")[1];
                if (n.startsWith("\"")) {
                    n = n.substring(1);
                }
                if (n.endsWith("\"")) {
                    n = n.substring(0, n.length() - 1);
                }
                return n;
            }
            return null;
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
    }

    private String buildFileName(DocumentData data) {
        String fileName = data.getName();
        //fix the suffix issue
        if (fileName.toUpperCase().endsWith(".ZIP")) {
            return fileName;
        } else if (fileName.toUpperCase().contains("PDF")) {
            fileName += ".pdf";
        } else if (fileName.toUpperCase().contains("XML")) {
            fileName += ".xml";
        }
        return fileName;
    }

    private void writeData(File file, byte[] data) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(data);
            bos.flush();
            bos.close();
        }
    }

    /**
     * Funcion recurrente.
     */
    @Override
    @Schedule(hour = "*", minute = "*", second = "*/15", persistent = false)
    protected void timeout() {
        super.timeout();
    }

}
