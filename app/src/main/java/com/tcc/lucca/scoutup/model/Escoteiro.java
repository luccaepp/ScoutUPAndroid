package com.tcc.lucca.scoutup.model;

import java.util.HashMap;


public class Escoteiro extends Usuario {

    private HashMap<String, String> cargos;

    public Escoteiro(String nome, String email) {

        setNome(nome);
        setEmail(email);

    }

    public Escoteiro(String nome, String email, String foto, String grupoId, Secao secao, int idade, String id, String patrulhaId, HashMap<String, String> cargos) {
        setNome(nome);
        setEmail(email);
        setFoto(foto);
        setGrupo(grupoId);
        setSecao(secao);
        setIdade(idade);
        setId(id);
        setTipo(TipoUser.escoteiro);
        this.cargos = cargos;


    }

}