package com.example.neveralone.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import res.layout.*;
import com.example.neveralone.R;
public class RegisterActivity extends AppCompatActivity {
    //private EditText idRegistroNombre, idRegistroCorreo, idRegistroContrasen, idRegistroContrasenaRepetida;
    //private EditText codigo_postal, vulnerabilidad, ciudad_trabajo;
    private RadioButton idRadioButtonBeneficiario, idRadioButtonVoluntario;
    private Button idRegistroRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText txtNombre, txtCorreo, txtContrasena, txtContrasenaRepetida;
    private Button btnRegistrar;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtNombre = findViewById(R.id.idRegistroNombre);
        txtCorreo = findViewById(R.id.idRegistroCorreo);
        txtContrasena = findViewById(R.id.idRegistroContrasena);
        txtContrasenaRepetida = findViewById(R.id.idRegistroContrasenaRepetida);
        btnRegistrar = findViewById(R.id.idRegistroRegistrar);

    }



}
