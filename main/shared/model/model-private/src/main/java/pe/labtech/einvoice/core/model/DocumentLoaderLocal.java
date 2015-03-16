/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.ejb.Local;
import pe.labtech.einvoice.core.entity.Document;

/**
 *
 * @author Carlos
 */
@Local
public interface DocumentLoaderLocal {

    <E> E save(E e);

    List<Document> loadForSignature();

    List<Document> loadForSync();

    List<Document> loadForDeclare();

    Document loadForWork(Long id, Consumer<Document> action);

    void markForSync(Long id);

    void markSigned(Long id, String signature, String hash, Map<String, String> responses);

    void markDeclared(Long id);

    void createEvent(Document d, final String eventType, final String eventMessage);

    Document markAsError(Long id);

    Document markAsError(Long id, Exception ex);

}
