package com.tcc.lucca.scoutup.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucca on 19/07/17.
 */

public class Usuario {


    private String nome;
    private String email;
    private String token;
    private String grupo;
    private Map<String, String> secao;
    private Map<String, String> patrulha;
    private String tipo;
    private Status status;
    private Map<String, Amigo> amigos;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Map<String, String> getSecao() {
        return secao;
    }

    public void setSecao(Map<String, String> secao) {
        this.secao = secao;
    }

    public Map<String, String> getPatrulha() {
        return patrulha;
    }

    public void setPatrulha(Map<String, String> patrulha) {
        this.patrulha = patrulha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(Map<String, Amigo> amigos) {
        this.amigos = amigos;
    }
}
