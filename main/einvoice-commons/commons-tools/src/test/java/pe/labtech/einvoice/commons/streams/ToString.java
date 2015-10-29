/*
* Producto elaborado para Alignet S.A.C.
*
*/

package pe.labtech.einvoice.commons.streams;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pe.labtech.einvoice.commons.ext.StringTools;

/**
* Clase ToString.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/

public class ToString {

    @Test
    public void join0() {
        String expected = null;
        String[] tokens = new String[]{};

        String actual = StringTools.join(tokens, "");
        assertEquals(expected, actual);
    }

    public void join1_1() {
        String expected = "a";
        String[] tokens = new String[]{"a"};

        String actual = StringTools.join(tokens, "");
        assertEquals(expected, actual);
    }

    public void join1_2() {
        String expected = "";
        String[] tokens = new String[]{""};

        String actual = StringTools.join(tokens, ", ");
        assertEquals(expected, actual);
    }

    public void join1_3() {
        String expected = "null";
        String[] tokens = new String[]{null};

        String actual = StringTools.join(tokens, ", ");
        assertEquals(expected, actual);
    }

    public void join1_4() {
        String expected = "'a'";
        String[] tokens = new String[]{"a"};

        String actual = StringTools.join(tokens, ", ", "'");
        assertEquals(expected, actual);
    }

    public void join2_1() {
        String expected = "a, b";
        String[] tokens = new String[]{"a", "b"};

        String actual = StringTools.join(tokens, ", ");
        assertEquals(expected, actual);
    }

    public void join2_2() {
        String expected = "null, b";
        String[] tokens = new String[]{null, "b"};

        String actual = StringTools.join(tokens, ",");
        assertEquals(expected, actual);
    }

    public void join2_3() {
        String expected = "'a', 'b'";
        String[] tokens = new String[]{"a", "b"};

        String actual = StringTools.join(tokens, ", ", "'");
        assertEquals(expected, actual);
    }

}
