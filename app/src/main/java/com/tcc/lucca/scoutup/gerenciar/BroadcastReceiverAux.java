package com.tcc.lucca.scoutup.gerenciar;


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
import com.tcc.lucca.scoutup.activitys.MainActivity;
import com.tcc.lucca.scoutup.model.Atividade;

public class BroadcastReceiverAux extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Script", "-> Alarme");

        String tipo = intent.getExtras().getString("tipo");
        String titulo = intent.getExtras().getString("titulo");
        String local = intent.getExtras().getString("local");

        if(tipo!=null || local!= null || titulo != null){

            gerarNotificacao(context, new Intent(context, MainActivity.class), "Nova mensagem", titulo, local + "  "+tipo );

            Log.d("Script", "-> noti");

        }else{

            gerarNotificacao(context, new Intent(context, MainActivity.class), "Nova mensagem", "Deu ruim","deu bem ruim");

        }

    }


    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_launcher, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception e){}
    }
}
