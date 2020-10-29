package com.example.neveralone.UI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /*TODO he añadido la función que está asociada al botón, cuando le das a cerrar sesión. Está será la pagina de inicio tras iniciar sesión.*/
    public void signOut(View view) {

    }
}
