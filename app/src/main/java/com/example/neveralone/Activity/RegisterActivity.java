package com.example.neveralone.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
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
import com.example.neveralone.Usuario.*;
public class RegisterActivity extends AppCompatActivity {
    //private EditText idRegistroNombre, idRegistroCorreo, idRegistroContrasen, idRegistroContrasenaRepetida;
    private EditText codigo_postal, vulnerabilidad, ciudad_trabajo;
    private RadioButton idRadioButtonBeneficiario, idRadioButtonVoluntario;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText txtNombre, txtCorreo, txtContrasena, txtContrasenaRepetida;
    private EditText txtpostal, txtvulnerabilidad, txtciudad;
    private Button btnRegistrar;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtNombre = findViewById(R.id.idRegistroNombre);
        txtCorreo = findViewById(R.id.idRegistroCorreo);
        //txtApellidos = findViewById(R.id.)
        txtContrasena = findViewById(R.id.idRegistroContrasena);
        txtContrasenaRepetida = findViewById(R.id.idRegistroContrasenaRepetida);
        btnRegistrar = findViewById(R.id.idRegistroRegistrar);
        txtpostal = findViewById(R.id.codigo_postal);
        txtvulnerabilidad = findViewById(R.id.vulnerabilidad);
        txtciudad = findViewById(R.id.ciudad_trabajo);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String correo = txtCorreo.getText().toString();
                final String nombre = txtNombre.getText().toString();
                //final String apellidos = txtApellidos.getTest().toString(); // Este campo falta añadir en front
                if(isValidEmail(correo) && validarContrasena() && validarNombre(nombre)){
                    String contrasena = txtContrasena.getText().toString();
                    mAuth.createUserWithEmailAndPassword(correo, contrasena)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Se registro correctamente", Toast.LENGTH_SHORT).show();
                                        Usuario usuario = new Usuario(correo, nombre, " ");
                                        usuario.setEmail(correo);
                                        //usuario.setApellidos(apellidos);
                                        usuario.setNombre(nombre);
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                        reference.setValue(usuario);
                                        txtNombre.setText("");
                                        txtCorreo.setText("");
                                        txtContrasena.setText("");
                                        txtContrasenaRepetida.setText("");
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Error al registrarse.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }





    private boolean isValidEmail(String email) {
        boolean valid = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(!valid) Toast.makeText(this, "Introduce un correo valido.", Toast.LENGTH_SHORT).show();
        return valid;
    }

    public boolean validarContrasena(){
        String contrasena, contrasenaRepetida;
        contrasena = txtContrasena.getText().toString();
        contrasenaRepetida = txtContrasenaRepetida.getText().toString();
        if(contrasena.equals(contrasenaRepetida)){
            if(contrasena.length()>=6 && contrasena.length() <16){
                return true;
            } else{
                Toast.makeText(this, "La contraseña ha de contener entre 6 y 15 carácteres.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validarNombre(String nombre){
        if(nombre.isEmpty()){
            Toast.makeText(this, "El nombre no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
/**
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }**/



}
