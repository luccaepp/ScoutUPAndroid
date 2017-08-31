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
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;


    public GenericDAO(Class<T> type) {
        this.type = type;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();


    }

    public void adicionar(T entidade) {

        String key = database.getReference(entidade.toString()).push().getKey();

        mDatabase.child(entidade.toString()).child(key).setValue(entidade);

    }

    public Query listarTodos(String entidade) {

        Query query = mDatabase.child(entidade);

        return query;
    }


    public Query buscarPorIdString(String entidade, String nomeAtributo, String id) {

        Query query = mDatabase.child(entidade).orderByChild(nomeAtributo).equalTo(id);

        return query;
    }

    public void excluir(T entidade) {

        String key = database.getReference(entidade.toString()).push().getKey();

        mDatabase.child(entidade.toString()).child(key).setValue(null);


    }

    public void alterar(T entidade) {

        String key = database.getReference(entidade.toString()).push().getKey();

        mDatabase.child(entidade.toString()).child(key).setValue(entidade);


    }
}
