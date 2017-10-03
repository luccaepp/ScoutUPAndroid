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

    private List<Amigo> amigos;
    private Context context;

    public AmigoListAdapter(List<Amigo> amigos, Context context) {
        super(context, 0, amigos);

        this.amigos = amigos;
        this.context = context;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.amigo_item, viewGroup);
        TextView textView = view.findViewById(R.id.tvAmigo);
        textView.setText(amigos.get(i).getNome());
        return view;
    }
}
