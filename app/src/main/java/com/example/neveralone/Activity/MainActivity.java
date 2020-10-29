package com.example.neveralone.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

public class MainActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        /*TODO aqui se tiene que decidir si hay que iniciar sesión o hay que mostrar la pantalla principal del usuario
        *  Y hay que cambiar el manifest para que apunte a esta pantalla PRIMERO*/
    }

}