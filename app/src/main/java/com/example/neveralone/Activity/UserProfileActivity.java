package com.example.neveralone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class UserProfileActivity<InputLayout> extends AppCompatActivity {
    private EditText nombretxt, apellidotxt, dnitxt, codigo_postaltxt, direcciontxt, emailtxt;
    private EditText puntuacion_mediatxt, piso_puertatxt, motivotxt;
    private TextInputLayout first_puntuacion, first_piso_puerta, first_direccion, first_motivo;
    private ImageView profile_image;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nombretxt = findViewById(R.id.Nombre);
        apellidotxt = findViewById(R.id.Apellido);
        dnitxt = findViewById(R.id.dni);
        emailtxt = findViewById(R.id.email);
        codigo_postaltxt = findViewById(R.id.codigo_postal);
        puntuacion_mediatxt = findViewById(R.id.puntuacion_media);
        piso_puertatxt = findViewById(R.id.piso_puerta);
        direcciontxt = findViewById(R.id.direccion);
        motivotxt = findViewById(R.id.motivo);
        //Para controlar la visibilidad de layouts
        first_piso_puerta = findViewById(R.id.firstpiso_puerta);
        first_direccion = findViewById(R.id.firstdireccion);
        first_puntuacion = findViewById(R.id.firstpuntuacion_media);
        first_motivo = findViewById(R.id.firstmotivo);
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
                            dnitxt.setEnabled(false);
                            emailtxt.setText(usuario.getEmail());
                            codigo_postaltxt.setText(usuario.getCodigopostal());

                            if (!usuario.getVoluntario()){
                                first_direccion.setVisibility(View.VISIBLE);
                                first_piso_puerta.setVisibility(View.VISIBLE);
                                first_motivo.setVisibility(View.VISIBLE);

                                motivotxt.setText(usuario.getMotivo());
                                motivotxt.setVisibility(View.VISIBLE);
                                motivotxt.setEnabled(false); // El motivo no se podra actualizar.
                                motivotxt.setVisibility(View.VISIBLE);


                                direcciontxt.setText(usuario.getDireccion());
                                direcciontxt.setVisibility(View.VISIBLE);


                                piso_puertatxt.setText(usuario.getPiso_puerta());
                                piso_puertatxt.setVisibility(View.VISIBLE);

                            }
                            else{
                                first_puntuacion.setVisibility(View.VISIBLE);
                                puntuacion_mediatxt.setText(String.valueOf(usuario.getPuntuacioMedia()));
                                puntuacion_mediatxt.setEnabled(false);
                                puntuacion_mediatxt.setVisibility(View.VISIBLE);

                            }
                        } else {
                            //Log.d("MyApa", "Hay un error");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                    }
                });
    }


    public void UpdateProfile(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
        finish();
    }

}