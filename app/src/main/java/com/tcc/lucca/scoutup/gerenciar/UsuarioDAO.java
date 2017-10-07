package com.tcc.lucca.scoutup.gerenciar;

import android.util.Log;

import com.google.firebase.firestore.Query;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.HashMap;

public class UsuarioDAO extends GenericDAO {

    private Usuario usuarioBusca;

    public UsuarioDAO() {
        super(Usuario.class);
        setReferencia("users");


    }

    public static UsuarioDAO getInstance() {
        return new UsuarioDAO();
    }


    public Query buscarPorIdUser(String id) {

        Query query = getDb().collection(getReferencia()).whereEqualTo("id", id);


        return query;

    }

    public void adicionar(Usuario entidade) {

        HashMap<String, Object> update = new HashMap<>();

        update.put("nome", entidade.getNome());
        update.put("email", entidade.getEmail());
        update.put("grupo", entidade.getGrupo());
        update.put("sessao", entidade.getSessao());
        update.put("patrulha", entidade.getPatrulha());
        update.put("tipo", entidade.getTipo().devolveString(entidade.getTipo()));


        Log.d("TAG", entidade.getId());
        getDb().collection(getReferencia()).document(entidade.getId()).set(entidade);


    }


}
