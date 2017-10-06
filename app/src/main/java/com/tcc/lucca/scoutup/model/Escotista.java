package com.tcc.lucca.scoutup.model;

public class Escotista extends Usuario {

    public Escotista(String nome, String email) {
        setNome(nome);
        setEmail(email);
        setTipo(Tipo.escotista);

    }

    public Escotista(String nome, String email, String grupoId, String sessao, String id) {
        setNome(nome);
        setEmail(email);
        setGrupo(grupoId);
        setSessao(sessao);
        setId(id);
        setTipo(Tipo.escoteiro);


    }

}
