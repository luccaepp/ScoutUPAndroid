package com.tcc.lucca.scoutup.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListViewEspecialidadeAdapter;
import com.tcc.lucca.scoutup.gerenciar.AreaDAO;
import com.tcc.lucca.scoutup.model.progressao.Especialidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CordoesActivity extends AppCompatActivity {

    private ListView listView;
    private String cordao;
    private ListViewEspecialidadeAdapter adapter;
    private List<Especialidade> listEsp;
    private int count = 0, somaTotal = 0;
    private int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cordoes);

        listView = findViewById(R.id.listView);



        TextView tvTitulo = findViewById(R.id.textViewTitulo);
        TextView textviewTexto = findViewById(R.id.textviewTexto);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvTitulo.setTypeface(type);

        final String cordao = getIntent().getStringExtra("cordao");
        tvTitulo.setText(cordao);
        this.cordao = cordao;

        if(cordao.equals("Dourado")){

            textviewTexto.setText(R.string.dourado);

        }else{
            textviewTexto.setText(R.string.desafioSenior);


        }

        adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), null);

        carrega();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Especialidade esp = listEsp.get(i);

                Intent intent = new Intent(getApplicationContext(), EspecialidadeActivity.class);

                Bundle bundle = new Bundle();

                bundle.putParcelable("especialidade", esp);

                try{

                    bundle.putString("requisitos", esp.getRequisitos());

                }catch (Exception e){



                }
                bundle.putStringArrayList("lista", (ArrayList<String>) esp.getItens());
                bundle.putString("id", esp.getId());
                bundle.putString("area", esp.getArea());

                intent.putExtras(bundle);

                startActivity(intent);



            }
        });



    }

    private void carrega() {


        listEsp = new ArrayList<>();

        if (cordao.equals("Dourado")) {

            final AreaDAO dao = new AreaDAO();

            dao.listar("serviços").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    HashMap<String, Especialidade> list = (HashMap<String, Especialidade>) dataSnapshot.getValue();

                    final int size = list.size();

                    for(DataSnapshot espSnap : dataSnapshot.getChildren()){

                        final String key =  espSnap.getKey();

                        final Especialidade esp = espSnap.getValue(Especialidade.class);
                        esp.setArea("serviços");

                        FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("serviços").child("especialidades").child(key).child("nivel").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                try{
                                    int nivel = Integer.parseInt(dataSnapshot.getValue().toString());
                                    esp.setNivel(nivel);

                                }catch (Exception e){

                                }

                                if(listEsp.size()<size){

                                    esp.setId(key);
                                    listEsp.add(esp);
                                    Collections.sort(listEsp);

                                    adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), listEsp);
                                    listView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }






                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        } else {

            FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Cordoes").child(cordao).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    List<String> map = (List<String>) dataSnapshot.getValue();
                    size = map.size();


                    for (final String key : map) {

                        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("especialidades").child("ciencia e tecnologia").child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Especialidade esp = dataSnapshot.getValue(Especialidade.class);
                                    esp.setArea("ciencia e tecnologia");


                                    carregarFeitas(esp, key);


                                } catch (Exception e) {
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("especialidades").child("desportos").child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Especialidade esp = dataSnapshot.getValue(Especialidade.class);
                                    esp.setArea("desportos");

                                    carregarFeitas(esp, key);

                                } catch (Exception e) {
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("especialidades").child("cultura").child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Especialidade esp = dataSnapshot.getValue(Especialidade.class);
                                    esp.setArea("cultura");

                                    carregarFeitas(esp, key);

                                } catch (Exception e) {
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("especialidades").child("habilidades escoteiras").child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Especialidade esp = dataSnapshot.getValue(Especialidade.class);
                                    esp.setArea("habilidades escoteiras");

                                    carregarFeitas(esp, key);

                                } catch (Exception e) {
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("especialidades").child("serviços").child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Especialidade esp = dataSnapshot.getValue(Especialidade.class);
                                    esp.setArea("serviços");

                                    carregarFeitas(esp, key);

                                } catch (Exception e) {
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public void carregarFeitas(final Especialidade esp, final String key) {

        FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(esp.getArea()).child("especialidades").child(key).child("nivel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {

                    esp.setNivel(Integer.parseInt(dataSnapshot.getValue().toString()));




                } catch (Exception e) {

                }


                if (listEsp.size() < size) {

                    esp.setId(key);
                    listEsp.add(esp);
                    Collections.sort(listEsp);
                    adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), listEsp);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
        count=0;
        somaTotal=0;
       // carrega();


    }
}
