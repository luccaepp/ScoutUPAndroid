package com.tcc.lucca.scoutup.gerenciar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Atividade;

import java.util.List;

/**
 * Created by lucca on 03/10/17.
 */

public class AtividadesAdapter extends ArrayAdapter {

    public LayoutInflater layoutInflate;
    private List<Atividade> atividades;
    private Context context;


    public AtividadesAdapter(Context ctx, List<Atividade> info) {
        super(ctx, 0, info);
        this.atividades = info;
        this.layoutInflate = LayoutInflater.from(ctx);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Atividade atividade = (Atividade) getItem(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.atividadeitem, viewGroup, false);
        }

        TextView tvNomeAt = view.findViewById(R.id.tvNomeAt);
        TextView tvInicio = view.findViewById(R.id.tvInicio);
        TextView tvFim = view.findViewById(R.id.tvFim);
        TextView tvEndereco = view.findViewById(R.id.tvEndereco);
        tvNomeAt.setText(atividade.getTitulo());
        tvInicio.setText(atividade.getInicio());
        tvFim.setText(atividade.getTermino());
        tvEndereco.setText(atividade.getLocal().getEndereco());


        return view;
    }
}