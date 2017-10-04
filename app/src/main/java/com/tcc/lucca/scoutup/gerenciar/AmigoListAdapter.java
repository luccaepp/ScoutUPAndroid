package com.tcc.lucca.scoutup.gerenciar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Amigo;

import java.util.List;

/**
 * Created by lucca on 03/10/17.
 */

public class AmigoListAdapter extends ArrayAdapter {

    public LayoutInflater layoutInflate;
    private List<Amigo> amigos;
    private Context context;


    public AmigoListAdapter(Context ctx, List<Amigo> info) {
        super(ctx, 0, info);
        this.amigos = info;
        this.layoutInflate = LayoutInflater.from(ctx);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Amigo amigo = (Amigo) getItem(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.listview_info, viewGroup, false);
        }

        TextView txtItem = view.findViewById(R.id.textViewLVInfo);
        txtItem.setText(amigo.getNome());

        return view;
    }

}
