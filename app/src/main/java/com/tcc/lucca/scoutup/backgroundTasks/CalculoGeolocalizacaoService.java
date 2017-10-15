package com.tcc.lucca.scoutup.backgroundTasks;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.activitys.MainActivity;
import com.tcc.lucca.scoutup.gerenciar.AtividadeDAO;
import com.tcc.lucca.scoutup.model.Atividade;
import com.tcc.lucca.scoutup.model.Local;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lucca on 15/10/17.
 */

public class CalculoGeolocalizacaoService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        android.location.LocationListener,


        ResultCallback<Status> {

    private LocationRequest locationRequest;

    private List<Geofence> mGeofenceList;
    private GoogleApiClient mGoogleApiClient;
    private Atividade atividade;
    public static final String TAG = "ServicoLocalizacao";
    String idAtividade;
    private Location lastLocation;

    double currentLatitude = 8.5565795, currentLongitude = 76.8810227;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG, "chamou api");


        String idAtividade = intent.getStringExtra("id");

        Log.d(TAG, "chamou calculo");
        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        AtividadeDAO dao = new AtividadeDAO();

        dao.buscarPorId(idAtividade).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Atividade atividade = dataSnapshot.getValue(Atividade.class);
                setAtividade(atividade);
                Local local = atividade.getLocal();
                currentLatitude = local.getLat();
                currentLongitude = local.getLng();

                mGeofenceList = new ArrayList<Geofence>();

                populateGeofenceList();

                Log.d(TAG, "chamou populate");

                buildGoogleApiClient();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public void populateGeofenceList() {

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId(UUID.randomUUID().toString())
                .setCircularRegion(
                        currentLatitude,
                        currentLongitude,
                        20

                )
                .setExpirationDuration((12 * 60 * 60 * 1000))
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

    }

    protected synchronized void buildGoogleApiClient() {

        Log.d(TAG, "Criando api");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        Log.d(TAG, "api criada");


    }


    @Override
    public void onConnected(Bundle connectionHint) {

        addGeofencesHandler();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do something with result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void addGeofencesHandler() {

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException securityException) {
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);


        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            Log.d(TAG, "Geofences Added");




        } else {
            // Get the status code for the error and log it using a user-friendly message.
            Log.d(TAG, "Geofences error");
        }
    }

    private void writeActualLocation(Location location) {
        Log.d(TAG, "Lat: " + location.getLatitude());
        Log.d(TAG, "Long: " + location.getLongitude());


    }

    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;

    // Start location Updates
    private void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (LocationListener) this);}

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;
        writeActualLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
