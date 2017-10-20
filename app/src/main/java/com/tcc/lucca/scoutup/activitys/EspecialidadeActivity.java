package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.ListViewEspecialidadeAdapter;
import com.tcc.lucca.scoutup.adapters.ListViewItemEspecialidadeAdapter;
import com.tcc.lucca.scoutup.model.progressao.Especialidade;

import java.util.ArrayList;
import java.util.List;

public class EspecialidadeActivity extends AppCompatActivity {

    private Especialidade especialidade;
    private ListView listViewItensEsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidade);

        TextView tvTitulo = findViewById(R.id.textViewTitulo);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvTitulo.setTypeface(type);


        especialidade = getIntent().getExtras().getParcelable("especialidade");
        especialidade.setItens(getIntent().getExtras().getStringArrayList("lista"));

        tvTitulo.setText(especialidade.getNome());

        listViewItensEsp = findViewById(R.id.listViewItensEsp);

        if (especialidade.getItens() != null) {

            ListViewItemEspecialidadeAdapter adapter = new ListViewItemEspecialidadeAdapter(this, especialidade.getItens());

            listViewItensEsp.setAdapter(adapter);

        }
    }
}
