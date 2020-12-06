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

import com.example.neveralone.Activity.Chat.Message;
import com.example.neveralone.Activity.Chat.MessageActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearChatActivity extends AppCompatActivity {

    private Button btnFinalizarRegistro;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference database2;
    private String currentUserName;

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
                String idUsuario2 = "";
                final String idPeticion = "MMMmVvp4TTw3UPIJV07";
                if (idUsuario1.equals("4IS1tZ6IrGbEqE2h6jXR05EeXCj1")) {
                    idUsuario2 = "yyZIf6aLmFYPklgpAtJwkSfNOqV2";
                }
                else if (idUsuario1.equals("yyZIf6aLmFYPklgpAtJwkSfNOqV2")) {
                    idUsuario2 = "4IS1tZ6IrGbEqE2h6jXR05EeXCj1";
                }
                //DatabaseReference reference = database.getReference("ChatPeticion/" + idUsuario1 + "/" + idPeticion + "/" + idUsuario2);
                //reference.push().setValue(new Message(null, "Eric", "", "1", "00:00"));
                ChatPeticion cp = new ChatPeticion(idUsuario2);

                /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios/" + idUsuario1 + "/nombre");
                databaseReference.addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String nombre_captura_2 =
                                currentUserName = dataSnapshot.getValue(String.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });*/

                Toast.makeText(CrearChatActivity.this, "Se creado el chat", Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                b.putString("idUsuario1", idUsuario1);
                b.putString("idUsuario2", idUsuario2);
                b.putString("idPeticion", idPeticion);

                Intent intent = new Intent(CrearChatActivity.this, MessageActivity.class);
                intent.putExtras(b);

                startActivity(intent);
                finish();
            }
        });
    }

}