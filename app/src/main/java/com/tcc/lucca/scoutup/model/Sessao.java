package com.tcc.lucca.scoutup.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucca on 02/10/17.
 */

public class Sessao {

    private String grupoId;
    private String nome;
    private Long timeStamp;
    private Map<String, String > criador;
    private Map<String, Patrulha> patrulhas;

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Map<String, Patrulha> getPatrulhas() {
        return patrulhas;
    }

    public void setPatrulhas(Map<String, Patrulha> patrulhas) {
        this.patrulhas = patrulhas;
    }
}
