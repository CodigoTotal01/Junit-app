package org.junit5_app.models;
// error scope - resolved - https://stackoverflow.com/questions/51567754/junit-on-intellij-not-working

import org.junit.jupiter.api.Test;
import org.junit5_app.exception.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {


    @Test
        // Metodo a ejecutar ocmo prueva unitaria
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal(10000.2121));
        cuenta.setPersona("Palacios");
        String esperando = "Palacios";
        String real = cuenta.getPersona();
        //assertEquals: varifica dos valores si  son iguales
        //? Methods assertions - message
        assertEquals(esperando, real, "El nombre de la cuenta no es el que se esperaba");// message only show it if we get a error
        assertTrue(real.equals("Palacios"), "El nombre cuenta esperadoa debe ser igual a la real "); // verifica si el valor es correcto
        //Si no hay noticias, (asertions), siempre estaran correctos
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Palacios", new BigDecimal("213.123213"));
        assertEquals(213.123213, cuenta.getSaldo().doubleValue()); //castear valor value

        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0); // evalua si es falso, si es asi es correcto
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Pereyra", new BigDecimal("1321321.313213"));
        Cuenta cuenta2 = new Cuenta("Pereyra", new BigDecimal("1321321.313213"));
        //comparando referencias de la clase (instancia)
        assertEquals(cuenta2, cuenta);
    }

    @Test
    void testDebitoCuenta() { //disminuye
        Cuenta cuenta = new Cuenta("Palacios", new BigDecimal("1000.0000"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue()); //verificando como entero
        assertEquals("900.0000", cuenta.getSaldo().toPlainString()); //dverruificando como string

    }


    @Test
    void testCreditoCuenta() { //aumenta
        Cuenta cuenta = new Cuenta("Palacios", new BigDecimal("1000.0000"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue()); //verificando como entero
        assertEquals("1100.0000", cuenta.getSaldo().toPlainString()); //dverruificando como string
    }


    //Probar clase s de exception
    @Test
    void testDineroInsuficienteException() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.0000"));
        System.out.println(DineroInsuficienteException.class);
        //devuelve el objeto exception, .class toma la ruta de la carpeta donde esta oubicadaa la clase
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });

        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";

        assertEquals(esperado, actual);
    }


    @Test
    void transferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Palacios", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Ariana", new BigDecimal("2500"));

        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("2000", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    //comprobando la relacion entre clientes y cuentas
    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Palacios", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Ariana", new BigDecimal("2500"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("BBVA");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        //cada uno erra independientemente
        assertAll(
                () -> {
                    //mensajes de eror en un assertion method dentro de un aassertAll
                    assertEquals("2000", cuenta2.getSaldo().toPlainString(),
                            () -> "El valor del saldo de la cuenta 2 no es el esperado ");
                }, () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString());
                }, () -> {
                    assertEquals(2, banco.getCuentas().size());
                }, () -> {
                    assertEquals(2, banco.getCuentas().size());

                }, () -> {
                    assertEquals("BBVA", cuenta1.getBanco().getNombre());
                }, () -> {
                    //        assertEquals("Ariana", banco.getCuentas().stream().filter(cuenta -> cuenta.getPersona().equals("Ariana")).findFirst().get().getPersona())
                    //       assertTrue(banco.getCuentas().stream().filter(cuenta -> cuenta.getPersona().equals("Ariana")).findFirst().isPresent());
                    assertTrue(banco.getCuentas().stream().anyMatch(cuenta -> cuenta.getPersona().equals("Ariana")));
                }

        );


    }

    //Muchos metodos assert - cuando falla uno - falla todos - assertAll - continuaran el resto de las pruebas


}