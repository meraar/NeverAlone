package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;



public class Home extends AppCompatActivity {

    private Button btnAutotest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAutotest = findViewById(R.id.buttonAutotestCoronavirus);
        btnAutotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AutotestActivity.class);
                startActivity(intent);
            }
        });
    }

    /*TODO he añadido la función que está asociada al botón, cuando le das a cerrar sesión. Está será la pagina de inicio tras iniciar sesión.*/
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(Home.this, LoginActivity.class));
        finish();

    }
}
