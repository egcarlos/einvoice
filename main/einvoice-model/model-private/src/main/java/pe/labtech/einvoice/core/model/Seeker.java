/*
 * Producto elaborado para Alignet S.A.C.
 *
 */
package pe.labtech.einvoice.core.model;

import java.util.List;
import pe.labtech.einvoice.core.entity.Document;

/**
 * Clase Seeker.
 * 
* @author Labtech S.R.L. (info@labtech.pe)
 */
public interface Seeker {

    List<Document> pullDocuments(String step, String status);

    boolean markSynkronized(Long id, String step, String oldstatus, String newstatus);

    boolean markSynkronized(Long id, String oldstep, String oldstatus, String newstep, String newstatus);

}
