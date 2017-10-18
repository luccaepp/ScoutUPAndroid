package com.tcc.lucca.scoutup.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.adapters.AmigoListAdapter;
import com.tcc.lucca.scoutup.adapters.ListViewAdapter;
import com.tcc.lucca.scoutup.gerenciar.GrupoDAO;
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

public class PerfilVisitadoActivity extends AppCompatActivity {

    private Amigo amigo;
    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private GrupoDAO grupoDAO = GrupoDAO.getInstance();
    private SessaoDAO sessaoDAO = SessaoDAO.getInstance();
    private PatrulhaDAO patrulhaDAO = PatrulhaDAO.getInstance();

    private ImageView imageView;

    private HashMap<String, Amigo> amigos;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_visitado);

        Bundle bundle = getIntent().getExtras();
        amigo = bundle.getParcelable("amigo");
        initComponents();
        initData();


    }

    private void initComponents() {

        TextView tvAgenda = findViewById(R.id.tvPerfil);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "font/ClaireHandRegular.ttf");
        tvAgenda.setTypeface(type);

        listViewInfo = (ListView) findViewById(R.id.listViewInformacoes);
        listViewAmigos = (ListView) findViewById(R.id.listViewAmigos);
        listViewEspec = (ListView) findViewById(R.id.listViewEspecialidades);
        imageView = (ImageView) findViewById(R.id.imgPerfil);
        listViewAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Amigo amigo = new ArrayList<Amigo>(amigos.values()).get(i);
                if(amigo.getChave().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                    finish();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), PerfilVisitadoActivity.class);
                Bundle bundle= new Bundle();
                bundle.putParcelable("amigo", amigo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        String uid = amigo.getChave();
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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("fotoPerfil/"+amigo.getChave());
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(imagesRef)
                .into(imageView);

        final List<String> info = new ArrayList<>();

        info.add("SCOUT UP");
        info.add(usuarioDatabase.getNome());
        info.add(usuarioDatabase.getEmail());
        info.add(usuarioDatabase.getTipo());

        final ListViewAdapter adapter = new ListViewAdapter(this, info);
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

    private void atualizarEspecialidades() {

    }


    private void atualizarAmigos() {

        listViewAmigos = (ListView) findViewById(R.id.listViewAmigos);
        String uid = amigo.getChave();
        usuarioDAO.buscarPorId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Amigo> amigos = (HashMap<String, Amigo>) dataSnapshot.getValue(Usuario.class).getAmigos();
                if (amigos == null) {
                    amigos = new HashMap<String, Amigo>();

                }
                AmigoListAdapter adapter = new AmigoListAdapter(getApplicationContext(), new ArrayList<Amigo>(amigos.values()));
                setAmigos(amigos);
                listViewAmigos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Amigo getAmigo() {
        return amigo;
    }

    public void setAmigo(Amigo amigo) {
        this.amigo = amigo;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public GrupoDAO getGrupoDAO() {
        return grupoDAO;
    }

    public void setGrupoDAO(GrupoDAO grupoDAO) {
        this.grupoDAO = grupoDAO;
    }

    public SessaoDAO getSessaoDAO() {
        return sessaoDAO;
    }

    public void setSessaoDAO(SessaoDAO sessaoDAO) {
        this.sessaoDAO = sessaoDAO;
    }

    public PatrulhaDAO getPatrulhaDAO() {
        return patrulhaDAO;
    }

    public void setPatrulhaDAO(PatrulhaDAO patrulhaDAO) {
        this.patrulhaDAO = patrulhaDAO;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public HashMap<String, Amigo> getAmigos() {
        return amigos;
    }

    public void setAmigos(HashMap<String, Amigo> amigos) {
        this.amigos = amigos;
    }

    public Usuario getUsuarioDatabase() {
        return usuarioDatabase;
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

    public Patrulha getPatrulhaDatabase() {
        return patrulhaDatabase;
    }

    public void setPatrulhaDatabase(Patrulha patrulhaDatabase) {
        this.patrulhaDatabase = patrulhaDatabase;
    }

    public ListView getListViewInfo() {
        return listViewInfo;
    }

    public void setListViewInfo(ListView listViewInfo) {
        this.listViewInfo = listViewInfo;
    }

    public ListView getListViewAmigos() {
        return listViewAmigos;
    }

    public void setListViewAmigos(ListView listViewAmigos) {
        this.listViewAmigos = listViewAmigos;
    }

    public ListView getListViewEspec() {
        return listViewEspec;
    }

    public void setListViewEspec(ListView listViewEspec) {
        this.listViewEspec = listViewEspec;
    }

    public Button getBtnLogout() {
        return btnLogout;
    }

    public void setBtnLogout(Button btnLogout) {
        this.btnLogout = btnLogout;
    }

    public Button getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(Button btnEditar) {
        this.btnEditar = btnEditar;
    }

    public void mensagem(View view) {

        Toast.makeText(this, "Enviar mensagem", Toast.LENGTH_SHORT).show();

    }
}
