package org.junit5_app.models;
// error scope - resolved - https://stackoverflow.com/questions/51567754/junit-on-intellij-not-working

import org.junit.jupiter.api.*;
import org.junit5_app.exception.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
 // #Ciclos de vida, por cada vez se crea una nueva instancia de la clase, evitar las dependencias fuertes por cada metodo o prueba rgenerar nuestro objeto

// La clase es el TEST, los metodos test son las pruebas
// @TestInstance(TestInstance.Lifecycle.PER_CLASS) //genera una solla referencia de la claes y manejara una sola intancia de la clase , se puede uqitar como metodos estaticos
class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest(){
        this.cuenta = new Cuenta("Andres", new BigDecimal(2500));

        System.out.println("(inidio del metodo) Lo que se repite a cada rato dentro de los metodos");
    }

    @AfterEach
    void tearDown(){
        System.out.println("finaliza los metodos");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("inicializando el test (se llama de manera estatica )");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("Una ves se hallan ejecutado todos los metodos de prueba");
    }

    @Test //metodos
    @Disabled
    @DisplayName("Probando el nombr de la cuenta corriente!")
    void testNombreCuenta() {
// Para fallar la prueba(metodo)  -    fail();
        cuenta.setPersona("Palacios");
        String esperando = "Palacios";
        String real = cuenta.getPersona();
        //assertEquals: varifica dos valores si  son iguales
        //? Methods assertions - message
        assertEquals(esperando, real, "El nombre de la cuenta no es el que se esperaba");
        assertTrue(real.equals("Palacios"), "El nombre cuenta esperadoa debe ser igual a la real ");
    }

    @Test
    @DisplayName("test de saldo de cuenta corriente")
    void testSaldoCuenta() {
        cuenta = new Cuenta("Palacios", new BigDecimal("213.123213"));
        assertEquals(213.123213, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Las dos cuentas deben ser iguales")
    void testReferenciaCuenta() {
        cuenta = new Cuenta("Pereyra", new BigDecimal("1321321.313213"));
        Cuenta cuenta2 = new Cuenta("Pereyra", new BigDecimal("1321321.313213"));
        assertEquals(cuenta2, cuenta);
    }

    @Test
    @DisplayName("Debe gestionar el debito del cliente")
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(2400, cuenta.getSaldo().intValue());
        assertEquals("2400", cuenta.getSaldo().toPlainString());

    }


    @Test
    @DisplayName("Debe gestionar el credito del cliente")
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(500));
        assertNotNull(cuenta.getSaldo());
        assertEquals(3000, cuenta.getSaldo().intValue());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
    }


    @Test
    void testDineroInsuficienteException() {
        System.out.println(DineroInsuficienteException.class);
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(888888));
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

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Palacios", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Ariana", new BigDecimal("2500"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("BBVA");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertAll(() -> { assertEquals("2000", cuenta2.getSaldo().toPlainString(),
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
                    assertTrue(banco.getCuentas().stream().anyMatch(cuenta -> cuenta.getPersona().equals("Ariana")));
                }

        );
    }
}