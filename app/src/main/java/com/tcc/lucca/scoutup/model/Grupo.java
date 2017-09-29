package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 01/09/17.
 */

public class Grupo {

    private String nome;
    private String cidade;
    private String estado;
    private String criador;
    private int numeral;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public int getNumeral() {
        return numeral;
    }

    public void setNumeral(int numeral) {
        this.numeral = numeral;
    }
}
