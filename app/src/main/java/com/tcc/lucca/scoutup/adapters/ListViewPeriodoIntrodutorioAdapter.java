package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.util.Log;
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

/**
 * Created by lucca on 26/10/17.
 */

public class ListViewPeriodoIntrodutorioAdapter  extends ArrayAdapter<String> {


    private List<String> info = new ArrayList<>();
    private LayoutInflater layoutInflate;
    private HashMap<String, Boolean> isFeita;
    private int count = 0;


    public ListViewPeriodoIntrodutorioAdapter(Context ctx, List<String> values, HashMap<String, Boolean> isFeita) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
        this.isFeita = isFeita;


    }

    public void atualizarLista(List<String> lista) {

        this.info = lista;
        count = 0 ;


    }
    public void atualizarListaFeitas(HashMap<String, Boolean> isFeita) {

        this.isFeita = isFeita;
        count = 0 ;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        String info = this.info.get(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.especialidade, viewGroup, false);
        }

        CheckBox checkBox = view.findViewById(R.id.checkBoxItem);

        checkBox.setText(info);

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
        databaseReference.child("progressaoUsuario").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("atividadesRamo").child("periodoIntrodutorio").child(Integer.toString(i)).setValue(b);


    }
}