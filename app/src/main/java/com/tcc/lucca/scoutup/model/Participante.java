package com.tcc.lucca.scoutup.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lucca on 13/10/17.
 */

public class Participante implements Parcelable {

    public Participante() {
    }

    private String chave;
    private String nome;
    private String tipo;

    protected Participante(Parcel in) {
        chave = in.readString();
        nome = in.readString();
        tipo = in.readString();
    }

    public static final Creator<Participante> CREATOR = new Creator<Participante>() {
        @Override
        public Participante createFromParcel(Parcel in) {
            return new Participante(in);
        }

        @Override
        public Participante[] newArray(int size) {
            return new Participante[size];
        }
    };

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chave);
        parcel.writeString(nome);
        parcel.writeString(tipo);
    }
}
