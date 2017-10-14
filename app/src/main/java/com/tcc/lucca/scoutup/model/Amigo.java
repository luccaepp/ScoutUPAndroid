package com.tcc.lucca.scoutup.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lucca on 08/10/17.
 */

public class Amigo implements Parcelable {

    private String nome;
    private String chave;
    private Status status;

    public Amigo() {
    }

    protected Amigo(Parcel in) {
        nome = in.readString();
        chave = in.readString();
    }

    public static final Creator<Amigo> CREATOR = new Creator<Amigo>() {
        @Override
        public Amigo createFromParcel(Parcel in) {
            return new Amigo(in);
        }

        @Override
        public Amigo[] newArray(int size) {
            return new Amigo[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(chave);
    }
}
