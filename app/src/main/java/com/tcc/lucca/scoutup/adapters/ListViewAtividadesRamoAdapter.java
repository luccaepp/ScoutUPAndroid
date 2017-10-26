package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.progressao.Especialidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucca on 25/10/17.
 */

public class ListViewAtividadesRamoAdapter  extends ArrayAdapter<String> {


    private List<String> info = new ArrayList<>();
    private LayoutInflater layoutInflate;


    public ListViewAtividadesRamoAdapter(Context ctx, List<String> values) {
        super(ctx, 0, values);
        this.info = values;
        this.layoutInflate = LayoutInflater.from(ctx);
    }

    public void atualizarLista(List<String> lista) {

        this.info = lista;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String info = this.info.get(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.especialidade, viewGroup, false);
        }

        CheckBox checkBox = view.findViewById(R.id.checkBoxItem);

        checkBox.setText(info);



        return view;
    }
}