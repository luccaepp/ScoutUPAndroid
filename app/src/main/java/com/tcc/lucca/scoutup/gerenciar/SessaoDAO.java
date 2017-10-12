package com.tcc.lucca.scoutup.gerenciar;

import com.tcc.lucca.scoutup.model.Grupo;

/**
 * Created by lucca on 06/10/17.
 */

public class SessaoDAO extends GenericDAO {

    public SessaoDAO() {
        super(Grupo.class);
        setReferencia("sessoes");

    }

    public static SessaoDAO getInstance() {
        return new SessaoDAO();
    }
}
