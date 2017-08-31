package com.tcc.lucca.scoutup.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.lucca.scoutup.R;
import com.tcc.lucca.scoutup.gerenciar.LoginClass;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Escoteiro;
import com.tcc.lucca.scoutup.model.Escotista;
import com.tcc.lucca.scoutup.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private RadioButton cbEscoteiro;
    private RadioButton cbEscotista;
    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etSenhaConf;
    private LoginClass loginClass = new LoginClass(this);
    private UsuarioDAO dao = new UsuarioDAO(Usuario.class);


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
