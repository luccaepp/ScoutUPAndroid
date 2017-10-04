package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.DatabaseReference;
import com.tcc.lucca.scoutup.model.Grupo;

public class GrupoDAO extends GenericDAO {

    private DatabaseReference databaseReference;

    public GrupoDAO() {
        super(Grupo.class);
        setReferencia("grupo");
        databaseReference = getDatabaseReference();

    }

    public static GrupoDAO getInstance() {
        return new GrupoDAO();
    }


}
