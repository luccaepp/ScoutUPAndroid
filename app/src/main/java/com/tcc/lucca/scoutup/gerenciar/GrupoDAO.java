package com.tcc.lucca.scoutup.gerenciar;

import com.tcc.lucca.scoutup.model.Grupo;

public class GrupoDAO extends GenericDAO {

    public GrupoDAO() {
        super(Grupo.class);
        setReferencia("grupos");

    }

    public static GrupoDAO getInstance() {
        return new GrupoDAO();
    }


}
