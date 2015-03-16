/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Carlos
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class DownloadRecurrent extends AbstractRecurrentTask<DocumentData> implements DownloadTaskLocal {

    @EJB
    DocumentDataLoaderLocal loader;

    public DownloadRecurrent() {

    }

    @Override
    @PostConstruct
    public void init() {
        super.init();
        this.findTasks = () -> loader.findMissing();
        this.tryLock = t -> loader.lock(t);
        this.getId = t -> t.getDocument().getClientId() + "-" + t.getDocument().getDocumentType() + "-" + t.getDocument().getDocumentNumber() + "[download:" + t.getName() + "]";
        this.consumer = t -> {
            try {
                File dir = buildDirectory(t);
                URL url = new URL(t.getSource());
                String fileName = getFileName(url);

                if (fileName == null) {
                    return;
                }

                File file = new File(dir, fileName);
                if (file.exists()) {
                    file.delete();
                }

                byte[] data = loader.download(url);

                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                    bos.write(data);
                    bos.flush();
                    bos.close();
                }

                loader.release(t, data);
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

    private String getFileName(URL url) throws IOException {
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

    //TODO ajustar el tiempo de llamada luego de pruebas
    @Override
    @Schedule(hour = "*", minute = "*", second = "*/15", persistent = false)
    protected void timeout() {
        super.timeout();
    }

}
