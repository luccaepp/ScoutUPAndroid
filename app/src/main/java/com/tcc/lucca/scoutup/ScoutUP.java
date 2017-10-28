package com.tcc.lucca.scoutup;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lucca on 28/10/17.
 */

public class ScoutUP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
