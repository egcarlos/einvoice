/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jyachez
 */
public class NewEmptyJUnitTest {

    @Test
    public void test() {
        String expected = "p1 AND p2";

        String[] p = new String[]{"p1", "p2"};

        String response = Arrays.stream(p).reduce(null, (a, b) -> a == null ? b : a + " AND " + b);

        assertEquals(expected, response);

    }

    @Test
    public void test3() {
        String expected = "p1 AND p2 AND p3";

        String[] p = new String[]{"p1", "p2", "p3"};

        String response = Arrays.stream(p).reduce(null, (a, b) -> a == null ? b : a + " AND " + b);
        
        System.out.println(response);
        
        assertEquals(expected, response);

    }

    @Test
    public void test2() {
        String expected = "p1";

        String[] p = new String[]{"p1"};

        String response = Arrays.stream(p).reduce(null, (a, b) -> a == null ? b : a + " AND " + b);

        assertEquals(expected, response);

    }

}
