/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.util.List;
import pe.labtech.einvoice.core.entity.Document;

/**
 *
 * @author Carlos
 */
public interface Seeker {

    List<Document> pullDocuments(String step, String status);

    boolean markSynkronized(Long id, String step, String oldstatus, String newstatus);

}
