package com.tcc.lucca.scoutup.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tcc.lucca.scoutup.R;

public class AdicionarAtividadeActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_atividade);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, new MapsFrag(), "Maps Frag");

        fragmentTransaction.commitAllowingStateLoss();



    }
}
