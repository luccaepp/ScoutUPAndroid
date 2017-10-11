package com.tcc.lucca.scoutup.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Tipo;
import com.tcc.lucca.scoutup.model.Usuario;


public class AgendaFrag extends Fragment {

    private Usuario usuario;
    private FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initData();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_atividades, container, false);

        TextView tvAgenda = root.findViewById(R.id.textView);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
        tvAgenda.setTypeface(type);
        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AdicionarAtividadeActivity.class);
                startActivity(intent);




            }
        });

        return root;
    }


    private void initData() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = firebaseUser.getUid();

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

        usuarioDAO.buscarPorId(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Usuario user = documentSnapshot.toObject(Usuario.class);

                if (user.getTipo().equals(Tipo.devolveString(Tipo.escoteiro))) {

                    fab.hide();

                }

            }
        });


    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
