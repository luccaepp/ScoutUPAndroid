package com.tcc.lucca.scoutup.activitys;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lucca on 17/10/17.
 */

public class MapFrag2  extends SupportMapFragment implements OnMapReadyCallback {

    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private static final String TAG = "MapsFrag";
    private GoogleMap mMap;
    private LocationManager locationManager;

    public MapFrag2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            googleMap.setMyLocationEnabled(true);

            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            mMap = googleMap;

            mMap.getUiSettings().setScrollGesturesEnabled(true);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.setMyLocationEnabled(true);


            try {
                LatLng latLng = (LatLng) getArguments().get("LatLng");

                if (latLng != null) {

                    Log.d(TAG, latLng.toString());


                    MarkerOptions marker = new MarkerOptions();
                    marker.position(latLng);
                    marker.title("Local Selecionando");
                    mMap.addMarker(marker);
                    mMap.getMinZoomLevel();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));


                }
            } catch (Exception e) {
            }







        } catch (SecurityException ex) {

            Log.e(TAG, "Error", ex);


        }


    }

}
