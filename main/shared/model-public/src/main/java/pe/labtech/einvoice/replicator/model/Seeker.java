/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 * @param <H> header type
 * @param <P> header pk type
 * @param <D> detail type
 */
public interface Seeker<H, P, D> {

    List<P> pullHeaders(String status);

    boolean markForProcess(P id, String oldstatus, String newstatus);

    H findById(P id);

    List<D> findDetails(P id);

    void update(P id, Map<String, String> responses);
}
