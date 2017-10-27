package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Participante;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucca on 27/10/17.
 */

public class ParticipanteAdapter extends ArrayAdapter<Participante> {


private List<Participante> info = new ArrayList<>();
private LayoutInflater layoutInflate;


public ParticipanteAdapter(Context ctx, List<Participante> values) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
        }

public void atualizarLista(List<Participante> lista) {

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