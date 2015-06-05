/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.process.download;

import com.jcraft.jsch.*;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import pe.labtech.einvoice.replicator.entity.CancelHeader;
import pe.labtech.einvoice.replicator.entity.DocumentHeader;
import pe.labtech.einvoice.replicator.entity.SummaryHeader;

/**
 *
 * @author martin
 */
public class BL_HandleFile implements Closeable {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BL_HandleFile.class.getName());
    private JSch jsch;
    private Session session;
    private ChannelSftp sftp;
    private List<Object> listDocument;

    //FIXME se debe serializar al disco las credenciales
    public void connectToSftp() {
        try {
            //obtener las credenciales del disco
            Properties p = new Properties();
            p.load(new FileReader("tgestiona.properties"));
            String user = p.getProperty("user");
            String password = p.getProperty("password");
            String server = p.getProperty("server");
            int port = Integer.parseInt(p.getProperty("port"));

            jsch = new JSch();
            //session = jsch.getSession("sftpdesarrollo", "10.4.50.226", 22);
            session = jsch.getSession(user, server, port);
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);
            //session.setPassword("tgestiona");
            session.setPassword(password);
            session.connect();
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
        } catch (IOException | NumberFormatException | JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateFileAndSendFilesToSftp() {
//        Calendar c = Calendar.getInstance();
//        String dd = c.get(Calendar.DATE) + "";
//        String mm = c.get(Calendar.MONTH + 1) + "";
//        String yy = c.get(Calendar.YEAR) + "";
        
        
        Calendar c = Calendar.getInstance();
	int dd = c.get(Calendar.DATE) ;
	int mm = c.get(Calendar.MONTH) ;
	int yy = c.get(Calendar.YEAR) ;
	        
	String dia = dd <= 9 ? "0"+dd : dd+"";
	String mes = (mm>= 0 || mm <= 8) ? "0"+(mm+1) : (mm+1)+"";
	String anio = yy+"";
        

        FileWriter fichero = null;
        BufferedWriter bw = null;
        File f = new File("../downloads/" + dia + mes + anio + ".txt");

        try {
            fichero = new FileWriter(f.getAbsolutePath());
            bw = new BufferedWriter(fichero);

            for (Object o : listDocument) {
                if (o instanceof DocumentHeader) {
                    if(((DocumentHeader) o).getBl_cdr() != null){
                        sendFilesToSftp((DocumentHeader) o);
                        generateFile((DocumentHeader) o, fichero, bw);
                    }
                    else if(((DocumentHeader) o).getId().getSerieNumero().startsWith("B") ){
                        sendFilesToSftp((DocumentHeader) o);
                        generateFile((DocumentHeader) o, fichero, bw);
                    }
                } else if (o instanceof SummaryHeader) {
                    if(((SummaryHeader) o).getBl_cdr() != null){
                        sendFilesToSftp((SummaryHeader) o);
                        generateFile((SummaryHeader) o, fichero, bw);
                    }
                    
                } else if (o instanceof CancelHeader) {
                    if(((CancelHeader) o).getBl_cdr() != null){
                        sendFilesToSftp((CancelHeader) o);
                        generateFile((CancelHeader) o, fichero, bw);
                    }
                }
            }
            bw.close();
            sftp.put(new FileInputStream(f), f.getName());
            f.delete();//prueba

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendFilesToSftp(SummaryHeader dh) {

        byte[] xmlUbl = dh.getBl_xmlubl();
        byte[] xmlPdf = dh.getBl_pdf();
        byte[] xmlCdr = dh.getBl_cdr();
        byte[] xmlBizlink = dh.getBl_xml();
        try {
            String fileName = " ";

            if (xmlUbl != null) {
                //fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumentoEmisor() + "-" + dh.getFechaEmisionComprobante() + "-" + dh.getId().getResumenId() + ".xml";
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getResumenId() + ".xml";
                buildFile(xmlUbl, fileName);
            }

            if (xmlCdr != null) {
                //fileName = "../downloads/R-" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumentoEmisor() + "-" + dh.getFechaEmisionComprobante() + "-" + dh.getId().getResumenId() + ".xml";
                fileName = "../downloads/R-" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getResumenId() + ".xml";
                buildFile(xmlCdr, fileName);
            }

            if (xmlBizlink != null) {
                //fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumentoEmisor() + "-" + dh.getFechaEmisionComprobante() + "-" + dh.getId().getResumenId() + "-B.xml";
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getResumenId() + "-B.xml";
                buildFile(xmlBizlink, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateFile(SummaryHeader dh, FileWriter fichero, BufferedWriter bw) throws IOException {

        StringBuilder s = new StringBuilder("");

        if (dh.getBl_xml() != null) {
            //s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumentoEmisor()).append("-").append(dh.getFechaEmisionComprobante()).append("-").append(dh.getId().getResumenId()).append("-B.xml");
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getResumenId()).append("-B.xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_xmlubl() != null) {
            //s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumentoEmisor()).append("-").append(dh.getFechaEmisionComprobante()).append("-").append(dh.getId().getResumenId()).append(".xml");
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getResumenId()).append(".xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_cdr() != null) {
            //s.append("R-").append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumentoEmisor()).append("-").append(dh.getFechaEmisionComprobante()).append("-").append(dh.getId().getResumenId()).append(".xml");
            s.append("R-").append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getResumenId()).append(".xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        s.append(" ");

        s.append("|");

        s.append(dh.getId().getResumenId());
        s.append("|").append(dh.getFechaEmisionComprobante());
        //s.append(dh.getCodigoAuxiliar40_3()).append("|").append(dh.getFechaEmisionComprobante());
        //s.append(" ").append("|").append(dh.getFechaEmisionComprobante());

        bw.write(s.append("\r\n").toString());
    }

    private void sendFilesToSftp(CancelHeader dh) {

        byte[] xmlUbl = dh.getBl_xmlubl();
        byte[] xmlPdf = dh.getBl_pdf();
        byte[] xmlCdr = dh.getBl_cdr();
        byte[] xmlBizlink = dh.getBl_xml();
        try {
            String fileName = " ";

            if (xmlUbl != null) {
                //fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getResumenTipo() + "-" + dh.getFechaEmisionComprobante() + "-" + dh.getId().getResumenId() + ".xml";
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getResumenId() + ".xml";
                buildFile(xmlUbl, fileName);
            }

            if (xmlCdr != null) {
                //fileName = "../downloads/R-" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getResumenTipo() + "-" + dh.getFechaEmisionComprobante() + "-" + dh.getId().getResumenId() + ".xml";
                fileName = "../downloads/R-" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getResumenId() + ".xml";
                buildFile(xmlCdr, fileName);
            }

            if (xmlBizlink != null) {
                //fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getResumenTipo() + "-" + dh.getFechaEmisionComprobante() + "-" + dh.getId().getResumenId() + "-B.xml";
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getResumenId() + "-B.xml";
                buildFile(xmlBizlink, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateFile(CancelHeader dh, FileWriter fichero, BufferedWriter bw) throws IOException {

        StringBuilder s = new StringBuilder("");

        if (dh.getBl_xml() != null) {
            //s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getResumenTipo()).append("-").append(dh.getFechaEmisionComprobante()).append("-").append(dh.getId().getResumenId()).append("-B.xml");
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getResumenId()).append("-B.xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_xmlubl() != null) {
            //s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getResumenTipo()).append("-").append(dh.getFechaEmisionComprobante()).append("-").append(dh.getId().getResumenId()).append(".xml");
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getResumenId()).append(".xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_cdr() != null) {
            //s.append("R-").append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getResumenTipo()).append("-").append(dh.getFechaEmisionComprobante()).append("-").append(dh.getId().getResumenId()).append(".xml");
            s.append("R-").append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getResumenId()).append(".xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        s.append(" ");

        s.append("|");

        s.append(dh.getId().getResumenId());
        s.append("|").append(dh.getFechaEmisionComprobante());
        //s.append(" ").append("|").append(dh.getFechaEmisionComprobante());

        bw.write(s.append("\r\n").toString());
    }

    private void sendFilesToSftp(DocumentHeader dh) {

        byte[] xmlUbl = dh.getBl_xmlubl();
        byte[] xmlPdf = dh.getBl_pdf();
        byte[] xmlCdr = dh.getBl_cdr();
        byte[] xmlBizlink = dh.getBl_xml();
        try {
            String fileName = " ";

            if (xmlUbl != null && dh.getId().getSerieNumero().startsWith("F")) {
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumento() + "-" + dh.getId().getSerieNumero() + ".xml";
                buildFile(xmlUbl, fileName);
            }

            if (xmlPdf != null) {
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumento() + "-" + dh.getId().getSerieNumero() + ".pdf";
                buildFile(xmlPdf, fileName);
            }

            if (xmlCdr != null) {
                fileName = "../downloads/R-" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumento() + "-" + dh.getId().getSerieNumero() + ".xml";
                buildFile(xmlCdr, fileName);
            }

            if (xmlBizlink != null) {
                fileName = "../downloads/" + dh.getId().getNumeroDocumentoEmisor() + "-" + dh.getId().getTipoDocumento() + "-" + dh.getId().getSerieNumero() + "-B.xml";
                buildFile(xmlBizlink, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateFile(DocumentHeader dh, FileWriter fichero, BufferedWriter bw) throws IOException {

        StringBuilder s = new StringBuilder("");

        if (dh.getBl_xml() != null) {
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumento()).append("-").append(dh.getId().getSerieNumero()).append("-B.xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_xmlubl() != null && dh.getId().getSerieNumero().startsWith("F")) {
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumento()).append("-").append(dh.getId().getSerieNumero()).append(".xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_cdr() != null) {
            s.append("R-").append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumento()).append("-").append(dh.getId().getSerieNumero()).append(".xml");
        } else {
            s.append(" ");
        }

        s.append("|");

        if (dh.getBl_pdf() != null) {
            s.append(dh.getId().getNumeroDocumentoEmisor()).append("-").append(dh.getId().getTipoDocumento()).append("-").append(dh.getId().getSerieNumero()).append(".pdf");
        } else {
            s.append(" ");
        }

        s.append("|");

        s.append(dh.getTextoAuxiliar40_3()).append("|").append(dh.getFechaEmision());

        //logger.log(Level.WARNING, "Validando linea de archivo............."+s.toString());
        bw.write(s.append("\r\n").toString());
    }

    private void buildFile(byte[] rawData, String fileName) {
        try {
            File file = new File(fileName);
            OutputStream out = new FileOutputStream(file);
            out.write(rawData);
            out.flush();
            out.close();
            sftp.put(new FileInputStream(file), file.getName());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectToSftp() {
        sftp.exit();
        sftp.disconnect();
        session.disconnect();
    }

    public List<Object> getListDocument() {
        return listDocument;
    }

    /**
     * @param listDocument the listDocument to set
     */
    public void setListDocument(List<Object> listDocument) {
        this.listDocument = listDocument;
    }

    @Override
    public void close() {
        try {
            this.disconnectToSftp();
        } catch (Exception ex) {
            logger.warning(() -> "Can't disconnect from sftp." + ex);
        }
    }
}
