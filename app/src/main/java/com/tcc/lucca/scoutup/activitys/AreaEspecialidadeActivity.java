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
import com.tcc.lucca.scoutup.adapters.AmigoListAdapter;
import com.tcc.lucca.scoutup.adapters.ListViewEspecialidadeAdapter;
import com.tcc.lucca.scoutup.gerenciar.AreaDAO;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Usuario;
import com.tcc.lucca.scoutup.model.progressao.Areas;
import com.tcc.lucca.scoutup.model.progressao.Especialidade;
import com.tcc.lucca.scoutup.model.progressao.Especialidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.tcc.lucca.scoutup.R.id.listViewAmigos;

public class AreaEspecialidadeActivity extends AppCompatActivity {


    private Areas area;
    private ListView listView;
    private List<Especialidade> especialidadeList= new ArrayList<Especialidade>();
    private List<String> idList= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_especialidade);

        listView = (ListView) findViewById(R.id.lvEspecialidades);


        TextView tvAgenda = findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvAgenda.setTypeface(type);

        String area = getIntent().getStringExtra("area");
        tvAgenda.setText(area);


        final AreaDAO dao = new AreaDAO();

        dao.listar(area).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot espSnap : dataSnapshot.getChildren()){

                   final String key =  espSnap.getKey();

                   final Especialidade esp = espSnap.getValue(Especialidade.class);

                    FirebaseDatabase.getInstance().getReference().child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("especialidades").child(key).child("nivel").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            try{
                                int nivel = Integer.parseInt(dataSnapshot.getValue().toString());
                                esp.setNivel(nivel);

                            }catch (Exception e){

                            }

                            idList.add(key);
                            especialidadeList.add(esp);
                            ListViewEspecialidadeAdapter adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), especialidadeList);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

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
                bundle.putString("id", idList.get(i));

                intent.putExtras(bundle);

                startActivity(intent);



            }
        });







    }



    public Areas getArea() {
        return area;
    }

    public void setArea(Areas area) {
        this.area = area;
    }
}
