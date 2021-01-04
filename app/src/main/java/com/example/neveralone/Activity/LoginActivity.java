package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
        System.out.println(FirebaseAuth.getInstance());
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
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("FirebaseAuth", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Sign in", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Failure", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        String personName = user.getDisplayName();
        String personFamilyName = user.getUid();
        String personEmail = user.getEmail();

    }

    public static boolean getUserType() {
        return voluntario;
    }
}