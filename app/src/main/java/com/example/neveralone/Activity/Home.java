package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /*TODO he añadido la función que está asociada al botón, cuando le das a cerrar sesión. Está será la pagina de inicio tras iniciar sesión.*/
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(Home.this, LoginActivity.class));
        finish();

    }
}
