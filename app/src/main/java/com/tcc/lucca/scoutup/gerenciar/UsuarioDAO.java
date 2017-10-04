package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.DatabaseReference;
import com.tcc.lucca.scoutup.model.Usuario;

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





}
