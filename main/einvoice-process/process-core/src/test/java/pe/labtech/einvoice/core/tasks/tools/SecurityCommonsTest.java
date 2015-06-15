/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.labtech.einvoice.core.tasks.tools;

import java.security.GeneralSecurityException;
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

//    @Test
//    public void hello1_1() {
//        String cifrado = NcCrypt.encriptarPassword("3b1zl4t1n$AVINKA");
//        System.out.println(cifrado);
//        String descifrado = NcCrypt.desencriptarPassword(cifrado);
//        System.out.println(descifrado);
//    }

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

    @Test
    public void hello5() throws GeneralSecurityException {
        String expected = "5tgbnhy6000";
        String encrypted = "x'6E57527655536E6C47324458543447326B4E327841413D3D2020202020202020'";
        String actual = new NcCrypt2().decode(encrypted);
        assertEquals(expected, actual);
    }

    @Test
    public void hello6() throws GeneralSecurityException {
        String expected = "123456789012345678901234567890";
        String encrypted = "x'497A426A65444B4F664B5030567A4D555039616775676F75443979636863416F32494464313764315966673D'";
        String actual = new NcCrypt2().decode(encrypted);
        assertEquals(expected, actual);
    }

    @Test
    public void hello7() throws GeneralSecurityException {
        String expected = "3b1zl4t1n$AVINKA";
        String encrypted = "x'45326E6F455566324E7376454750584674614178696A6479583955375170686C'";
        String actual = new NcCrypt2().decode(encrypted);
        assertEquals(expected, actual);
    }
}
