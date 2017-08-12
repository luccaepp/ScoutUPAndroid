package com.tcc.lucca.scoutup;

import java.util.List;

/**
 * Created by lucca on 19/07/17.
 */

public class Atividade {


    private String nome;
    private String descricao;
    private String local;
    private String horario;
    private List<Escoteiro> participantes;
    private Escotista escotistaGerador;
    private String id;
    private Chamada chamada;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Escoteiro> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Escoteiro> participantes) {
        this.participantes = participantes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
