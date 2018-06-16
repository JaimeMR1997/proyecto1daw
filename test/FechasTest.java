/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;
import proyecto1daw.modelo.Fechas;

/**
 *
 * @author Jaime
 */
public class FechasTest {
    public FechasTest() {
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        LocalDate fecha = null;
        String expResult = null;
        String result = Fechas.toString(fecha);
        assertNull(result);
    }
    
    @Test
    public void testToString2() {
        System.out.println("toString");
        LocalDate fecha = LocalDate.of(2018, Month.MARCH, 1);
        String expResult = "1/3/2018";
        String result = Fechas.toString(fecha);
        assertEquals(expResult,result);
    }
    
    @Test
    public void testToLocalDate() {
        System.out.println("toLocalDate");
        String fecha = "28/2/2018";
        LocalDate expResult = LocalDate.of(2018, Month.FEBRUARY, 28);
        LocalDate result = Fechas.toLocalDate(fecha);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToLocalDate2() {
        System.out.println("toLocalDate");
        String fecha = "29/2/2018";
        LocalDate expResult = LocalDate.of(2018, Month.FEBRUARY, 28);
        LocalDate result = Fechas.toLocalDate(fecha);
        assertNull(result);
    }
    
    @Test
    public void testToLocalDate3() {
        System.out.println("toLocalDate");
        String fecha = "50/4/2018";
        LocalDate expResult = LocalDate.of(2018, Month.FEBRUARY, 28);
        LocalDate result = Fechas.toLocalDate(fecha);
        assertNull(result);
    }
    
    @Test
    public void testToLocalDate4() {
        System.out.println("toLocalDate");
        String fecha = "2/26/2018";
        LocalDate expResult = LocalDate.of(2018, Month.FEBRUARY, 28);
        LocalDate result = Fechas.toLocalDate(fecha);
        assertNull(result);
    }
    
    @Test 
    public void testToLocalDate5() {
        System.out.println("toLocalDate");
        String fecha = "10/2/3950";
        LocalDate expResult = LocalDate.of(3950, Month.FEBRUARY, 10);
        LocalDate result = Fechas.toLocalDate(fecha);
        assertNull(result);
    }

    @Test
    public void testValidarDni() {
        System.out.println("validarDni");
        String nif = "12345678Y";
        boolean expResult = true;
        boolean result = Fechas.validarDni(nif);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testValidarDni2() {
        System.out.println("validarDni");
        String nif = "X1234567Y";
        boolean expResult = true;
        boolean result = Fechas.validarDni(nif);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testValidarDni3() {
        System.out.println("validarDni");
        String nif = "X12345678Y";
        boolean expResult = true;
        boolean result = Fechas.validarDni(nif);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testValidarDni4() {
        System.out.println("validarDni");
        String nif = "1234567Y";
        boolean expResult = false;
        boolean result = Fechas.validarDni(nif);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testValidarDni5() {
        System.out.println("validarDni");
        String nif = "123456789Y";
        boolean expResult = false;
        boolean result = Fechas.validarDni(nif);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testValidarDni6() {
        System.out.println("validarDni");
        String nif = "12345678Ñ";
        boolean expResult = true;
        boolean result = Fechas.validarDni(nif);
        assertEquals(expResult, result);
    }
}
