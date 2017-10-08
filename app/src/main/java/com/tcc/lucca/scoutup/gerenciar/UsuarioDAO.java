package com.tcc.lucca.scoutup.gerenciar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.List;

public class UsuarioDAO extends GenericDAO {
    private List<Amigo> amigos;


    public UsuarioDAO() {
        super(Usuario.class);
        setReferencia("usuario");



    }

    public static UsuarioDAO getInstance() {
        return new UsuarioDAO();


    }

    public CollectionReference getAmigos(String uid) {

        CollectionReference collectionRef = getDb().collection(getReferencia()).document(uid).collection("amigos");

        return collectionRef;
    }

    public void setAmigos(List<Amigo> amigos) {
        this.amigos = amigos;
    }

    public void adicionar(Usuario entidade) {


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getDb().collection(getReferencia()).document(uid).set(entidade);


    }

}
