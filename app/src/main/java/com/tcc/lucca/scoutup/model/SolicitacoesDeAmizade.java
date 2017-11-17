package com.tcc.lucca.scoutup.model;

import com.tcc.lucca.scoutup.activitys.MainActivity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lucca on 08/11/17.
 */

class SolicitacoesDeAmizade implements Serializable {


    public String timestamp;
    public Map<String, String>de;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getDe() {
        return de;
    }

    public void setDe(Map<String, String> de) {
        this.de = de;
    }
}
