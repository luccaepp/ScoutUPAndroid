package com.tcc.lucca.scoutup.model;

public class Escotista extends Usuario {

    public Escotista(String nome, String email) {
        setNome(nome);
        setEmail(email);
        setTipo(TipoUser.escotista);

    }

}
