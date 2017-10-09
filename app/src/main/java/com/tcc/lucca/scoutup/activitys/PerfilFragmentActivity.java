package com.tcc.lucca.scoutup.activitys;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lucca.scoutup.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tcc.lucca.scoutup.gerenciar.AmigoListAdapter;
import com.tcc.lucca.scoutup.gerenciar.GrupoDAO;
import com.tcc.lucca.scoutup.gerenciar.ListViewAdapter;
import com.tcc.lucca.scoutup.gerenciar.PatrulhaDAO;
import com.tcc.lucca.scoutup.gerenciar.SessaoDAO;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Grupo;
import com.tcc.lucca.scoutup.model.Patrulha;
import com.tcc.lucca.scoutup.model.Sessao;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PerfilFragmentActivity extends Fragment {


    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private GrupoDAO grupoDAO = GrupoDAO.getInstance();
    private SessaoDAO sessaoDAO = SessaoDAO.getInstance();
    private PatrulhaDAO patrulhaDAO = PatrulhaDAO.getInstance();

    private List<Amigo> amigos;
    private FirebaseUser firebaseUser;
    private Usuario usuarioDatabase;
    private Grupo grupoDatabase;
    private Sessao sessaoDatabase;
    private Patrulha patrulhaDatabase;

    private ListView listViewInfo;
    private ListView listViewAmigos;
    private ListView listViewEspec;

    private Button btnLogout;
    private Button btnEditar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_perfil_fragment, container, false);

        TextView tvPerfil = root.findViewById(R.id.textView);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "font/ClaireHandRegular.ttf");
        tvPerfil.setTypeface(type);

        initComponents(root);

        initData();

        return root;
    }

    private void initComponents(View container) {
        listViewInfo = container.findViewById(R.id.listViewInformacoes);
        listViewAmigos = container.findViewById(R.id.listViewAmigos);
        listViewEspec = container.findViewById(R.id.listViewEspecialidades);

    }

    private void initData() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        usuarioDAO.buscarPorId(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
                setUsuarioDatabase(user);

            }
        });


    }

    private void atualizarInfoPerfil() {


        final List<String> info = new ArrayList<>();

        info.add("SCOUT UP");

        info.add(usuarioDatabase.getNome());
        info.add(usuarioDatabase.getEmail());
        info.add(usuarioDatabase.getTipo());

        final ListViewAdapter adapter = new ListViewAdapter(getContext(), info);
        listViewInfo.setAdapter(adapter);


        if (usuarioDatabase.getGrupo() != null) {
            String uidGrupo = usuarioDatabase.getGrupo();
            grupoDAO.buscarPorId(uidGrupo).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try {
                        Grupo grupo = documentSnapshot.toObject(Grupo.class);
                        setGrupoDatabase(grupo);
                        info.add(grupoDatabase.getNome());
                        adapter.atualizarLista(info);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }

                }
            });

            if (usuarioDatabase.getSessao() != null) {
                String uidSecao = usuarioDatabase.getSessao();
                sessaoDAO.buscarPorId(uidSecao).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        try {
                            Sessao sessao = documentSnapshot.toObject(Sessao.class);
                            setSessaoDatabase(sessao);
                            info.add(sessaoDatabase.getNome());
                            adapter.atualizarLista(info);
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {
                        }

                    }
                });

                if (usuarioDatabase.getPatrulha() != null) {
                    String uidPatrulha = usuarioDatabase.getPatrulha();
                    patrulhaDAO.buscarPorId(uidPatrulha).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            try {
                                Patrulha patrulha = documentSnapshot.toObject(Patrulha.class);
                                setPatrulhaDatabase(patrulha);
                                info.add(patrulhaDatabase.getNome());
                                adapter.atualizarLista(info);
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                            }

                        }
                    });


                }

            }
        }

    }

    public void setUsuarioDatabase(Usuario usuarioDatabase) {
        this.usuarioDatabase = usuarioDatabase;
        atualizarInfoPerfil();
        atualizarAmigos();
        atualizarEspecialidades();
    }


    private void atualizarAmigos() {



        listViewAmigos = getView().findViewById(R.id.listViewAmigos);

        String uid = firebaseUser.getUid();

        usuarioDAO.getAmigos(uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {

                List<DocumentSnapshot> docs = documentSnapshots.getDocuments();
                List<Amigo> amigos = new ArrayList<>();
                Log.d("TAG", Integer.toString(docs.size()));

                for (DocumentSnapshot doc : docs) {

                    amigos.add(doc.toObject(Amigo.class));


                }
                AmigoListAdapter adapter = new AmigoListAdapter(getContext(), amigos);
                listViewAmigos.setAdapter(adapter);
            }
        });


    }

    private void atualizarEspecialidades() {

    }

    public Grupo getGrupoDatabase() {
        return grupoDatabase;
    }

    public void setGrupoDatabase(Grupo grupoDatabase) {
        this.grupoDatabase = grupoDatabase;
    }

    public Sessao getSessaoDatabase() {
        return sessaoDatabase;
    }

    public void setSessaoDatabase(Sessao sessaoDatabase) {
        this.sessaoDatabase = sessaoDatabase;
    }

    public List<Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Amigo> amigos) {
        this.amigos = amigos;
    }

    public Patrulha getPatrulhaDatabase() {
        return patrulhaDatabase;
    }

    public void setPatrulhaDatabase(Patrulha patrulhaDatabase) {
        this.patrulhaDatabase = patrulhaDatabase;
    }
}

