package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 08/10/17.
 */

public class Amigo {

    private String nome;
    private String chave;
    private Status status;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
