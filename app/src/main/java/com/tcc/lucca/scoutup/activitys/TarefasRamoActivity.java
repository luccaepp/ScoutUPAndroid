package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListViewAtividadesRamoAdapter;

import java.util.ArrayList;

public class TarefasRamoActivity extends AppCompatActivity {

    private ListView listaItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas_ramo);

        TextView tvProgressao = (TextView) findViewById(R.id.textView);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvProgressao.setTypeface(type);

        listaItens = findViewById(R.id.listView);


       caregarItens();


        }

    private void caregarItens() {



        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();


                ListViewAtividadesRamoAdapter adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista);
                listaItens.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
