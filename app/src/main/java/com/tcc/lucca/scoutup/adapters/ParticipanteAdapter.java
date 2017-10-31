package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucca on 27/10/17.
 */

public class ParticipanteAdapter extends ArrayAdapter<Usuario> {


    private List<Usuario> info = new ArrayList<>();
    private LayoutInflater layoutInflate;
    private List<String> confirmados;
    private List<String> presentes;
    private boolean isStarted;
    private String idAtividade;
    private String tipo;
    private CheckBox checkBox;


    public ParticipanteAdapter(Context ctx, List<Usuario> values, List<String> confirmados, List<String> presentes, boolean isStarted, String idAtividade, String tipo ) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
        this.confirmados = confirmados;
        this.presentes = presentes;
        this.isStarted = isStarted;
        this.idAtividade = idAtividade;
        this.tipo = tipo;
    }

    public void atualizarLista(List<Usuario> lista) {

        this.info = lista;

    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        String info = getItem(i).getNome();

        if(!isStarted){

            if (view == null) {
                view = layoutInflate.inflate(R.layout.listview_info, viewGroup, false);
            }
            TextView txtItem = view.findViewById(R.id.textViewLVInfo);


            for (String confir: confirmados) {

                if(getItem(i).getId().equals(confir)){


                    txtItem.setTextColor(Color.BLUE);

                }

            }

            txtItem.setText(info);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
            txtItem.setTypeface(type);
            return view;

        }else{

            if (view == null) {
                view = layoutInflate.inflate(R.layout.presente, viewGroup, false);
            }


            checkBox = view.findViewById(R.id.checkBoxItem);

            notifyDataSetChanged();


            checkBox.setText(info);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
            checkBox.setTypeface(type);


            Log.d("TAG", presentes.indexOf(getItem(i).getId())+" "+getItem(i).getNome());

            Log.d("TAG", (presentes.indexOf(getItem(i).getId()) >= 0 )+"");

            checkBox.setChecked(presentes.indexOf(getItem(i).getId()) >= 0);

            if(tipo.equals("escotista")) {


                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckBox temp = (CheckBox)view;
                        salvarChamada(i, temp.isChecked());
                    }
                });

            }


            return view;


        }



    }

    private void atualizarPresentes(String id) {
        for (String presente: presentes) {

            if(id.equals(presente)){


                if(!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                }
            }

        }


    }


    private void salvarChamada(int i, boolean b) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("atividade").child(idAtividade).child("presentes").child(getItem(i).getId()).setValue(b);

        presentes = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("atividade").child(idAtividade).child("presentes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{

                    for (DataSnapshot snap:dataSnapshot.getChildren()) {

                        String id = snap.getKey();
                        boolean map = snap.getValue(Boolean.class);

                        if(map){
                            presentes.add(id);
                            notifyDataSetChanged();
                        }

                    }


                }catch (Exception e){


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



}