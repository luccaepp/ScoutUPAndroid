package com.tcc.lucca.scoutup.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lucca on 19/07/17.
 */

public class Usuario {


    private String nome;
    private String email;
    private String token;
    private String grupo;
    private String id;
    private Map<String, String> secao;
    private Map<String, String> patrulha;
    private String tipo;
    private Map<String, Amigo> amigos;
    private Map<String, SolicitacoesDeAmizade> solicitacoesDeAmizade;
    private Map<String, Conversa> conversas;
    private String status;

    public Map<String, Conversa> getConversas() {
        return conversas;
    }

    public void setConversas(Map<String, Conversa> conversas) {
        this.conversas = conversas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(Map<String, Amigo> amigos) {
        this.amigos = amigos;
    }


    public Map<String, SolicitacoesDeAmizade> getSolicitacoesDeAmizade() {
        return solicitacoesDeAmizade;
    }

    public void setSolicitacoesDeAmizade(Map<String, SolicitacoesDeAmizade> solicitacoesDeAmizade) {
        this.solicitacoesDeAmizade = solicitacoesDeAmizade;
    }
}
