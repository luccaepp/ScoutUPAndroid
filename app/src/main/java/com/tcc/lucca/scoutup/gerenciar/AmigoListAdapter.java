package com.tcc.lucca.scoutup.gerenciar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Amigo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucca on 08/10/17.
 */

public class AmigoListAdapter extends ArrayAdapter<Amigo> {


    private List<Amigo> info = new ArrayList<>();
    private LayoutInflater layoutInflate;


    public AmigoListAdapter(Context ctx, List<Amigo> values) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
    }

    public void atualizarLista(List<Amigo> lista) {

        this.info = lista;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String info = getItem(i).getNome();
        if (view == null) {
            view = layoutInflate.inflate(R.layout.listview_info, viewGroup, false);
        }

        TextView txtItem = view.findViewById(R.id.textViewLVInfo);
        txtItem.setText(info);

        return view;
    }

}