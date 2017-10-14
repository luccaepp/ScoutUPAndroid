package com.tcc.lucca.scoutup.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atividade implements Parcelable {

    private String titulo;
    private String desc;
    private Local local;
//    private Map<String, String> materiais;
    private Long inicio;
    private Long termino;
    private String tipo;
//    private Map<String, Participante> participantes;


    public Atividade() {
    }

    protected Atividade(Parcel in) {
        titulo = in.readString();
        desc = in.readString();
        tipo = in.readString();
    }

    public static final Creator<Atividade> CREATOR = new Creator<Atividade>() {
        @Override
        public Atividade createFromParcel(Parcel in) {
            return new Atividade(in);
        }

        @Override
        public Atividade[] newArray(int size) {
            return new Atividade[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
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
        parcel.writeString(titulo);
        parcel.writeString(desc);
        parcel.writeString(tipo);
    }

//    public Map<String, Participante> getParticipantes() {
//        return participantes;
//    }
//
//    public void setParticipantes(Map<String, Participante> participantes) {
//        this.participantes = participantes;
//    }
//public Map<String, String> getMateriais() {
//    return materiais;
//}
//
//    public void setMateriais(Map<String, String> materiais) {
//        this.materiais = materiais;
//    }




    public Long getInicio() {
        return inicio;
    }

    public void setInicio(Long inicio) {
        this.inicio = inicio;
    }

    public Long getTermino() {
        return termino;
    }

    public void setTermino(Long termino) {
        this.termino = termino;
    }


    public static Creator<Atividade> getCREATOR() {
        return CREATOR;
    }
}
