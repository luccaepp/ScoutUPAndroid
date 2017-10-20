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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

import static com.tcc.lucca.scoutup.R.id.listViewAmigos;

public class AreaEspecialidadeActivity extends AppCompatActivity {


    private Areas area;
    private ListView listView;
    private List<Especialidade> especialidadeList= new ArrayList<Especialidade>();


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

                   String key =  espSnap.getKey();

                   Especialidade esp = espSnap.getValue(Especialidade.class);

                    especialidadeList.add(esp);
                }


                ListViewEspecialidadeAdapter adapter = new ListViewEspecialidadeAdapter(getApplicationContext(), especialidadeList);


                listView.setAdapter(adapter);

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
                bundle.putStringArrayList("lista", (ArrayList<String>) esp.getItens());

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
