package com.tcc.lucca.scoutup.backgroundTasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.activitys.MainActivity;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Tipo;
import com.tcc.lucca.scoutup.model.Usuario;

public class LoginClass {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;
    private Context context;
    private Usuario usuario;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private boolean isEscotista;


    public LoginClass(final Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };


    }



    public void loginCredentials(String email, String senha) {

        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    firebaseUser = mAuth.getCurrentUser();
                    Intent main = new Intent(context, MainActivity.class);
                    context.startActivity(main);
                    ((Activity) context).finish();



                } else {

                    Toast.makeText(context, "Email ou senha erradas", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, final boolean checked) {
        isEscotista = checked;

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        verificarUsuarioCadastrado();
                    }
                });
    }

    public void firebaseAuthWithFacebook(AccessToken accessToken, final boolean checked) {
        isEscotista = checked;

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        verificarUsuarioCadastrado();
                    }
                });

    }

    public void verificarUsuarioCadastrado() {

        String uid = mAuth.getCurrentUser().getUid();

        usuarioDAO.buscarPorId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    cadastro();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

                Intent main = new Intent(context, MainActivity.class);
                context.startActivity(main);

                ((Activity) context).finish();



    }

    private void cadastro() {


        Usuario user = new Usuario();
        user.setNome(mAuth.getCurrentUser().getDisplayName());
        user.setEmail(mAuth.getCurrentUser().getEmail());
        user.setTipo(Tipo.devolveString(Tipo.escoteiro));
        if (isEscotista) {
            user.setTipo(Tipo.devolveString(Tipo.escotista));
        }
        usuarioDAO.adicionar(user);
        Intent main = new Intent(context, MainActivity.class);
        context.startActivity(main);

        ((Activity) context).finish();




    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
