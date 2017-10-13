package com.tcc.lucca.scoutup.gerenciar;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.model.Grupo;
import com.tcc.lucca.scoutup.model.Sessao;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.concurrent.ExecutionException;

/**
 * Created by lucca on 06/10/17.
 */

public class SessaoDAO extends GenericDAO {

    public SessaoDAO() {
        setReferencia("sessoes");

    }

    public static SessaoDAO getInstance() {
        return new SessaoDAO();
    }



}
