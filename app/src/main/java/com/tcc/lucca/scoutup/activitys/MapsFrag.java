package com.tcc.lucca.scoutup.activitys;

import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsFrag extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private static final String TAG = "MapsFrag";
    private GoogleMap mMap;
    private LocationManager locationManager;

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

            mMap.setOnMapClickListener(this);

            mMap.getUiSettings().setZoomControlsEnabled(true);

            Geocoder geo = new Geocoder(getContext(), Locale.getDefault());

            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            LatLng latlng = new LatLng(latitude, longitude);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latlng);

            try {
                markerOptions.title(geo.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0));

            } catch (Exception E) {
                markerOptions.title("Localização inicial");
            }

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        } catch (SecurityException ex) {

            Log.e(TAG, "Error", ex);


        }


    }

    @Override
    public void onMapClick(LatLng latLng) {
        Geocoder geo = new Geocoder(getContext(), Locale.getDefault());

        double latitude = latLng.latitude;
        double longitude = latLng.longitude;


        try {
            String addres = geo.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0);
            Toast.makeText(getContext(), "Local clicado: " + addres, Toast.LENGTH_LONG).show();


        } catch (Exception E) {
            Toast.makeText(getContext(), "Local clicado: " + latLng.toString(), Toast.LENGTH_LONG).show();

        }

    }


}
