/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.entity;

import java.util.List;

/**
* Clase Header.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public interface Header<I extends Detail> extends BLResponse {

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
