package com.tcc.lucca.scoutup.gerenciar;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsuarioDAO extends GenericDAO {
    private List<Amigo> amigos;

    private Usuario user;

    public UsuarioDAO() {
        setReferencia("usuario");



    }

    public static UsuarioDAO getInstance() {
        return new UsuarioDAO();


    }


    public void setAmigos(List<Amigo> amigos) {
        this.amigos = amigos;
    }

    public void adicionar(Usuario entidade) {


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getDatabaseReference().child(getReferencia()).child(uid).setValue(entidade);

    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void saveToken(final String token) {

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        buscarPorId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuario user = dataSnapshot.getValue(Usuario.class);
                    user.setToken(token);
                    getDatabaseReference().child(getReferencia()).child(uid).setValue(user);
                    Log.d("TAG", token);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
