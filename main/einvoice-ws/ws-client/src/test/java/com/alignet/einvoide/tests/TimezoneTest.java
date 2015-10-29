/*
* Producto elaborado para Alignet S.A.C.
*
*/
package com.alignet.einvoide.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.testng.annotations.Test;

/**
* Clase TimezoneTest.
*
* @author Labtech S.R.L. (info@labtech.pe)
*/
public class TimezoneTest {

    @Test
    public void test() throws ParseException {
        String fecha = "2014-09-30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
        Date d = sdf.parse(fecha);
        System.out.println(d);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-ddX");
        final String ft1 = sdf1.format(d);
        System.out.println(ft1);

        sdf1.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));

        Date d2 = sdf1.parse(ft1);
        System.out.println(sdf1.format(d2));

    }

}
