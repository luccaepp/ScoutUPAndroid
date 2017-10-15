package com.tcc.lucca.scoutup.backgroundTasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.tcc.lucca.scoutup.R;

/**
 * Created by lucca on 13/10/17.
 */

public class MyNotificationManager {

    private static final int NOTIFICATION_ID = 123;
    private Context context;

    public MyNotificationManager(Context context) {
        this.context = context;
    }



    public void showNotification(String from, String notifi, Intent intent){

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                 .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notifi)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round)).build();


        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);



    }

}
