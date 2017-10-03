package com.tcc.lucca.scoutup.gerenciar;

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
import com.tcc.lucca.scoutup.activitys.MainActivity;

public class LoginClass {

    String id;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;
    private Context context;


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

    public void criarUser(String email, String password) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            firebaseUser = mAuth.getCurrentUser();
                            Intent main = new Intent(context, MainActivity.class);
                            context.startActivity(main);
                            ((Activity) context).finish();


                        } else {

                            Toast.makeText(context, "Nao foi possivel efetuar o cadastro", Toast.LENGTH_LONG).show();


                        }


                    }
                });


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

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                    }
                });
    }

    public void firebaseAuthWithFacebook(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                    }
                });

    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }

    public void setmAuthListener(FirebaseAuth.AuthStateListener mAuthListener) {
        this.mAuthListener = mAuthListener;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
