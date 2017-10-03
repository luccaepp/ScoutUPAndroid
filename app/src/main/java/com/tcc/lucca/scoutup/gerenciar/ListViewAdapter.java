package com.tcc.lucca.scoutup.gerenciar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lucca.scoutup.R;

import java.util.List;

/**
 * Created by lucca on 02/10/17.
 */

public class ListViewAdapter extends ArrayAdapter<String> {


    private List<String> info;
    private LayoutInflater layoutInflate;


    public ListViewAdapter(Context ctx, List<String> info) {
        super(ctx, 0, info);
        this.info = info;
        this.layoutInflate = LayoutInflater.from(ctx);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String info = getItem(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.listview_info, viewGroup, false);
        }

        TextView txtItem = view.findViewById(R.id.textViewLVInfo);
        txtItem.setText(info);

        return view;
    }

}
