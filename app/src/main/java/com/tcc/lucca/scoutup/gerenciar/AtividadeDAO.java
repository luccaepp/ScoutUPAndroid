package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.Query;
import com.tcc.lucca.scoutup.model.Atividade;
import com.tcc.lucca.scoutup.model.MapAtividadePH;

import java.util.List;

/**
 * Created by lucca on 13/10/17.
 */

public class AtividadeDAO extends GenericDAO{

    public AtividadeDAO() {
        setReferencia("atividade");



    }

    public static AtividadeDAO getInstance() {
        return new AtividadeDAO();


    }


    public Query listar(String chaveGrupo, String chaveSessao) {

        setReferencia("mapAtividadePH");
        Query query = getDatabaseReference().child(getReferencia()).orderByChild("chavePH").equalTo(chaveSessao);
        setReferencia("atividade");
        return  query;



    }

}
