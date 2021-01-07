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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
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
    private static SharedPreferencesSingleton sharedPreferencesSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        System.out.println(FirebaseAuth.getInstance());
    }


    private void nextActivity(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + userID + "/voluntario");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                voluntario = (boolean) dataSnapshot.getValue();
                sharedPreferencesSingleton.write("voluntario", voluntario);
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
                                    System.out.println("Voluntario: " + voluntario);
                                    sharedPreferencesSingleton.write("voluntario", voluntario);
                                    sharedPreferencesSingleton.write("LoggedInGoogle", false);
                                    System.out.println("eres voluntario??" + sharedPreferencesSingleton.read("voluntario", false));

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
        System.out.println("GSO: " + gso.toString());
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        System.out.println("mGoogleSignInClient: " + mGoogleSignInClient.toString());
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void forgetpass(View view){
        startActivity(new Intent(LoginActivity.this,RecoverPasswordActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //System.out.println("Data: " + result.getSignInAccount().getDisplayName());
            if (result.isSuccess()) {
                //FirebaseUser user = mAuth.getCurrentUser();
                //updateUI(user);
                System.out.println("Yep, si que entra");
                System.out.println("User name: " + result.getSignInAccount().getDisplayName());
                System.out.println("User email: " + result.getSignInAccount().getEmail());
                System.out.println("User uid: " + result.getSignInAccount().getId());

                startActivity(new Intent(LoginActivity.this, RegisterGoogleActivity.class));
            } else {
                Toast.makeText(this, "Login Failed!" ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        String personName = user.getDisplayName();
        String personFamilyName = user.getUid();
        String personEmail = user.getEmail();

    }

    public static boolean getUserType() {
        if (sharedPreferencesSingleton.read("voluntario", false)) return true;
        return false;
        //return voluntario;
    }
}