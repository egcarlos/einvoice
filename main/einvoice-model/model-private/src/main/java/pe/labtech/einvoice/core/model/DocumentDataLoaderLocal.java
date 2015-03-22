/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.net.URL;
import java.util.List;
import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.DocumentData;

/**
 *
 * @author Carlos
 */
@Local
public interface DocumentDataLoaderLocal {

    String DATA_MISSING = "MISSING";
    String DATA_DOWNLOADING = "DOWNLOADING";
    String DATA_LOADED = "LOADED";
    String DATA_COPYING = "COPYING";
    String DATA_COPIED = "COPIED";

    List<DocumentData> find(String status);

    boolean changeStatus(DocumentData data, String oldstatus, String newstatus);

    byte[] readData(DocumentData data);

    boolean addData(DocumentData data, byte[] rawdata);

    byte[] download(URL url);

}
