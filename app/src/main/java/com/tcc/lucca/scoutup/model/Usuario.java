package com.tcc.lucca.scoutup.model;

import java.util.HashMap;

/**
 * Created by lucca on 19/07/17.
 */

public class Usuario {


    private String nome;
    private String email;
    private String token;
    private String grupo;
    private HashMap<String, String> secao;
    private HashMap<String, String> patrulha;
    private String tipo;
    private Status status;
    private HashMap<String, Amigo> amigos;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HashMap<String, Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(HashMap<String, Amigo> amigos) {
        this.amigos = amigos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public HashMap<String, String> getSecao() {
        return secao;
    }

    public void setSecao(HashMap<String, String> secao) {
        this.secao = secao;
    }

    public HashMap<String, String> getPatrulha() {
        return patrulha;
    }

    public void setPatrulha(HashMap<String, String> patrulha) {
        this.patrulha = patrulha;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
