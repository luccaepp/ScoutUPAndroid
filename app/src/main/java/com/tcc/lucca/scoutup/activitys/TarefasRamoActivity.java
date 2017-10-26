package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListViewAtividadesRamoAdapter;
import com.tcc.lucca.scoutup.adapters.ListViewEspecialidadeAdapter;

import java.util.ArrayList;

public class TarefasRamoActivity extends AppCompatActivity {

    private ListView fisico, intelectual, carater, afetivo, social, espiritual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas_ramo);

        TextView tvProgressao = (TextView) findViewById(R.id.textView);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvProgressao.setTypeface(type);

        fisico = findViewById(R.id.listView);
        intelectual = findViewById(R.id.listView2);
        carater = findViewById(R.id.listView3);
        afetivo = findViewById(R.id.listView4);
        social = findViewById(R.id.listView5);
        espiritual = findViewById(R.id.listView6);


       caregarItens();


        }

    private void caregarItens() {


        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Fisico").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                fisico.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Intelectual").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                intelectual.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Carater").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                carater.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Afetivo").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                afetivo.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Social").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                social.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Espiritual").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                espiritual.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
