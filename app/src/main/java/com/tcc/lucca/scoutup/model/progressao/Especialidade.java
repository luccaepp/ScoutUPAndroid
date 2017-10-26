package com.tcc.lucca.scoutup.model.progressao;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lucca on 20/10/17.
 */

public class Especialidade implements Parcelable {


    private String nome;
    private List<String> itens = new ArrayList<>();
    private int nivel;
    private String requisitos;
    private String area;


    public Especialidade() {






    }

    protected Especialidade(Parcel in) {
        nome = in.readString();
    }

    public static final Creator<Especialidade> CREATOR = new Creator<Especialidade>() {
        @Override
        public Especialidade createFromParcel(Parcel in) {
            return new Especialidade(in);
        }

        @Override
        public Especialidade[] newArray(int size) {
            return new Especialidade[size];
        }
    };


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public static Creator<Especialidade> getCREATOR() {
        return CREATOR;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
    }

    public List<String> getItens() {
        return itens;
    }

    public void setItens(List<String> itens) {
        this.itens = itens;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
