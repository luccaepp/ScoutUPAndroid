package com.tcc.lucca.scoutup.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucca on 02/10/17.
 */

public class Patrulha {

    private String sessaoId;
    private String grupoId;
    private Long timeStamp;
    private String nome;
    private Map<String, String > criador;

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
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

    public String getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(String sessaoId) {
        this.sessaoId = sessaoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
