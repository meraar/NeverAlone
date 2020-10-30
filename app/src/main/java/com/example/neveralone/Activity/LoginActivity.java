package com.example.neveralone.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText txtCorreo, txtContrasena;
    private Button btnLogin, btnRegistro;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("Debug4: entra login");

        txtCorreo = findViewById(R.id.emailAddressLogin);
        txtContrasena = findViewById(R.id.editTextTextPassword);
        btnLogin = findViewById(R.id.button);
        btnRegistro = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo =  txtCorreo.getText().toString();
                if(isValidEmail(correo) && validarContrasena()){
                    String contrasena = txtContrasena.getText().toString();
                    mAuth.signInWithEmailAndPassword(correo, contrasena)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Se ha iniciado sesion correctamente.", Toast.LENGTH_SHORT).show();
                                        txtCorreo.setText("");
                                        txtContrasena.setText("");
                                        startActivity(new Intent(LoginActivity.this,Home_Activity.class));
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Error, credenciales incorrectas.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Error de Log in.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //ESTO FALTA HACER

         btnRegistro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
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
        if(contrasena.length()>=6 && contrasena.length() <16){
            return true;
        } else{
            Toast.makeText(this, "La contraseña ha de contener entre 6 y 15 carácteres.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
    /*
    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "Usuario logeado.", Toast.LENGTH_SHORT).show();
            nextActivity();
        }
    }

    private void nextActivity(){
        System.out.println("Debug4: entra next");
        startActivity(new Intent(LoginActivity.this,Home_Activity.class));
        finish();
    }
}





/**
 public void login(View view) { /*
 Usuario u = new Usuario();
 EditText email = findViewById(R.id.emailAddressLogin);
 EditText pass = findViewById(R.id.passLogin);
 String mail = email.getText().toString();
 if (verifyEmail(mail)) { //TODO falta mirar que tenga un nombre antes de @ y que solo haya 1 @
 u.setEmail(mail);
 u.setPassword(hashSha256(pass.getText().toString()));
 } else {
 //TODO mensaje de error, color en rojo, a parte el toast y en el register, la contraseña es too weak
 Context context = getApplicationContext();
 CharSequence text = "Correo electrónico no válido!";
 int duration = Toast.LENGTH_SHORT;

 Toast toast = Toast.makeText(context, text, duration);
 toast.show();

 }

 }


 public void register(View view) {
 Intent intent = new Intent(this, RegisterActivity.class);
 startActivity(intent);
 }

 public void registerWithGoogle(View view) {

 }

 private boolean verifyEmail(String email) {
 return (email.contains("@hotmail.com") || email.contains("@gmail.com") || email.contains("@hotmail.es") || email.contains("@yahoo.com"));
 }

 private String hashSha256(String passw) {
 try {
 MessageDigest sha256 = MessageDigest.getInstance( "SHA-256" );
 final byte[] data = passw.getBytes();
 return toHexString(sha256.digest(data));
 } catch (NoSuchAlgorithmException e) {
 e.printStackTrace();
 }
 return passw;
 }


 public static String toHexString(byte[] hash)
 {
 // Convert byte array into signum representation
 BigInteger number = new BigInteger(1, hash);

 // Convert message digest into hex value
 StringBuilder hexString = new StringBuilder(number.toString(16));

 // Pad with leading zeros
 while (hexString.length() < 32) {
 hexString.insert(0, '0');
 }
 return hexString.toString();
 }*/



