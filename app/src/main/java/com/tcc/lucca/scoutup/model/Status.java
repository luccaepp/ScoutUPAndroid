package com.tcc.lucca.scoutup.model;

/**
 * Created by lucca on 08/10/17.
 */

public enum Status {

    offline,
    online;

    public static String devolveString(Status status) {

        if (status == offline) {

            return "offline";
        } else {
            return "online";


        }
    }


}
