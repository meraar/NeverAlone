package com.example.neveralone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neveralone.R;
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
    FirebaseUser useer;
    DatabaseReference DataRef;
    String nombre, apellido, codigopostal, direccion, dni, piso_puerta, email;
    Double puntuacion_media;
    Boolean voluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nombretxt = (EditText)findViewById(R.id.Nombre);
        String text = "here put the text that you want";
        nombretxt.setText(text);
        setContentView(R.layout.activity_user_profile);




    }

    private void initialize(){
        apellidotxt = findViewById(R.id.Apellido);
        dnitxt = findViewById(R.id.dni);
        emailtxt = findViewById(R.id.email);
        codigo_postaltxt = findViewById(R.id.codigo_postal);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String id = firebaseUser.getUid();
        DataRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        DataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    nombre = snapshot.child(id).child("nombre").getValue().toString();
                    apellido = snapshot.child(id).child("apellidos").getValue().toString();
                    codigopostal = snapshot.child(id).child("codigopostal").getValue().toString();
                    direccion = snapshot.child(id).child("direccion").getValue().toString();
                    dni = snapshot.child(id).child("dni").getValue().toString();
                    email = snapshot.child(id).child("email").getValue().toString();
                    piso_puerta = snapshot.child(id).child("motivo").getValue().toString();
                    puntuacion_media = snapshot.child(id).child("puntuacionMedia").getValue(Double.class);
                    voluntario = snapshot.child(id).child("voluntario").getValue(Boolean.class);
                }
                else{
                    Log.d("MyApa","ERRRRRRRRRROR");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.i("Nombre", "nombre"+ nombre);
        nombretxt.setText(nombre);
        apellidotxt.setText(apellido);
        dnitxt.setText(dni);
        emailtxt.setText(email);
        codigo_postaltxt.setText(codigopostal);


    }

}