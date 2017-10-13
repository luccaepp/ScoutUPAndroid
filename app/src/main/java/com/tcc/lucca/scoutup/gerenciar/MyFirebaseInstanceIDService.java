package com.tcc.lucca.scoutup.gerenciar;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by lucca on 09/10/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public final String TAG = "myfirebase";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        storeToken(refreshedToken);

    }

    private void storeToken(String refreshedToken) {

        SharedPrefManager.getInstance(getApplicationContext()).storeToken(refreshedToken);


    }
}
