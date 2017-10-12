package com.tcc.lucca.scoutup.gerenciar;

import com.tcc.lucca.scoutup.model.Patrulha;

/**
 * Created by lucca on 08/10/17.
 */

public class PatrulhaDAO extends GenericDAO {

    public PatrulhaDAO() {
        super(Patrulha.class);
        setReferencia("patrulhas");

    }

    public static PatrulhaDAO getInstance() {
        return new PatrulhaDAO();
    }


}