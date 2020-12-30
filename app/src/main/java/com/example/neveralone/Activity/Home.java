package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.neveralone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.mas:
                            selectedFragment = new MasFragment();
                            break;
                        case R.id.peticiones:
                            selectedFragment = new PeticionesFragment();
                            break;
                        case R.id.infocovid:
                            selectedFragment = new InfoCovidFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    /*TODO he añadido la función que está asociada al botón, cuando le das a cerrar sesión. Está será la pagina de inicio tras iniciar sesión.*/
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(Home.this, LoginActivity.class));
        finish();

    }
}
