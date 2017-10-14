package com.tcc.lucca.scoutup.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
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
import java.util.HashMap;
import java.util.List;

public class PerfilFrag extends Fragment {


    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private GrupoDAO grupoDAO = GrupoDAO.getInstance();
    private SessaoDAO sessaoDAO = SessaoDAO.getInstance();
    private PatrulhaDAO patrulhaDAO = PatrulhaDAO.getInstance();

    private HashMap<String, Amigo> amigos;
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

        listViewAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Amigo amigo = new ArrayList<Amigo>(amigos.values()).get(i);
                Intent intent = new Intent(getContext(), PerfilVisitadoActivity.class);
                Bundle bundle= new Bundle();
                bundle.putParcelable("amigo", amigo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    private void initData() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

       usuarioDAO.buscarPorId(uid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               if(dataSnapshot.exists()) {
                   setUsuarioDatabase(dataSnapshot.getValue(Usuario.class));
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

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
            final String uidGrupo = usuarioDatabase.getGrupo();
            grupoDAO.buscarPorId(uidGrupo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        setGrupoDatabase(dataSnapshot.getValue(Grupo.class));
                        info.add(grupoDatabase.getNome());
                        adapter.atualizarLista(info);
                        adapter.notifyDataSetChanged();

                        if (usuarioDatabase.getSecao() != null) {
                            String uidSecao = usuarioDatabase.getSecao().get("nome");

                            info.add(uidSecao);
                            adapter.atualizarLista(info);
                            adapter.notifyDataSetChanged();
                        }




                        if (usuarioDatabase.getPatrulha() != null) {

                            String uidPatrulha = usuarioDatabase.getPatrulha().get("nome");
                            Log.d("TAG", uidPatrulha);


                            info.add(uidPatrulha);


                            adapter.atualizarLista(info);
                            adapter.notifyDataSetChanged();

                        }


                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






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

        usuarioDAO.buscarPorId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Amigo> amigos = (HashMap<String, Amigo>) dataSnapshot.getValue(Usuario.class).getAmigos();
                if (amigos == null) {

                    amigos = new HashMap<String, Amigo>();

                }
                AmigoListAdapter adapter = new AmigoListAdapter(getContext(), new ArrayList<Amigo>(amigos.values()));
                setAmigos(amigos);
                listViewAmigos.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public HashMap<String, Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(HashMap<String, Amigo> amigos) {
        this.amigos = amigos;
    }

    public Patrulha getPatrulhaDatabase() {
        return patrulhaDatabase;
    }

    public void setPatrulhaDatabase(Patrulha patrulhaDatabase) {
        this.patrulhaDatabase = patrulhaDatabase;
    }
}

