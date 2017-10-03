package com.tcc.lucca.scoutup.gerenciar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Amigo;

import java.util.List;

/**
 * Created by lucca on 03/10/17.
 */

public class AmigoListAdapter extends BaseAdapter {

    private List<Amigo> amigos;
    private Context context;

    public AmigoListAdapter(List<Amigo> amigos, Context context) {
        this.amigos = amigos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.amigo_item, viewGroup);
        TextView textView = view.findViewById(R.id.tvAmigo);
        textView.setText(amigos.get(i).getNome());
        return null;
    }
}
