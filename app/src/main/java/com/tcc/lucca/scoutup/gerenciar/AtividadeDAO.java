package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.DatabaseReference;
import com.tcc.lucca.scoutup.model.Grupo;

/**
 * Created by lucca on 03/10/17.
 */

public class AtividadeDAO extends GenericDAO {

    private DatabaseReference databaseReference;

    public AtividadeDAO() {
        super(Grupo.class);
        setReferencia("atividade");
        databaseReference = getDatabaseReference();

    }

    public static AtividadeDAO getInstance() {
        return new AtividadeDAO();
    }


}