package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.location.Geocoder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Atividade;

import java.io.IOException;
import java.util.HashMap;

public class AtividadeActivity extends AppCompatActivity {

    private TextView textViewTituilo;
    private TextView tvAtividade;
    private TextView tvDataInicio;
    private TextView tvDataFim;
    private TextView tvEndereco;
    private String dataInicio;
    private String dataFim;
    private Switch aSwitch;
    private String idUsuario;
    private String idAtividade;
    private Atividade atividade;
    private LatLng latLng;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_atividade);

        Bundle bundle = getIntent().getExtras();

        atividade = bundle.getParcelable("atividade");
        latLng = bundle.getParcelable("local");
        dataFim = bundle.getString("fim");
        dataInicio = bundle.getString("inicio");
        idAtividade = bundle.getString("id");
        idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        initComponents();

        initData();

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








    }


    private void escreveNaBase() {

        Log.d("TAG", "escreve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("atividade").child(idAtividade).child("confirmados").child(idUsuario).child("isParticipante").setValue(true);


    }

    private void removeDaBase() {
        Log.d("TAG", "deleta");

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

        adress = adress.substring(0, adress.indexOf("-"));
        tvEndereco.setText(adress);
        carregarMap();

        carregarConfirmacao();

    }

    private void carregarConfirmacao() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("atividade").child(idAtividade).child("confirmados").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();

                    boolean isParticipante = map.get("isParticipante");
                    aSwitch.setChecked(isParticipante);
                }catch (Exception e){

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void initComponents() {

        textViewTituilo = (TextView) findViewById(R.id.tvTituloAtv);
        aSwitch = (Switch) findViewById(R.id.switch1);
        tvAtividade = (TextView) findViewById(R.id.tvAtividade);
        tvDataFim = (TextView) findViewById(R.id.tvDataFim);
        tvDataInicio = (TextView) findViewById(R.id.tvDataInicio);
        tvEndereco = (TextView) findViewById(R.id.tvEndereco);



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
