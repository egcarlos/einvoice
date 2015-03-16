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

    List<DocumentData> findMissing();

    boolean lock(DocumentData data);

    byte[] download(URL url);

    void release(DocumentData data, byte[] rawdata);
}
