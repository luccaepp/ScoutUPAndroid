package com.tcc.lucca.scoutup.model;

import android.os.Parcel;
import android.os.Parcelable;

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
}
