package com.tcc.lucca.scoutup.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.lucca.scoutup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.gerenciar.GrupoDAO;
import com.tcc.lucca.scoutup.gerenciar.LoginClass;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Escoteiro;
import com.tcc.lucca.scoutup.model.Escotista;
import com.tcc.lucca.scoutup.model.Grupo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private RadioButton cbEscoteiro;
    private RadioButton cbEscotista;
    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etSenhaConf;
    private LoginClass loginClass = new LoginClass(this);
    private UsuarioDAO dao = new UsuarioDAO();
    private Spinner spinnerGrupo;
    private Spinner spinnerSecao;
    private GrupoDAO grupoDAO = new GrupoDAO();
    private List<Grupo> listGrupos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        cbEscoteiro = (RadioButton) findViewById(R.id.cbEscoteiro);
        cbEscotista = (RadioButton) findViewById(R.id.cbEscotista);
        etNome = (EditText) findViewById(R.id.etNomeCad);
        etEmail = (EditText) findViewById(R.id.etEmailCad);
        etSenha = (EditText) findViewById(R.id.etSenhaCad);
        etSenhaConf = (EditText) findViewById(R.id.etSenhaConfCad);
        spinnerGrupo = (Spinner) findViewById(R.id.spinnerGrupo);
        spinnerSecao = (Spinner) findViewById(R.id.spinnerSecao);
        final List<String> list = new ArrayList<String>();

        grupoDAO.listarTodos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Grupo> td = (HashMap<String, Grupo>) dataSnapshot.getValue();

                List<Grupo> valuesToMatch = new ArrayList<Grupo>(td.values());

                for (Grupo g : valuesToMatch) {

                    list.add(Integer.toString(g.getNumeral()) + " - " + g.getNome());


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupo.setAdapter(dataAdapter);


        cbEscoteiro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (cbEscoteiro.isChecked()) {
                    cbEscotista.setChecked(false);

                }

            }
        });


        cbEscotista.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (cbEscotista.isChecked()) {
                    cbEscoteiro.setChecked(false);
                }
            }
        });

    }

    public void cadastrar(View view) {

        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String conf = etSenhaConf.getText().toString().trim();

        if (senha.equals(conf)) {

            if (cbEscoteiro.isChecked()) {

                Escoteiro escoteiro = new Escoteiro(nome, email);
                loginClass.criarUser(email, senha);
                dao.adicionar(escoteiro);

            } else {

                Escotista escotista = new Escotista(nome, email);
                loginClass.criarUser(email, senha);
                dao.adicionar(escotista);


        }

    }


    }



}
