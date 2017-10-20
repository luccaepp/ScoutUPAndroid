package com.tcc.lucca.scoutup.model.progressao;

import java.util.HashMap;

/**
 * Created by lucca on 20/10/17.
 */

public class Areas {

    private String nome;
   private HashMap<String, Especialidade> map;


    public Areas() {


    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public HashMap<String, Especialidade> getMap() {

        return map;
    }

    public void setMap(HashMap<String, Especialidade> map) {
        this.map = map;
    }
}


