package org.junit5_app.models;
import jdk.jfr.Enabled;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit5_app.exception.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
//Test Condicionales - Pruebas unitarias se ejecuen en ciertos OS o versiones java, se

// nest se generaran nodos con un orden de gerarquia

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


    @RepeatedTest(5)
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


    @Nested // para agrupar pruebas dentro de sus propios test, pero teniendo en ceunta la clase principal@
        @DisplayName("Se le peude dar al a clase un nombre para ser mas esceificos ")
    class sistemasOperativos{
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows(){

        }

        //estara desabilidato si no esta ejecutandoce en este sistema operativo

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac(){

        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows(){

        }




    }


    //desabilitar si existe scierta cosa en el sistema
    @Nested
    class JavaVersionTest{
        @Test
        @EnabledOnJre(JRE.JAVA_17)
        void soloEnJdk17(){

        }
    }

    @Nested
    class SistemPropertiesTest{
        @Test
        void imprimirPropiedades(){
            Properties properties = System.getProperties();
            properties.forEach((k, l)-> System.out.println(k + " : " + l));
        }
        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = "17")
        void testJavaVersion(){

        }


        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = "dev")
        void testUsername(){
        }





        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = "32")
        void testSolo64(){

        }



    }

    @Nested
    class VariablesAmbienteTest{

        //Vairables del sistema operativo

        @Test
        void imprimirVariablesAmbiente(){
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k,v)-> System.out.println(k+ " : " + v));
        }



        //aseption afirmar prueba unitaria
        //assumtion - afirmar puerba unitaria con valores booleanos

        //test condicional Assumption
        @Test
        @DisplayName("test de saldo de cuenta corriente")
        void testSaldoCuentaDEV() {
            boolean esDev = "dev".equals(System.getProperty("ENV"));
            System.out.println(System.getProperty("ENV"));
            assumeTrue(esDev); //s i es verdadero ejecuta la prueva
            cuenta = new Cuenta("Palacios", new BigDecimal("213.123213"));
            assertEquals(213.123213, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        }

        @Test
        @DisplayName("test de saldo de cuenta corriente")
        void testSaldoCuentaDEV2() {
            boolean esDev = "dev".equals(System.getProperty("ENV"));
            System.out.println(System.getProperty("ENV"));
            assumingThat(esDev, () -> { //assumi that - permite ejecutar un determinado codigo si es verdad algo
                cuenta = new Cuenta("Palacios", new BigDecimal("213.123213"));
                assertEquals(213.123213, cuenta.getSaldo().doubleValue());
                assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            }); //s i es verdadero ejecuta la prueva

        }
    }



}