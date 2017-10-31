package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tcc.lucca.scoutup.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListViewItemEspecialidadeAdapter extends ArrayAdapter<String> {


    private List<String> info = new ArrayList<>();
    private LayoutInflater layoutInflate;
    private String especialidadeID, area;
    private HashMap<String, Boolean> isFeita;
    private CheckBox checkBox;


    public ListViewItemEspecialidadeAdapter(Context ctx, List<String> values, String especialidadeID, HashMap<String, Boolean> isFeita, String area) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
        this.especialidadeID = especialidadeID;
        this.isFeita = isFeita;
        this.area = area;
    }

    public void atualizarLista(List<String> lista) {

        this.info = lista;

    }
    public void atualizarListaFeitas(HashMap<String, Boolean> isFeita) {

        this.isFeita = isFeita;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        String info = this.info.get(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.especialidade, viewGroup, false);
        }
        checkBox = view.findViewById(R.id.checkBoxItem);
        checkBox.setText(info);
        checkBox.setTextColor(Color.BLACK);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
        checkBox.setTypeface(type);

        try{

            boolean b = isFeita.get(Integer.toString(i+1));

            checkBox.setChecked(b);

        }catch (Exception e){
            checkBox.setChecked(false);

        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox temp = (CheckBox)view;
                cadastrarItem(i, temp.isChecked());

            }
        });





        return view;
    }







    private void cadastrarItem(int i, boolean b) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        i++;
        databaseReference.child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(area).child("especialidades").child(especialidadeID).child(Integer.toString(i)).setValue(b);


    }

}
