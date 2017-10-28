package com.tcc.lucca.scoutup.activitys;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tcc.lucca.scoutup.R;
import com.tcc.lucca.scoutup.backgroundTasks.LoginClass;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity{

    private static final int RC_SIGN_IN = 0;
    private EditText etLogin;
    private EditText etSenha;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private LoginClass loginClass = new LoginClass(this);
    private RadioButton rbEscotista;
    private RadioButton rbEscoteiro;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        rbEscoteiro = (RadioButton) findViewById(R.id.rbEscoteiro);
        rbEscotista = (RadioButton) findViewById(R.id.rbEscotista);

        rbEscoteiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbEscoteiro.setChecked(true);
                rbEscotista.setChecked(false);

            }
        });

        rbEscotista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rbEscoteiro.setChecked(false);
                rbEscotista.setChecked(true);
            }
        });






        loginButton = (LoginButton) findViewById(R.id.btFace);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginClass.firebaseAuthWithFacebook(loginResult.getAccessToken(), rbEscotista.isChecked());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });




        etLogin = (EditText) findViewById(R.id.eTxtUsuario);
        etSenha = (EditText) findViewById(R.id.eTxtSenha);
        SignInButton signInButton = (SignInButton) findViewById(R.id.btGoogle);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.btGoogle);
        setGooglePlusButtonText(signInButton, "Continuar com o Google");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();

            }
        });






    }






    private void signInGoogle() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        DialogFragment dialogFragment = new DialogFragment();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();

                loginClass.firebaseAuthWithGoogle(account, rbEscotista.isChecked());

            }
        } else {

            callbackManager.onActivityResult(requestCode, resultCode, data);


        }
    }


    public void cadastro(View view) {

        Uri uri = Uri.parse("https://scoutup-59cc7.firebaseapp.com/#/cadastro");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);


    }


    public void efetuarLogin(View view) {

        String email = etLogin.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
        } else {

            loginClass.loginCredentials(email, senha);

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        loginClass.getmAuth().addAuthStateListener(loginClass.getmAuthListener());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loginClass.getmAuthListener() != null) {
            loginClass.getmAuth().removeAuthStateListener(loginClass.getmAuthListener());
        }
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    public void esqueceuSenha(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        alert.setMessage("Por favor, insira o seu email");
        alert.setTitle("Recuperar Senha");

        alert.setView(edittext);

        alert.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String YouEditTextValue = edittext.getText().toString();
                Toast.makeText(getApplicationContext(), YouEditTextValue, Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();


    }
}
