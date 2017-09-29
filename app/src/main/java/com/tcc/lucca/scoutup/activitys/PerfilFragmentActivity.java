package com.tcc.lucca.scoutup.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lucca.scoutup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Usuario;

public class PerfilFragmentActivity extends Fragment {


    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();


    private FirebaseUser firebaseUser;
    private Usuario usuarioDatabase;

    private ListView listViewInfo;
    private ListView listViewAmigos;
    private ListView listViewEspec;

    private Button btnLogout,
            btnEditar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initComponents(container);
        return inflater.inflate(R.layout.activity_perfil_fragment, container, false);
    }

    private void initComponents(ViewGroup container) {
        listViewInfo = container.findViewById(R.id.listViewInfo);
        listViewAmigos = container.findViewById(R.id.listViewAmigos);
        listViewEspec = container.findViewById(R.id.listViewEspecialidades);

    }

    private void initData() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        Query query = usuarioDAO.getUsuarioDatabase(uid);
        final PerfilFragmentActivity self = this;


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                self.setUsuarioDatabase((Usuario) dataSnapshot.getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void atualizarInfoPerfil() {

    }

    public void setUsuarioDatabase(Usuario usuarioDatabase) {
        this.usuarioDatabase = usuarioDatabase;
        atualizarInfoPerfil();
    }
}

