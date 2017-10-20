package com.tcc.lucca.scoutup.gerenciar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.Map;

public class GenericDAO<T> {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String referencia;

    public GenericDAO() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();

    }



    public void adicionar(T entidade) {

        String key = database.getReference(referencia).push().getKey();

        databaseReference.child(referencia).child(key).setValue(entidade);
    }

    public void deletar(String id) {

        String key = database.getReference(referencia).push().getKey();

        databaseReference.child(referencia).child(key).setValue(null);
    }

    public void update(T entidade, String id) {

        String key = database.getReference(referencia).push().getKey();

        databaseReference.child(referencia).child(key).setValue(entidade);

    }

    public Query listar() {


        Query query = databaseReference.child(referencia);

        return query;
    }



    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public DatabaseReference buscarPorId(String id){

        DatabaseReference documentReference = databaseReference.child(getReferencia()).child(id);

        return documentReference;


    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
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
