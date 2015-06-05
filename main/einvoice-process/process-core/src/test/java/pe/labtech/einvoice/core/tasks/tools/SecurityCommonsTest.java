/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carlos Echeverria
 */
public class SecurityCommonsTest {

    public SecurityCommonsTest() {
    }

    @Test
    public void hello1() {
        String expected = "5tgbnhy6000";
        String actual = SecurityCommons.unlock("x'6E57527655536E6C47324458543447326B4E327841413D3D2020202020202020'");
        assertEquals(expected, actual);
    }
    @Test
    public void hello2() {
        String expected = "3b1zl4t1n$AVINKA";
        String actual = SecurityCommons.unlock("y'FE27DCFD7EE4D3E0A437403483CF1CFF7E422822773666C0'");
        assertEquals(expected, actual);
    }
    @Test
    public void hello3() {
        String expected = "3b1zl4t1n$AVINKA";
        String actual = SecurityCommons.unlock("3b1zl4t1n$AVINKA");
        assertEquals(expected, actual);
    }
}
