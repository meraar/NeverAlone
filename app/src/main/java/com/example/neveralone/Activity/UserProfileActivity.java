package com.example.neveralone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    private EditText nombretxt, apellidotxt, dnitxt, codigo_postaltxt, direcciontxt, emailtxt;
    private ImageView profile_image;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String nombre, apellido, codigopostal, dni;
    String  direccion, piso_puerta, email;
    Double puntuacion_media;
    Boolean voluntario;
    Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nombretxt = findViewById(R.id.Nombre);
        apellidotxt = findViewById(R.id.Apellido);
        dnitxt = findViewById(R.id.dni);
        emailtxt = findViewById(R.id.email);
        codigo_postaltxt = findViewById(R.id.codigo_postal);
        initialize();
    }

    private void initialize(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String id = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            usuario = snapshot.getValue(Usuario.class);
                            nombretxt.setText(usuario.getNombre());
                            apellidotxt.setText(usuario.getApellidos());
                            dnitxt.setText(usuario.getDni());
                            emailtxt.setText(usuario.getEmail());
                            codigo_postaltxt.setText(usuario.getCodigopostal());
                            //TODO diferenciar entre voluntario y beneficiario

                        } else {
                            Log.d("MyApa", "ERRRRRRRRRROR");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                    }
                });
    }


    public void cerrarSesion(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
        finish();
    }

}