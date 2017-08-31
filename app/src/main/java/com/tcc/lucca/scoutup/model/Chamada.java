package com.tcc.lucca.scoutup.model;

import java.util.List;

/**
 * Created by lucca on 19/07/17.
 */

public class Chamada {

    private List<Escoteiro> escoteirosPresentes;
    private Escotista escotistaGerador;

    public List<Escoteiro> getEscoteirosPresentes() {
        return escoteirosPresentes;
    }

    public void setEscoteirosPresentes(List<Escoteiro> escoteirosPresentes) {
        this.escoteirosPresentes = escoteirosPresentes;
    }

    public Escotista getEscotistaGerador() {
        return escotistaGerador;
    }

    public void setEscotistaGerador(Escotista escotistaGerador) {
        this.escotistaGerador = escotistaGerador;
    }
}
