package com.tcc.lucca.scoutup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lucca.scoutup.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (firebaseAuth.getCurrentUser() == null) {

            Intent intent = new Intent(this, LoginActivity.class);


        }
    }


}

