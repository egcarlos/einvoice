/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.commons;

import java.util.List;

/**
 * Identifica a un objeto como tipo de cabecera.
 *
 * @param <I> tipo del item agrupado en la cabecera
 *
 * @author Carlos
 */
public interface Header<I> {

    /**
     * Retorna de lista de items asociados al objeto.
     *
     * @return
     */
    List<I> getItem();

    /**
     * Establece la lista de items asociados al objeto.
     *
     * @param item
     */
    void setItem(List<I> item);

}
