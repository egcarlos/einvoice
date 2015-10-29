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
 * @param <L> tipo de la línea a agregar
 */
public interface HeaderBuilder<T, L> extends Builder<T> {

    HeaderBuilder<T, L> addLine(L line);
}
