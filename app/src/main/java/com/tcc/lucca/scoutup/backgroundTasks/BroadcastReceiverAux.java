package com.tcc.lucca.scoutup.backgroundTasks;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tcc.lucca.scoutup.R;

public class BroadcastReceiverAux extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Script", "-> Alarme");

        String id = intent.getExtras().getString("id");
        gerarNotificacao(context, intent, intent.getExtras().getString("id"), intent.getExtras().getString("titulo"), intent.getExtras().getString("tipo"));

        Intent localizar = new Intent(context, CalculoGeolocalizacaoService.class);
        localizar.putExtra("id", id );
        context.startService(localizar);


    }


    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher_round, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception e){}
    }
}
