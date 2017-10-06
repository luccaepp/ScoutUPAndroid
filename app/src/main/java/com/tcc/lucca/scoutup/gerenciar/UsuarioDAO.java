package com.tcc.lucca.scoutup.gerenciar;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.HashMap;

public class UsuarioDAO extends GenericDAO {


    public UsuarioDAO() {
        super(Usuario.class);
        setReferencia("users");


    }

    public static UsuarioDAO getInstance() {
        return new UsuarioDAO();
    }


    public Usuario buscarPorIdUser(String id) {

        final Usuario[] user = {new Usuario()};
        DocumentReference docRef = getDb().collection(getReferencia()).document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                user[0] = usuario;
            }
        });


        return user[0];

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
