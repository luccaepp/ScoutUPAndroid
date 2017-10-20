package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.Query;

/**
 * Created by lucca on 20/10/17.
 */

public class AreaDAO extends GenericDAO {

    public AreaDAO() {
        setReferencia("escopoProgressao/especialidades");

    }

    public static AreaDAO getInstance() {
        return new AreaDAO();
    }


    public Query listar(String ref) {

        Query query = getDatabaseReference().child(getReferencia()).child(ref);

        return query;
    }
}