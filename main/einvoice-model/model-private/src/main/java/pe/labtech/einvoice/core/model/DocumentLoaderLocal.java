/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;

/**
* Clase DocumentLoaderLocal
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
@Local
public interface DocumentLoaderLocal {

    <E> E save(E e);

    List<Document> loadForSignature();

    List<Document> loadForSync();

    List<Document> loadForDeclare();

    Document loadForWork(Long id, Consumer<Document> action);

    void markForSync(Long id);

    void markSigned(Long id, String status, String signature, String hash, Map<String, String> responses);

    void markDeclared(Long id);

    void createEvent(Long id, final String eventType, final String eventMessage);

    void createEvent(Document d, final String eventType, final String eventMessage);

    Document markAsError(Long id);

    Document markAsError(Long id, Exception ex);

}
