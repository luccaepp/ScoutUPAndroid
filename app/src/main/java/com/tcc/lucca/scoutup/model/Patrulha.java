package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 02/10/17.
 */

public class Patrulha {

    private String sessaoId;
    private String grupoId;

    private String nome;

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(String sessaoId) {
        this.sessaoId = sessaoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
