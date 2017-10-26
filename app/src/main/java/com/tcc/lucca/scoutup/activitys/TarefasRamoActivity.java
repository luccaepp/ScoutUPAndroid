package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListViewAtividadesRamoAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TarefasRamoActivity extends AppCompatActivity {

    private ListView listaItens;
    private HashMap<String, Boolean> isFeita;
    private ListViewAtividadesRamoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas_ramo);

        TextView tvProgressao = (TextView) findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvProgressao.setTypeface(type);

        listaItens = findViewById(R.id.listView);


       caregarItens();


        }

    private void caregarItens() {


        isFeita = new HashMap<String, Boolean>();


        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("atividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();
                 adapter = new ListViewAtividadesRamoAdapter(getApplicationContext(), lista, isFeita);
                listaItens.setAdapter(adapter);


                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                reference.child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("atividadesRamo").child("atividades").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {

                            int contItens = 0;

                            for (DataSnapshot snap: dataSnapshot.getChildren()) {

                                isFeita.put(snap.getKey().toString(), (Boolean) snap.getValue());


                            }

                        } catch (Exception e) {
                        }

                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public synchronized void onCancelled(DatabaseError databaseError) {

                    }
                });






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
