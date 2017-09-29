package com.tcc.lucca.scoutup.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lucca.scoutup.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.tcc.lucca.scoutup.gerenciar.LoginClass;
import com.tcc.lucca.scoutup.gerenciar.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private LoginClass loginClass = new LoginClass(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (loginClass.getFirebaseUser() == null) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new PerfilFragmentActivity(), "Perfil");
        adapter.addFragment(new AtividadesActivity(), "Atividades");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }


    public void logout(View view) {


        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();

    }


}

