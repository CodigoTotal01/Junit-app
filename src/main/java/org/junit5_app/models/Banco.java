package org.junit5_app.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nombre;
    //las listas tieen que tener una inicializacion si no estas seran de valor null, basta con un consturctor sin argumentos
    private List<Cuenta> cuentas;


    public Banco() {
        cuentas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //transferencia de dinero
    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto){
        origen.debito(monto); // resta a la primera cuenta
        destino.credito(monto); // aumenta a la segunda cuenta
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
//Cannot invoke "org.junit5_app.models.Banco.getNombre()" because the return value of "org.junit5_app.models.Cuenta.getBanco()" is null
    public void addCuenta(Cuenta cuenta){
        cuentas.add(cuenta);
        cuenta.setBanco(this); //bidireccional
    }
}
