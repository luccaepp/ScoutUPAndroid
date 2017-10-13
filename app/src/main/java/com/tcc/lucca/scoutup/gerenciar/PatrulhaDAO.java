package com.tcc.lucca.scoutup.gerenciar;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.model.Patrulha;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.concurrent.ExecutionException;

/**
 * Created by lucca on 08/10/17.
 */

public class PatrulhaDAO extends GenericDAO {

    public PatrulhaDAO() {
        setReferencia("patrulhas");

    }

    public static PatrulhaDAO getInstance() {
        return new PatrulhaDAO();
    }




}