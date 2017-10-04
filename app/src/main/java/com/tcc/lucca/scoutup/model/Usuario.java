package com.tcc.lucca.scoutup.model;

import java.util.HashMap;

/**
 * Created by lucca on 19/07/17.
 */

public class Usuario {


    private String nome;
    private String email;
    private String foto;
    private String grupo;
    private Secao secao;
    private int idade;
    private Patrulha patrulha;
    private String id;
    private Tipo tipo;
    private HashMap<String, Amigo> amigos;

    public Patrulha getPatrulha() {
        return patrulha;
    }

    public void setPatrulha(Patrulha patrulha) {
        this.patrulha = patrulha;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
