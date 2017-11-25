package com.tcc.lucca.scoutup.model;

/**
 * Created by trickstival on 13/10/17.
 */

public class Material {
    private String nome, key;

    public Material() {

    }

    public Material(String nome, String key) {
        this.nome = nome;
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
