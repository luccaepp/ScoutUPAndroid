package com.tcc.lucca.scoutup.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lucca.scoutup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.gerenciar.AtividadeDAO;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Atividade;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.List;

public class AtividadesActivity extends Fragment {

    private AtividadeDAO atividadeDAO = AtividadeDAO.getInstance();
    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private ListView listView;
    private FirebaseUser firebaseUser;
    private Usuario user;
    private List<Atividade> atividades;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initComponents(container);
        return inflater.inflate(R.layout.activity_atividades, container, false);

    }

    private void initComponents(ViewGroup container) {

        listView = container.findViewById(R.id.listViewAgenda);

    }

    private void initData() {


        Query q = atividadeDAO.listarTodos();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        Query query = usuarioDAO.getClassFromDatabase(uid);
        final AtividadesActivity self = this;


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Usuario user = dataSnapshot.getValue(Usuario.class);
                self.setUsuarioDatabase(user);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setUsuarioDatabase(Usuario user) {
        this.user = user;
    }


}
