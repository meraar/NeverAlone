package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterGoogleActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerwithgoogle);
        userName = findViewById(R.id.holaUser);

        sharedPreferencesSingleton.write("LoggedInGoogle", true);
        String nameGoogle = sharedPreferencesSingleton.read("nameGoogle", "");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        userName.setText("Hola " + nameGoogle + ", aún estamos trabajando en esto. ¡Próximamente estará disponible!");
        //System.out.println(FirebaseAuth.getInstance());
    }

    public void cerrarSesion(View view) {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sharedPreferencesSingleton.write("LoggedInGoogle", false);
                startActivity(new Intent(RegisterGoogleActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

}