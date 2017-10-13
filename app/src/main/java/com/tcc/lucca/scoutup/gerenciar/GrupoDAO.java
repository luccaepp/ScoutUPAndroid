package com.tcc.lucca.scoutup.gerenciar;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.model.Grupo;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.concurrent.ExecutionException;

public class GrupoDAO extends GenericDAO {

    public GrupoDAO() {
        setReferencia("grupo");

    }

    public static GrupoDAO getInstance() {
        return new GrupoDAO();
    }


}
