/*
* Producto elaborado para Alignet S.A.C.
*
*/
package pe.labtech.einvoice.core.model;

import javax.ejb.Local;

/**
* Clase AsyncWrapperLocal
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
@Local
public interface AsyncWrapperLocal {

    void perform(Runnable r);

}
