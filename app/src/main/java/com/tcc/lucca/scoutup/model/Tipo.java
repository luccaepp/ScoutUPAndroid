package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 31/08/17.
 */

public enum Tipo {
    escoteiro,
    escotista;

    public String devolveString(Tipo tipo) {

        if (tipo == escoteiro) {

            return "escoteiro";
        } else {
            return "escotista";


        }
    }
}
