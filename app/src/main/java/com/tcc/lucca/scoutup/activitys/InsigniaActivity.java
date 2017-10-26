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
import com.tcc.lucca.scoutup.adapters.ListViewInsigniasAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class InsigniaActivity extends AppCompatActivity {

    private ListView listView;
    private HashMap<String, Boolean> isFeita;
    private String insignia;
    private ListViewInsigniasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insignia);

        TextView tvTitulo = findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvTitulo.setTypeface(type);

        listView = findViewById(R.id.listView);

        final String insignia = getIntent().getStringExtra("insignia");
        this.insignia = insignia;
        tvTitulo.setText(insignia);


        caregarItens();



    }

    private void caregarItens() {



        isFeita = new HashMap<String, Boolean>();


        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Insignias").child(insignia).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final ArrayList<String> lista = (ArrayList<String>) dataSnapshot.getValue();

                adapter = new ListViewInsigniasAdapter(getApplicationContext(), lista, isFeita, insignia);
                listView.setAdapter(adapter);


                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                reference.child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("atividadesRamo").child("insignias").child(insignia).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {

                            int contItens = 0;

                            for (DataSnapshot snap: dataSnapshot.getChildren()) {

                                isFeita.put(snap.getKey().toString(), (Boolean) snap.getValue());

                                adapter.notifyDataSetChanged();


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
