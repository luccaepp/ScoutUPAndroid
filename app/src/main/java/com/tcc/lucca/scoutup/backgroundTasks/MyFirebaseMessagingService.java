package com.tcc.lucca.scoutup.backgroundTasks;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tcc.lucca.scoutup.activitys.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public final String TAG = "myfirebase";
    private MyNotificationManager myNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        notifyUser(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());

    }


    public void notifyUser(String from, String notification){

    myNotificationManager = new MyNotificationManager(getApplicationContext());
    myNotificationManager.showNotification(from, notification,new Intent(getApplicationContext(), MainActivity.class));

    }


}
