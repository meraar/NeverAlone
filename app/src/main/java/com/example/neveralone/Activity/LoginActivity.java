package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Activity.Peticiones.VerMisPeticiones;
import com.example.neveralone.MapsActivity;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity{
    private static boolean voluntario;
    private EditText txtCorreo, txtContrasena;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    DatabaseReference reference;

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
            /*Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();*/
            nextActivity();
        }
    }

    private void nextActivity(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + userID + "/voluntario");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                voluntario = (boolean) dataSnapshot.getValue();
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void login(View view) {
        txtCorreo = findViewById(R.id.emailAddressLogin);
        txtContrasena = findViewById(R.id.editTextTextPassword);
        String correo =  txtCorreo.getText().toString();
        String contrasena = txtContrasena.getText().toString();
        boolean comprovacion_correo = correo.isEmpty();
        boolean comprovacion_contrasena = contrasena.isEmpty();
        if(comprovacion_correo || comprovacion_contrasena){
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

                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + userID + "/voluntario");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    voluntario = (boolean) dataSnapshot.getValue();
                                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                                }
                                @Override
                                public void onCancelled(DatabaseError error) {
                                }
                            });
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
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void forgetpass(View view){
        startActivity(new Intent(LoginActivity.this,RecoverPasswordActivity.class));
        finish();
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                nextActivity();
            } else {
                Toast.makeText(this, "Login Failed!" ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean getUserType() {
        return voluntario;
    }
}