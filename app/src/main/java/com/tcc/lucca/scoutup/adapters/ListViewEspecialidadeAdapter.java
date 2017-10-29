package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Especialidade;

import java.util.List;


public class ListViewEspecialidadeAdapter extends ArrayAdapter<Especialidade> {


    private List<Especialidade> info;
    private LayoutInflater layoutInflate;


    public ListViewEspecialidadeAdapter(Context ctx, List<Especialidade> values) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
    }

    public void atualizarLista(List<Especialidade> lista) {

        this.info = lista;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         Especialidade info = this.info.get(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.especialidades, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.imageView27);


        try{

            int nivel = info.getNivel();
            if(nivel ==1){

                imageView.setImageResource(R.drawable.nivelum);



            }
            if(nivel ==2){

                imageView.setImageResource(R.drawable.niveldois);



            }
            if(nivel ==3){


                imageView.setImageResource(R.drawable.niveltres);


            }
            if(nivel == 0){

                imageView.setImageResource(R.drawable.nivelzero);


            }

        }catch (Exception e){
            imageView.setImageResource(R.drawable.nivelzero);

        }

        TextView txtItem = view.findViewById(R.id.textView26);
        txtItem.setText(info.getNome());
        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
        txtItem.setTypeface(type);

        return view;
    }

}
