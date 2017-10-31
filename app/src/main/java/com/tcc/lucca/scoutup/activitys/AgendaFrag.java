package com.tcc.lucca.scoutup.activitys;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.gerenciar.AtividadeDAO;
import com.tcc.lucca.scoutup.adapters.AtividadeListAdapter;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Atividade;
import com.tcc.lucca.scoutup.model.MapAtividadePH;
import com.tcc.lucca.scoutup.model.Participante;
import com.tcc.lucca.scoutup.model.Tipo;
import com.tcc.lucca.scoutup.model.Usuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.ALARM_SERVICE;


public class AgendaFrag extends Fragment {

    private Usuario usuario;
    private FloatingActionButton fab;
    private ListView listView;
    private FirebaseUser firebaseUser;
    private String uid;
    private List<Atividade> atividades = new ArrayList<>();
    private AtividadeListAdapter adapter;
    private static final int TAG_CODE_PERMISSION_CALENDAR = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }

    private void pedirPermi() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

        }else{

            initData();

        }

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
                Intent intent;

                if(usuario.getTipo().equals(Tipo.devolveString(Tipo.escoteiro))){
                    intent = new Intent(getContext(), AtividadeEscoteiroActivity.class);

                }else{

                    intent = new Intent(getContext(), AtividadeEscotistaActivity.class);


                }

                Bundle bundle = new Bundle();
                bundle.putParcelable("atividade", atividade);
                bundle.putStringArrayList("materiais", (ArrayList<String>) atividade.getMateriais());
                bundle.putParcelableArrayList("participantes", (ArrayList<Participante>) atividade.getParticipantes());
                bundle.putParcelable("local", new LatLng(atividade.getLocal().getLat(), atividade.getLocal().getLng()));
                bundle.putString("fim", getDate(atividade.getTermino()));
                bundle.putLong("fimTime", atividade.getTermino());
                bundle.putString("inicio", getDate(atividade.getInicio()));
                bundle.putLong("inicioTime", atividade.getInicio());
                bundle.putString("id", atividades.get(i).getId());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


        TextView tvAgenda = root.findViewById(R.id.textViewTitulo);
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

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("HH:mm - dd/MM/yyyy", cal).toString();
        return date;
    }

    private void initData(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        uid = firebaseUser.getUid();

        final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

        usuarioDAO.buscarPorId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Usuario user = dataSnapshot.getValue(Usuario.class);

                    try {
                        if (user.getTipo().equals(Tipo.devolveString(Tipo.escotista))) {

                            if (user.getSecao() != null) {

                               // fab.show();


                            }

                        }
                    }catch (Exception e){



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

        atividades = new ArrayList<>();

        adapter = new AtividadeListAdapter(getContext(), atividades);
        listView.setAdapter(adapter);

        final AtividadeDAO dao = AtividadeDAO.getInstance();

        try{

        List<Query> querys = dao.listar(usuario.getGrupo(), usuario.getSecao().get("chave"));

        for (Query query : querys) {


            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    final MapAtividadePH map = dataSnapshot.getValue(MapAtividadePH.class);

                    dao.buscarPorId(map.getChaveAtividade()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            if (dataSnapshot.exists()) {

                                Atividade atividade = dataSnapshot.getValue(Atividade.class);

                                atividade.setId(map.getChaveAtividade());

                                boolean isListada = false;

                                for (Atividade ativ:atividades){

                                    if(ativ.getId().equals(atividade.getId())){

                                        isListada = true;


                                    }

                                }

                                if(isListada){

                                    adapter.notifyDataSetChanged();

                                }else{

                                    atividades.add(atividade);
                                    Collections.sort(atividades);

                                    try{

                                        addEventToCalendar(atividade);

                                    }catch (Exception e){}

                                    adapter.atualizarLista(atividades);
                                    adapter.notifyDataSetChanged();
                                    setAlarme(atividade, dataSnapshot.getKey());

                                }


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


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


        }


        }catch (Exception e){}
    }

    public boolean isEventInCal(Context context, Atividade atividade) {

        long begin = atividade.getInicio();
        long end = atividade.getTermino();
                String[] proj =
                new String[]{
                        CalendarContract.Instances.BEGIN,
                        CalendarContract.Instances.END,
                        CalendarContract.Instances.EVENT_ID};
        Cursor cursor =
                CalendarContract.Instances.query(getContext().getContentResolver(), proj, begin, end, atividade.getTitulo());
        if (cursor.getCount() > 0) {return true;
        }else{

            return  false;
        }
    }


    private void addEventToCalendar(Atividade atividade) {

        if (isEventInCal(getContext(), atividade)) {

            Log.d("tag", "existe");

        } else {

            final ContentValues event = new ContentValues();
            event.put(CalendarContract.Events.CALENDAR_ID, 1);

            event.put(CalendarContract.Events.TITLE, atividade.getTitulo());
            event.put(CalendarContract.Events.EVENT_LOCATION, atividade.getLocal().getEndereco());

            event.put(CalendarContract.Events.DTSTART, atividade.getInicio());
            event.put(CalendarContract.Events.DTEND, atividade.getTermino());
            event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
            event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true

            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");
            } else {
                baseUri = Uri.parse("content://calendar/events");
            }

            getContext().getContentResolver().insert(baseUri, event);
            Log.d("TAG", "evento criado");

        }
    }

    private void setAlarme(Atividade atividade, String key) {

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

            Log.d("Script", "ainda vai acontecer");


            Intent intent = new Intent("ALARME_DISPARADO");
            Bundle bundle = new Bundle();
            bundle.putString("titulo", atividade.getTitulo());
            bundle.putString("local", local);
            bundle.putString("tipo", atividade.getTipo());
            bundle.putString("id", key);
            intent.putExtras(bundle);

            Date now = new Date();
            int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));


            if(PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_NO_CREATE) == null) {


                PendingIntent p = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarme = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                alarme.set(AlarmManager.RTC_WAKEUP, atividade.getInicio(), p);
            }
        }




    }

    @Override
    public void onResume() {
        super.onResume();

        pedirPermi();

        adapter = new AtividadeListAdapter(getContext(), atividades);

        adapter.notifyDataSetChanged();



    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }






}
