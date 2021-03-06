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
import com.tcc.lucca.scoutup.gerenciar.AreaDAO;
import com.tcc.lucca.scoutup.model.Especialidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AreaEspecialidadeActivity extends AppCompatActivity {


    private ListView listView;
    private List<Especialidade> especialidadeList= new ArrayList<Especialidade>();
    private ListViewEspecialidadeAdapter adapter;
    private String area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_especialidade);

        listView = (ListView) findViewById(R.id.lvEspecialidades);

        adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), null);

        TextView tvAgenda = findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvAgenda.setTypeface(type);

        final String area = getIntent().getStringExtra("area");
        tvAgenda.setText(area);
        this.area = area;

        carrega();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Especialidade esp = especialidadeList.get(i);

                Intent intent = new Intent(getApplicationContext(), EspecialidadeActivity.class);

                Bundle bundle = new Bundle();

                bundle.putParcelable("especialidade", esp);

                try{

                    bundle.putString("requisitos", esp.getRequisitos());

                }catch (Exception e){



                }
                bundle.putStringArrayList("lista", (ArrayList<String>) esp.getItens());
                bundle.putString("id", esp.getId());
                bundle.putString("area", area);

                intent.putExtras(bundle);

                startActivity(intent);



            }
        });






    }

    private void carrega() {

        final AreaDAO dao = new AreaDAO();

        dao.listar(area).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Especialidade> list = (HashMap<String, Especialidade>) dataSnapshot.getValue();

                final int size = list.size();

                especialidadeList = new ArrayList<>();



                for(DataSnapshot espSnap : dataSnapshot.getChildren()){

                    final String key =  espSnap.getKey();

                    final Especialidade esp = espSnap.getValue(Especialidade.class);

                    FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(key).child("nivel").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            try{
                                int nivel = Integer.parseInt(dataSnapshot.getValue().toString());
                                esp.setNivel(nivel);

                            }catch (Exception e){

                            }

                            if(especialidadeList.size()<size){

                                esp.setId(key);
                                especialidadeList.add(esp);
                                Collections.sort(especialidadeList);
                                adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), especialidadeList);
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





    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();


    }

}
