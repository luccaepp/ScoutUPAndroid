package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.tcc.lucca.scoutup.model.Usuario;

/**
 * Created by lucca on 31/08/17.
 */

public class UsuarioDAO extends GenericDAO {

    private DatabaseReference databaseReference;

    public UsuarioDAO() {
        super(Usuario.class);
        setReferencia("usuario");
        databaseReference = getDatabaseReference();
    }

    public static UsuarioDAO getInstance() {
        return new UsuarioDAO();
    }

    public Query getUsuarioDatabase(String uid) {

        return databaseReference.getDatabase().getReference("usuario").child(uid);
    }



}
