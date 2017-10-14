package com.tcc.lucca.scoutup.activitys;

import android.Manifest;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


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

                        dao.setReferencia("atividade");

                        dao.buscarPorId(chaveAtiv).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                Atividade atividade = dataSnapshot.getValue(Atividade.class);

                                if (dataSnapshot.getValue() != null) {

                                    atividades.add(atividade);
                                    adapter.atualizarLista(atividades);
                                    adapter.notifyDataSetChanged();
                                    Local local = atividade.getLocal();
                                    Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
                                    LatLng latLng = new LatLng(local.getLat(), local.getLng());
                                    String lat = local.getLat() + "";
                                    String lng = local.getLng() + "";
                                    String adress = null;
                                    try {
                                        adress = geo.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1).get(0).getAddressLine(0);
//                                Intent intent = new Intent(Intent.ACTION_INSERT)
//                                        .setData(CalendarContract.Events.CONTENT_URI)
//                                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, atividade.getInicio())
//                                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, atividade.getTermino())
//                                        .putExtra(CalendarContract.Events.TITLE, atividade.getTitulo())
//                                        .putExtra(CalendarContract.Events.DESCRIPTION, atividade.getDesc())
//                                        .putExtra(CalendarContract.Events.EVENT_LOCATION, adress);
//                                startActivity(intent);
                                        long calID = 3;

                                        ContentResolver cr = getActivity().getContentResolver();
                                        ContentValues values = new ContentValues();
                                        values.put(CalendarContract.Events.DTSTART, atividade.getInicio());
                                        values.put(CalendarContract.Events.DTEND, atividade.getTermino());
                                        values.put(CalendarContract.Events.TITLE, atividade.getTitulo());
                                        values.put(CalendarContract.Events.DESCRIPTION, atividade.getDesc());
                                        values.put(CalendarContract.Events.EVENT_LOCATION, adress);
                                        values.put(CalendarContract.Events.CALENDAR_ID, calID);
                                        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");



                                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                                        Log.d("TAG","passou uri");

// get the event ID that is the last element in the Uri
                                long eventID = Long.parseLong(uri.getLastPathSegment());


                            } catch (IOException e) {
                                e.printStackTrace();
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
