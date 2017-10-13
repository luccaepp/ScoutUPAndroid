package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 13/10/17.
 */

public class MapAtividadePH {



    private String chaveAtividade;
    private String chavePH;

    public MapAtividadePH() {
    }

    public MapAtividadePH(String chaveAtividade, String chavePH) {
        this.chaveAtividade = chaveAtividade;
        this.chavePH = chavePH;
    }

    public String getChaveAtividade() {
        return chaveAtividade;
    }

    public void setChaveAtividade(String chaveAtividade) {
        this.chaveAtividade = chaveAtividade;
    }

    public String getChavePH() {
        return chavePH;
    }

    public void setChavePH(String chavePH) {
        this.chavePH = chavePH;
    }


}
