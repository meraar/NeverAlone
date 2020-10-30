package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

public class Home_Activity  extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Debug: entra home");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*TODO aqui se tiene que decidir si hay que iniciar sesión o hay que mostrar la pantalla principal del usuario
         *  Y hay que cambiar el manifest para que apunte a esta pantalla PRIMERO*/

    }
}
