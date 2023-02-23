package org.junit5_app.models;
// error scope - resolved - https://stackoverflow.com/questions/51567754/junit-on-intellij-not-working
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() { //espera algo siempre una prueba
        //intanciar clase a evaluar
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal(10000.2121));
        cuenta.setPersona("Palacios");
        String esperando = "Palacios";
        String real = cuenta.getPersona();
        //assertEquals: varifica dos valores si  son iguales
        assertEquals(esperando, real);
        assertTrue(real.equals("Palacios")); // verifica si el valor es correcto
        //Si no hay noticias, (asertions), siempre estaran correctos

    }
}