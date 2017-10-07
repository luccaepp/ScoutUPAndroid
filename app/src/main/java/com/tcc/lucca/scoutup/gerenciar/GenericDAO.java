package com.tcc.lucca.scoutup.gerenciar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class GenericDAO<T> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Class classeUsada;
    private String referencia;

    public GenericDAO(Class c) {
        this.classeUsada = c;
    }

    public void adicionar(T entidade) {

        db.collection(referencia).add(entidade);

    }

    public void deletar(String id) {

        db.collection(referencia).document(id).delete();

    }

    public void update(Map<String, Object> lista, String id) {

        db.collection(referencia).document(id).update(lista);

    }

    public Task<QuerySnapshot> listar() {


        return db.collection(referencia).get();

    }

    public Query buscarPorId(String id) {

        Query query = getDb().collection(getReferencia()).whereEqualTo("id", id);

        return query;


    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }


}
