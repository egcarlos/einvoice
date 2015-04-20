/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.replicator.commons;

import java.util.List;

/**
 * 
 *
 * @param <I> tipo del item agrupado en la cabecera
 *
 * @author Carlos
 */
public interface Header<I> {

    List<I> getItem();

    void setItem(List<I> item);

}
