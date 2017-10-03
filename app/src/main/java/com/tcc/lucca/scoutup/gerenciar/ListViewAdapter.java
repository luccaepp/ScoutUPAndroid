package com.tcc.lucca.scoutup.gerenciar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lucca.scoutup.R;

import java.util.List;

/**
 * Created by lucca on 02/10/17.
 */

public class ListViewAdapter extends BaseAdapter {


    private List<String> info;
    private Context context;
    private LayoutInflater layoutInflater;


    public ListViewAdapter(List<String> data, Context context) {
        super();
        this.info = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.listview_info, parent, false);
        TextView textView = view.findViewById(R.id.textViewLVInfo);
        textView.setText(info.get(position));
        Log.d("TAG", info.get(position) + " oi");

        return null;
    }

}
