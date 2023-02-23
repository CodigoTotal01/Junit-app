package org.junit5_app.models;

import org.junit5_app.exception.DineroInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {
    private String persona;
    private BigDecimal saldo; //Best Enterprise
    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    //! Big decimal es inmutable

    public void debito(BigDecimal monto) {
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        //si retorna -1 se supone que es negativo su saldo
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;
    }


    public void credito(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }


    //sobre escribir el metodo icuals para que se peuda comprar por valor y ya no por refeerencia de memoria
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cuenta)) {
            return false;
        }

        //cast
        Cuenta c = (Cuenta) obj;

        if (this.persona == null || this.saldo == null) {
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo()); // comparaando clases por valor (persona)

    }
}
