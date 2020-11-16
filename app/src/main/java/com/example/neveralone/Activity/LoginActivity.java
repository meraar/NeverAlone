package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText txtCorreo, txtContrasena;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            nextActivity();
        }
    }



    private void nextActivity(){
        startActivity(new Intent(LoginActivity.this,Home.class));
        finish();
    }

    public void login(View view) {
        txtCorreo = findViewById(R.id.emailAddressLogin);
        txtContrasena = findViewById(R.id.editTextTextPassword);
        String correo =  txtCorreo.getText().toString();
        String contrasena = txtContrasena.getText().toString();
        boolean comprovacion_correo = correo.isEmpty();
        boolean comprovacion_contrasena = contrasena.isEmpty();
        if(comprovacion_correo && comprovacion_contrasena){
            if (comprovacion_correo){
                txtCorreo.setError("Añade el correo para iniciar sesión");
            }
            if (comprovacion_contrasena){
                txtContrasena.setError("Añade la contraseña para iniciar sesión");
            }
        } else {
            mAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (!mAuth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "El email no esta verificado.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Se ha iniciado sesion correctamente.", Toast.LENGTH_SHORT).show();
                            txtCorreo.setText("");
                            txtContrasena.setText("");
                            startActivity(new Intent(LoginActivity.this, Home.class));
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "El email i/o la contraseña son incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    public void registerWithGoogle(View view) {


    }
}