package com.tcc.lucca.scoutup.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Atividade;

public class AtividadeActivity extends AppCompatActivity {

    private Atividade atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        Bundle bundle = getIntent().getExtras();
        atividade = bundle.getParcelable("atividade");

        TextView textView = (TextView) findViewById(R.id.tvTituloAtv);

        textView.setText(atividade.getTitulo());




    }
}
