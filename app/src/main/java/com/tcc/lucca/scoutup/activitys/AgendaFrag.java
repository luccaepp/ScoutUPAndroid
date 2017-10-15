package com.tcc.lucca.scoutup.activitys;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.primitives.Bytes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.gerenciar.AtividadeDAO;
import com.tcc.lucca.scoutup.gerenciar.AtividadeListAdapter;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Atividade;
import com.tcc.lucca.scoutup.model.Local;
import com.tcc.lucca.scoutup.model.MapAtividadePH;
import com.tcc.lucca.scoutup.model.Tipo;
import com.tcc.lucca.scoutup.model.Usuario;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.content.Context.ALARM_SERVICE;


public class AgendaFrag extends Fragment {

    private static final int TAG_CODE_PERMISSION_CALENDAR = 2;
    private Usuario usuario;
    private FloatingActionButton fab;
    private ListView listView;
    private List<Atividade> atividades = new ArrayList<>();
    private AtividadeListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        initData();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_atividades, container, false);

        listView = root.findViewById(R.id.listViewAgenda);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Atividade atividade = atividades.get(i);
                Intent intent = new Intent(getContext(), AtividadeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("atividade", atividade);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


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

        fab.hide();


        return root;
    }


    private void initData() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = firebaseUser.getUid();

        final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

        usuarioDAO.buscarPorId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Usuario user = dataSnapshot.getValue(Usuario.class);

                    if (user.getTipo().equals(Tipo.devolveString(Tipo.escotista))) {

                        if (user.getSecao() != null) {

                            fab.show();


                        }

                    }
                    setUsuario(user);
                    carregarAtividades();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void carregarAtividades() {

        adapter = new AtividadeListAdapter(getContext(), atividades);
        listView.setAdapter(adapter);

        final AtividadeDAO dao = AtividadeDAO.getInstance();

        try {
            dao.listar(usuario.getGrupo(), usuario.getSecao().get("chave")).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    for (DataSnapshot data : dataSnapshot.getChildren()) {


                        String chaveAtiv = data.getValue().toString();

                        Log.d("Script", "ativ"+chaveAtiv);

                        dao.setReferencia("atividade");

                        dao.buscarPorId(chaveAtiv).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {



                                Atividade atividade = dataSnapshot.getValue(Atividade.class);


                                if (dataSnapshot.getValue() != null) {


                                    atividades.add(atividade);
                                    adapter.atualizarLista(atividades);
                                    adapter.notifyDataSetChanged();

                                    Geocoder geo = new Geocoder(getContext(), Locale.getDefault());

                                    double lat = atividade.getLocal().getLat();
                                    double lng = atividade.getLocal().getLng();
                                    String local = "";


                                    try {
                                        local = geo.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    Long current = Long.parseLong(System.currentTimeMillis() + "");

                                    Log.d("Script", " current "+Long.toString(current));

                                    Long inicio = Long.parseLong(atividade.getInicio() + "");

                                    Log.d("Script"," inicio " +Long.toString(inicio));


                                    if (current < inicio) {

                                        Log.d("Script", "ainda vai acontecer");


                                        Intent intent = new Intent("ALARME_DISPARADO");
                                    Bundle bundle = new Bundle();
                                    bundle.putString("titulo", atividade.getTitulo());
                                    bundle.putString("local", local);
                                    bundle.putString("tipo", atividade.getTipo());
                                    bundle.putString("id", dataSnapshot.getKey());
                                        intent.putExtras(bundle);

                                        Date now = new Date();
                                        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));


                                        if(PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_NO_CREATE) == null) {

                                            Log.d("Script", "Novo alarme "+  atividade.getTitulo());


                                            PendingIntent p = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                            AlarmManager alarme = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                            alarme.set(AlarmManager.RTC_WAKEUP, atividade.getInicio(), p);
                                        }else{

                                            Log.d("Script", "Ja existe");


                                        }
                                }



                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


            dao.listar(usuario.getGrupo(), usuario.getGrupo()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    for (DataSnapshot data : dataSnapshot.getChildren()) {


                        String chaveAtiv = data.getValue().toString();

                        dao.setReferencia("atividade");

                        dao.buscarPorId(chaveAtiv).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {



                                Atividade atividade = dataSnapshot.getValue(Atividade.class);

                                if (dataSnapshot.getValue() != null) {


                                    atividades.add(atividade);
                                    adapter.atualizarLista(atividades);
                                    adapter.notifyDataSetChanged();

                                    Geocoder geo = new Geocoder(getContext(), Locale.getDefault());

                                    double lat = atividade.getLocal().getLat();
                                    double lng = atividade.getLocal().getLng();
                                    String local = "";


                                    try {
                                        local = geo.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    Long current = Long.parseLong(System.currentTimeMillis() + "");

                                    Long inicio = Long.parseLong(atividade.getInicio() + "");


                                    if (current < inicio) {



                                        Intent intent = new Intent("ALARME_DISPARADO");
                                        Bundle bundle = new Bundle();
                                        bundle.putString("titulo", atividade.getTitulo());
                                        bundle.putString("local", local);
                                        bundle.putString("tipo", atividade.getTipo());
                                        bundle.putString("id", dataSnapshot.getKey());
                                        intent.putExtras(bundle);



                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTimeInMillis(atividade.getInicio());
                                        Date date = calendar.getTime();


                                        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(date));

                                        if(PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_NO_CREATE) == null) {

                                            Log.d("Script", "Novo alarme "+  atividade.getTitulo());


                                            PendingIntent p = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                            AlarmManager alarme = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                            alarme.set(AlarmManager.RTC_WAKEUP, atividade.getInicio(), p);
                                        }else{

                                            Log.d("Script", "Ja existe");


                                        }
                                    }



                                } else {

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

}catch (Exception e){




}


    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }




}
