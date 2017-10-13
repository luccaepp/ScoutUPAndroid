package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 13/10/17.
 */

public class Participante {


    private String chave;
    private String chaveGrupo;
    private String nome;
    private String tipo;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getChaveGrupo() {
        return chaveGrupo;
    }

    public void setChaveGrupo(String chaveGrupo) {
        this.chaveGrupo = chaveGrupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
