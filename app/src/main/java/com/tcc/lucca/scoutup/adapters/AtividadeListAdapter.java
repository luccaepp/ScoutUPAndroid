package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Atividade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AtividadeListAdapter extends ArrayAdapter<Atividade> {


    private List<Atividade> info = new ArrayList<>();
    private LayoutInflater layoutInflate;


    public AtividadeListAdapter(Context ctx, List<Atividade> values) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
    }

    public void atualizarLista(List<Atividade> lista) {

        this.info = lista;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Atividade info = getItem(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.list_atividades, viewGroup, false);
        }

        TextView txtItem = view.findViewById(R.id.tvTitulo);
        TextView tvDataInicio = view.findViewById(R.id.tvInicio);
        TextView tvDataFim = view.findViewById(R.id.tvFim);

        txtItem.setText(info.getTitulo()+" - "+info.getTipo());
        tvDataFim.setText("Fim "+getDate(info.getTermino()));
        tvDataInicio.setText("Inicio "+getDate(info.getInicio()));



        return view;
    }



    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("HH:mm dd/MM/yyyy", cal).toString();
        return date;
    }

}