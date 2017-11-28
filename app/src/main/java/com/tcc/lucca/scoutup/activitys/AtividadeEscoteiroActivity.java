package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.location.Geocoder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ParticipanteAdapter;
import com.tcc.lucca.scoutup.adapters.StringAdapter;
import com.tcc.lucca.scoutup.model.Atividade;
import com.tcc.lucca.scoutup.model.Participante;
import com.tcc.lucca.scoutup.model.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AtividadeEscoteiroActivity extends AppCompatActivity {

    private TextView textViewTituilo;
    private TextView tvAtividade;
    private TextView tvDataInicio;
    private TextView tvDataFim;
    private TextView tvEndereco;
    private String dataInicio;
    private String dataFim;
    private String idUsuario;
    private String idAtividade;
    private Atividade atividade;
    private List<String> materiais = new ArrayList<>();
    private List<Participante> participantes;
    private Long inicioTime;
    private boolean isStarded = false;
    private Switch aSwitch;


    private LatLng latLng;
    private FragmentManager fragmentManager;
    private ListView lvParticipantes;
    private ListView lvMaterias;
    private List<String> confirmados = new ArrayList<>();
    private ParticipanteAdapter adapterPart;
    private List<String> presentes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();


        inicioTime = bundle.getLong("inicioTime");



        Long current = Long.parseLong(System.currentTimeMillis() + "");

        if (current < inicioTime) {
            setContentView(R.layout.activity_atividade);

            carregarConfirmacao();
        }else{
            setContentView(R.layout.activity_atividade_iniciada);

        }




        atividade = bundle.getParcelable("atividade");
        latLng = bundle.getParcelable("local");
        dataFim = bundle.getString("fim");
        dataInicio = bundle.getString("inicio");
        idAtividade = bundle.getString("id");
        materiais = bundle.getStringArrayList("materiais");
        participantes = bundle.getParcelableArrayList("participantes");
        idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d("TAG", idUsuario);
        Log.d("TAG", idAtividade);

        initComponents();

        initData();










    }


    private void escreveNaBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("atividade").child(idAtividade).child("confirmados").child(idUsuario).child("isParticipante").setValue(true);


    }

    private void removeDaBase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("atividade").child(idAtividade).child("confirmados").child(idUsuario).setValue(null);

    }

    private void initData() {

        textViewTituilo.setText(atividade.getTitulo()+" - "+atividade.getTipo());

        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");

        tvAtividade.setTypeface(type);

        tvDataInicio.setText("Inicio: "+dataInicio);
        tvDataFim.setText("Fim: "+dataFim);


        Geocoder geo = new Geocoder(this);

        String adress = "";

        try {
            adress = geo.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(adress.indexOf("-") != -1)
            adress = adress.substring(0, adress.indexOf("-"));
        tvEndereco.setText(adress);
        carregarMap();

        try {

            if(materiais != null) {
                StringAdapter adapterMat = new StringAdapter(this, materiais);

                lvMaterias.setAdapter(adapterMat);
                carregarParticipantes();
            }
        }catch (Exception e){}

    }

    private void carregarParticipantes() {


        final List<Usuario> users = new ArrayList<>();

        Long current = Long.parseLong(System.currentTimeMillis() + "");




        if (current < inicioTime) {



            FirebaseDatabase.getInstance().getReference().child("atividade").child(idAtividade).child("confirmados").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{

                        for (DataSnapshot snap:dataSnapshot.getChildren()) {

                            String id = snap.getKey();
                            HashMap<String, Boolean> map = (HashMap<String, Boolean>) snap.getValue();
                            boolean isParticipante = map.get("isParticipante");

                            if(isParticipante){
                                confirmados.add(id);
                                adapterPart.notifyDataSetChanged();
                                if(id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                                aSwitch.setChecked(true);


                                }
                            }


                        }


                    }catch (Exception e){


                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            isStarded = false;

        }else{


            FirebaseDatabase.getInstance().getReference().child("atividade").child(idAtividade).child("presentes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try{

                        for (DataSnapshot snap:dataSnapshot.getChildren()) {

                            String id = snap.getKey();
                            boolean map = snap.getValue(Boolean.class);

                            if(map){
                                presentes.add(id);
                                adapterPart.notifyDataSetChanged();
                            }

                        }


                    }catch (Exception e){


                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            isStarded = true;


        }

        adapterPart = new ParticipanteAdapter(this, users, confirmados, presentes, isStarded, idAtividade, "escoteiro");


        lvParticipantes.setAdapter(adapterPart);

        for (Participante participante:participantes) {

            try{


                String chave = participante.getChave();
                FirebaseDatabase.getInstance().getReference().child("usuario").orderByChild("grupo").equalTo(chave).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        user.setId(dataSnapshot.getKey());

                        if(user.getTipo().equals("escoteiro"))
                            users.add(user);
                        adapterPart.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                FirebaseDatabase.getInstance().getReference().child("usuario").orderByChild("secao/chave").equalTo(chave).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        user.setId(dataSnapshot.getKey());

                        users.add(user);
                        adapterPart.notifyDataSetChanged();



                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }catch (Exception e){}


        }


    }

    private void carregarConfirmacao() {



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        Log.d("TAG", FirebaseAuth.getInstance().getCurrentUser().getUid());

        try {



            reference.child("atividade").child(idAtividade).child("confirmados").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.d("TAG", "é participante data");


                    try {
                        HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();

                        boolean isParticipante = map.get("isParticipante");
                        Log.d("TAG", "é participante "+isParticipante);
                        aSwitch.setChecked(isParticipante);
                    } catch (Exception e) {

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b){

                        escreveNaBase();


                    }else{

                        removeDaBase();


                    }


                }

            });


        }catch (Exception e){}


    }


    private void initComponents() {

        aSwitch = (Switch) findViewById(R.id.switch1);

        textViewTituilo = (TextView) findViewById(R.id.tvTituloAtv);
        tvAtividade = (TextView) findViewById(R.id.tvAtividade);
        tvDataFim = (TextView) findViewById(R.id.tvDataFim);
        tvDataInicio = (TextView) findViewById(R.id.tvDataInicio);
        tvEndereco = (TextView) findViewById(R.id.tvEndereco);
        lvParticipantes = (ListView) findViewById(R.id.lvParticipantes);
        lvMaterias = (ListView) findViewById(R.id.lvMateriais);



    }

    private void carregarMap() {

        fragmentManager = getSupportFragmentManager();

        MapFrag2 newFragment = new MapFrag2();

        Bundle args = new Bundle();

        args.putParcelable("LatLng", latLng);

        newFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, newFragment, "Maps Frag");

        fragmentTransaction.commitAllowingStateLoss();


    }
}
