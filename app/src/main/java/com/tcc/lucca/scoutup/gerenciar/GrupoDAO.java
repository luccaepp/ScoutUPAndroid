package com.tcc.lucca.scoutup.gerenciar;

import com.tcc.lucca.scoutup.model.Grupo;

/**
 * Created by lucca on 01/09/17.
 */

public class GrupoDAO extends GenericDAO {
    public GrupoDAO() {
        super(Grupo.class);
        setReferencia("grupo");
    }
}
