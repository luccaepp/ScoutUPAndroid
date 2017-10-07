package com.tcc.lucca.scoutup.gerenciar;

import com.tcc.lucca.scoutup.model.Usuario;

public class UsuarioDAO extends GenericDAO {

    public UsuarioDAO() {
        super(Usuario.class);
        setReferencia("users");


    }

    public static UsuarioDAO getInstance() {
        return new UsuarioDAO();
    }



    public void adicionar(Usuario entidade) {


        getDb().collection(getReferencia()).document(entidade.getId()).set(entidade);


    }


}
