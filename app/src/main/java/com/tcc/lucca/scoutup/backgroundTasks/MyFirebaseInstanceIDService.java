package com.tcc.lucca.scoutup.backgroundTasks;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by lucca on 09/10/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        storeToken(refreshedToken);

    }

    private void storeToken(String refreshedToken) {

        SharedPrefManager.getInstance(getApplicationContext()).storeToken(refreshedToken);


    }
}
