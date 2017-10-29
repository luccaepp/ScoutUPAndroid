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
import android.widget.TextView;

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


    public ParticipanteAdapter(Context ctx, List<Usuario> values, List<String> confirmados, List<String> presentes, boolean isStarted ) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
        this.confirmados = confirmados;
        this.presentes = presentes;
        this.isStarted = isStarted;
    }

    public void atualizarLista(List<Usuario> lista) {

        this.info = lista;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String info = getItem(i).getNome();

        Log.d("TAG", ""+isStarted);


        if(!isStarted){

            Log.d("TAG", "nao comecou");

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

            Log.d("TAG",  "comecou");

            if (view == null) {
                view = layoutInflate.inflate(R.layout.presente, viewGroup, false);
            }
            CheckBox checkBox = view.findViewById(R.id.checkBoxItem);

            checkBox.setChecked(false);
            for (String presente: presentes) {

                if(getItem(i).getId().equals(presente)){


                    checkBox.setChecked(true);

                }

            }

            checkBox.setText(info);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
            checkBox.setTypeface(type);

            return view;


        }



    }

}