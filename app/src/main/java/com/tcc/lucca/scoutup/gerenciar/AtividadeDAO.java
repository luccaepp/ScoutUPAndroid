package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucca on 13/10/17.
 */

public class AtividadeDAO extends GenericDAO {

    public AtividadeDAO() {
        setReferencia("atividade");



    }

    public static AtividadeDAO getInstance() {
        return new AtividadeDAO();


    }


    public List<Query> listar(String chaveGrupo, String chaveSessao) {

        Query query = FirebaseDatabase.getInstance().getReference().child("mapAtividadePH").orderByChild("chavePH").equalTo(chaveSessao);
        Query query2 = FirebaseDatabase.getInstance().getReference().child("mapAtividadePH").orderByChild("chavePH").equalTo(chaveGrupo);

        List<Query> querys =  new ArrayList<>();
        querys.add(query);
        querys.add(query2);
        return  querys;



    }

}
