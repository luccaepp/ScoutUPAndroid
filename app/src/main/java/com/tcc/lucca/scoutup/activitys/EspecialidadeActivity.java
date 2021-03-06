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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListViewItemEspecialidadeAdapter;
import com.tcc.lucca.scoutup.model.Especialidade;

import java.util.HashMap;

public class EspecialidadeActivity extends AppCompatActivity {

    private Especialidade especialidade;
    private ListView listViewItensEsp;
    private String id;
    private String requisitos;
    private String area;
    private ListViewItemEspecialidadeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requisitos = getIntent().getExtras().getString("requisitos");

        if(requisitos != null){

            setContentView(R.layout.activity_especialidade);
            TextView tvPreRequisitos = findViewById(R.id.tvPreRequisitos);

            tvPreRequisitos.setText(requisitos);
            Log.d("TAG", requisitos);




        } else{

            setContentView(R.layout.activity_especialidade__sem_requisito);
            Log.d("TAG", "sem requisito");



        }

        especialidade = getIntent().getExtras().getParcelable("especialidade");

        TextView tvTitulo = findViewById(R.id.textViewTitulo);


        especialidade = getIntent().getExtras().getParcelable("especialidade");
        id = getIntent().getExtras().getString("id");
        area = getIntent().getExtras().getString("area");
        especialidade.setItens(getIntent().getExtras().getStringArrayList("lista"));

        tvTitulo.setText(especialidade.getNome());

        listViewItensEsp = findViewById(R.id.listViewItensEsp);




        if (especialidade.getItens() != null) {

            final HashMap<String, Boolean> isFeita = new HashMap<String,Boolean>();

            adapter = new ListViewItemEspecialidadeAdapter(getApplicationContext(), especialidade.getItens(), id, isFeita, area);

            final int size = especialidade.getItens().size();

            listViewItensEsp.setAdapter(adapter);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            reference.child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    try {

                        int contItens = 0;


                        for (DataSnapshot snap: dataSnapshot.getChildren()) {

                           isFeita.put(snap.getKey().toString(), (Boolean) snap.getValue());

                            for (Boolean b:isFeita.values()) {

                                if(b){
                                    ++contItens;


                                }

                            }

                            if(contItens>=(size/3)){

                                if(contItens>=((size/3)*2)){

                                    if(contItens==size){

                                        FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(id).child("nivel").setValue(3);
                                        adapter.notifyDataSetChanged();



                                    }else{
                                        FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(id).child("nivel").setValue(2);
                                        adapter.notifyDataSetChanged();



                                    }
                                }else{

                                    FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(id).child("nivel").setValue(1);
                                    adapter.notifyDataSetChanged();




                                }
                            }else{
                                FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(id).child("nivel").setValue(0);


                            }

                            contItens=0;
                            adapter.atualizarListaFeitas(isFeita);
                            adapter.notifyDataSetChanged();



                        }



                    } catch (Exception e) {
                        adapter = new ListViewItemEspecialidadeAdapter(getApplicationContext(), especialidade.getItens(), id, isFeita, area);
                        adapter.atualizarListaFeitas(isFeita);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public synchronized void onCancelled(DatabaseError databaseError) {

                }
            });



        }



    }

}





