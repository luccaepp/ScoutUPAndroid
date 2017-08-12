package com.tcc.lucca.scoutup;

/**
 * Created by lucca on 19/07/17.
 */

public class Escoteiro extends Usuario {

    private Cargo cargo;
    private String patrulha;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getPatrulha() {
        return patrulha;
    }

    public void setPatrulha(String patrulha) {
        this.patrulha = patrulha;
    }
}
