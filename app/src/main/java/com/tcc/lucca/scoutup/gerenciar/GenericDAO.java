package com.tcc.lucca.scoutup.gerenciar;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class GenericDAO<T> {

    private Class<T> type;
    private FirebaseAuth auth;
    private Firebase firebase;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String referencia;



    public GenericDAO(Class<T> type) {
        this.type = type;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();



    }

    public Query getClassFromDatabase(String uid) {

        return databaseReference.child(getReferencia() + "/" + uid);
    }


    public void adicionar(T entidade) {

        String key = database.getReference(referencia).push().getKey();

        databaseReference.child(referencia).child(key).setValue(entidade);



    }

    public Query listarTodos() {

        Query query = databaseReference.child(referencia);


        return query;
    }


    public Query buscarPorAtributoString(String path, String nomeAtributo, String id) {

        Query query = databaseReference.child(path).orderByChild(nomeAtributo).equalTo(id);


        return query;
    }

    public void excluir(T entidade) {

        String key = database.getReference(referencia).push().getKey();

        databaseReference.child(referencia).child(key).setValue(null);


    }

    public void alterar(T entidade) {

        String key = database.getReference(referencia).push().getKey();


        databaseReference.child(referencia).child(key).setValue(entidade);


    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public void setFirebase(Firebase firebase) {
        this.firebase = firebase;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }
}
