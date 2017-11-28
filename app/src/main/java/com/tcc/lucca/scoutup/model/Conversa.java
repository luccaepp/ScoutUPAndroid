package com.tcc.lucca.scoutup.model;

import java.io.Serializable;

/**
 * Created by trickstival on 28/11/17.
 */

public class Conversa implements Serializable {
    private String chave, outroUser;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getOutroUser() {
        return outroUser;
    }

    public void setOutroUser(String outroUser) {
        this.outroUser = outroUser;
    }
}
