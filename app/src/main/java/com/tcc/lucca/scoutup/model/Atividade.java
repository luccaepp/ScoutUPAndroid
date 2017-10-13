package com.tcc.lucca.scoutup.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atividade {

    private String titulo;
    private String desc;
    private Local local;
    private Map<String, String> materiais;
    private Timestamp inicio;
    private Timestamp termino;
    private String tipo;
    private Map<String, Participante> participantes;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Map<String, String> getMateriais() {
        return materiais;
    }

    public void setMateriais(Map<String, String> materiais) {
        this.materiais = materiais;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public Timestamp getTermino() {
        return termino;
    }

    public void setTermino(Timestamp termino) {
        this.termino = termino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Map<String, Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Map<String, Participante> participantes) {
        this.participantes = participantes;
    }
}
