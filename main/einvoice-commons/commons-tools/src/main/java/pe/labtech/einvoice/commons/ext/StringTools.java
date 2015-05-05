/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.commons.ext;

import java.util.Arrays;

/**
 *
 * @author Carlos
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
