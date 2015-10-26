/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons2.ubl.api;

/**
 *
 * @author carloseg
 * @param <T> tipo del documento o resumen a construir
 */
public interface Builder<T> {

    /**
     *
     * @return
     */
    Builder<T> init();

    /**
     *
     *
     * @return
     */
    T compile();
}
