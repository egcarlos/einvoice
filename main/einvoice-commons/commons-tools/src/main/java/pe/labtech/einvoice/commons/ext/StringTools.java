/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.ext;

import java.util.Arrays;

/**
* Clase StringTools.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class StringTools {

    public static <T> String join(T[] tokens) {
        return join(tokens, "", "");
    }

    public static <T> String join(T[] tokens, String separator) {
        return join(tokens, separator, "");
    }

    public static <T> String join(T[] tokens, String separator, String wrapper) {
        return Arrays
                .stream(tokens)
                .map(t -> t == null ? "null" : wrapper + t + wrapper)
                .reduce(null, (a, b) -> a == null ? b : a + separator + b);
    }
}
