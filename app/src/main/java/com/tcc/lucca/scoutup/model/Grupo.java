package com.tcc.lucca.scoutup.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucca on 01/09/17.
 */

public class Grupo {

    private int numeral;
    private String nome;
    private String cidade;
    private String estado;
    private Long timeStamp;
    private Map<String, String >criador;
    private Map<String, Sessao> secoes;


    public int getNumeral() {
        return numeral;
    }

    public void setNumeral(int numeral) {
        this.numeral = numeral;
    }

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

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, String> getCriador() {
        return criador;
    }

    public void setCriador(Map<String, String> criador) {
        this.criador = criador;
    }

    public Map<String, Sessao> getSecoes() {
        return secoes;
    }

    public void setSecoes(Map<String, Sessao> secoes) {
        this.secoes = secoes;
    }
}
