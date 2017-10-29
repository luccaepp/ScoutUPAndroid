package com.tcc.lucca.scoutup.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.tcc.lucca.scoutup.model.Especialidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModalidadeActivity extends AppCompatActivity {

    private ListView listView;
    private String modalidade;
    private ListViewEspecialidadeAdapter adapter;
    private List<Especialidade> listEsp;
    private int count = 0, somaTotal = 0;
    private boolean isNivelUm = false, isNivelDois = false;
    private int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalidade);

        listView = findViewById(R.id.listView);


        TextView tvTitulo = findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvTitulo.setTypeface(type);

        final String modalidade = getIntent().getStringExtra("modalidade");
        tvTitulo.setText(modalidade);
        this.modalidade = modalidade;

        adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), null);

        carrega();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Especialidade esp = listEsp.get(i);

                Intent intent = new Intent(getApplicationContext(), EspecialidadeActivity.class);

                Bundle bundle = new Bundle();

                bundle.putParcelable("especialidade", esp);

                try {

                    bundle.putString("requisitos", esp.getRequisitos());

                } catch (Exception e) {


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


        FirebaseDatabase.getInstance().getReference().child("escopoProgressao").child("atividadesSenior").child("Modalidade").child(modalidade).addValueEventListener(new ValueEventListener() {
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

                    calculaPorcentagem();



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
        calculaPorcentagem();



    }

    private void calculaPorcentagem() {


        for (Especialidade esp : listEsp) {

            try{
                if (esp.getNivel() == 1 && !isNivelUm && !isNivelDois) {

                    somaTotal += 11;
                    isNivelUm = true;

                }
                if (esp.getNivel() == 2 && !isNivelDois) {

                    somaTotal += 22;
                    isNivelDois = true;
                    if (isNivelUm) {
                        somaTotal -= 11;
                    }


                }


                if (esp.getNivel() == 3) {

                    count += 1;
                    if (count >= 3) {

                        somaTotal = 100;

                    }

                    if (count == 2) {

                        somaTotal += 33;


                    }
                    if (count == 1) {

                        somaTotal += 33;

                    }


                }

            }catch(Exception e){}

        }

        FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("atividadesRamo").child("modalidades").child(modalidade).setValue(somaTotal);
        somaTotal=0;
        count=0;
        isNivelUm=false;
        isNivelDois=false;



    }
}