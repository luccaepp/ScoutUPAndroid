package com.tcc.lucca.scoutup.model;

import java.sql.Timestamp;
import java.util.List;

public class Atividade {

    private String nome;
    private String desc;
    private String endereco;
    private String lat;
    private String lon;
    private List<String> itens;
    private Timestamp horaInicio;
    private Timestamp horaFim;
    private List<String> confirmados;
    private String sessao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public List<String> getItens() {
        return itens;
    }

    public void setItens(List<String> itens) {
        this.itens = itens;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Timestamp horaFim) {
        this.horaFim = horaFim;
    }

    public List<String> getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(List<String> confirmados) {
        this.confirmados = confirmados;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }
}
