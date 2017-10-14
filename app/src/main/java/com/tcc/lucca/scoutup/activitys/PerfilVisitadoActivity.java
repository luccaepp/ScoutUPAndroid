package com.tcc.lucca.scoutup.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Usuario;

public class PerfilVisitadoActivity extends AppCompatActivity {

    private Amigo amigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_visitado);

        Bundle bundle = getIntent().getExtras();
        amigo = bundle.getParcelable("amigo");

        TextView textView = (TextView) findViewById(R.id.tvUserVisitado);

        textView.setText(amigo.getNome());

    }
}
