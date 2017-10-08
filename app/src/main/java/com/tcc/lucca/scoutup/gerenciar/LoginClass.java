package com.tcc.lucca.scoutup.gerenciar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.tcc.lucca.scoutup.activitys.MainActivity;
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
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (user.getDisplayName() != null) {

                        Intent main = new Intent(context, MainActivity.class);
                        context.startActivity(main);
                        ((Activity) context).finish();


                    }

                }
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

        usuarioDAO.buscarPorId(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    Log.d("TAG", "Sucesso");


                } else {
                    Log.d("TAG", "Deu Ruim");
                    cadastro();


                }


            }
        });


    }


    private void cadastro() {

        Log.d("TAG", "Criando usuario");

        Usuario user = new Usuario();
        user.setNome(mAuth.getCurrentUser().getDisplayName());
        user.setEmail(mAuth.getCurrentUser().getEmail());
        if (isEscotista) {
            user.setTipo(Tipo.escotista);
        } else {
            user.setTipo(Tipo.escoteiro);
        }
        usuarioDAO.adicionar(user);

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
