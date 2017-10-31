package com.tcc.lucca.scoutup.model;

import android.location.Geocoder;
import android.os.Parcel;
import android.os.Parcelable;

import com.tcc.lucca.scoutup.activitys.MainActivity;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by lucca on 13/10/17.
 */

public class Local implements Parcelable {
    private Double lat;
    private Double lng;

    protected Local(Parcel in) {
    }

    public Local() {
    }

    public static final Creator<Local> CREATOR = new Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel in) {
            return new Local(in);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public String getEndereco() {

        Geocoder geo = new Geocoder(MainActivity.CONTEXT, Locale.getDefault());

        double lat = getLat();
        double lng = getLng();
        String local = "";


        try {
            local = geo.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return local;
    }
}
