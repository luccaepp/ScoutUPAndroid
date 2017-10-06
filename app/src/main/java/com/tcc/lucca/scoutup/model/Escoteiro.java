package com.tcc.lucca.scoutup.model;

public class Escoteiro extends Usuario {

    public Escoteiro(String nome, String email) {

        setNome(nome);
        setEmail(email);

    }

    public Escoteiro(String nome, String email, String grupoId, String sessao, String id, String patrulhaId) {
        setNome(nome);
        setEmail(email);
        setGrupo(grupoId);
        setSessao(sessao);
        setPatrulha(patrulhaId);
        setId(id);
        setTipo(Tipo.escoteiro);


    }

}