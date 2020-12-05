package com.example.neveralone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.Activity.RegisterBenefactorActivity;
import com.example.neveralone.ChatPeticion;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearChatActivity extends AppCompatActivity {

    private Button btnFinalizarRegistro;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference database2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_chat);
        btnFinalizarRegistro = findViewById(R.id.CrearChat);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        btnFinalizarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idUsuario1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final String idUsuario2 = "0PUqBwfOVMWEbO0lKskG78KrTe83";
                final String idPeticion = "MMMmVvp4TTw3UPIJV07";
                DatabaseReference reference = database.getReference("ChatPeticion/" + idUsuario1 + "/" + idPeticion + "/idUsuario2");
                ChatPeticion cp = new ChatPeticion(idUsuario2);
                //  cp.setidPeticion(idPeticion);
                //  cp.setidUsuario1(idUsuario1);
               // cp.setidUsuario2(idUsuario2);

                reference.setValue(idUsuario2);
                //verficar el mail
                Toast.makeText(CrearChatActivity.this, "Se creado el chat", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CrearChatActivity.this, ListaContactosActivity.class));
                finish();
            }
            });
    }

}